package com.uniware.driver.data;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.reactivex.netty.channel.Connection;
import io.reactivex.netty.protocol.tcp.client.TcpClient;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * Created by jian on 16/03/15.
 */
public class RxNettyClientTest {
  Connection<String, String> mConnection;

  @Test public void rxNettyTest1() {
    //"uniwaredev.6655.la", 9556
    TcpClient.newClient("10.144.123.110", 60000)./*<ByteBuf, String>addChannelHandlerLast("string-decoder",
        new Func0<ChannelHandler>() {
          @Override public ChannelHandler call() {
            return new StringLineDecoder();
          }
        }).*/createConnectionRequest().flatMap(
        new Func1<Connection<ByteBuf, ByteBuf>, Observable<?>>() {
          @Override
          public Observable<?> call(Connection<ByteBuf, ByteBuf> byteBufByteBufConnection) {
            return byteBufByteBufConnection.writeString(Observable.just("Hello World!"))
                .cast(ByteBuf.class)
                .concatWith(byteBufByteBufConnection.getInput());
          }
        }).take(10).toBlocking().forEach(new Action1<Object>() {
      @Override public void call(Object o) {
        System.out.println("Test：" + ((ByteBuf) o).toString(Charset.defaultCharset()));
      }
    });
  }

  @Test public void rxNettyTest2() {
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
                rxNettyTest3();
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
        })/*forEach(new Action1<Object>() {
      @Override public void call(Object o) {
        System.out.println("Test：" + o.toString());
        mConnection.writeString(Observable.just("echo:" + o))
            .onBackpressureBuffer()
            .subscribe(new Action1<Void>() {
              @Override public void call(Void aVoid) {

              }
            });
      }
    })*/;
    while (true) {

    }
  }

  @Test public void rxNettyTest3() {
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
                rxNettyTest3();
              }
            });
          }

          @Override public void onNext(String s) {
            System.out.println("onNext " + s);
            mConnection.writeString(Observable.just("echo:" + s)).subscribe(new Action1<Void>() {
              @Override public void call(Void aVoid) {

              }
            });
          }
        });
  }

  public Observable<Boolean> connect(final String url, final int port) {
    return Observable.create(new Observable.OnSubscribe<Boolean>() {
      @Override public void call(final Subscriber<? super Boolean> subscriber) {
        TcpClient.newClient(url, port).<String, String>addChannelHandlerLast("decoder",
            new Func0<ChannelHandler>() {
              @Override public ChannelHandler call() {
                return new StringDecoder();
              }
            }).<String, String>addChannelHandlerLast("encoder", new Func0<ChannelHandler>() {
          @Override public ChannelHandler call() {
            return new StringEncoder();
          }
        }).createConnectionRequest().subscribe(new Observer<Connection<String, String>>() {
          @Override public void onCompleted() {
            subscriber.onCompleted();
          }

          @Override public void onError(Throwable e) {
            subscriber.onError(e);
          }

          @Override public void onNext(Connection<String, String> connection) {
            mConnection = connection;
            subscriber.onNext(true);
          }
        });
      }
    });
  }

  public Observable<String> receive() {
    if (mConnection != null) {
      return mConnection.getInput();
    }
    return null;
  }

  public Observable<Void> send(String s) {
    return mConnection.writeString(Observable.just(s));
  }

  @Test public void rxNettyClientTest() {
    connect("localhost", 60000).subscribe(new Observer<Boolean>() {
      @Override public void onCompleted() {

      }

      @Override public void onError(Throwable e) {
        //reconnect
        Observable.timer(1, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
          @Override public void call(Long aLong) {
            if (mConnection != null) mConnection.closeNow();
            rxNettyClientTest();
          }
        });
        System.out.println("reconnect");
      }

      @Override public void onNext(Boolean aBoolean) {
        //send data
        send("hello world!").subscribe(new Action1<Void>() {
          @Override public void call(Void aVoid) {
            System.out.println("send success!");
          }
        });
        //receive data
        receive().subscribe(new Observer<String>() {
          @Override public void onCompleted() {

          }

          @Override public void onError(Throwable e) {
            //reconnect
            Observable.timer(1, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
              @Override public void call(Long aLong) {
                if (mConnection != null) mConnection.closeNow();
                rxNettyClientTest();
              }
            });
            System.out.println("reconnect");
          }

          @Override public void onNext(String s) {
            System.out.println("receive:" + s);
          }
        });
      }
    });

    while (true) {

    }
  }
}
