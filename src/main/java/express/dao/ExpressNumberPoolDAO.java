package express.dao;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

import express.entity.ExpressNumberPool;
import express.entity.User;

public class ExpressNumberPoolDAO extends BasicDAO<ExpressNumberPool, ObjectId> {

  public ExpressNumberPoolDAO(Datastore ds) {
    super(ds);
  }

}
