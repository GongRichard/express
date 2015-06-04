package express.dao;

import java.util.List;

import javax.annotation.PostConstruct;

import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import express.entity.User;

@Repository
public class UserDAO {

  @Autowired
  private Datastore datastore;

  private UserBasicDAO basicDAO;

  public UserBasicDAO getBasicDAO() {
    return basicDAO;
  }

  public User saveUser(User user) {
    this.datastore.save(user);
    return user;
  }

  public List<User> getAllUsers() {
    return datastore.find(User.class).asList();
  }

  public List<User> getUserByMobilePhone(String mobilePhone) {
    return datastore.find(User.class, "mobilePhone", mobilePhone).asList();
  }

  public User getUserByEmail(String email) {
    return datastore.find(User.class, "email", email).get();
  }

  public User getUserByEmployeeId(String employeeId) {
    return datastore.find(User.class, "employeeId", employeeId).get();
  }

  @PostConstruct
  public void init() {
    this.basicDAO = new UserBasicDAO(this.datastore);
  }
}
