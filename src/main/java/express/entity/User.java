package express.entity;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import com.fasterxml.jackson.annotation.JsonIgnore;

import express.entity.vo.UserVO;

@Entity
public class User {
  @JsonIgnore
  @Id
  ObjectId id;

  private long userId;

  private String mobilePhone;

  private String email;

  @Reference
  private List<ExpressItem> expresses;

  /* iNumber, cNumber... */
  private String employeeId;

  @JsonIgnore
  private StaffRole staffRole;

  public User() {
    super();
  }

  public User(long userId, String mobilePhone, String email, String employeeId) {
    super();
    this.userId = userId;
    this.mobilePhone = mobilePhone;
    this.email = email;
    this.employeeId = employeeId;
    this.staffRole = null;
  }

  public User(long userId, String mobilePhone, String email, String employeeId,
      StaffRole staffRole) {
    super();
    this.userId = userId;
    this.mobilePhone = mobilePhone;
    this.email = email;
    this.employeeId = employeeId;
    this.staffRole = staffRole;
  }
  
  public User(UserVO vo) {
    this.userId = vo.getUserId();
    this.mobilePhone = vo.getMobilePhone();
    this.email = vo.getEmail();
    this.employeeId = vo.getEmployeeId();
    if (vo.getStaffRole() != null) {
      this.staffRole = new StaffRole(vo.getStaffRole()); 
    }
  }

  public ObjectId getId() {
    return id;
  }

  public void setId(ObjectId id) {
    this.id = id;
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

  public List<ExpressItem> getExpresses() {
    return expresses;
  }

  public void setExpresses(List<ExpressItem> expresses) {
    this.expresses = expresses;
  }

  public StaffRole getStaffRole() {
    return staffRole;
  }

  public void setStaffRole(StaffRole staffRole) {
    this.staffRole = staffRole;
  }

  public boolean getStaff() {
    return this.staffRole != null;
  }
}
