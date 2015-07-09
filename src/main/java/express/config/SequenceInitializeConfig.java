package express.config;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;

import express.entity.SequenceId;

@Configuration
public class SequenceInitializeConfig {

  private MongoOperations mongoOperation;

  @PostConstruct
  public void init() {
    if (this.mongoOperation == null) {
      @SuppressWarnings("resource")
      ApplicationContext ctx = new AnnotationConfigApplicationContext(
          SpringMongoConfig.class);
      this.mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
    }
    this.createSequences();
  }

  private void createSequences() {
    Query query = new Query();
    List<SequenceId> sequences = this.mongoOperation.find(query,
        SequenceId.class);
    if (sequences.isEmpty()) {
      SequenceId sequenceUser = new SequenceId(SequenceId.SEQUENCE_USER, 0);
      this.mongoOperation.save(sequenceUser);
      SequenceId sequenceExpressItem = new SequenceId(
          SequenceId.SEQUENCE_EXPRESS_ITEM, 0);
      this.mongoOperation.save(sequenceExpressItem);
    }
  }
}
