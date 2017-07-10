package com.uniware.driver.data.entity;

import io.sting.buffer.StingBuffer;

/**
 * 驾驶员取消订单
 */
public class Ox0B08 extends JT905MessageBody {

  public static final byte CANCEL_BY_ACCIDENT = (byte) 0;
  public static final byte CANCEL_BY_TRAFFIC_JAM = (byte) 1;
  public static final byte CANCEL_BY_OTHER_REASON = (byte) 2;

  /**
   * 业务 ID
   */
  private int oid;

  /**
   * @Fields 取消原因
   */
  private byte cancelReason;

  @Override public byte[] WriteToBytes() {

    StingBuffer buff = new StingBuffer();

    buff.putInt(getOid());

    buff.putByte(getCancelReason());

    return buff.array();
  }

  @Override public void ReadFromBytes(byte[] messageBodyBytes) {
    StingBuffer buff = new StingBuffer(messageBodyBytes);

    setOid(buff.getInt());

    setCancelReason(buff.getByte());
  }

  public final int getOid() {
    return oid;
  }

  public final void setOid(int oid) {
    this.oid = oid;
  }

  public final byte getCancelReason() {
    return cancelReason;
  }

  public final void setCancelReason(byte cancelReason) {
    this.cancelReason = cancelReason;
  }

  @Override public String toString() {
    return "订单ID：" + oid + "，取消原因：" + cancelReason;
  }
}
