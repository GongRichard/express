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
import express.entity.SequenceId;
import express.entity.User;
import express.service.hystrix.UserUpsert;
import express.service.hystrix.UserSearch;

@RestController
public class UserApi {

  @Autowired
  UserDAO userDAO;

  @Autowired
  private SequenceDAO sequenceDAO;

  @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
  public List<User> userById(@PathVariable long userId) {
    if (userId == 0)
      return null;
    return new UserSearch(userDAO, sequenceDAO, userId, null, null, null)
        .execute();
  }

  @RequestMapping(value = "/user", method = RequestMethod.GET)
  public List<User> user(
      @RequestParam(value = "email", defaultValue = "") String email,
      @RequestParam(value = "mobilePhone", defaultValue = "") String mobilePhone,
      @RequestParam(value = "employeeId", defaultValue = "") String employeeId) {
    return new UserSearch(userDAO, sequenceDAO, 0, email, mobilePhone,
        employeeId).execute();
  }

  @RequestMapping(value = "/user", method = RequestMethod.POST)
  public long userCreate(
      @RequestParam(value = "email", defaultValue = "") String email,
      @RequestParam(value = "mobilePhone", defaultValue = "") String mobilePhone,
      @RequestParam(value = "employeeId", defaultValue = "") String employeeId)
      throws Exception {
    return new UserUpsert(userDAO, sequenceDAO, 0, email, mobilePhone,
        employeeId).execute();
  }

  @RequestMapping(value = "/user", method = RequestMethod.PUT)
  public long userUpdate(
      @RequestParam(value = "userId", defaultValue = "0") long userId,
      @RequestParam(value = "email", defaultValue = "") String email,
      @RequestParam(value = "mobilePhone", defaultValue = "") String mobilePhone,
      @RequestParam(value = "employeeId", defaultValue = "") String employeeId)
      throws Exception {
    if (Long.valueOf(userId) == 0) {
      return 0;
    }
    return new UserUpsert(userDAO, sequenceDAO, userId, email, mobilePhone,
        employeeId).execute();
  }
}
