package express.entity.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import express.entity.ExpressBill;
import express.entity.ExpressItem;
import express.entity.ExpressItemStateEnum;

public class ExpressItemVO {

  private long expressItemId;

  private ExpressBillVO expressBill;

  private String expressNumber;

  private long sccanedDate;

  private long recievedDate;

  private ExpressItemStateEnum stateEnum;

  public ExpressItemVO() {
  }

  public ExpressItemVO(long expressItemId, ExpressBillVO expressBill,
      String expressNumber, long sccanedDate, long recievedDate,
      ExpressItemStateEnum stateEnum) {
    super();
    this.expressItemId = expressItemId;
    this.expressBill = expressBill;
    this.expressNumber = expressNumber;
    this.sccanedDate = sccanedDate;
    this.recievedDate = recievedDate;
    this.stateEnum = stateEnum;
  }

  public ExpressItemVO(ExpressItem bean) {
    this.expressItemId = bean.getExpressItemId();
    this.expressBill = new ExpressBillVO(bean.getExpressBill());
    this.expressNumber = bean.getExpressNumber();
    this.sccanedDate = bean.getSccanedDateLong();
    this.recievedDate = bean.getRecievedDateLong();
    this.stateEnum = bean.getStateEnum();
  }

  public long getExpressItemId() {
    return expressItemId;
  }

  public void setExpressItemId(long expressItemId) {
    this.expressItemId = expressItemId;
  }

  public ExpressBillVO getExpressBill() {
    return expressBill;
  }

  public void setExpressBill(ExpressBillVO expressBill) {
    this.expressBill = expressBill;
  }

  public String getExpressNumber() {
    return expressNumber;
  }

  public void setExpressNumber(String expressNumber) {
    this.expressNumber = expressNumber;
  }

  public long getSccanedDate() {
    return sccanedDate;
  }

  public void setSccanedDate(long sccanedDate) {
    this.sccanedDate = sccanedDate;
  }

  public long getRecievedDate() {
    return recievedDate;
  }

  public void setRecievedDate(long recievedDate) {
    this.recievedDate = recievedDate;
  }

  public ExpressItemStateEnum getStateEnum() {
    return stateEnum;
  }

  public void setStateEnum(ExpressItemStateEnum stateEnum) {
    this.stateEnum = stateEnum;
  }

}
