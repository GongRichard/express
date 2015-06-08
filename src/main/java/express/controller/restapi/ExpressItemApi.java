package express.controller.restapi;

import java.util.Date;
import java.util.List;

import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import express.dao.ExpressBillDAO;
import express.dao.ExpressItemDAO;
import express.dao.SequenceDAO;
import express.dao.UserDAO;
import express.entity.ExpressBill;
import express.entity.ExpressItem;
import express.entity.ExpressItemStateEnum;
import express.entity.SequenceId;
import express.entity.User;

@RestController
public class ExpressItemApi {

  @Autowired
  private UserDAO userDAO;

  @Autowired
  private SequenceDAO sequenceDAO;

  @Autowired
  private ExpressItemDAO expressItemDAO;

  @Autowired
  private ExpressBillDAO expressBillDAO;

  @RequestMapping(value = "/expressItem/{expressItemId}", method = RequestMethod.GET)
  public List<ExpressItem> expressItemById(@PathVariable long expressItemId) {
    if (expressItemId == 0)
      return null;
    Query<ExpressItem> q = this.expressItemDAO.getBasicDAO().createQuery()
        .filter("expressItemId =", expressItemId);
    return this.expressItemDAO.getBasicDAO().find(q).asList();
  }

  @RequestMapping(value = "/expressItem", method = RequestMethod.GET)
  public List<ExpressItem> expressItem(
      @RequestParam(value = "expressNumber", defaultValue = "") String expressNumber,
      @RequestParam(value = "stateEnum", defaultValue = "") ExpressItemStateEnum stateEnum) {
    if (!expressNumber.isEmpty()) {
      return this.expressItemDAO.findByExpressNumber(expressNumber);
    } else if (stateEnum != null) {
      return this.expressItemDAO.findByStateEnum(stateEnum);
    } else {
      return this.expressItemDAO.getBasicDAO().find().asList();
    }
  }

  @RequestMapping(value = "/expressItem", method = RequestMethod.POST)
  public long expressItemCreate(
      @RequestParam(value = "stateEnum", defaultValue = "") ExpressItemStateEnum stateEnum,
      @RequestParam(value = "expressNumber", defaultValue = "") String expressNumber,
      @RequestParam(value = "sccanedDateLong", defaultValue = "") long sccanedDateLong,
      @RequestParam(value = "orderNumber", defaultValue = "") String orderNumber,
      @RequestParam(value = "mobilePhone", defaultValue = "") String mobilePhone)
      throws Exception {
    long expressBillId = this.sequenceDAO
        .getNextSequenceId(SequenceId.SEQUENCE_EXPRESS_BILL);
    ExpressBill bill = new ExpressBill(orderNumber, mobilePhone);
    this.expressBillDAO.getBasicDAO().save(bill);
    long expressItemId = this.sequenceDAO
        .getNextSequenceId(SequenceId.SEQUENCE_EXPRESS_ITEM);
    ExpressItem item = new ExpressItem(stateEnum, bill, expressNumber,
        new Date(sccanedDateLong));
    this.expressItemDAO.getBasicDAO().save(item);
    return item.getExpressItemId();
  }

  @RequestMapping(value = "/expressItem", method = RequestMethod.PUT)
  public long expressItemUpdate(
      @RequestParam(value = "expressItemId", defaultValue = "") long expressItemId,
      @RequestParam(value = "stateEnum", defaultValue = "") ExpressItemStateEnum stateEnum,
      @RequestParam(value = "expressNumber", defaultValue = "") String expressNumber,
      @RequestParam(value = "sccanedDateLong", defaultValue = "") long sccanedDateLong,
      @RequestParam(value = "sccanedDateLong", defaultValue = "") long recievedDateLong,
      @RequestParam(value = "orderNumber", defaultValue = "") String orderNumber,
      @RequestParam(value = "mobilePhone", defaultValue = "") String mobilePhone)
      throws Exception {
    ExpressItem item = this.expressItemById(expressItemId).get(0);
    item.setStateEnum(stateEnum);
    item.setExpressNumber(expressNumber);
    item.setSccanedDate(new Date(sccanedDateLong));
    item.setRecievedDate(new Date(recievedDateLong));
    ExpressBill bill = this.expressBillDAO.findByExpressBillId(
        item.getExpressBill().getExpressBillId()).get(0);
    bill.setMobilePhone(mobilePhone);
    bill.setOrderNumber(orderNumber);
    this.expressBillDAO.getBasicDAO().save(bill);
    this.expressItemDAO.getBasicDAO().save(item);
    return item.getExpressItemId();
  }
}
