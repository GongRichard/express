package express.dao;

import javax.annotation.PostConstruct;

import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import express.config.SpringMongoConfig;
import express.entity.SequenceId;

@Repository
public class SequenceDAO {

  private MongoOperations mongoOperation;

  @Autowired
  private Datastore datastore;

  public long getNextSequenceId(String key) throws Exception {

    // get sequence id
    Query query = new Query(Criteria.where("_id").is(key));

    // increase sequence id by 1
    Update update = new Update();
    update.inc("seq", 1);

    // return new increased id
    FindAndModifyOptions options = new FindAndModifyOptions();
    options.returnNew(true);

    // this is the magic happened.
    SequenceId seqId = mongoOperation.findAndModify(query, update, options,
        SequenceId.class);
    // if no id, throws SequenceException
    // optional, just a way to tell user when the sequence id is failed to
    // generate.
    if (seqId == null) {
      throw new Exception("Unable to get sequence id for key : " + key);
    }
    return seqId.getSeq();
  }

  @PostConstruct
  public void init() {
    if (this.mongoOperation == null) {
      @SuppressWarnings("resource")
      ApplicationContext ctx = new AnnotationConfigApplicationContext(
          SpringMongoConfig.class);
      this.mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
    }
  }

}
