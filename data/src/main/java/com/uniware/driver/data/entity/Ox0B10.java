package com.uniware.driver.data.entity;

import io.sting.buffer.StingBuffer;

/**
 * Created by ayue on 2017/11/24.
 */

public class Ox0B10 extends JT905MessageBody  {

  /**
   * 模式UINT8 1 普通，2 指派，3 定点
   */
  private byte type;
  /**
   * 地址 id
   */
  private int addressId;
  /**
   * 纬度,以度为单位的纬度值乘以10的6次方，精确到百万分之一度
   */
  private int latitude;

  public final int getLatitude() {
    return latitude;
  }

  public final void setLatitude(int value) {
    latitude = value;
  }

  /**
   * 经度,以度为单位的经度值乘以10的6次方，精确到百万 分之一度
   */
  private int longitude;

  public final int getLongitude() {
    return longitude;
  }

  public final void setLongitude(int value) {
    longitude = value;
  }


  @Override public byte[] WriteToBytes() {
    StingBuffer buff = new StingBuffer();
    buff.putByte(getType());
    buff.putInt(getAddressId());
    buff.putInt(getLatitude());
    buff.putInt(getLongitude());
    return buff.array();
  }

  @Override public void ReadFromBytes(byte[] messageBodyBytes) {

  }

  public int getAddressId() {
    return addressId;
  }

  public void setAddressId(int addressId) {
    this.addressId = addressId;
  }

  public byte getType() {
    return type;
  }

  public void setType(byte type) {
    this.type = type;
  }

  @Override public String toString() {
    return "Ox0B10{" +
        "type=" + type +
        ", addressId=" + addressId +
        ", latitude=" + latitude +
        ", longitude=" + longitude +
        '}';
  }
}
