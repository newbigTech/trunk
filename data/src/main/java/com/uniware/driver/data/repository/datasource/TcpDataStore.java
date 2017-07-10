package com.uniware.driver.data.repository.datasource;

import android.util.Log;
import com.uniware.driver.data.DriverLocation;
import com.uniware.driver.data.entity.JT905Message;
import com.uniware.driver.data.entity.Ox0001;
import com.uniware.driver.data.entity.Ox0200;
import com.uniware.driver.data.entity.Ox0B01;
import com.uniware.driver.data.entity.Ox0B02;
import com.uniware.driver.data.entity.Ox0B08;
import com.uniware.driver.data.entity.Ox8B01;
import com.uniware.driver.data.entity.Ox8B02;
import com.uniware.driver.data.entity.Ox8B09;
import com.uniware.driver.data.exception.NetworkConnectionException;
import com.uniware.driver.data.net.JT905MessageDecoder;
import com.uniware.driver.data.net.JT905MessageEncoder;
import com.uniware.driver.domain.BizObject;
import com.uniware.driver.domain.NetBiz;
import com.uniware.driver.domain.Order;
import com.uniware.driver.domain.OrderStatus;
import com.uniware.driver.domain.StriveStatus;
import io.netty.channel.ChannelHandler;
import io.reactivex.netty.channel.Connection;
import io.reactivex.netty.protocol.tcp.client.TcpClient;
import java.nio.channels.ClosedChannelException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * {@link CloudDataStore} implementation based on connections to the api (Cloud).
 */
@Singleton public class TcpDataStore implements OrderDataStore {

  //private final Context context;
  @Inject DriverLocation driverLocation;
  private Connection<JT905Message, JT905Message> mConnection;
  private String isuID;
  private Subject<Object, Object> bus;
  private String rurl;
  private int rport;

  /**
   * Construct a {@link TcpDataStore} based on connections to the api (Cloud).
   */
  @Inject public TcpDataStore() {
    //this.context = context;
    bus = new SerializedSubject<>(PublishSubject.create());
  }
  //public TcpDataStore(){
  //  bus = new SerializedSubject<>(PublishSubject.create());
  //}

