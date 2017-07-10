package com.uniware.driver.domain;

/**
 * Created by ayue on 2017/4/13.
 */

public class OrderStatus extends NetBiz {

  private String oid;

  private int status;

  public OrderStatus() {
  }

  public String getOid() {
    return oid;
  }

  public void setOid(String oid) {
    this.oid = oid;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}
