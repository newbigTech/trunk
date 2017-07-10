package com.uniware.driver.data.entity;

import io.sting.buffer.StingBuffer;

/**
 * Created by ayue on 2017/5/16.
 * 乘客已送达
 */

public class Ox0B02 extends JT905MessageBody {
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
    return "乘客已送达：" + oid;
  }
}
