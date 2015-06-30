package express.service.hystrix;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import express.dao.SequenceDAO;
import express.dao.UserDAO;
import express.entity.ExpressItem;
import express.entity.SequenceId;
import express.entity.StaffRole;
import express.entity.User;
import express.util.ContextUtil;

public class UserUpsert extends HystrixCommand<Long> {

  private UserDAO userDAO;

  private SequenceDAO sequenceDAO;

  private User user;

  public UserUpsert(UserDAO userDAO, SequenceDAO sequenceDAO, User user) {
    super(HystrixCommandGroupKey.Factory.asKey("UserMgmtGroup"));
    this.userDAO = userDAO;
    this.sequenceDAO = sequenceDAO;
    this.user = user;
  }

  @Override
  protected Long run() throws Exception {
    if (this.user.getUserId() > 0) {
      // update
      Query<User> q = this.userDAO.getBasicDAO().createQuery()
          .filter("userId =", user.getUserId());
      User existingUser = this.userDAO.getBasicDAO().find(q).get();
      existingUser.setEmail(user.getEmail());
      existingUser.setEmployeeId(user.getEmployeeId());
      existingUser.setMobilePhone(user.getMobilePhone());
      existingUser.setStaffRole(user.getStaffRole());
      existingUser.setExpresses(processExpresses(user));
      this.userDAO.getBasicDAO().save(existingUser);
      return user.getUserId();
    } else {
      // create
      long nextUserId = this.sequenceDAO
          .getNextSequenceId(SequenceId.SEQUENCE_USER);
      this.userDAO.getBasicDAO().save(user);
      return user.getUserId();
    }
  }

  private List<ExpressItem> processExpresses(User user) {
    List<ExpressItem> dbExpresses = new ArrayList<ExpressItem>();
    if (user.getExpresses() != null) {
      for (ExpressItem item : user.getExpresses()) {
        ExpressItem dbItem = ContextUtil.EXPRESSITEM_DAO
            .findOneByExpressItemId(item.getExpressItemId());
        dbItem.setBelongUserId(user.getUserId());
        ContextUtil.EXPRESSITEM_DAO.saveExpressItem(dbItem);
        dbExpresses.add(dbItem);
      }
    }
    return dbExpresses;
  }
}
