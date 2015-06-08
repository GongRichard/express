package express.entity;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ExpressBill {

  @JsonIgnore
  @Id
  ObjectId id;

  private long expressBillId;

  private String orderNumber;

  private String mobilePhone;

  public ExpressBill(){}
  
  public ExpressBill(String orderNumber, String mobilePhone) {
    super();
    this.orderNumber = orderNumber;
    this.mobilePhone = mobilePhone;
  }

  public ObjectId getId() {
    return id;
  }

  public void setId(ObjectId id) {
    this.id = id;
  }

  public long getExpressBillId() {
    return expressBillId;
  }

  public void setExpressBillId(long expressBillId) {
    this.expressBillId = expressBillId;
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
