package express.entity.vo;

import express.entity.StaffRole;

public class StaffRoleVO {

  private boolean staff = true;

  public StaffRoleVO() {
  }

  public StaffRoleVO(StaffRole bean) {
    this.staff = bean.isStaff();
  }

  public boolean isStaff() {
    return staff;
  }

  public void setStaff(boolean staff) {
    this.staff = staff;
  }
}
