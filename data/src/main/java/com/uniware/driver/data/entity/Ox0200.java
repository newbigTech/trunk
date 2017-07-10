package com.uniware.driver.data.entity;

import io.sting.buffer.StingBuffer;

/**
 * 位置信息汇报
 */
public class Ox0200 extends JT905MessageBody {

  /**
   * 报警标志
   */
  private int alarmFlag;

  public final int getAlarmFlag() {
    return alarmFlag;
  }

  public final void setAlarmFlag(int value) {
    alarmFlag = value;
  }

  /**
   * 状态
   */
  private int taxiStatus;

  public final int getTaxiStatus() {
    return taxiStatus;
  }

  public final void setTaxiStatus(int value) {
    taxiStatus = value;
  }

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

  /**
   * 速度,0.1km/h
   */
  private short speed;

  public final short getSpeed() {
    return speed;
  }

  public final void setSpeed(short value) {
    speed = value;
  }

  /**
   * 方向,0～179，每刻度为两度，正北为0，顺时针
   */
  private short bearing;

  public final short getBearing() {
    return bearing;
  }

  public final void setBearing(short value) {
    bearing = value;
  }

  /**
   * 时间,YY-MM-DD-hh-mm-ss（GMT+8时间，本标准中之后涉及的时间均采用此时区）
   */
  private String time;

  public final String getTime() {
    return time;
  }

  public final void setTime(String value) {
    time = value;
  }

  public byte[] WriteToBytes() {
    StingBuffer buff = new StingBuffer();

    buff.putInt(getAlarmFlag());
    buff.putInt(getTaxiStatus());
    buff.putInt(getLatitude());
    buff.putInt(getLongitude());
    buff.putShort(getSpeed());
    buff.putByte((byte) getBearing());
    buff.putByte(Byte.parseByte(getTime().substring(0, 2), 16));
    buff.putByte(Byte.parseByte(getTime().substring(2, 4), 16));
    buff.putByte(Byte.parseByte(getTime().substring(4, 6), 16));
    buff.putByte(Byte.parseByte(getTime().substring(6, 8), 16));
    buff.putByte(Byte.parseByte(getTime().substring(8, 10), 16));
    buff.putByte(Byte.parseByte(getTime().substring(10, 12), 16));
    return buff.array();
  }

  public void ReadFromBytes(byte[] bytes) {
    StingBuffer buff = new StingBuffer(bytes);

    setAlarmFlag(buff.getInt());
    setTaxiStatus(buff.getInt());
    setLatitude(buff.getInt());
    setLongitude(buff.getInt());
    setSpeed(buff.getShort());
    setBearing(buff.getByte());
    byte[] timeBytes = buff.getBytes(6);
    setTime("20"
        + String.format("%02X", timeBytes[0])
        + "-"
        + String.format("%02X", timeBytes[1])
        + "-"
        + String.format("%02X", timeBytes[2])
        + " "
        + String.format("%02X", timeBytes[3])
        + ":"
        + String.format("%02X", timeBytes[4])
        + ":"
        + String.format("%02X", timeBytes[5]));
  }

  @Override public String toString() {
    StringBuilder sBuilder = new StringBuilder();
    sBuilder.append(
        String.format("报警标示：%1$s,状态：%2$s,经度：%3$s,纬度：%4$s,速度：%5$s,方向：%6$s,时间：%7$s", getAlarmFlag(),
            getTaxiStatus(), getLongitude(), getLatitude(), getSpeed(), getBearing(), getTime()));
    return sBuilder.toString();
  }
}