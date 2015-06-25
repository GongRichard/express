package express.entity.vo;

import express.entity.ExpressBill;

public class ExpressBillVO {

  private String orderNumber;

  private String mobilePhone;

  public ExpressBillVO(){}
  
  public ExpressBillVO(String orderNumber, String mobilePhone) {
    super();
    this.orderNumber = orderNumber;
    this.mobilePhone = mobilePhone;
  }
  
  public ExpressBillVO(ExpressBill bean){
    this.orderNumber = bean.getOrderNumber();
    this.mobilePhone = bean.getMobilePhone();
  }

  public String getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(String orderNumber) {
    this.orderNumber = orderNumber;
  }

  public String getMobilePhone() {
    return mobilePhone;
  }

  public void setMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
  }
}
