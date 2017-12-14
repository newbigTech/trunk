package com.uniware.driver.mvp.presenter;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import com.uniware.driver.AppApplication;
import com.uniware.driver.R;
import com.uniware.driver.config.LoginConfig;
import com.uniware.driver.data.DriverLocation;
import com.uniware.driver.data.repository.datasource.SQLiteDataStore;
import com.uniware.driver.domain.BizObject;
import com.uniware.driver.domain.NetBiz;
import com.uniware.driver.domain.Order;
import com.uniware.driver.domain.OrderStatus;
import com.uniware.driver.domain.StriveStatus;
import com.uniware.driver.domain.exception.DefaultErrorBundle;
import com.uniware.driver.domain.exception.ErrorBundle;
import com.uniware.driver.domain.interactor.DefaultSubscriber;
import com.uniware.driver.domain.interactor.GrabOrder;
import com.uniware.driver.domain.interactor.ModelApply;
import com.uniware.driver.domain.interactor.SaveOrder;
import com.uniware.driver.domain.interactor.UpdateOrder;
import com.uniware.driver.domain.interactor.UseCase;
import com.uniware.driver.mvp.exception.ErrorMessageFactory;
import com.uniware.driver.mvp.model.OrderModel;
import com.uniware.driver.mvp.view.ControlPanelView;
import com.uniware.driver.mvp.view.OrderFragmentView;
import com.uniware.driver.mvp.view.OrderStateView;
import com.uniware.driver.util.LogUtils;
import com.uniware.driver.util.MapDistance;
import com.uniware.driver.util.PMUtils;
import com.uniware.driver.util.ToastUtil;
import com.uniware.driver.util.Tools;
import com.uniware.driver.util.XFSpeech;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by jian on 15/11/20.
 */
public class OrderPanelPresenter implements Presenter {

  @Inject @Named("deleteOrder") UseCase deleteOrder;
  @Inject @Named("countdown") UseCase countdown;
  @Inject @Named("updateOrder") UseCase updateOrder;
  @Inject @Named("modelApply") UseCase modelApply;
  private final UseCase recvPush;
  private final UseCase grabOrder;
  private final UseCase saveOrder;
  private ControlPanelView controlPanelView;
  private OrderFragmentView orderFragmentView;
  private OrderModel orderModel;
  private OrderStateView orderStateView;
  private MediaPlayer mediaPlayer;
  private int num=0;
  private boolean isSshow=true;//是否显示此单
  //private boolean isClick=false;//抢单按钮是否被点击
  private boolean isResume=false;//orderfragment是否是resume状态
  private boolean receved=false;
  private boolean isCache=false;
  private boolean isPause=false;
  @Inject XFSpeech xfSpeech;
  String ttsString;

  @Inject public OrderPanelPresenter(@Named("recvPush") UseCase recvPush,
      @Named("grabOrder") UseCase grabOrder, @Named("saveOrder") UseCase saveOrder, @Named("modelApply") UseCase modelApply) {
    this.recvPush = recvPush;
    this.grabOrder = grabOrder;
    this.saveOrder = saveOrder;
    this.modelApply=modelApply;
    recvPush.execute(new OrderListeningSubscriber());
  }

  @Override public void resume() {
    xfSpeech.resume();
    isResume=true;
    isPause=false;
    //if (num==0&&receved&&isCache){
    //  Log.e("=====","num"+num);
    //  //reFresh();
    //  //controlPanelView.showForbidGrabBtn();
    //  controlPanelView.hideOrderView();
    //  isCache=false;
    //}
  }

  @Override public void pause() {
    //Log.e("=====","pause...........................");
    xfSpeech.pause();
    isPause=true;
    //recvPush.unsubscribe();
    countdown.unsubscribe();
    isResume=false;
  }

  @Override public void destroy() {
    this.recvPush.unsubscribe();
    this.grabOrder.unsubscribe();
    this.saveOrder.unsubscribe();
    this.updateOrder.unsubscribe();
    xfSpeech.destroy();
  }

  public void setControlPanelView(@NonNull ControlPanelView view) {
    controlPanelView = view;
  }

