package express.dao;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

import express.entity.ExpressItem;

public class ExpressItemBasicDAO extends BasicDAO<ExpressItem, ObjectId> {

  protected ExpressItemBasicDAO(Datastore ds) {
    super(ds);
  }

}
