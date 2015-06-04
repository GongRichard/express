package express.dao;

import javax.annotation.PostConstruct;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.Mongo;

import express.entity.ExpressItem;

@Repository
public class ExpressItemDAO {

  @Autowired
  private Datastore datastore;
  
  private ExpressItemBasicDAO basicDAO;

  public ExpressItemBasicDAO getBasicDAO() {
    return basicDAO;
  }

  public ExpressItem saveExpressItem(ExpressItem expressItem) {
    this.datastore.save(expressItem);
    return expressItem;
  }
  
  @PostConstruct
  public void init() {
    this.basicDAO = new ExpressItemBasicDAO(this.datastore);
  }
}
