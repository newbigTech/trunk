package com.uniware.driver.domain;

/**
 * Created by ayue on 2016/12/16.
 */

public class VersionCodeResult extends NetBiz{
  int codeVersion;
  String detail;

  public int getCodeVersion() {
    return codeVersion;
  }

  public void setCodeVersion(int codeVersion) {
    this.codeVersion = codeVersion;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }
}
