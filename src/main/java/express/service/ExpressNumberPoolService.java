package express.service;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.annotation.PostConstruct;

import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import express.dao.ExpressNumberPoolDAO;
import express.dao.UserBasicDAO;
import express.entity.ExpressNumberPool;

@Service
public class ExpressNumberPoolService {

  @Autowired
  private Datastore datastore;

  private ExpressNumberPoolDAO basicDAO;

  // get the minimum number to use
  public Integer pullAnAvaliableExpressNumber() {
    int target = 1;
    while (target < 99999) {
      if (inUse(target)) {
        target++;
        continue;
      } else {
        addToPool(target);
        return target;
      }
    }
    return 0;
  }

  public void ErasureExpressNumber(int number) {
    ExpressNumberPool pool = this.getPool();
    pool.getInUseExpressNumbers().remove(number);
    this.basicDAO.save(pool);
  }

  private boolean inUse(Integer target) {
    ExpressNumberPool pool = this.getPool();
    return pool.getInUseExpressNumbers().contains(target);
  }

  private ExpressNumberPool getPool() {
    return this.basicDAO.find().get();
  }

  private void addToPool(Integer target) {
    ExpressNumberPool pool = this.getPool();
    pool.getInUseExpressNumbers().add(target);
    this.basicDAO.save(pool);
  }

  @PostConstruct
  public void init() {
    this.basicDAO = new ExpressNumberPoolDAO(this.datastore);
  }
}
