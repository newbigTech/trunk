package com.uniware.driver.mvp.view;

/**
 * Created by ayue on 2017/2/28.
 */

public interface OrderStateView {
  void orderComplete(String oid,int state);
  void orderCancel(String oid,int state);
  void showError(String errorMsg);
}
