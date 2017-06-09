package com.uniware.driver.mvp.view;

import android.content.Context;

/**
 * Created by jian on 15/12/30.
 */
public interface ForgetPswdView {
  Context getContext();

  void forgetPswdCommiteSuccess();

  void forgetPswdCommiteFailure(String reason);
  void getCodeSuccess();

  void getCodeFailure(String reason);
}
