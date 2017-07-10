package com.uniware.driver.domain;

/**
 * Created by ayue on 2017/3/29.
 */

public class HttpResult<T> extends NetBiz {
  private T callRecords;

  public HttpResult(T callRecords) {
    this.callRecords = callRecords;
  }

  public T getCallRecords() {
    return callRecords;
  }

  public void setCallRecords(T callRecords) {
    callRecords = callRecords;
  }
}
