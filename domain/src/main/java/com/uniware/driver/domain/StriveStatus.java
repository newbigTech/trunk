package com.uniware.driver.domain;

/**
 * Created by jian on 15/11/24.
 */
public class StriveStatus extends NetBiz {

  private String oid;

  private Status status;

  public String getOid() {
    return oid;
  }

  public void setOid(String oid) {
    this.oid = oid;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public static enum Status {
    SUCCESS,//抢单成功
    FINISH,//完成 4
    FAILED,//抢单失败
    CANCEL,//弃用
    TIMEOUT,//超时 3
    CANCEL_DRIVER,//司机毁约 5
    CANCEL_PASSENGER,//乘客毁约 6
    DISMISS_DRIVER,//司机取消 9
    INVALID,
    PROCESSING;


    private Status() {

    }
  }
}
