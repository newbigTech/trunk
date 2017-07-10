package com.uniware.driver.data.net;

import android.util.Log;
import com.uniware.driver.data.entity.JT905Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码器
 *
 * @author sj
 */
public class JT905MessageEncoder extends MessageToByteEncoder<JT905Message> {

  @Override protected void encode(ChannelHandlerContext ctx, JT905Message msg, ByteBuf out)
      throws Exception {
    out.writeBytes(msg.WriteToBytes());
    Log.e("decode", ">>> " + msg.toString());
  }

  @Override public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
      throws Exception {
    Log.e("exceptionCaught", cause.toString());
  }
}
