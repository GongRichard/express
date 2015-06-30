package express.dao;

import java.net.UnknownHostException;

import javax.annotation.PostConstruct;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClient;

import express.config.SpringMongoConfig;
import express.entity.ExpressNumberPool;

@Repository
public class DataStoreFactory {

  private static Datastore datastore;

  @PostConstruct
  void init() throws UnknownHostException {
    datastore = new Morphia().mapPackage("express.entity").createDatastore(
        new MongoClient(), SpringMongoConfig.DB);
    initializeExpressNumberPool();
  }

  @Bean
  public Datastore datastore() {
    return datastore;
  }
  
  private void initializeExpressNumberPool() {
    ExpressNumberPool pool = this.datastore.find(ExpressNumberPool.class).get();
    if (pool == null) {
      this.datastore.save(new ExpressNumberPool());
    }
  }
}
