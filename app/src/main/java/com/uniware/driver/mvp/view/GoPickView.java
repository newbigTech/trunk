package com.uniware.driver.mvp.view;

/**
 * Created by jian on 15/12/30.
 */
public interface GoPickView {
  void orderComplete();
  void orderCancel();
  void showError(String errorMsg);
}
