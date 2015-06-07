package express.dao;

import java.net.UnknownHostException;

import javax.annotation.PostConstruct;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClient;

import express.config.SpringMongoConfig;

@Repository
public class DataStoreFactory {

  private static Datastore datastore;

  @PostConstruct
  void init() throws UnknownHostException {
    datastore = new Morphia().mapPackage("express.entity").createDatastore(
        new MongoClient(), SpringMongoConfig.DB);
  }

  @Bean
  public Datastore datastore() {
    return datastore;
  }
}
