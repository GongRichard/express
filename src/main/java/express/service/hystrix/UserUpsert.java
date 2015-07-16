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

  private User user;

  public UserUpsert(User user) {
    super(HystrixCommandGroupKey.Factory.asKey("UserMgmtGroup"));
    this.user = user;
  }

  @Override
  protected Long run() throws Exception {
    if (this.user.getUserId() > 0) {
      // update
      Query<User> q = ContextUtil.USER_DAO.getBasicDAO().createQuery()
          .filter("userId =", user.getUserId());
      User existingUser = ContextUtil.USER_DAO.getBasicDAO().find(q).get();
      existingUser.setEmail(user.getEmail());
      existingUser.setEmployeeId(user.getEmployeeId());
      existingUser.setMobilePhone(user.getMobilePhone());
      existingUser.setStaffRole(user.getStaffRole());
      existingUser.setExpresses(processExpresses(user));
      ContextUtil.USER_DAO.getBasicDAO().save(existingUser);
      return user.getUserId();
    } else {
      // create
      long nextUserId = ContextUtil.SEQUENCE_DAO
          .getNextSequenceId(SequenceId.SEQUENCE_USER);
      user.setUserId(nextUserId);
      ContextUtil.USER_DAO.getBasicDAO().save(user);
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
