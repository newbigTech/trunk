package com.uniware.driver.mvp.presenter;

import com.uniware.driver.domain.Order;
import com.uniware.driver.mvp.view.AnnounceFragmentView;
import javax.inject.Inject;

/**
 * Created by jian on 15/12/07.
 */
public class AnnouncePresenter implements Presenter {

  private AnnounceFragmentView announceFragmentView;

  @Inject public AnnouncePresenter() {

  }

  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void destroy() {

  }

  public void setView(AnnounceFragmentView announceFragmentView) {
    this.announceFragmentView = announceFragmentView;
  }

  public void onOrderClicked(Order order) {
    this.announceFragmentView.viewOrder(order);
  }
}
