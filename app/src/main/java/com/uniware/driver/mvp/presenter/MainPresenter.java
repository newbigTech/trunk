package com.uniware.driver.mvp.presenter;

import android.content.ContentValues;
import android.util.Log;
import com.uniware.driver.AppApplication;
import com.uniware.driver.config.LoginConfig;
import com.uniware.driver.data.database.Provider;
import com.uniware.driver.data.exception.NetworkConnectionException;
import com.uniware.driver.domain.CallRecord;
import com.uniware.driver.domain.HttpResult;
import com.uniware.driver.domain.Order;
import com.uniware.driver.domain.exception.DefaultErrorBundle;
import com.uniware.driver.domain.exception.ErrorBundle;
import com.uniware.driver.domain.interactor.DefaultSubscriber;
import com.uniware.driver.domain.interactor.FinishOrder;
import com.uniware.driver.domain.interactor.UnfinishOrder;
import com.uniware.driver.domain.interactor.UpdateLocation;
import com.uniware.driver.domain.interactor.UseCase;
import com.uniware.driver.mvp.exception.ErrorMessageFactory;
import com.uniware.driver.mvp.view.MainActivityView;
import com.uniware.driver.util.LogUtils;
import com.uniware.driver.util.Tools;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by jian on 15/11/20.
 */
public class MainPresenter implements Presenter {

  private final UseCase startPush;
  private final UseCase updateLocation;
  private final UseCase unfinishOrder;
  private final UseCase finishOrder;
  private MainActivityView mainActivityView;

  @Inject public MainPresenter(@Named("startPush") UseCase startPush,
      @Named("updateLocation") UseCase updateLocation,@Named("unfinishOrder") UseCase unfinishOrder,@Named("finishOrder") UseCase finishOrder) {
    this.startPush = startPush;
    this.updateLocation = updateLocation;
    this.unfinishOrder=unfinishOrder;
    this.finishOrder=finishOrder;
  }

  @Override public void resume() {
  }

  @Override public void pause() {
  }

  @Override public void destroy() {
    this.startPush.unsubscribe();
    this.updateLocation.unsubscribe();
  }

  public void startPush() {
    startPush.execute(new StartPushSubscriber());
  }

  public void startOff() {
    ((UpdateLocation) updateLocation).setStatusFlag(0x00);
  }

  public void stopOff() {
    ((UpdateLocation) updateLocation).setStatusFlag(0x08);
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage =
        ErrorMessageFactory.create(this.mainActivityView.getContext(), errorBundle.getException());
    this.mainActivityView.showError(errorMessage);
  }

  public void setMainActivityView(MainActivityView mainActivityView) {
    this.mainActivityView = mainActivityView;
  }

  private final class StartPushSubscriber extends DefaultSubscriber<Boolean> {
    @Override public void onCompleted() {
      super.onCompleted();
    }

    @Override public void onError(Throwable e) {
      super.onError(e);
      LogUtils.e(this, "链接push服务失败~");
      startPush.schedule(10, new StartPushSubscriber());
    }

    @Override public void onNext(Boolean aBoolean) {
      super.onNext(aBoolean);
      LogUtils.e(this, "链接push服务成功~");
      updateLocation.schedule(5, 10, new UpdateLocationSubscriber());
    }
  }

  private final class UpdateLocationSubscriber extends DefaultSubscriber<Void> {
    @Override public void onCompleted() {
      super.onCompleted();
    }

    @Override public void onError(Throwable e) {
      super.onError(e);
      showErrorMessage(new DefaultErrorBundle((Exception) e));
      if (e instanceof NetworkConnectionException) {
        startPush.execute(new StartPushSubscriber());
      } else {
        updateLocation.schedule(5, 10, new UpdateLocationSubscriber());
      }
    }

    @Override public void onNext(Void aVoid) {
      super.onNext(aVoid);
    }
  }
  public void updateOrder(){
    AppApplication.getAppContext().getContentResolver()
        .delete(Provider.Orders.CONTENT_URI,null,null );
    ((UnfinishOrder)unfinishOrder).setPhoneNo(LoginConfig.getInstance().getUserName());
    ((UnfinishOrder)unfinishOrder).setCurrPage("1");
    unfinishOrder.execute(new updateOrderSubscriber());
    ((FinishOrder)finishOrder).setPhoneNo(LoginConfig.getInstance().getUserName());
    ((FinishOrder)finishOrder).setCurrPage("1");
    finishOrder.execute(new updateFinishOrderSubscriber());
  }

  private final class updateFinishOrderSubscriber extends DefaultSubscriber<HttpResult<List<CallRecord>>>{
    @Override public void onCompleted() {
      super.onCompleted();
    }

