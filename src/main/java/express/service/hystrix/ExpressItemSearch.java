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
import express.util.ContextUtil;

public class ExpressItemSearch extends HystrixCommand<List<ExpressItem>> {

  private long expressItemId;

  private String expressNumber;

  private String orderNumber;

  private ExpressItemStateEnum stateEnum;

  public ExpressItemSearch(long expressItemId, String expressNumber,
      ExpressItemStateEnum stateEnum, String orderNumber) {
    super(HystrixCommandGroupKey.Factory.asKey("UserMgmtGroup"));
    this.expressItemId = expressItemId;
    this.expressNumber = expressNumber;
    this.stateEnum = stateEnum;
    this.orderNumber = orderNumber;
  }

  @Override
  protected List<ExpressItem> run() throws Exception {
    if (expressItemId > 0) {
      return ContextUtil.EXPRESSITEM_DAO.findByExpressItemId(expressItemId);
    } else {
      if (!expressNumber.isEmpty()) {
        return ContextUtil.EXPRESSITEM_DAO.findByExpressNumber(expressNumber);
      } else if (!orderNumber.isEmpty()) {
        return ContextUtil.EXPRESSITEM_DAO.findByOrderNumber(orderNumber);
      } else if (stateEnum != null) {
        return ContextUtil.EXPRESSITEM_DAO.findByStateEnum(stateEnum);
      } else {
        return ContextUtil.EXPRESSITEM_DAO.getBasicDAO().find().asList();
      }
    }
  }
}