  public void setOrderStateView(@NonNull OrderStateView view){ orderStateView=view;}

  public void setOrderFragmentView(@NonNull OrderFragmentView view) {
    orderFragmentView = view;
  }

  public void ttsSpeaking(String ttsText) {
    xfSpeech.ttsSpeaking(ttsText);
  }

  public void startOff() {
    controlPanelView.showListeningBtn();
    //controlPanelView.startLinking();
    //recvPush.execute(new OrderListeningSubscriber());
    receved=true;
    xfSpeech.ttsSpeaking("开始接单了！");
    LogUtils.e(this, "出车~");
  }

  public void stopOff() {
    controlPanelView.showStartOffBtn();
    xfSpeech.ttsSpeaking("停止接单了！");
    //recvPush.unsubscribe();
    receved=false;
    LogUtils.e(this, "收车~");
  }

  public void grabOrder() {
    cancelCountdown();
    //isClick=true;
    ((GrabOrder) grabOrder).setOrderId(orderModel.getOrder().getOid());
    grabOrder.execute(new GrabOrderSubscriber());
    controlPanelView.showForbidGrabBtn();
    orderFragmentView.grabOrder();
    xfSpeech.ttsSpeaking("抢单中！");

  }

  public void cancelCountdown(){
    countdown.unsubscribe();
    //isCancel=true;
    //isSshow=true;
    xfSpeech.stop();
  }
  public void cancelCountdown1(){
    isSshow=true;
    countdown.unsubscribe();
    //isCancel=true;
    //isSshow=true;
    xfSpeech.stop();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage =
        ErrorMessageFactory.create(this.controlPanelView.getContext(), errorBundle.getException());
    this.controlPanelView.showError(errorMessage);
  }

  private final class OrderListeningSubscriber extends DefaultSubscriber<BizObject> {

    @Override public void onCompleted() {
      super.onCompleted();
    }

    @Override public void onError(Throwable e) {
      super.onError(e);
      showErrorMessage(new DefaultErrorBundle((Exception) e));
      //recvPush.execute(new OrderListeningSubscriber());
    }

