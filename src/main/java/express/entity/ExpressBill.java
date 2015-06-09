package express.entity;

public class ExpressBill {

  private String orderNumber;

  private String mobilePhone;

  public ExpressBill(){}
  
  public ExpressBill(String orderNumber, String mobilePhone) {
    super();
    this.orderNumber = orderNumber;
    this.mobilePhone = mobilePhone;
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
