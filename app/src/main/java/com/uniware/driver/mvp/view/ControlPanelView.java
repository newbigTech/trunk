package com.uniware.driver.mvp.view;

import android.content.Context;
import com.uniware.driver.domain.Order;

/**
 * Created by jian on 15/11/20.
 */
public interface ControlPanelView {

  void showOrderView(Order order);

  void hideOrderView();

  void showError(String message);

  Context getContext();

  void showGrabBtn(int countdown);

  void showForbidGrabBtn();

  void showStartOffBtn();

  void showListeningBtn();

  void startLinking();

  void stopLinking();
}
