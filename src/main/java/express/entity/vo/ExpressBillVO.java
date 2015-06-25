package express.entity.vo;

public class ExpressBillVO {

  private String orderNumber;

  private String mobilePhone;

  public ExpressBillVO(){}
  
  public ExpressBillVO(String orderNumber, String mobilePhone) {
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
