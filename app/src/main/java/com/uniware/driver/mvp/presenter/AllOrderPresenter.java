package com.uniware.driver.mvp.presenter;

import com.uniware.driver.config.LoginConfig;
import com.uniware.driver.domain.CallRecord;
import com.uniware.driver.domain.HttpResult;
import com.uniware.driver.domain.interactor.DefaultSubscriber;
import com.uniware.driver.domain.interactor.FinishOrder;
import com.uniware.driver.domain.interactor.UseCase;
import com.uniware.driver.mvp.view.RefreshView;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by ayue on 2017/5/24.
 */

public class AllOrderPresenter  implements Presenter{
  private RefreshView refreshView;
  private UseCase finishOrder;
  private int page=1;
  @Inject
  public AllOrderPresenter(@Named("finishOrder") UseCase finishOrder){
    this.finishOrder=finishOrder;

  }
  public void setView(RefreshView view){
    this.refreshView=view;
  }
  public void requestOrder(int page){
    this.page=page;
    ((FinishOrder)finishOrder).setPhoneNo(LoginConfig.getInstance().getUserName());
    ((FinishOrder)finishOrder).setCurrPage(page+"");
    finishOrder.execute(new ReQuestOrderSubscriber());
  }
  private final class ReQuestOrderSubscriber extends DefaultSubscriber<HttpResult<List<CallRecord>>>{
    @Override public void onCompleted() {
      super.onCompleted();
    }

    @Override public void onError(Throwable e) {
      super.onError(e);
    }

    @Override public void onNext(HttpResult<List<CallRecord>> listHttpResult) {
      super.onNext(listHttpResult);
      if (page==1){
        refreshView.reFreshOrder(listHttpResult);
      }
      else {
        refreshView.loadMore(listHttpResult);
      }
    }
  }
  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void destroy() {

  }
}
