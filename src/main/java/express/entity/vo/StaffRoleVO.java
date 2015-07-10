package express.entity.vo;

import express.entity.StaffRole;

public class StaffRoleVO {

  private boolean staff = true;
  
  private boolean admin = true;

  public StaffRoleVO() {
  }

  public StaffRoleVO(StaffRole bean) {
    this.staff = bean.isStaff();
    this.admin = bean.isAdmin();
  }

  public boolean isStaff() {
    return staff;
  }

  public void setStaff(boolean staff) {
    this.staff = staff;
  }

  public boolean isAdmin() {
    return admin;
  }

  public void setAdmin(boolean admin) {
    this.admin = admin;
  }
}
