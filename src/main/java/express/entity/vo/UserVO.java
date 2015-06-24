package express.entity.vo;

import java.util.List;

import org.mongodb.morphia.annotations.Reference;

import com.fasterxml.jackson.annotation.JsonIgnore;

import express.entity.ExpressItem;
import express.entity.StaffRole;

public class UserVO {

  private String mobilePhone;

  private String email;

  private String employeeId;

  private boolean staff;

  public String getMobilePhone() {
    return mobilePhone;
  }

  public void setMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(String employeeId) {
    this.employeeId = employeeId;
  }

  public boolean isStaff() {
    return staff;
  }

  public void setStaff(boolean staff) {
    this.staff = staff;
  }
  
}
