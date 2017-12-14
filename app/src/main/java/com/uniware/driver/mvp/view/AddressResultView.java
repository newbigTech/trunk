package com.uniware.driver.mvp.view;

import android.content.Context;
import com.uniware.driver.domain.AddressResult;

/**
 * Created by ayue on 2017/7/27.
 */

public interface AddressResultView {

  Context getContext();

  void querySuccess(AddressResult result);

  void queryFails(String error);

  void updateSuccess();

  void updateFails(String error);
}
