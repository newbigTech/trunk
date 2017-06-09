package com.uniware.driver.mvp.presenter;

import com.uniware.driver.domain.NetBiz;
import com.uniware.driver.domain.interactor.CancelOrder;
import com.uniware.driver.domain.interactor.CompleteOrder;
import com.uniware.driver.domain.interactor.DefaultSubscriber;
import com.uniware.driver.domain.interactor.UseCase;
import com.uniware.driver.mvp.view.GoPickView;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by jian on 16/03/25.
 */
public class GoPickPresenter implements Presenter {

  private final UseCase completeOrder;

  private final UseCase cancelOrder;

  private GoPickView goPickView;

  @Inject GoPickPresenter(@Named("completeOrder") UseCase completeOrder,
      @Named("cancelOrder") UseCase cancelOrder) {
    this.completeOrder = completeOrder;
    this.cancelOrder = cancelOrder;
  }

  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void destroy() {
    completeOrder.unsubscribe();
  }

  public void setGoPickView(GoPickView goPickView) {
    this.goPickView = goPickView;
  }

  public void completeOrder(String orderId) {
    ((CompleteOrder) completeOrder).setOrderId(orderId);
    completeOrder.execute(new CompleteOrderSubscriber());
  }

  private final class CompleteOrderSubscriber extends DefaultSubscriber<NetBiz> {
    @Override public void onCompleted() {
      super.onCompleted();
    }

    @Override public void onError(Throwable e) {
      super.onError(e);
    }

    @Override public void onNext(NetBiz result) {
      super.onNext(result);
      if (result.getErrno() == 0) {
        goPickView.orderComplete();
      } else {
        goPickView.showError(result.getErrmsg());
      }
    }
  }

  public void cancelOrder(String orderId) {
    ((CancelOrder) cancelOrder).setOrderId(orderId);
    cancelOrder.execute(new CancelOrderSubscriber());
  }

  private final class CancelOrderSubscriber extends DefaultSubscriber<NetBiz> {
    @Override public void onCompleted() {
      super.onCompleted();
    }

    @Override public void onError(Throwable e) {
      super.onError(e);
    }

    @Override public void onNext(NetBiz result) {
      super.onNext(result);
      if (result.getErrno() == 0) {
        goPickView.orderCancel();
      } else {
        goPickView.showError(result.getErrmsg());
      }
    }
  }
}
