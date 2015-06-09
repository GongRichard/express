package express.controller;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import express.controller.restapi.UserApi;
import express.dao.ExpressBillDAO;
import express.dao.ExpressItemDAO;
import express.dao.UserDAO;
import express.entity.ExpressBill;
import express.entity.ExpressItem;
import express.entity.ExpressItemStateEnum;
import express.entity.User;

@RestController
public class DemoController {

  @Autowired
  UserDAO userDAO;

  @Autowired
  UserApi userApi;

  @Autowired
  ExpressItemDAO expressItemDAO;

  @RequestMapping("/login")
  public String login(@RequestParam("username") String username) {
    return username;
  }

  @RequestMapping("/createUserTest")
  public User createUserTest() throws Exception {
    User user = new User();
    user.setEmail("zonghan.wu@sap.com");
    user.setEmployeeId("I303152");
    user.setMobilePhone("18616703467");
    long userId = this.userApi.userCreate(user.getEmail(),
        user.getMobilePhone(), user.getEmployeeId());
    user.setUserId(userId);
    return user;
  }

  @RequestMapping("/getUserByEmployeeIdTest")
  public User getUserByEmployeeIdTest() {
    User user = this.userDAO.getUserByEmployeeId("I303152");
    return this.userDAO.saveUser(user);
  }

  @RequestMapping("/createItemTest")
  public ExpressItem createItemTest() {
    ExpressBill bill = new ExpressBill();
    bill.setMobilePhone("18616703467");
    bill.setOrderNumber("1221323");
    ExpressItem item = new ExpressItem();
    item.setExpressNumber("1");
    item.setRecievedDate(new Date());
    item.setSccanedDate(new Date());
    item.setStateEnum(ExpressItemStateEnum.SCANNED);
    item.setExpressBill(bill);
    Key<ExpressItem> result = this.expressItemDAO.getBasicDAO().save(item);
    return item;
  }

}
