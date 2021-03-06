package express.dao;

import java.util.List;

import javax.annotation.PostConstruct;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.Mongo;

import express.entity.ExpressItem;
import express.entity.ExpressItemStateEnum;

@Repository
public class ExpressItemDAO {

  @Autowired
  private Datastore datastore;

  private ExpressItemBasicDAO basicDAO;

  public ExpressItemBasicDAO getBasicDAO() {
    return basicDAO;
  }

  public ExpressItem saveExpressItem(ExpressItem expressItem) {
    this.datastore.save(expressItem);
    return expressItem;
  }

  public List<ExpressItem> findByExpressNumber(String expressNumber) {
    return this.datastore.find(ExpressItem.class, "expressNumber",
        expressNumber).asList();
  }

  public List<ExpressItem> findByStateEnum(ExpressItemStateEnum stateEnum) {
    int state = stateEnum.getFlag();
    return this.datastore.find(ExpressItem.class, "state", state).asList();
  }

  public List<ExpressItem> findByExpressItemId(long expressItemId) {
    Query<ExpressItem> q = this.getBasicDAO().createQuery()
        .filter("expressItemId =", expressItemId);
    return this.getBasicDAO().find(q).asList();
  }
  
  public ExpressItem findOneByExpressItemId(long expressItemId) {
    Query<ExpressItem> q = this.getBasicDAO().createQuery()
        .filter("expressItemId =", expressItemId);
    return this.getBasicDAO().find(q).get();
  }
  
  public List<ExpressItem> findByOrderNumber(String orderNumber) {
    return this.datastore.find(ExpressItem.class, "expressBill.orderNumber",
        orderNumber).asList();
  }

  @PostConstruct
  public void init() {
    this.basicDAO = new ExpressItemBasicDAO(this.datastore);
  }
}
