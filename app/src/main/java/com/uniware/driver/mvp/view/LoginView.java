package com.uniware.driver.mvp.view;

import android.content.Context;

/**
 * Created by jian on 15/12/30.
 */
public interface LoginView {
  Context getContext();

  void loginSuccess();

  void loginFailure(String reason);
}
