package com.uniware.driver.data.entity;

import io.sting.buffer.StingBuffer;

/**
 * 下发抢答结果信息
 */
public class Ox8B02 extends JT905MessageBody {

  /**
   * 业务 ID
   */
  private int bizId;

  /**
   * 抢单结果：0：成功；1：失败；2：无效
   */
  private byte grabResult;

  @Override public byte[] WriteToBytes() {

    StingBuffer buffer = new StingBuffer();

    buffer.putInt(getBizId());
    buffer.putByte(getGrabResult());

    return buffer.array();
  }

  @Override public void ReadFromBytes(byte[] messageBodyBytes) {

    StingBuffer buffer = new StingBuffer(messageBodyBytes);

    setBizId(buffer.getInt());

    setGrabResult(buffer.getByte());
  }

  public final int getBizId() {
    return bizId;
  }

  public final void setBizId(int bizId) {
    this.bizId = bizId;
  }

  public final byte getGrabResult() {
    return grabResult;
  }

  public final void setGrabResult(byte grabResult) {
    this.grabResult = grabResult;
  }

  @Override public String toString() {
    return "业务ID：" + bizId + " 抢单结果：" + grabResult;
  }
}
