package com.uniware.driver.mvp.view;

import android.content.Context;

/**
 * Created by jian on 15/11/20.
 */
public interface MainActivityView {

  Context getContext();

  void showError(String message);
}
