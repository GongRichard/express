package express.entity;

import express.entity.vo.ExpressBillVO;

public class ExpressBill {

  private String orderNumber;

  private String mobilePhone;

  public ExpressBill() {
  }

  public ExpressBill(String orderNumber, String mobilePhone) {
    super();
    this.orderNumber = orderNumber;
    this.mobilePhone = mobilePhone;
  }

  public ExpressBill(ExpressBillVO vo) {
    this.orderNumber = vo.getOrderNumber();
    this.mobilePhone = vo.getMobilePhone();
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
