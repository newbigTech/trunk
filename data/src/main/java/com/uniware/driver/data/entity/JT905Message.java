package com.uniware.driver.data.entity;

import com.uniware.util.Tools;
import io.sting.buffer.StingBuffer;
import java.security.InvalidParameterException;
import java.util.concurrent.atomic.AtomicInteger;

public class JT905Message {
  /**
   * 报文头
   */
  private JT905MessageHeader header = new JT905MessageHeader();

  public final JT905MessageHeader getHeader() {
    return header;
  }

  public final void setHeader(JT905MessageHeader value) {
    header = value;
  }

  private com.uniware.driver.data.entity.JT905MessageBody body;

  public final com.uniware.driver.data.entity.JT905MessageBody getBody() {
    return body;
  }

  public final void setBody(com.uniware.driver.data.entity.JT905MessageBody value) {
    body = value;
  }

  private static final byte _PrefixID = 0x7E;

  public static byte getPrefixID() {
    return _PrefixID;
  }

  public final short getMessageId() {
    return getHeader().getMessageId();
  }

  public String getIsuId() {
    if (header != null) return header.getIsuId();
    return "";
  }

  private String packetDescr;

  public final String getPacketDescr() {
    return packetDescr;
  }

  public final void setPacketDescr(String value) {
    packetDescr = value;
  }

  private String errorMessage;

  public final String getErrorMessage() {
    return errorMessage;
  }

  public final void setErrorMessage(String value) {
    errorMessage = value;
  }

  public final byte[] WriteToBytes() {
    StingBuffer buff = new StingBuffer();
    byte[] bodyBytes = null;
    if (getBody() != null) {
      bodyBytes = getBody().WriteToBytes();
    }
    if (bodyBytes != null) {
      header.setMessageProperty((short) bodyBytes.length);
      byte[] headerBytes = header.WriteToBytes();
      buff.putBytes(headerBytes);
      buff.putBytes(bodyBytes);
    } else {
      header.setMessageProperty((short) 0);
      byte[] headerBytes = header.WriteToBytes();
      buff.putBytes(headerBytes);
    }
    byte[] messageBytes = buff.array();
    byte checkSum = GetCheckXor(messageBytes, 0, messageBytes.length);
    buff.putByte(checkSum);
    byte[] escapedBytes = Escape(buff.array()); // 转义
    buff.clear();
    buff.putByte(_PrefixID);
    buff.putBytes(escapedBytes);
    buff.putByte(_PrefixID);

    byte[] data = buff.array();
    this.packetDescr = Tools.ToHexString(data);
    return data;
  }

  public final void ReadFromBytes(byte[] messageBytes) {
    this.packetDescr = Tools.ToHexString(messageBytes);
    byte[] validMessageBytes = UnEscape(messageBytes);
    try {
      // 检测校验码
      byte xor = GetCheckXor(validMessageBytes, 0, validMessageBytes.length - 1);
      byte realXor = validMessageBytes[validMessageBytes.length - 1];
      if (xor == realXor) {
        byte[] headerBytes = new byte[12];
        System.arraycopy(validMessageBytes, 0, headerBytes, 0, headerBytes.length);
        header.ReadFromBytes(headerBytes);
        if ((header.getMessageProperty() & 0xFFFF) > 0) {// java没有无符号整形 超过7FFF会变负数 所以&0xFFFF
          byte[] bodyBytes = new byte[header.getMessageProperty()];
          System.arraycopy(validMessageBytes, 12, bodyBytes, 0, bodyBytes.length);
          setBody(com.uniware.driver.data.entity.JT905MessageFactory.Create(header.getMessageId(), bodyBytes));
        }
      } else {
        setErrorMessage("校验码不正确");
        System.out.println(getErrorMessage() + ":" + Tools.ToHexFormatString(messageBytes));
        System.out.println(getErrorMessage() + ":" + Tools.ToHexFormatString(validMessageBytes));
        return;
      }
    } catch (Exception ex) {
      System.out.println("T808Message : ReadFromBytes() " + Tools.ToHexFormatString(messageBytes));
      setErrorMessage("解析异常:" + ex.getMessage());
      System.out.println(getErrorMessage());
      return;
    }
  }

