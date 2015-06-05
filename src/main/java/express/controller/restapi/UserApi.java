package express.controller.restapi;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import express.dao.SequenceDAO;
import express.dao.UserDAO;
import express.entity.User;

@RestController
public class UserApi {

  @Autowired
  UserDAO userDAO;

  @Autowired
  private SequenceDAO sequenceDAO;

  @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
  public List<User> userById(@PathVariable int userId) {
    if (userId == 0)
      return null;
    Query<User> q = this.userDAO.getBasicDAO().createQuery()
        .filter("userId =", userId);
    return this.userDAO.getBasicDAO().find(q).asList();
  }

  @RequestMapping(value = "/user", method = RequestMethod.GET)
  public List<User> user(
      @RequestParam(value = "email", defaultValue = "") String email,
      @RequestParam(value = "mobilePhone", defaultValue = "") String mobilePhone,
      @RequestParam(value = "employeeId", defaultValue = "") String employeeId) {
    return this.userDAO.FindByFields(email, employeeId, mobilePhone);
  }

  @RequestMapping(value = "/user", method = RequestMethod.POST)
  public long userCreate(
      @RequestParam(value = "email", defaultValue = "") String email,
      @RequestParam(value = "mobilePhone", defaultValue = "") String mobilePhone,
      @RequestParam(value = "employeeId", defaultValue = "") String employeeId)
      throws Exception {
    long userId = this.sequenceDAO.getNextSequenceId("user");
    User user = new User(userId, mobilePhone, email, employeeId);
    this.userDAO.getBasicDAO().save(user);
    return user.getUserId();
  }
  
  @RequestMapping(value = "/user", method = RequestMethod.PUT)
  public long userUpdate(
      @RequestParam(value = "userId", defaultValue = "0") String userId,
      @RequestParam(value = "email", defaultValue = "") String email,
      @RequestParam(value = "mobilePhone", defaultValue = "") String mobilePhone,
      @RequestParam(value = "employeeId", defaultValue = "") String employeeId)
      throws Exception {
    if (Long.valueOf(userId) == 0) {
      return 0;
    }
    Query<User> q = this.userDAO.getBasicDAO().createQuery()
        .filter("userId =", userId);
    User user = this.userDAO.getBasicDAO().find(q).get();
    user.setEmail(email);
    user.setEmployeeId(employeeId);
    user.setMobilePhone(mobilePhone);
    this.userDAO.getBasicDAO().save(user);
    return user.getUserId();
  }
}
