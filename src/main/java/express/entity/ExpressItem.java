package express.entity;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.annotations.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ExpressItem {

  @JsonIgnore
  @Id
  ObjectId id;

  @JsonIgnore
  private int state;

  @Transient
  private ExpressItemStateEnum stateEnum;

  @Reference
  private ExpressBill expressBill;

  private String expressNumber;
  
  private Date sccanedDate;
  
  private Date recievedDate;

  public ObjectId getId() {
    return id;
  }

  public void setId(ObjectId id) {
    this.id = id;
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

  public ExpressItemStateEnum getStateEnum() {
    return stateEnum;
  }

  public void setStateEnum(ExpressItemStateEnum stateEnum) {
    this.stateEnum = stateEnum;
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

}
