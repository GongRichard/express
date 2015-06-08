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

public class UserSearch extends HystrixCommand<List<User>> {

  private UserDAO userDAO;

  private SequenceDAO sequenceDAO;

  private long userId;

  private String email;

  private String mobilePhone;

  private String employeeId;

  public UserSearch(UserDAO userDAO, SequenceDAO sequenceDAO, long userId,
      String email, String mobilePhone, String employeeId) {
    super(HystrixCommandGroupKey.Factory.asKey("UserMgmtGroup"));
    this.userDAO = userDAO;
    this.sequenceDAO = sequenceDAO;
    this.userId = userId;
    this.email = email;
    this.mobilePhone = mobilePhone;
    this.employeeId = employeeId;
  }

  @Override
  protected List<User> run() throws Exception {
    if (this.userId > 0) {
      // get by id
      Query<User> q = this.userDAO.getBasicDAO().createQuery()
          .filter("userId =", userId);
      return this.userDAO.getBasicDAO().find(q).asList();
    } else {
      // get by fields or get all
      return this.userDAO.FindByFields(email, employeeId, mobilePhone);
    }
  }
}
