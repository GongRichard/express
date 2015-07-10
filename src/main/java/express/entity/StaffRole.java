package express.entity;

import express.entity.vo.StaffRoleVO;

public class StaffRole {

  private boolean staff = true;
  
  private boolean admin = true;

  public StaffRole() {
  }

  public StaffRole(StaffRoleVO vo) {
    this.staff = vo.isStaff();
    this.admin = vo.isAdmin();
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
