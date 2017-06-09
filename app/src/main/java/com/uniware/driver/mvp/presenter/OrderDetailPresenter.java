package com.uniware.driver.mvp.presenter;

import com.uniware.driver.domain.interactor.DefaultSubscriber;
import com.uniware.driver.domain.interactor.DeleteOrder;
import com.uniware.driver.domain.interactor.UseCase;
import com.uniware.driver.mvp.view.OrderDetailView;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by jian on 16/03/25.
 */
public class OrderDetailPresenter implements Presenter {

  private final UseCase deleteOrder;

  private OrderDetailView orderDetailView;

  @Inject OrderDetailPresenter(@Named("deleteOrder") UseCase deleteOrder) {
    this.deleteOrder = deleteOrder;
  }

  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void destroy() {
    deleteOrder.unsubscribe();
  }

  public void setOrderDetailView(OrderDetailView orderDetailView) {
    this.orderDetailView = orderDetailView;
  }

  public void deleteOrder(String orderId) {
    ((DeleteOrder) deleteOrder).setOid(orderId);
    deleteOrder.execute(new DeleteOrderSubscriber());
  }

  private final class DeleteOrderSubscriber extends DefaultSubscriber<Integer> {
    @Override public void onCompleted() {
      super.onCompleted();
    }

    @Override public void onError(Throwable e) {
      super.onError(e);
      orderDetailView.showError("删除失败！");
    }

    @Override public void onNext(Integer integer) {
      super.onNext(integer);
      orderDetailView.orderDelete();
    }
  }
}
