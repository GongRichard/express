package express.dao;

import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import express.entity.ExpressItem;

@Repository
public class ExpressItemDAO {

  @Autowired
  private Datastore datastore;

  public ExpressItem saveExpressItem(ExpressItem expressItem) {
    this.datastore.save(expressItem);
    return expressItem;
  }
}
