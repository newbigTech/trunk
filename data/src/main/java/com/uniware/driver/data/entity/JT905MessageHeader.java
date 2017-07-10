package com.uniware.driver.data.entity;

import com.uniware.util.Tools;
import io.sting.buffer.StingBuffer;

public class JT905MessageHeader {

  /**
   * 消息ID，无符号整型，2byte
   */
  private short messageId;

  public final short getMessageId() {
    return messageId;
  }

  public final void setMessageId(short value) {
    messageId = value;
  }

  /**
   * 消息体属性=消息体长度,无符号整型，2byte
   */
  private short messageProperty;

  public final short getMessageProperty() {
    return messageProperty;
  }

  public final void setMessageProperty(short value) {
    messageProperty = value;
  }

  /**
   * ISU标识 BCD[6]
   */
  private String isuId;

  public final String getIsuId() {
    return isuId;
  }

  public final void setIsuId(String value) {
    isuId = value;
  }

  /**
   * 接收消息的消息体流水号，无符号整型，2byte
   */
  private short messageSerialNo;

  public final short getMessageSerialNo() {
    return messageSerialNo;
  }

  public final void setMessageSerialNo(short value) {
    messageSerialNo = value;
  }

  public final byte[] WriteToBytes() {
    StingBuffer buff = new StingBuffer();
    buff.putShort(getMessageId());
    buff.putShort(getMessageProperty());
    byte[] isuBytes = Tools.HexString2Bytes(isuId);
    buff.putBytes(isuBytes);
    buff.putShort(getMessageSerialNo());
    return buff.array();
  }

  public final void ReadFromBytes(byte[] headerBytes) {
    StingBuffer buff = new StingBuffer(headerBytes);
    // System.out.println("headerBytes"+Tools.ToHexString(headerBytes));
    setMessageId(buff.getShort());
    setMessageProperty(buff.getShort());
    byte[] isuBytes = buff.getBytes(6);
    setIsuId(
        String.format("%02X", isuBytes[0]) + String.format("%02X", isuBytes[1]) + String.format(
            "%02X", isuBytes[2]) + String.format("%02X", isuBytes[3]) + String.format("%02X",
            isuBytes[4]) + String.format("%02X", isuBytes[5]));
    setMessageSerialNo(buff.getShort());
  }

  @Override public String toString() {
    return "ID："
        + Tools.ToHexString(messageId)
        + " 属性："
        + Tools.ToHexString(messageProperty)
        + " ISU："
        + isuId
        + " 流水号："
        + Tools.ToHexString(messageSerialNo);
  }
}