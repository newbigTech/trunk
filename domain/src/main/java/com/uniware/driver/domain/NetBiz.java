package com.uniware.driver.domain;

/**
 * Created by jian on 16/03/24.
 */

public class NetBiz extends BizObject {
  private int errno = 0;
  private String errmsg = "OK";

  public int getErrno() {
    return errno;
  }

  public void setErrno(int errno) {
    this.errno = errno;
  }

  public String getErrmsg() {
    return errmsg;
  }

  public void setErrmsg(String errmsg) {
    this.errmsg = errmsg;
  }
}
