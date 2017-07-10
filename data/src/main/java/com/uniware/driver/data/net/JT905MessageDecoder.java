package com.uniware.driver.data.net;

import android.util.Log;
import com.uniware.driver.data.entity.JT905Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * 解码器
 *
 * @author sj
 */
public class JT905MessageDecoder extends ByteToMessageDecoder {

  /**
   * ************ 数据解析标志位 **************
   */
  private static final int P_START = 1;

  private static final int P_END = 65550; // 最大长度65550 = 65535（包体最大长度） + 12（包头最大长度） + 3（标识位和校验码）;

  private int p_Status = P_END + 1;

  private ByteBuffer buffer = ByteBuffer.allocate(2048);

  private void calculateCapacity(int length) {
    int capacity = buffer.capacity() - buffer.position();
    if (capacity < length || capacity > length * 2) {
      if (buffer.position() > 0) {
        buffer.flip();
        byte[] tmp = new byte[buffer.limit()];
        buffer.get(tmp);
        buffer = ByteBuffer.allocate(length + length / 10);
        buffer.put(tmp);
      } else {
        buffer = ByteBuffer.allocate(length + length / 10);
      }
    }
  }

  public List<JT905Message> Analyse(byte[] data) {
    //calculateCapacity(data.length);
    List<JT905Message> list = new ArrayList();
    for (byte b : data) {
      if (b == (byte) 0x7E) {
        if (p_Status > P_END || p_Status == P_START + 1) {
          p_Status = P_START;
          buffer.clear();
        } else {
          p_Status = P_END;
        }
      } else {
        buffer.put(b);
      }

      if (p_Status == P_END) {
        buffer.flip();
        byte[] tmp = new byte[buffer.limit()];
        buffer.get(tmp);
        buffer.clear();
        JT905Message message = new JT905Message();
        message.ReadFromBytes(tmp);
        list.add(message);
      }
      p_Status++;
    }
    return list;
  }

  @Override protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out)
      throws Exception {
    byte data[] = new byte[in.readableBytes()];
    in.readBytes(data);
    //Log.e("<<<", Arrays.toString(data));
    List list = Analyse(data);
    if (list.size() > 0) {
      out.addAll(list);
      for (Object msg : list)
        Log.e("decode", "<<< " + msg.toString());
    }
  }

  @Override public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
      throws Exception {
    Log.e("exceptionCaught", cause.toString());
  }
}
