package com.uniware.driver.data.entity;

import io.sting.buffer.StingBuffer;
import java.io.UnsupportedEncodingException;

/**
 * 下发抢答结果信息
 */
public class Ox8B01 extends JT905MessageBody {

  /**
   * 业务 ID
   */
  private int bizId;

  /**
   * 业务类型
   */
  private byte bizType;

  /**
   * 用车时间
   */
  private String usingTime;

  /**
   * @Fields 乘客位置经度
   */
  private int customerLongitude;

  /**
   * @Fields 乘客位置纬度
   */
  private int customerLatitude;

  /**
   * @Fields 目的地位置经度
   */
  private int destinationLongitude;

  /**
   * @Fields 目的地位置纬度
   */
  private int destinationLatitude;

  /**
   * @Fields 电召服务费
   */
  private String serviceFee;

  /**
   * @Fields 乘客电话号码
   */
  private String phoneNum;

  private byte fromAddrLength;

  private String fromAddr;

  private byte toAddrLength;

  private String toAddr;



  private byte xfromAddrLength;

  private String xfromAddr;

  private byte xtoAddrLength;

  private String xtoAddr;

  /**
   * 业务描述长度
   */
  private byte bizDescLength;

  /**
   * 业务描述:对乘客要车详细地点的描述
   */
  private String bizDescription;
  /**
   * 距离描述长度
   */
  private byte disDescLength;
  /**
   * 业务描述:对乘客要车距离的描述
   */
  private String disDescription;
  /**
   * 是否指派
   */
  private byte assign;

  @Override public byte[] WriteToBytes() {

    StingBuffer buffer = new StingBuffer();

    buffer.putInt(getBizId());
    buffer.putByte(getBizType());

    buffer.putByte(Byte.parseByte(getUsingTime().substring(0, 2), 16));
    buffer.putByte(Byte.parseByte(getUsingTime().substring(2, 4), 16));
    buffer.putByte(Byte.parseByte(getUsingTime().substring(4, 6), 16));
    buffer.putByte(Byte.parseByte(getUsingTime().substring(6, 8), 16));
    buffer.putByte(Byte.parseByte(getUsingTime().substring(8, 10), 16));
    buffer.putByte(Byte.parseByte(getUsingTime().substring(10, 12), 16));

    buffer.putInt(getCustomerLongitude());
    buffer.putInt(getCustomerLatitude());
    buffer.putInt(getDestinationLongitude());
    buffer.putInt(getDestinationLatitude());

    String str1 = (String) getServiceFee().subSequence(0, 2);
    String str2 = (String) getServiceFee().subSequence(2, 4);

    Byte b0 = Byte.parseByte(str1, 16);
    Byte b1 = Byte.parseByte(str2, 16);

    buffer.putByte(b0);
    buffer.putByte(b1);

    //之前以下2行代码注释掉了
    buffer.putString(getPhoneNum());
    buffer.putByte(getBizDescLength());

    buffer.putString(getBizDescription());

    return buffer.array();
  }