    @Override public void onNext(HttpResult<List<CallRecord>> listHttpResult) {
      super.onNext(listHttpResult);
      Log.e("====---finish",listHttpResult.getCallRecords().toString()+"");
      for (int i=listHttpResult.getCallRecords().size()-1;i>=0;i--){
        ContentValues values = new ContentValues();
        values.put(Provider.Orders.COLUMN_OID, listHttpResult.getCallRecords().get(i).getBizId());
        values.put(Provider.Orders.COLUMN_TYPE, listHttpResult.getCallRecords().get(i).getBizType()==0 ? 0:1);
        Log.e("olds...",listHttpResult.getCallRecords().get(i).getUseTime()+"");
        String s= Tools.stampToDate(listHttpResult.getCallRecords().get(i).getUseTime());
        s=s.substring(2);
        Log.e("news....",s);
        values.put(Provider.Orders.COLUMN_TIME, s);
        values.put(Provider.Orders.COLUMN_FROM_ADDR, listHttpResult.getCallRecords().get(i).getUserLocation());
        values.put(Provider.Orders.COLUMN_TO_ADDR, listHttpResult.getCallRecords().get(i).getDestLocation());
        values.put(Provider.Orders.COLUMN_FROM_LNG, listHttpResult.getCallRecords().get(i).getUserLon() + "");
        values.put(Provider.Orders.COLUMN_FROM_LAT, listHttpResult.getCallRecords().get(i).getUserLat() + "");
        values.put(Provider.Orders.COLUMN_TO_LNG, listHttpResult.getCallRecords().get(i).getDestLon() + "");
        values.put(Provider.Orders.COLUMN_TO_LAT, listHttpResult.getCallRecords().get(i).getDestLat() + "");
        values.put(Provider.Orders.COLUMN_PHONE, listHttpResult.getCallRecords().get(i).getPassengerTel());
        values.put(Provider.Orders.COLUMN_FREE, listHttpResult.getCallRecords().get(i).getCallFee()+"");
        values.put(Provider.Orders.COLUMN_description, listHttpResult.getCallRecords().get(i).getPassengerAttr()+"");
        int status=100+listHttpResult.getCallRecords().get(i).getStatus();
        //switch (listHttpResult.getCallRecords().get(i).getStatus()){
        //  case 4: status=Order.STATUS_END;break;
        //  case 1:
        //  case 2: status=Order.STATUS_START;break;
        //  case 3: status=103;break;
        //  case
        //  default:status=Order.STATUS_CANCEL;break;
        //}
        values.put(Provider.Orders.COLUMN_STATUS, status);
        AppApplication.getAppContext().getContentResolver()
            .delete(Provider.Orders.CONTENT_URI,"oid=?",new String[]{listHttpResult.getCallRecords().get(i).getBizId()+"" });
        AppApplication.getAppContext().getContentResolver()
            .insert(Provider.Orders.CONTENT_URI, values);
      }
    }

    @Override public void onError(Throwable e) {
      super.onError(e);
    }
  }

  private final class updateOrderSubscriber extends DefaultSubscriber<HttpResult<List<CallRecord>>>{
    @Override public void onNext(HttpResult<List<CallRecord>> listHttpResult) {
      super.onNext(listHttpResult);
      Log.e("====---unfinish",listHttpResult.getCallRecords().toString()+"");
      //SQLiteDataStore sqLiteDataStore=new SQLiteDataStore(AppApplication.getAppContext());
      //sqLiteDataStore.updateUnfinishOrder(new ArrayList<Order>());
      for (int i=0;i<listHttpResult.getCallRecords().size();i++){
        ContentValues values = new ContentValues();
        values.put(Provider.Orders.COLUMN_OID, listHttpResult.getCallRecords().get(i).getBizId());
        values.put(Provider.Orders.COLUMN_TYPE, listHttpResult.getCallRecords().get(i).getBizType()==0 ? 0:1);
        Log.e("olds...",listHttpResult.getCallRecords().get(i).getUseTime()+"");
        String s= Tools.stampToDate(listHttpResult.getCallRecords().get(i).getUseTime());
        s=s.substring(2);
        Log.e("news....",s);
        values.put(Provider.Orders.COLUMN_TIME, s);
        values.put(Provider.Orders.COLUMN_FROM_ADDR, listHttpResult.getCallRecords().get(i).getUserLocation());
        values.put(Provider.Orders.COLUMN_TO_ADDR, listHttpResult.getCallRecords().get(i).getDestLocation());
        values.put(Provider.Orders.COLUMN_FROM_LNG, listHttpResult.getCallRecords().get(i).getUserLon() + "");
        values.put(Provider.Orders.COLUMN_FROM_LAT, listHttpResult.getCallRecords().get(i).getUserLat() + "");
        values.put(Provider.Orders.COLUMN_TO_LNG, listHttpResult.getCallRecords().get(i).getDestLon() + "");
        values.put(Provider.Orders.COLUMN_TO_LAT, listHttpResult.getCallRecords().get(i).getDestLat() + "");
        values.put(Provider.Orders.COLUMN_PHONE, listHttpResult.getCallRecords().get(i).getPassengerTel());
        values.put(Provider.Orders.COLUMN_FREE, listHttpResult.getCallRecords().get(i).getCallFee()+"");
        values.put(Provider.Orders.COLUMN_description, listHttpResult.getCallRecords().get(i).getPassengerAttr()+"");
        int status;
        switch (listHttpResult.getCallRecords().get(i).getStatus()) {
          case 4:
            status = 104;
            break;
          case 1:
          case 2:
            status = Order.STATUS_START;
            break;
          default:
            status = 100+listHttpResult.getCallRecords().get(i).getStatus();
            break;
        }
          values.put(Provider.Orders.COLUMN_STATUS, status);
        AppApplication.getAppContext().getContentResolver()
            .delete(Provider.Orders.CONTENT_URI,"oid=?",new String[]{listHttpResult.getCallRecords().get(i).getBizId()+"" });
        AppApplication.getAppContext().getContentResolver()
            .insert(Provider.Orders.CONTENT_URI, values);
      }
    }

    @Override public void onError(Throwable e) {
      super.onError(e);
      Log.e("====","error"+e.getMessage()+"");
    }

    @Override public void onCompleted() {
      super.onCompleted();
    }
  }
}