    @Override public void onNext(BizObject biz) {
      super.onNext(biz);
      //Observable 不指定线程就运行在当前线程中
      Log.e("=====","----来单了-----");
      if (biz instanceof Order) {
        if (receved==false){
          return;
        }
        if(!isSshow){
          Log.e("是否显示","false");
          return;
        }
        isSshow=false;
        if (isPause) isCache=true;
        if (!PMUtils.isScreenOn(AppApplication.getAppContext())){
          PMUtils.wakeAndUnlock(AppApplication.getAppContext());
          //isResume=true;
        }
        Order order = (Order) biz;
        DriverLocation driverLocation=new DriverLocation(AppApplication.getAppContext());
        double lon = driverLocation.getLocation().getLongitude();
        double lat = driverLocation.getLocation().getLatitude();
        MapDistance mapDistance1=MapDistance.getInstance();
        double d=mapDistance1.getDoubleDistance(lon,lat,order.getFromLng(),order.getFromLat());
        order.setDistances(d);
        ttsString=order.buildTtsStr();
        Log.e("=====",order.getUseTime());
        LoginConfig loginConfig=LoginConfig.getInstance();
        if(0!=loginConfig.getHobby()){
          if(loginConfig.getHobby()!=(order.getTypeNum())){
              isSshow=true;
              return;
          }
        }
        if(0!=loginConfig.getDistance()&&order.getType()==0){
          //DriverLocation driverLocation=new DriverLocation(AppApplication.getAppContext());
          double lon1= Double.parseDouble(loginConfig.getLon());
          double lat11=Double.parseDouble(loginConfig.getLat());
          Log.e("====", lon1 + "----------" + lat11);
          double lon2=order.getFromLng();
          double lat2=order.getFromLat();
          MapDistance mapDistance=MapDistance.getInstance();
          int distance=mapDistance.getLongDistance(lon1,lat11,lon2,lat2);
          Log.e("====","----distance"+distance);
          if (distance>loginConfig.getDistance()){
            isSshow=true;
            return;
          }
        }
          mediaPlayer = MediaPlayer.create(AppApplication.getAppContext(), R.raw.news);
          mediaPlayer.stop();
          mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mp) {
              mp.release();//释放音频资源
              xfSpeech.ttsSpeaking(ttsString,true);
            }
          });
          try {
            //在播放音频资源之前，必须调用Prepare方法完成些准备工作
            if(true)
              mediaPlayer.prepare();
            //开始播放音频
            mediaPlayer.start();
          } catch (IllegalStateException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          }
        //xfSpeech.ttsSpeaking(order.buildTtsStr());
        orderModel = new OrderModel(order);
        countdown.unsubscribe();
        controlPanelView.showOrderView(order);
        countdown.execute(new CountdownSubscriber(12));
        LogUtils.e(this, "有新订单了~");
      } else if (biz instanceof StriveStatus) {
        StriveStatus striveStatus = (StriveStatus) biz;
        if (striveStatus.getStatus() == StriveStatus.Status.SUCCESS) {
          ((SaveOrder) saveOrder).setOrder(orderModel.getOrder());
          saveOrder.execute(new SaveOrderSubscriber());
          orderFragmentView.grabSuccess();
          xfSpeech.ttsSpeaking("抢单成功！");
          isSshow=true;
          Tools.SetTaxiStatus(true);
        } else if (striveStatus.getStatus() == StriveStatus.Status.FAILED) {
          orderFragmentView.grabFailure();
          xfSpeech.ttsSpeaking("抢单失败！");
          isSshow=true;
          Tools.SetTaxiStatus(false);
        } else if (striveStatus.getStatus() == StriveStatus.Status.CANCEL) {
          isSshow=true;
        }
        else if (striveStatus.getStatus() == StriveStatus.Status.FINISH){
          isSshow=true;
        }
      }
      else if (biz instanceof OrderStatus){
        OrderStatus orderStatus=(OrderStatus)biz;
        if (orderStatus.getStatus()==4){
          ((UpdateOrder)updateOrder).setOid(orderStatus.getOid());
          ((UpdateOrder)updateOrder).setStatus(orderStatus.getStatus());
          orderStateView.orderComplete(orderStatus.getOid(),orderStatus.getStatus());
          updateOrder.execute(new UpdateSubscriber());
        }else if (orderStatus.getStatus()==3||orderStatus.getStatus()==5||orderStatus.getStatus()==6
            ||orderStatus.getStatus()==9||orderStatus.getStatus()==7||orderStatus.getStatus()==8){
          ((UpdateOrder)updateOrder).setOid(orderStatus.getOid());
          ((UpdateOrder)updateOrder).setStatus(orderStatus.getStatus());
          orderStateView.orderCancel(orderStatus.getOid(),orderStatus.getStatus());
          updateOrder.execute(new UpdateSubscriber());
          SQLiteDataStore sqLiteDataStore=new SQLiteDataStore(AppApplication.getAppContext());
          Order order=sqLiteDataStore.findOrder(Integer.valueOf(orderStatus.getOid()));
          Log.e("=====find",order.toString());
          String s;
          if (order.getType()==0){
            s="即时单";
          }
          else {
            if (order.getCallFee().equals(5)){
              s="短约单";
            }else {
              s="长约单";
            }
          }
          if (order.getUseTime()!=null)
          {
            s=order.getUseTime().substring(6,8)+"时"+order.getUseTime().substring(8,10)+"分,的"+s;
            String reason="";
            switch (orderStatus.getStatus()){
              case 3: reason="超时";break;
              case 5: reason="司机毁约";break;
              case 6: reason="乘客毁约";break;
              case 7: reason="乘客取消";break;
              case 8: reason="乘客取消";break;
              case 9: reason="司机取消";break;
              default:
                break;
            }
            xfSpeech.ttsSpeaking(s + ","+reason);
          }
        }
      }
    }
  }

  private final class GrabOrderSubscriber extends DefaultSubscriber<StriveStatus> {
    @Override public void onCompleted() {
      super.onCompleted();
    }

    @Override public void onError(Throwable e) {
      super.onError(e);
      controlPanelView.showListeningBtn();
      controlPanelView.hideOrderView();
    }

    @Override public void onNext(StriveStatus striveStatus) {
      super.onNext(striveStatus);
      if (striveStatus.getStatus() == StriveStatus.Status.SUCCESS) {
        orderFragmentView.grabSuccess();
        ((SaveOrder) saveOrder).setOrder(orderModel.getOrder());
        saveOrder.execute(new SaveOrderSubscriber());
        xfSpeech.ttsSpeaking("抢单成功！");
        Tools.SetTaxiStatus(true);
      } else if (striveStatus.getStatus() == StriveStatus.Status.FAILED) {
        orderFragmentView.grabFailure();
        xfSpeech.ttsSpeaking("抢单失败！");
      } else if (striveStatus.getStatus() == StriveStatus.Status.INVALID) {
        orderFragmentView.grabFailure();
        xfSpeech.ttsSpeaking("订单无效！");
      } else if (striveStatus.getStatus() == StriveStatus.Status.PROCESSING) {
        LogUtils.e(this, "努力抢单中！");
        //grabOrder.schedule(1, this);
        return;
      }
      isSshow=true;
      controlPanelView.showListeningBtn();
    }
  }

  private final class SaveOrderSubscriber extends DefaultSubscriber<Uri> {
    @Override public void onCompleted() {
      super.onCompleted();
    }

    @Override public void onError(Throwable e) {
      super.onError(e);
    }

    @Override public void onNext(Uri uri) {
      super.onNext(uri);
    }
  }

  private final class DeleteOrderSubscriber extends DefaultSubscriber<Integer> {
    @Override public void onCompleted() {
      super.onCompleted();
    }

    @Override public void onError(Throwable e) {
      super.onError(e);
      ToastUtil.getToast().show("删除失败！");
    }

    @Override public void onNext(Integer integer) {
      super.onNext(integer);
      ToastUtil.getToast().show("删除成功！");
      //isSshow=true;
    }
  }

  private final class CountdownSubscriber extends DefaultSubscriber<Long> {
    private int countdownNum;

    public CountdownSubscriber(int countdown) {
      this.countdownNum = countdown;
    }

    @Override public void onStart() {
      super.onStart();
      num=countdownNum;
      if (!isPause)
      controlPanelView.showGrabBtn(countdownNum);
      LogUtils.e(this, "抢单时间总数：" + countdownNum);
    }

    @Override public void onCompleted() {
      super.onCompleted();
    }

    @Override public void onError(Throwable e) {
      super.onError(e);
    }

    @Override public void onNext(Long l) {
      super.onNext(l);
      Log.e("====","pause---"+isPause);
      --countdownNum;
      if (!isPause)
      controlPanelView.showGrabBtn(countdownNum);
      //isSshow=false;
      num=countdownNum;
      if (countdownNum == 0) {
        controlPanelView.hideOrderView();
        xfSpeech.stop();
        //isClick=false;
        //isSshow=true;
        isSshow=true;
        LogUtils.e(this, "未抢单！");
      }
      LogUtils.e(this, "抢单时间剩余：" + countdownNum);
    }
  }

  private class UpdateSubscriber extends DefaultSubscriber<Integer> {
    @Override public void onCompleted() {
      LogUtils.e(this, "完成");
    }

    @Override public void onError(Throwable e) {
      LogUtils.e(this, e.getMessage());
    }

    @Override public void onNext(Integer d) {
      LogUtils.e(this, "对应id："+d);
    }
  }
  public void setModelApply(int type){
    ((ModelApply) modelApply).setType(type);
    modelApply.execute(new ModelApplySubscriber());
  }
  private class ModelApplySubscriber extends DefaultSubscriber<NetBiz> {
    @Override public void onCompleted() {
      LogUtils.e(this, "完成");
    }

    @Override public void onError(Throwable e) {
      LogUtils.e(this, e.getMessage());
    }

    @Override public void onNext(NetBiz d) {
      LogUtils.e(this, "对应id："+d);
    }
  }
}