  /**
   * 获取校验和
   */
  public byte GetCheckXor(byte[] data, int pos, int len) {
    byte A = 0;
    for (int i = pos; i < len; i++) {
      A ^= data[i];
    }
    return A;
  }

  /**
   * 将标识字符的转义字符还原
   */
  private byte[] UnEscape(byte[] data) {
    StingBuffer buff = new StingBuffer();
    for (int i = 0; i < data.length; i++) {
      if (data[i] == 0x7D) {
        if (data[i + 1] == 0x01) {
          buff.putByte((byte) 0x7D);
          i++;
        } else if (data[i + 1] == 0x02) {
          buff.putByte((byte) 0x7E);
          i++;
        }
      } else {
        buff.putByte(data[i]);
      }
    }

    byte[] a = buff.array();

    return a;
  }

  /**
   * 加入标示符的转义进行封装
   */
  private byte[] Escape(byte[] data) {
    StingBuffer buff = new StingBuffer();
    for (int j = 0; j < data.length; j++) {
      if (data[j] == 0x7D) {
        buff.putByte((byte) 0x7D);
        buff.putByte((byte) 0x01);
      } else if (data[j] == 0x7E) {
        buff.putByte((byte) 0x7D);
        buff.putByte((byte) 0x02);
      } else {
        buff.putByte(data[j]);
      }
    }

    return buff.array();
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    /**
     * 消息ID，无符号整型，2byte
     */
    private short messageId;
    /**
     * ISU标识 BCD[6]
     */
    private String isuId;
    /**
     * 接收消息的消息体流水号，无符号整型，2byte
     */
    private short messageSerialNo;

    private com.uniware.driver.data.entity.JT905MessageBody body;

    private Builder() {
    }

    public JT905Message build() {
      if (body == null && messageId == 0) {
        throw new IllegalStateException("messageId must be set");
      }
      if (messageId == 0) {
        messageId = body.getId();
      }
      if (isuId == null) {
        throw new IllegalStateException("isuId must be set");
      }
      if (messageSerialNo == 0) {
        messageSerialNo = getSendSerialNo();
      }
      JT905Message taximeterMessage = new JT905Message();
      taximeterMessage.getHeader().setMessageId(messageId);
      taximeterMessage.getHeader().setIsuId(isuId);
      taximeterMessage.getHeader().setMessageSerialNo(messageSerialNo);
      taximeterMessage.setBody(body);
      return taximeterMessage;
    }

    public Builder messageId(short messageId) {
      if (messageId == 0) {
        throw new InvalidParameterException("messageId");
      }
      this.messageId = messageId;
      return this;
    }

    public Builder isuId(String isuId) {
      if (isuId == null) {
        throw new InvalidParameterException("isuId");
      }
      this.isuId = isuId;
      return this;
    }

    public Builder messageSerialNo(short messageSerialNo) {
      this.messageSerialNo = messageSerialNo;
      return this;
    }

    public Builder body(com.uniware.driver.data.entity.JT905MessageBody body) {
      if (body == null) {
        throw new NullPointerException("body");
      }
      this.body = body;
      return this;
    }
  }

  /**
   * 发送消息的消息体流水号，从0开始循环累加。支持同步自增。
   */
  private static AtomicInteger sendSerialNo = new AtomicInteger(0);

  private static short getSendSerialNo() {
    if (sendSerialNo.get() > Short.MAX_VALUE) {
      sendSerialNo.set(0);
    }
    return (short) sendSerialNo.getAndIncrement();
  }

  public String toString() {
    StringBuilder sBuilder = new StringBuilder();
    if (header != null) {
      sBuilder.append("消息头{" + header.toString() + "}");
    }
    if (body != null) sBuilder.append(" 消息体{" + body.toString() + "}");
    return sBuilder.toString();
  }
}