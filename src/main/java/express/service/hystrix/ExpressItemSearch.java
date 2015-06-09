package express.service.hystrix;

import java.util.List;

import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import express.dao.ExpressItemDAO;
import express.dao.SequenceDAO;
import express.dao.UserDAO;
import express.entity.ExpressItem;
import express.entity.ExpressItemStateEnum;
import express.entity.User;

public class ExpressItemSearch extends HystrixCommand<List<ExpressItem>> {

  private ExpressItemDAO expressItemDAO;

  private long expressItemId;

  private String expressNumber;

  private ExpressItemStateEnum stateEnum;

  public ExpressItemSearch(ExpressItemDAO expressItemDAO, long expressItemId,
      String expressNumber, ExpressItemStateEnum stateEnum) {
    super(HystrixCommandGroupKey.Factory.asKey("UserMgmtGroup"));
    this.expressItemDAO = expressItemDAO;
    this.expressItemId = expressItemId;
    this.expressNumber = expressNumber;
    this.stateEnum = stateEnum;
  }

  @Override
  protected List<ExpressItem> run() throws Exception {
    if (expressItemId > 0) {
      return this.expressItemDAO.findByExpressItemId(expressItemId);
    } else {
      if (!expressNumber.isEmpty()) {
        return this.expressItemDAO.findByExpressNumber(expressNumber);
      } else if (stateEnum != null) {
        return this.expressItemDAO.findByStateEnum(stateEnum);
      } else {
        return this.expressItemDAO.getBasicDAO().find().asList();
      }
    }
  }
}
