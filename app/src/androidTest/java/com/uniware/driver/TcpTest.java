package com.uniware.driver;

import android.test.InstrumentationTestCase;
import android.util.Log;
import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.reactivex.netty.channel.Connection;
import io.reactivex.netty.protocol.tcp.client.TcpClient;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * Created by jian on 16/03/21.
 */
public class TcpTest extends InstrumentationTestCase {

  Connection<String, String> mConnection;

  public void test() {
    Log.e("startPush", "current thread id=" + Thread.currentThread());
    TcpClient.newClient("10.144.123.110", 60000).<String, String>addChannelHandlerLast(
        "string-decoder", new Func0<ChannelHandler>() {
          @Override public ChannelHandler call() {
            return new StringDecoder();
          }
        }).<String, String>addChannelHandlerLast("string-encoder", new Func0<ChannelHandler>() {
      @Override public ChannelHandler call() {
        return new StringEncoder();
      }
    }).createConnectionRequest()
        .flatMap(new Func1<Connection<String, String>, Observable<String>>() {
          @Override public Observable<String> call(Connection<String, String> connection) {
            mConnection = connection;
            mConnection.closeListener().subscribe(new Action1<Void>() {
              @Override public void call(Void aVoid) {
                System.out.println("close");
              }
            });
            return connection.getInput();
          }
        })
        .take(10)
        .toBlocking()
        .subscribe(new Observer<String>() {
          @Override public void onCompleted() {
            System.out.println("onCompleted");
          }

          @Override public void onError(Throwable e) {
            System.out.println("onError");
            Observable.timer(1, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
              @Override public void call(Long aLong) {
                if (mConnection != null) mConnection.closeNow();
                testNetty();
              }
            });
          }

          @Override public void onNext(String s) {
            System.out.println("onNext " + s);
            mConnection.writeString(Observable.just("echo:" + s))
                .onBackpressureBuffer()
                .subscribe(new Action1<Void>() {
                  @Override public void call(Void aVoid) {

                  }
                });
          }
        });
    while (true) {

    }
  }

  public void testNetty() {
    Log.e("startPush", "current thread id=" + Thread.currentThread());
    TcpClient.newClient("uniwaredev.6655.la", 6000).<String, String>addChannelHandlerLast(
        "string-decoder", new Func0<ChannelHandler>() {
          @Override public ChannelHandler call() {
            return new StringDecoder();
          }
        }).<String, String>addChannelHandlerLast("string-encoder", new Func0<ChannelHandler>() {
      @Override public ChannelHandler call() {
        return new StringEncoder();
      }
    }).createConnectionRequest()
        .flatMap(new Func1<Connection<String, String>, Observable<String>>() {
          @Override public Observable<String> call(Connection<String, String> connection) {
            mConnection = connection;
            return connection.getInput();
          }
        })
        .take(10)
        .subscribe(new Observer<String>() {
          @Override public void onCompleted() {
            System.out.println("onCompleted");
          }

          @Override public void onError(Throwable e) {
            System.out.println("onError");
            Observable.timer(1, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
              @Override public void call(Long aLong) {
                if (mConnection != null) mConnection.closeNow();
                testNetty();
              }
            });
          }

          @Override public void onNext(String s) {
            System.out.println("onNext " + s);
            mConnection.writeString(Observable.just("echo:" + s)).subscribe(new Action1<Void>() {
              @Override public void call(Void aVoid) {
                Log.e("startPush", "current thread id=" + Thread.currentThread());
              }
            });
          }
        });
  }
}
