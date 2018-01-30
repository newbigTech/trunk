package com.uniware.driver.mvp.presenter;

import android.util.Log;
import com.uniware.driver.config.LoginConfig;
import com.uniware.driver.domain.CallRecord;
import com.uniware.driver.domain.HttpResult;
import com.uniware.driver.domain.NoticeResult;
import com.uniware.driver.domain.interactor.AllNotice;
import com.uniware.driver.domain.interactor.DefaultSubscriber;
import com.uniware.driver.domain.interactor.FinishOrder;
import com.uniware.driver.domain.interactor.UseCase;
import com.uniware.driver.mvp.view.NoticeView;
import com.uniware.driver.mvp.view.RefreshView;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by ayue on 2017/5/24.
 */

public class AllOrderPresenter  implements Presenter{
  private RefreshView refreshView;
  private NoticeView noticeView;
  private UseCase finishOrder;
  private UseCase requestNotice;
  private int page=1;
  private int noPage=1;
  @Inject
  public AllOrderPresenter(@Named("finishOrder") UseCase finishOrder,
      @Named("allNotice") UseCase requestNotice){
    this.finishOrder=finishOrder;
    this.requestNotice=requestNotice;

  }
  public void setView(RefreshView view){
    this.refreshView=view;
  }
  public void setNoticeView(NoticeView view){
    this.noticeView=view;
  }
  public void requestOrder(int page){
    this.page=page;
    ((FinishOrder)finishOrder).setPhoneNo(LoginConfig.getInstance().getUserName());
    ((FinishOrder)finishOrder).setCurrPage(page+"");
    finishOrder.execute(new ReQuestOrderSubscriber());
  }
  public  void requestNotice(int page){
    this.noPage=page;
    ((AllNotice)requestNotice).setTel(LoginConfig.getInstance().getUserName());
    ((AllNotice)requestNotice).setPage(page);
    requestNotice.execute(new RequestNoticeSubscriber());
  }
  private final class RequestNoticeSubscriber extends DefaultSubscriber<NoticeResult>{

    @Override public void onNext(NoticeResult noticeResult) {
      super.onNext(noticeResult);
      if (page==1){
        noticeView.reFreshOrder(noticeResult);
        Log.e("notice_list",noticeResult.toString());
      }
      else {
        noticeView.loadMore(noticeResult);
      }
    }

    @Override public void onError(Throwable e) {
      super.onError(e);
      Log.e("notice_list",e.getMessage());
    }
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
