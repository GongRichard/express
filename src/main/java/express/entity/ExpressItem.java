package express.entity;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.annotations.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import express.entity.vo.ExpressItemVO;

@Entity
public class ExpressItem {

  @Id
  ObjectId id;
  
  private long expressItemId;

  /** for DB storage */
  private int state = 0;

  private ExpressBill expressBill;

  private String expressNumber;

  private Date sccanedDate;

  private Date recievedDate;
  
  private long belongUserId;

  public ExpressItem() {
  }

  public ExpressItem(ExpressItemStateEnum stateEnum, ExpressBill expressBill,
      String expressNumber, Date sccanedDate) {
    super();
    this.state = stateEnum.getFlag();
    this.expressBill = expressBill;
    this.expressNumber = expressNumber;
    this.sccanedDate = sccanedDate;
  }
  
  public ExpressItem(ExpressItemStateEnum stateEnum, ExpressBill expressBill,
      String expressNumber, Date sccanedDate, Date recievedDate) {
    super();
    this.state = stateEnum.getFlag();
    this.expressBill = expressBill;
    this.expressNumber = expressNumber;
    this.sccanedDate = sccanedDate;
    this.recievedDate = recievedDate;
  }
  
  public ExpressItem(ExpressItemVO vo) {
    this.setStateEnum(vo.getStateEnum());
    this.expressBill = new ExpressBill(vo.getExpressBill());
    this.expressNumber = vo.getExpressNumber();
    this.sccanedDate = new Date(vo.getSccanedDate());
    this.recievedDate = new Date(vo.getRecievedDate());
    this.expressItemId = vo.getExpressItemId();
  }

  public ObjectId getId() {
    return id;
  }

  public void setId(ObjectId id) {
    this.id = id;
  }

  public long getExpressItemId() {
    return expressItemId;
  }

  public void setExpressItemId(long expressItemId) {
    this.expressItemId = expressItemId;
  }

  public int getState() {
    return state;
  }

  public ExpressItemStateEnum getStateEnum() {
    return ExpressItemStateEnum.getByFlag(state);
  }

  public void setStateEnum(ExpressItemStateEnum stateEnum) {
    this.state = stateEnum.getFlag();
  }

  public ExpressBill getExpressBill() {
    return expressBill;
  }

  public void setExpressBill(ExpressBill expressBill) {
    this.expressBill = expressBill;
  }

  public String getExpressNumber() {
    return expressNumber;
  }

  public void setExpressNumber(String expressNumber) {
    this.expressNumber = expressNumber;
  }

  public Date getSccanedDate() {
    return sccanedDate;
  }

  public void setSccanedDate(Date sccanedDate) {
    this.sccanedDate = sccanedDate;
  }

  public Date getRecievedDate() {
    return recievedDate;
  }

  public void setRecievedDate(Date recievedDate) {
    this.recievedDate = recievedDate;
  }

  public long getSccanedDateLong() {
    if (this.sccanedDate == null) {
      return 0;
    }
    return sccanedDate.getTime();
  }

  public long getRecievedDateLong() {
    if (this.recievedDate == null) {
      return 0;
    }
    return this.recievedDate.getTime();
  }

  public long getBelongUserId() {
    return belongUserId;
  }

  public void setBelongUserId(long belongUserId) {
    this.belongUserId = belongUserId;
  }

}
