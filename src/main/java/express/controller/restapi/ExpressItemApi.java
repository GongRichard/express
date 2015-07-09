package express.controller.restapi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import express.dao.ExpressItemDAO;
import express.dao.SequenceDAO;
import express.dao.UserDAO;
import express.entity.ExpressBill;
import express.entity.ExpressItem;
import express.entity.ExpressItemStateEnum;
import express.entity.SequenceId;
import express.entity.User;
import express.entity.vo.ExpressItemVO;
import express.entity.vo.UserVO;
import express.service.ExpressNumberPoolService;
import express.service.hystrix.ExpressItemSearch;
import express.service.hystrix.UserSearch;
import express.service.hystrix.UserUpsert;

@RestController
public class ExpressItemApi {

  @Autowired
  private SequenceDAO sequenceDAO;

  @Autowired
  private UserDAO userDAO;

  @Autowired
  private ExpressItemDAO expressItemDAO;

  @Autowired
  private ExpressNumberPoolService expressNumberPoolSerivce;

  @RequestMapping(value = "/expressItem/{expressItemId}", method = RequestMethod.GET)
  public ExpressItemVO expressItemById(@PathVariable long expressItemId) {
    if (expressItemId == 0)
      return null;
    List<ExpressItem> item = new ExpressItemSearch(expressItemId, null, null,
        null).execute();
    if (item.isEmpty()) {
      return null;
    }
    ExpressItemVO vo = new ExpressItemVO(item.get(0));
    return vo;
  }

  @RequestMapping(value = "/expressItem", method = RequestMethod.GET)
  public List<ExpressItemVO> expressItem(
      @RequestParam(value = "orderNumber", defaultValue = "") String orderNumber,
      @RequestParam(value = "expressNumber", defaultValue = "") String expressNumber,
      @RequestParam(value = "stateEnum", defaultValue = "") ExpressItemStateEnum stateEnum) {
    List<ExpressItem> items = new ExpressItemSearch(0, expressNumber,
        stateEnum, orderNumber).execute();
    List<ExpressItemVO> itemVOs = new ArrayList<ExpressItemVO>();
    for (ExpressItem item : items) {
      itemVOs.add(new ExpressItemVO(item));
    }
    return itemVOs;
  }

  @RequestMapping(value = "/expressItem", method = RequestMethod.POST)
  public long expressItemCreate(@RequestBody ExpressItemVO expressItemVO)
      throws Exception {
    ExpressItem item = new ExpressItem(expressItemVO);
    long expressItemId = this.sequenceDAO
        .getNextSequenceId(SequenceId.SEQUENCE_EXPRESS_ITEM);
    item.setExpressItemId(expressItemId);
    item.setExpressNumber(expressNumberPoolSerivce
        .pullAnAvaliableExpressNumber().toString());
    this.expressItemDAO.getBasicDAO().save(item);
    return item.getExpressItemId();
  }

  @RequestMapping(value = "/expressItem/{expressItemId}", method = RequestMethod.PUT)
  public long expressItemUpdate(@PathVariable long expressItemId,
      @RequestBody ExpressItemVO expressItemVO) throws Exception {
    ExpressItem freshItem = new ExpressItem(expressItemVO);
    List<ExpressItem> existingItem = this.expressItemDAO
        .findByExpressItemId(expressItemId);
    if (existingItem.isEmpty()) {
      return 0;
    } else {
      freshItem.setId(existingItem.get(0).getId());
    }
    this.expressItemDAO.getBasicDAO().save(freshItem);
    return expressItemId;
  }
  
  @RequestMapping(value = "/expressItem/command/{command}", method = RequestMethod.PUT)
  public String expressItemCommand(@PathVariable String command,
      @RequestParam(value = "userId", defaultValue = "0") long userId,
      @RequestParam(value = "delegateUserId", defaultValue = "0") long delegateUserId,
      @RequestParam(value = "expressItemId", defaultValue = "0") long expressItemId) {
    CommandEnum commandEnum = CommandEnum.getByCommand(command);
    if (commandEnum == CommandEnum.DELEGATE) {
      Date recevedDate = new Date();
      ExpressItem item = new ExpressItemSearch(expressItemId, null, null,
          null).execute().get(0);
      item.setDelegateUserId(delegateUserId);
      item.setRecievedDate(recevedDate);
      item.setStateEnum(ExpressItemStateEnum.RECIEVED);
      this.expressItemDAO.getBasicDAO().save(item);
    } else if (commandEnum == CommandEnum.SCAN) {
      User user = new UserSearch(userId, null, null, null).execute().get(0);
      ExpressItem item = new ExpressItemSearch(expressItemId, null, null, null)
          .execute().get(0);
      item.setBelongUserId(userId);
      item.setStateEnum(ExpressItemStateEnum.SCANNED);
      this.expressItemDAO.getBasicDAO().save(item);
      user.getExpresses().add(item);
      new UserUpsert(user).execute();
    } else if (commandEnum == CommandEnum.RECEIVE) {
      User user = new UserSearch(userId, null, null, null).execute().get(0);
      ExpressItem item = new ExpressItemSearch(expressItemId, null, null, null)
          .execute().get(0);
      item.setStateEnum(ExpressItemStateEnum.RECIEVED);
      this.expressItemDAO.getBasicDAO().save(item);
      user.getExpresses().add(item);
      new UserUpsert(user).execute();
    }   
    return "success!";
  }
  
  private enum CommandEnum {
    NONE(""), DELEGATE("delegate"), SCAN("scan"), RECEIVE("receive");

    private String command;

    CommandEnum(String command) {
      this.command = command;
    }

    public String getCommand() {
      return this.command;
    }

    public static CommandEnum getByCommand(String command) {
      for (CommandEnum commandEnum : CommandEnum.values()) {
        if (commandEnum.getCommand().equals(command)) {
          return commandEnum;
        }
      }
      return CommandEnum.NONE;
    }
  }
}
