package com.uniware.driver.mvp.presenter;

import com.uniware.driver.domain.Order;
import com.uniware.driver.domain.interactor.DefaultSubscriber;
import com.uniware.driver.domain.interactor.UseCase;
import com.uniware.driver.mvp.view.DriverFragmentView;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by jian on 15/12/07.
 */
public class DriverPresenter implements Presenter {
  private DriverFragmentView driverFragmentView;
  private final UseCase getOrderList;

  @Inject public DriverPresenter(@Named("orderList") UseCase getOrderList) {
    this.getOrderList = getOrderList;
  }

  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void destroy() {
    getOrderList.unsubscribe();
  }

  public void setView(DriverFragmentView driverFragmentView) {
    this.driverFragmentView = driverFragmentView;
  }

  public void getOrderList() {
    getOrderList.execute(new GetOrderListSubscriber());
  }

  private final class GetOrderListSubscriber extends DefaultSubscriber<List<Order>> {

    @Override public void onCompleted() {
    }

    @Override public void onError(Throwable e) {
    }

    @Override public void onNext(List<Order> orders) {
      driverFragmentView.loadOrders(orders);
    }
  }

  public void onOrderClicked(Order order) {
    this.driverFragmentView.viewOrder(order);
  }
}