  private void createInput() {
    mConnection.getInput().map(new Func1<JT905Message, BizObject>() {
      @Override public BizObject call(JT905Message jt905Message) {
        if ((jt905Message.getMessageId() & 0xFFFF) != 0x8001) {
          Ox0001 ox0001 = new Ox0001();
          ox0001.setResponseMessageId(jt905Message.getMessageId());
          ox0001.setResponseMessageSerialNo(jt905Message.getHeader().getMessageSerialNo());
          ox0001.setResponseResult((byte) 0x00);
          JT905Message message = JT905Message.builder().body(ox0001).isuId(isuID).build();
          mConnection.write(Observable.just(message)).subscribe(new Action1<Void>() {
            @Override public void call(Void aVoid) {
              Log.e("通用应答：", "发送成功");
            }
          });
        }

        if ((jt905Message.getMessageId() & 0xFFFF) == 0x8B01) {
          Ox8B01 ox8B01 = (Ox8B01) jt905Message.getBody();
          Order order = new Order();
          order.setOid(ox8B01.getBizId() + "");
          if (ox8B01.getBizType()==0){
            order.setType(0);
          }
          else {
            order.setType(1);
          }
          order.setUseTime(ox8B01.getUsingTime());
          order.setPhone(ox8B01.getPhoneNum());
          order.setCallFee(ox8B01.getServiceFee());
          order.setFromAddr(ox8B01.getXfromAddr());
          order.setToAddr(ox8B01.getXtoAddr());
          order.setXfromAddr(ox8B01.getFromAddr());
          order.setXtoAddr(ox8B01.getToAddr());
          order.setFromLat((double) ox8B01.getCustomerLatitude() / 600000);
          order.setFromLng((double) ox8B01.getCustomerLongitude() / 600000);
          order.setToLat((double) ox8B01.getDestinationLatitude() / 600000);
          order.setToLng((double) ox8B01.getDestinationLongitude() / 600000);
          order.setDescription(ox8B01.getBizDescription());
          Log.e("callFee",order.toString());
          //lisenter.setData(order);
          return order;
        } else if ((jt905Message.getMessageId() & 0xFFFF) == 0x8B02) {
          Ox8B02 ox8B02 = (Ox8B02) jt905Message.getBody();
          StriveStatus striveStatus = new StriveStatus();
          striveStatus.setOid(ox8B02.getBizId() + "");
          if (ox8B02.getGrabResult() == 0x00) {
            striveStatus.setStatus(StriveStatus.Status.SUCCESS);
          } else {
            striveStatus.setStatus(StriveStatus.Status.FAILED);
          }
          //lisenter.setData(striveStatus);
          return striveStatus;
        } else if ((jt905Message.getMessageId() & 0xFFFF) == 0x8B09) {
          Ox8B09 ox8B09 = (Ox8B09) jt905Message.getBody();
          OrderStatus status = new OrderStatus();
          switch (ox8B09.getStatus()){
            case 0x03:status.setStatus(3);break;
            case 0x04:status.setStatus(4);break;
            case 0x05:status.setStatus(5);break;
            case 0x06:status.setStatus(6);break;
            case 0x07:status.setStatus(7);break;
            case 0x08:status.setStatus(8);break;
            case 0x09:status.setStatus(9);break;
            default:
              break;
          }
          //lisenter.setData(striveStatus);
          status.setOid(ox8B09.getBizId() + "");
          return status;
        }
        return null;
      }
    }).onErrorResumeNext(new Func1<Throwable, Observable<? extends BizObject>>() {
      @Override public Observable<? extends BizObject> call(Throwable throwable) {
        if (throwable instanceof ClosedChannelException) {
          return Observable.error(new NetworkConnectionException(throwable));
        }
        return Observable.error(throwable);
      }
    }).subscribe(new Observer<BizObject>() {
      @Override public void onCompleted() {
        bus.onCompleted();
      }

      @Override public void onError(Throwable e) {
        bus.onError(e);
      }

      @Override public void onNext(BizObject bizObject) {
        bus.onNext(bizObject);
      }
    });
  }

  public Observable<Boolean> startPush(final String url, final int port) {
    rurl=url;
    rport=port;
    return Observable.create(new Observable.OnSubscribe<Boolean>() {
      @Override public void call(final Subscriber<? super Boolean> subscriber) {
        TcpClient.newClient(url, port).<JT905Message, JT905Message>addChannelHandlerLast("decoder",
            new Func0<ChannelHandler>() {
              @Override public ChannelHandler call() {
                return new JT905MessageDecoder();
              }
            }).<JT905Message, JT905Message>addChannelHandlerLast("encoder",
            new Func0<ChannelHandler>() {
              @Override public ChannelHandler call() {
                return new JT905MessageEncoder();
              }
            }).createConnectionRequest()
            .subscribe(new Observer<Connection<JT905Message, JT905Message>>() {
              @Override public void onCompleted() {
                subscriber.onCompleted();
              }

              @Override public void onError(Throwable e) {
                Log.e("netty", e.toString());
                reconnect(e);
                subscriber.onError(e);
              }

              @Override public void onNext(Connection<JT905Message, JT905Message> connection) {
                if (mConnection == null || !mConnection.unsafeNettyChannel().isActive()) {
                  mConnection = connection;
                  createInput();
                }
                subscriber.onNext(true);
              }
            });
      }
    });
  }

  public Observable<BizObject> recvPush() {
    return bus.ofType(BizObject.class);
  }

  public Observable<Void> sendPush(byte[] bytes) {
    JT905Message message = new JT905Message();
    message.ReadFromBytes(bytes);
    return mConnection.write(Observable.just(message));
  }

