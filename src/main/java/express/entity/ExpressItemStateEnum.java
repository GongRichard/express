package express.entity;

public enum ExpressItemStateEnum {

  NONE(0), SCANNED(1), RECIEVED(2);

  private int flag;

  ExpressItemStateEnum(int flag) {
    this.flag = flag;
  }

  public static ExpressItemStateEnum getByFlag(int flag) {
    for (ExpressItemStateEnum stateEnum : ExpressItemStateEnum.values()) {
      if (stateEnum.flag == flag) {
        return stateEnum;
      }
    }
    return null;
  }
  
  public int getFlag() {
    return this.flag;
  }
}
