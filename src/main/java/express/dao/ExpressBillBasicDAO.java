package express.dao;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

import express.entity.ExpressBill;

public class ExpressBillBasicDAO extends BasicDAO<ExpressBill, ObjectId> {

  protected ExpressBillBasicDAO(Datastore ds) {
    super(ds);
  }

}
