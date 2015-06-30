package express.controller.restapi;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import express.dao.SequenceDAO;
import express.dao.UserDAO;
import express.entity.SequenceId;
import express.entity.User;
import express.entity.vo.UserVO;
import express.service.hystrix.UserUpsert;
import express.service.hystrix.UserSearch;

@RestController
public class UserApi {

  @Autowired
  UserDAO userDAO;

  @Autowired
  private SequenceDAO sequenceDAO;

  @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
  public UserVO userById(@PathVariable long userId) {
    if (userId == 0)
      return null;
    List<User> users = new UserSearch(userId, null, null, null).execute();
    if (users.isEmpty()) {
      return null;
    }
    UserVO vo = new UserVO(users.get(0));
    return vo;
  }

  @RequestMapping(value = "/user", method = RequestMethod.GET)
  public List<UserVO> user(
      @RequestParam(value = "email", defaultValue = "") String email,
      @RequestParam(value = "mobilePhone", defaultValue = "") String mobilePhone,
      @RequestParam(value = "employeeId", defaultValue = "") String employeeId) {
    List<User> users = new UserSearch(0, email, mobilePhone, employeeId)
        .execute();
    List<UserVO> userVOs = new ArrayList<UserVO>();
    for (User user : users) {
      UserVO vo = new UserVO(user);
      userVOs.add(vo);
    }
    return userVOs;
  }

  @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public long userCreate(@RequestBody UserVO userVO) throws Exception {
    User user = new User(userVO);
    List<User> sameEmailUser = new UserSearch(0, user.getEmail(), "", "")
        .execute();
    if (!sameEmailUser.isEmpty()) {
      return sameEmailUser.get(0).getUserId();
    }
    return new UserUpsert(user).execute();
  }

  @RequestMapping(value = "/user/{userId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
  public long userUpdate(@PathVariable long userId, @RequestBody UserVO userVO)
      throws Exception {
    User user = new User(userVO);
    if (Long.valueOf(userId) == 0) {
      return 0;
    } else {
      user.setUserId(userId);
    }
    return new UserUpsert(user).execute();
  }
}
