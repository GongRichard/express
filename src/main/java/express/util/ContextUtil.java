package express.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import express.dao.ExpressItemDAO;
import express.dao.SequenceDAO;
import express.dao.UserDAO;

@Component
public class ContextUtil {

  @Autowired
  private UserDAO userDAO;
  @Autowired
  private SequenceDAO sequenceDAO;
  @Autowired
  private ExpressItemDAO expressItemDAO;

  public static UserDAO USER_DAO;
  public static SequenceDAO SEQUENCE_DAO;
  public static ExpressItemDAO EXPRESSITEM_DAO;

  @PostConstruct
  void init() {
    USER_DAO = this.userDAO;
    SEQUENCE_DAO = this.sequenceDAO;
    EXPRESSITEM_DAO = this.expressItemDAO;
  }
}
