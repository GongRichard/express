package express.service.hystrix;

import java.util.List;

import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import express.dao.SequenceDAO;
import express.dao.UserDAO;
import express.entity.SequenceId;
import express.entity.User;
import express.util.ContextUtil;

public class UserSearch extends HystrixCommand<List<User>> {

  private long userId;

  private String email;

  private String mobilePhone;

  private String employeeId;
  
  private String office;

  public UserSearch(long userId, String email, String mobilePhone,
      String employeeId) {
    super(HystrixCommandGroupKey.Factory.asKey("UserMgmtGroup"));
    this.userId = userId;
    this.email = email;
    this.mobilePhone = mobilePhone;
    this.employeeId = employeeId;
  }
  
  public UserSearch(long userId, String email, String mobilePhone,
      String employeeId, String office) {
    super(HystrixCommandGroupKey.Factory.asKey("UserMgmtGroup"));
    this.userId = userId;
    this.email = email;
    this.mobilePhone = mobilePhone;
    this.employeeId = employeeId;
    this.office = office;
  }

  @Override
  protected List<User> run() throws Exception {
    if (this.userId > 0) {
      // get by id
      Query<User> q = ContextUtil.USER_DAO.getBasicDAO().createQuery()
          .filter("userId =", userId);
      return ContextUtil.USER_DAO.getBasicDAO().find(q).asList();
    } else {
      // get by fields or get all
      return ContextUtil.USER_DAO.FindByFields(email, employeeId, mobilePhone,
          office);
    }
  }
}
