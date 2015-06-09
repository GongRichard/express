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
  
  private long expressItemId;

  /** for DB storage */
  @JsonIgnore
  private int state = 0;

  /** for business logic */
  @Transient
  private ExpressItemStateEnum stateEnum;

  private ExpressBill expressBill;

  private String expressNumber;

  @JsonIgnore
  private Date sccanedDate;

  @JsonIgnore
  private Date recievedDate;

  public ExpressItem() {
  }

  public ExpressItem(ExpressItemStateEnum stateEnum, ExpressBill expressBill,
      String expressNumber, Date sccanedDate) {
    super();
    this.stateEnum = stateEnum;
    this.state = this.stateEnum.getFlag();
    this.expressBill = expressBill;
    this.expressNumber = expressNumber;
    this.sccanedDate = sccanedDate;
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

  public void setState(int state) {
    this.state = state;
    this.stateEnum = ExpressItemStateEnum.getByFlag(state);
  }

  public ExpressItemStateEnum getStateEnum() {
    if (this.stateEnum == null) {
      return ExpressItemStateEnum.getByFlag(state);
    }
    return stateEnum;
  }

  public void setStateEnum(ExpressItemStateEnum stateEnum) {
    this.stateEnum = stateEnum;
    this.state = this.stateEnum == null ? 0 : this.stateEnum.getFlag();
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
}
