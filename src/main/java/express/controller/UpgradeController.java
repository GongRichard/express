package express.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import express.config.SpringMongoConfig;
import express.entity.SequenceId;

@RestController
public class UpgradeController {

  @Autowired
  private Datastore datastore;
  
  private MongoOperations mongoOperation;

  @RequestMapping("/upgrade")
  public String upgrade() {
    this.createSequences();
    return "Success!";
  }
  
  private void createSequences() {
    Query query = new Query();
    List<SequenceId> sequences = this.mongoOperation.find(query, SequenceId.class);
    if (sequences.isEmpty()) {
      SequenceId sequenceUser = new SequenceId(SequenceId.SEQUENCE_USER, 0);
      this.mongoOperation.save(sequenceUser);
      SequenceId sequenceExpressItem = new SequenceId(SequenceId.SEQUENCE_EXPRESS_ITEM, 0);
      this.mongoOperation.save(sequenceExpressItem);
    }
    
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
