package express.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import express.dao.ExpressItemDAO;
import express.dao.UserDAO;
import express.entity.ExpressItem;
import express.entity.ExpressItemStateEnum;
import express.entity.User;

@RestController
public class DemoController {

  @Autowired
  UserDAO userDAO;
  
  @Autowired
  ExpressItemDAO expressItemDAO;

  @RequestMapping("/login")
  public String login(@RequestParam("username") String username) {
    return username;
  }

  @RequestMapping("/createUserTest")
  public User createUserTest() {
    User user = new User();
    user.setEmail("zonghan.wu@sap.com");
    user.setEmployeeId("I303152");
    user.setMobilePhone("18616703467");
    return this.userDAO.saveUser(user);
  }

  @RequestMapping("/getUserByEmployeeIdTest")
  public User getUserByEmployeeIdTest() {
    User user = this.userDAO.getUserByEmployeeId("I303152");
    return this.userDAO.saveUser(user);
  }
  
  @RequestMapping("/createItemTest")
  public ExpressItem createItemTest() {
    ExpressItem item = new ExpressItem();
    item.setExpressNumber("1");
    item.setRecievedDate(new Date());
    item.setSccanedDate(new Date());
    item.setStateEnum(ExpressItemStateEnum.SCANNED);
    return this.expressItemDAO.saveExpressItem(item);
  }

}
