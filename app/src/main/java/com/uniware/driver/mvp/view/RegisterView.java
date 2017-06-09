package com.uniware.driver.mvp.view;

import android.content.Context;

/**
 * Created by jian on 15/12/30.
 */
public interface RegisterView {
  Context getContext();

  void registerSuccess();

  void registerFailure(String reason);

  void getCodeSuccess();

  void getCodeFailure(String reason);
}
