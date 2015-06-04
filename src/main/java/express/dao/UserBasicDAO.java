package express.dao;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

import express.entity.User;

public class UserBasicDAO extends BasicDAO<User, ObjectId> {

  protected UserBasicDAO(Datastore ds) {
    super(ds);
  }

}
