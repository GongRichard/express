package express.entity;

import express.entity.vo.StaffRoleVO;

public class StaffRole {

  private boolean staff = true;

  public StaffRole() {
  }

  public StaffRole(StaffRoleVO vo) {
    this.staff = vo.isStaff();
  }

  public boolean isStaff() {
    return staff;
  }

  public void setStaff(boolean staff) {
    this.staff = staff;
  }

}
