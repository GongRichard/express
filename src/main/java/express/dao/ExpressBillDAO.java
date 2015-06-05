package express.dao;

import javax.annotation.PostConstruct;

import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ExpressBillDAO {

  @Autowired
  private Datastore datastore;

  private ExpressBillBasicDAO basicDAO;

  public ExpressBillBasicDAO getBasicDAO() {
    return basicDAO;
  }

  @PostConstruct
  public void init() {
    this.basicDAO = new ExpressBillBasicDAO(this.datastore);
  }
}
