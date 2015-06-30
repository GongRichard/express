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

@RestController
public class ExpressItemApi {

  @Autowired
  private SequenceDAO sequenceDAO;

  @Autowired
  private ExpressItemDAO expressItemDAO;
  
  @Autowired
  private ExpressNumberPoolService expressNumberPoolSerivce;

  @RequestMapping(value = "/expressItem/{expressItemId}", method = RequestMethod.GET)
  public ExpressItemVO expressItemById(@PathVariable long expressItemId) {
    if (expressItemId == 0)
      return null;
    List<ExpressItem> item = new ExpressItemSearch(expressItemDAO,
        expressItemId, null, null).execute();
    if (item.isEmpty()) {
      return null;
    }
    ExpressItemVO vo = new ExpressItemVO(item.get(0));
    return vo;
  }

  @RequestMapping(value = "/expressItem", method = RequestMethod.GET)
  public List<ExpressItemVO> expressItem(
      @RequestParam(value = "expressNumber", defaultValue = "") String expressNumber,
      @RequestParam(value = "stateEnum", defaultValue = "") ExpressItemStateEnum stateEnum) {
    List<ExpressItem> items = new ExpressItemSearch(expressItemDAO, 0,
        expressNumber, stateEnum).execute();
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
}
