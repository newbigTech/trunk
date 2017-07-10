package com.uniware.driver.data.entity;

import io.sting.buffer.StingBuffer;

/**
 * 驾驶员电召任务完成确认
 */
public class Ox0B07 extends JT905MessageBody {

  /**
   * 业务 ID
   */
  private int oid;

  @Override public byte[] WriteToBytes() {

    StingBuffer buff = new StingBuffer();

    buff.putInt(getOid());

    return buff.array();
  }

  @Override public void ReadFromBytes(byte[] messageBodyBytes) {
    StingBuffer buff = new StingBuffer(messageBodyBytes);

    setOid(buff.getInt());
  }

  public final int getOid() {
    return oid;
  }

  public final void setOid(int oid) {
    this.oid = oid;
  }

  @Override public String toString() {
    return "订单完成ID：" + oid;
  }
}
