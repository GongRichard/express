package express.service.hystrix;

import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import express.dao.SequenceDAO;
import express.dao.UserDAO;
import express.entity.SequenceId;
import express.entity.StaffRole;
import express.entity.User;

public class UserUpsert extends HystrixCommand<Long> {

  private UserDAO userDAO;

  private SequenceDAO sequenceDAO;

  private long userId;

  private String email;

  private String mobilePhone;

  private String employeeId;
  
  private boolean staffRole;

  public UserUpsert(UserDAO userDAO, SequenceDAO sequenceDAO, long userId,
      String email, String mobilePhone, String employeeId, boolean staffRole) {
    super(HystrixCommandGroupKey.Factory.asKey("UserMgmtGroup"));
    this.userDAO = userDAO;
    this.sequenceDAO = sequenceDAO;
    this.userId = userId;
    this.email = email;
    this.mobilePhone = mobilePhone;
    this.employeeId = employeeId;
    this.staffRole = staffRole;
  }

  @Override
  protected Long run() throws Exception {
    StaffRole staff = null;
    if (this.staffRole) {
      staff = new StaffRole();
    }
    if (this.userId <= 0) {
      // insert
      long nextUserId = this.sequenceDAO
          .getNextSequenceId(SequenceId.SEQUENCE_USER);
      User user = new User(nextUserId, mobilePhone, email, employeeId, staff);
      this.userDAO.getBasicDAO().save(user);
      return user.getUserId();
    } else {
      // update
      Query<User> q = this.userDAO.getBasicDAO().createQuery()
          .filter("userId =", userId);
      User user = this.userDAO.getBasicDAO().find(q).get();
      user.setEmail(email);
      user.setEmployeeId(employeeId);
      user.setMobilePhone(mobilePhone);
      user.setStaffRole(staff);
      this.userDAO.getBasicDAO().save(user);
      return user.getUserId();
    }
  }
}
