package express.dao;

import java.util.List;

import javax.annotation.PostConstruct;

import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import express.entity.ExpressBill;

@Repository
public class ExpressBillDAO {

  @Autowired
  private Datastore datastore;

  private ExpressBillBasicDAO basicDAO;

  public ExpressBillBasicDAO getBasicDAO() {
    return basicDAO;
  }
  
  public List<ExpressBill> findByExpressBillId(long expressBillId) {
    return this.datastore.find(ExpressBill.class, "expressBillId",
        expressBillId).asList();
  }

  @PostConstruct
  public void init() {
    this.basicDAO = new ExpressBillBasicDAO(this.datastore);
  }
}