  @Override public void ReadFromBytes(byte[] messageBodyBytes) {

    StingBuffer buffer = new StingBuffer(messageBodyBytes);

    setBizId(buffer.getInt());

    setBizType(buffer.getByte());

    byte[] timeBytes = buffer.getBytes(6);
    setUsingTime(
        String.format("%02X", timeBytes[0]) + String.format("%02X", timeBytes[1]) + String.format(
            "%02X", timeBytes[2]) + String.format("%02X", timeBytes[3]) + String.format("%02X",
            timeBytes[4]) + String.format("%02X", timeBytes[5]));

    setCustomerLongitude(buffer.getInt());

    setCustomerLatitude(buffer.getInt());

    setDestinationLongitude(buffer.getInt());

    setDestinationLatitude(buffer.getInt());

    byte[] calling_fee = buffer.getBytes(2);
    setServiceFee(String.format("%02X", calling_fee[0]) + String.format("%02X", calling_fee[1]));

    byte[] phone = buffer.getBytes(20);
    for (int i = 0; i < phone.length; i++) {
      if (phone[i] == 0x00) {
        setPhoneNum(new String(phone, 0, i));
        break;
      }
    }

    try {
      setFromAddrLength(buffer.getByte());
      setFromAddr(new String(buffer.getBytes(getFromAddrLength()), "GBK"));

      setToAddrLength(buffer.getByte());
      setToAddr(new String(buffer.getBytes(getToAddrLength()), "GBK"));

      setXfromAddrLength(buffer.getByte());
      setXfromAddr(new String(buffer.getBytes(getXfromAddrLength()),"GBK"));

      setXtoAddrLength(buffer.getByte());
      setXtoAddr(new String(buffer.getBytes(getXtoAddrLength()),"GBK"));
      setBizDescLength(buffer.getByte());
      setBizDescription(new String(buffer.getBytes(getBizDescLength()), "GBK"));
      setDisDescLength(buffer.getByte());
      setDisDescription(new String(buffer.getBytes(getDisDescLength()),"GBK"));
      setAssign(buffer.getByte());
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }

  public byte getAssign() {
    return assign;
  }

  public void setAssign(byte assign) {
    this.assign = assign;
  }

  public byte getDisDescLength() {
    return disDescLength;
  }

  public void setDisDescLength(byte disDescLength) {
    this.disDescLength = disDescLength;
  }

  public String getDisDescription() {
    return disDescription;
  }

  public void setDisDescription(String disDescription) {
    this.disDescription = disDescription;
  }

  public final int getBizId() {
    return bizId;
  }

  public final void setBizId(int bizId) {
    this.bizId = bizId;
  }

  public final byte getBizType() {
    return bizType;
  }

  public final void setBizType(byte bizType) {
    this.bizType = bizType;
  }

  public final String getUsingTime() {
    return usingTime;
  }

  public final void setUsingTime(String usingTime) {
    this.usingTime = usingTime;
  }

  public final int getCustomerLongitude() {
    return customerLongitude;
  }

  public final void setCustomerLongitude(int customerLongitude) {
    this.customerLongitude = customerLongitude;
  }

  public final int getCustomerLatitude() {
    return customerLatitude;
  }

  public final void setCustomerLatitude(int customerLatitude) {
    this.customerLatitude = customerLatitude;
  }

  public final int getDestinationLongitude() {
    return destinationLongitude;
  }

  public final void setDestinationLongitude(int destinationLongitude) {
    this.destinationLongitude = destinationLongitude;
  }

  public final int getDestinationLatitude() {
    return destinationLatitude;
  }

  public final void setDestinationLatitude(int destinationLatitude) {
    this.destinationLatitude = destinationLatitude;
  }

  public final String getServiceFee() {
    return serviceFee;
  }

  public final void setServiceFee(String serviceFee) {
    this.serviceFee = serviceFee;
  }

  public final String getPhoneNum() {
    return phoneNum;
  }

  public final void setPhoneNum(String phoneNum) {
    this.phoneNum = phoneNum;
  }

  public byte getFromAddrLength() {
    return fromAddrLength;
  }

  public void setFromAddrLength(byte fromAddrLength) {
    this.fromAddrLength = fromAddrLength;
  }

  public String getFromAddr() {
    return fromAddr;
  }

  public void setFromAddr(String fromAddr) {
    this.fromAddr = fromAddr;
  }

  public byte getToAddrLength() {
    return toAddrLength;
  }

  public void setToAddrLength(byte toAddrLength) {
    this.toAddrLength = toAddrLength;
  }

  public String getToAddr() {
    return toAddr;
  }

  public void setToAddr(String toAddr) {
    this.toAddr = toAddr;
  }

  public byte getBizDescLength() {
    return bizDescLength;
  }

  public void setBizDescLength(byte bizDescLength) {
    this.bizDescLength = bizDescLength;
  }

  public final String getBizDescription() {
    return bizDescription;
  }

  public final void setBizDescription(String bizDescription) {
    this.bizDescription = bizDescription;
  }

  public byte getXfromAddrLength() {
    return xfromAddrLength;
  }

  public void setXfromAddrLength(byte xfromAddrLength) {
    this.xfromAddrLength = xfromAddrLength;
  }

  public String getXfromAddr() {
    return xfromAddr;
  }

  public void setXfromAddr(String xfromAddr) {
    this.xfromAddr = xfromAddr;
  }

  public byte getXtoAddrLength() {
    return xtoAddrLength;
  }

  public void setXtoAddrLength(byte xtoAddrLength) {
    this.xtoAddrLength = xtoAddrLength;
  }

  public String getXtoAddr() {
    return xtoAddr;
  }

  public void setXtoAddr(String xtoAddr) {
    this.xtoAddr = xtoAddr;
  }

  @Override public String toString() {
    return "业务ID："
        + bizId
        + " 业务类型："
        + bizType
        + " 用车时间："
        + usingTime
        + " 乘客纬度："
        + customerLatitude
        + " 乘客经度："
        + customerLongitude
        + " 目的地纬度："
        + destinationLatitude
        + " 目的地经度："
        + destinationLongitude
        + " 电召服务费："
        + serviceFee
        + " 电话："
        + phoneNum
        + " 出发地："
        + fromAddr
        + " 目的地："
        + toAddr
        + " x出发地："
        + xfromAddr
        + " x目的地："
        + xtoAddr
        + " 描述："
        + bizDescription
        +"  距离:"
        +disDescription
        +"  是否指派:"
        +assign;
  }
}