  /**
   * 断开自动重新连接
   */
  private void reconnect(Throwable e) {
    //延迟spacingTime秒后进行重连
    Observable.timer(5, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
      @Override public void onCompleted() {

      }

      @Override public void onError(Throwable e) {

      }

      @Override public void onNext(Long aLong) {
        if (mConnection != null) mConnection.closeNow();
        startPush(rurl,rport);
        Log.e("netty","reconnect");
      }
    });
  }

  public Observable<Void> updateLocation(int flag) {
    double lon = driverLocation.getLocation().getLongitude();
    double lat = driverLocation.getLocation().getLatitude();
    float speed = driverLocation.getLocation().getSpeed();
    float direction = driverLocation.getLocation().getBearing();
    int mFlag = driverLocation.getFlag();
    Ox0200 ox0200 = new Ox0200();
    ox0200.setTaxiStatus(mFlag);
    ox0200.setLatitude((int) (lat * 60 * 10000));
    ox0200.setLongitude((int) (lon * 60 * 10000));
    ox0200.setSpeed((short) (speed * 1.852 / 3.6));
    ox0200.setBearing((byte) (direction / 2));
    SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss", Locale.CHINA);
    ox0200.setTime(format.format(new Date()));
    JT905Message jt905Message = JT905Message.builder().body(ox0200).isuId(isuID).build();
    return mConnection.write(Observable.just(jt905Message))
        .onErrorResumeNext(new Func1<Throwable, Observable<? extends Void>>() {
          @Override public Observable<? extends Void> call(Throwable throwable) {
            if (throwable instanceof ClosedChannelException) {
              return Observable.error(new NetworkConnectionException(throwable));
            }
            reconnect(throwable);
            return Observable.error(throwable);
          }
        });
  }

  @Override public Observable<StriveStatus> grabOrder(final String orderId) {
    return Observable.create(new Observable.OnSubscribe<StriveStatus>() {
      @Override public void call(Subscriber<? super StriveStatus> subscriber) {
        Ox0B01 ox0B01 = new Ox0B01();
        ox0B01.setBizId(Integer.parseInt(orderId));
        JT905Message message = JT905Message.builder().body(ox0B01).isuId(isuID).build();
        mConnection.write(Observable.just(message)).subscribe(new Action1<Void>() {
          @Override public void call(Void aVoid) {
            Log.e("抢单：", "发送成功");
          }
        });
        StriveStatus striveStatus = new StriveStatus();
        striveStatus.setStatus(StriveStatus.Status.PROCESSING);
        subscriber.onNext(striveStatus);
      }
    });
  }

  @Override public Observable<NetBiz> completeOrder(final String orderId) {
    return Observable.create(new Observable.OnSubscribe<NetBiz>() {
      @Override public void call(Subscriber<? super NetBiz> subscriber) {
        Ox0B02 ox0B02= new Ox0B02();
        ox0B02.setOid(Integer.parseInt(orderId));
        JT905Message message = JT905Message.builder().body(ox0B02).isuId(isuID).build();
        mConnection.write(Observable.just(message)).subscribe(new Action1<Void>() {
          @Override public void call(Void aVoid) {
            Log.e("订单完成：", "发送成功");
          }
        });
        NetBiz netBiz = new NetBiz();
        netBiz.setErrno(0);
        subscriber.onNext(netBiz);
      }
    });
  }

  @Override public Observable<NetBiz> cancelOrder(final String orderId) {
    return Observable.create(new Observable.OnSubscribe<NetBiz>() {
      @Override public void call(Subscriber<? super NetBiz> subscriber) {
        Ox0B08 ox0B08 = new Ox0B08();
        ox0B08.setOid(Integer.parseInt(orderId));
        ox0B08.setCancelReason((byte) 0x00);
        JT905Message message = JT905Message.builder().body(ox0B08).isuId(isuID).build();
        mConnection.write(Observable.just(message)).subscribe(new Action1<Void>() {
          @Override public void call(Void aVoid) {
            Log.e("订单取消：", "发送成功");
          }
        });
        NetBiz netBiz = new NetBiz();
        netBiz.setErrno(0);
        subscriber.onNext(netBiz);
      }
    });
  }

  public String getIsuID() {
    return isuID;
  }

  public void setIsuID(String isuID) {
    this.isuID = "0" + isuID;
  }
}
