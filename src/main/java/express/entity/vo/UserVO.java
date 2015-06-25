package express.entity.vo;

import java.util.List;

import org.mongodb.morphia.annotations.Reference;

import com.fasterxml.jackson.annotation.JsonIgnore;

import express.entity.ExpressItem;
import express.entity.StaffRole;
import express.entity.User;

public class UserVO {

  private long userId;

  private String mobilePhone;

  private String email;

  private String employeeId;

  private StaffRoleVO staffRole;

  public UserVO() {
  }

  public UserVO(User bean) {
    this.userId = bean.getUserId();
    this.mobilePhone = bean.getMobilePhone();
    this.email = bean.getEmail();
    this.employeeId = bean.getEmployeeId();
    this.staffRole = new StaffRoleVO(bean.getStaffRole());
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

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

  public StaffRoleVO getStaffRole() {
    return staffRole;
  }

  public void setStaffRole(StaffRoleVO staffRole) {
    this.staffRole = staffRole;
  }

}
