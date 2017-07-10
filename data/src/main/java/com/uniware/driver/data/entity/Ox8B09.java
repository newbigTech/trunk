package com.uniware.driver.data.entity;

import io.sting.buffer.StingBuffer;

/**
 * 中心取消订单
 */
public class Ox8B09 extends JT905MessageBody {

  /**
   * 业务 ID
   */
  private int bizId;

  private byte status;

  @Override public byte[] WriteToBytes() {
    StingBuffer buff = new StingBuffer();

    buff.putInt(getBizId());
    buff.putByte(getStatus());
    return buff.array();
  }

  @Override public void ReadFromBytes(byte[] messageBodyBytes) {
    StingBuffer buff = new StingBuffer(messageBodyBytes);

    setBizId(buff.getInt());
    setStatus(buff.getByte());

  }

  public byte getStatus() {
    return status;
  }

  public void setStatus(byte status) {
    this.status = status;
  }

  public final int getBizId() {
    return bizId;
  }

  public final void setBizId(int bizId) {
    this.bizId = bizId;
  }

  @Override public String toString() {
    return "中心取消订单ID" + bizId
        +"状态"+status;
  }
}
