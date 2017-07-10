package com.uniware.driver.data;

import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.reactivex.netty.channel.Connection;
import io.reactivex.netty.protocol.tcp.server.ConnectionHandler;
import io.reactivex.netty.protocol.tcp.server.TcpServer;
import org.junit.Test;
import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * Created by jian on 16/03/15.
 */
public class RxNettyServerTest {

  @Test public void rxNettyServerTest() {
    TcpServer<String, String> server;
    server = TcpServer.newServer(60000).<String, String>addChannelHandlerLast("string-decoder",
        new Func0<ChannelHandler>() {
          @Override public ChannelHandler call() {
            return new StringDecoder();
          }
        }).<String, String>addChannelHandlerLast("string-encoder", new Func0<ChannelHandler>() {
      @Override public ChannelHandler call() {
        return new StringEncoder();
      }
    }).start(new ConnectionHandler<String, String>() {
      @Override public Observable<Void> handle(Connection<String, String> newConnection) {
        return newConnection.writeStringAndFlushOnEach(
            newConnection.getInput().map(new Func1<String, String>() {
              @Override public String call(String s) {
                System.out.println("receive:" + s);
                return "echo=> " + s;
              }
            }));
      }
    });
    server.awaitShutdown();
  }
}
