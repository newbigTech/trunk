package com.uniware.driver.mvp.view;

import android.content.Context;

/**
 * Created by ayue on 2016/12/16.
 */

public interface VersionView {
  Context getContext();

  void inSuccess(int code,String detail);

  void inFailure(String reason);
}
