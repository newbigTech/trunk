package com.uniware.driver.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jian on 15/11/20.
 */
public class Order extends BizObject implements Comparable<Order> {

  public static final byte BIZ_TYPE_IMMEDIATELY = (byte) 0;
  public static final byte BIZ_TYPE_BOOK = (byte) 1;
  public static final byte BIZ_TYPE_DESIGNATE = (byte) 2;

  public static final int STATUS_START = 0;
  public static final int STATUS_GO_PICK = 1;
  public static final int STATUS_ARRIVE_PSNGER1_LOCATION = 2;
  public static final int STATUS_ARRIVE_PSNGER2_LOCATION = 3;
  public static final int STATUS_DRIVING = 4;
  public static final int STATUS_ARRIVED = 99;
  public static final int STATUS_END = 100;
  public static final int STATUS_CANCEL = 101;
  public static final int STATUS_CANCEL_DRIVER = 105;
  public static final int STATUS_TIMROUT = 103;
  public static final int STATUS_CANCEL_PASSENGER =106;
  public static final int STATUS_DISMISS_DRIVER=109;

  public Order() {
  }

  private String oid;
  private int typeNum;
  private int type;
  /**
   * 用车时间
   */
  private String useTime;

  private int status;

  private String fromAddr;

  private String toAddr;
  /**
   * 乘客位置经度
   */
  private double fromLng;
  /**
   * 乘客位置纬度
   */
  private double fromLat;

  private double toLng;

  private double toLat;

  /**
   * 服务电召飞
   */
  private String callFee;

  /**
   * 乘客ID
   */
  private String passengerId;

  /**
   * 乘客姓名
   */
  private String passengerName;

  /**
   * 乘客性别：MALE男；FEMALE女；QT其他
   */
  private String passengerGender;

  private String passengerAttr;

  /**
   * 乘客电话号码
   */
  private String phone;

  private String xfromAddr;

  private String xtoAddr;

  /**
   * 业务描述:对乘客要车详细地点的描述
   */
  private String description;

  private String descrTimer;

  private double distances;

  public String getXtoAddr() {
    return xtoAddr;
  }

  public void setXtoAddr(String xtoAddr) {
    this.xtoAddr = xtoAddr;
  }

  public String getXfromAddr() {
    return xfromAddr;
  }

  public void setXfromAddr(String xfromAddr) {
    this.xfromAddr = xfromAddr;
  }

  public String getDescrTimer() {
    return descrTimer;
  }

  public void setDescrTimer(String descrTimer) {
    this.descrTimer = descrTimer;
  }

  public void setTypeNum(int typeNum) {
    this.typeNum = typeNum;
  }

  public double getDistances() {
    return distances;
  }

  public void setDistances(double distances) {
    this.distances = distances;
  }

  public String getOid() {
    return oid;
  }

  public void setOid(String oid) {
    this.oid = oid;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getFromAddr() {
    return fromAddr;
  }

  public void setFromAddr(String fromAddr) {
    this.fromAddr = fromAddr;
  }

  public String getToAddr() {
    return toAddr;
  }

  public void setToAddr(String toAddr) {
    this.toAddr = toAddr;
  }

  public String getUseTime() {
    return useTime;
  }

  public void setUseTime(String useTime) {
    this.useTime = useTime;
  }

  public double getFromLng() {
    return fromLng;
  }

  public void setFromLng(double fromLng) {
    this.fromLng = fromLng;
  }

  public double getFromLat() {
    return fromLat;
  }

  public void setFromLat(double fromLat) {
    this.fromLat = fromLat;
  }

  public double getToLng() {
    return toLng;
  }

  public void setToLng(double toLng) {
    this.toLng = toLng;
  }

  public double getToLat() {
    return toLat;
  }

  public void setToLat(double toLat) {
    this.toLat = toLat;
  }

  public String getCallFee() {
    return callFee;
  }

  public void setCallFee(String callFee) {
    this.callFee = callFee;
  }

  public String getPassengerId() {
    return passengerId;
  }

  public void setPassengerId(String passengerId) {
    this.passengerId = passengerId;
  }

  public String getPassengerName() {
    return passengerName;
  }

  public void setPassengerName(String passengerName) {
    this.passengerName = passengerName;
  }

  public String getPassengerGender() {
    return passengerGender;
  }

  public void setPassengerGender(String passengerGender) {
    this.passengerGender = passengerGender;
  }

  public String getPassengerAttr() {
    return passengerAttr;
  }

  public void setPassengerAttr(String passengerAttr) {
    this.passengerAttr = passengerAttr;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getTypeNum() {
    return typeNum;
  }

  public String buildTtsStr() {
    StringBuilder stringBuilder = new StringBuilder();
    switch (type) {
      case 0:
        stringBuilder.append("即时，据您"+distances+"公里,");

        typeNum=1;
        break;
      case 1:
      case 2:
      case 3:
        SimpleDateFormat sdf=new SimpleDateFormat("yyMMddHHmmss", Locale.CHINA);
        String time=sdf.format(new Date());
        DateFormat df = new SimpleDateFormat("yyMMddHHmmss");
        try
        {

          Date d2 = df.parse(time);
          Date d1 = df.parse("20"+useTime);
          long diff = d1.getTime() - d2.getTime();
          long days = diff / (1000 * 60 * 60 * 24);
          long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
          //if(hours<4){
          //  stringBuilder.append("短约，");
          //  typeNum=2;
          //}
          //else{
          //  stringBuilder.append("长约，");
          //  typeNum=3;
          //}

          if (callFee.equals("0500")){
            stringBuilder.append("短约，");
            typeNum=2;
          }else if (callFee.equals("0600")){
            stringBuilder.append("长约，");
            typeNum=3;
          }
          if (time.substring(0,6).equals(useTime.substring(0,6))){
            descrTimer="今天"+useTime.substring(6,8)+"时"+useTime.substring(8,10)+"分";
            stringBuilder.append("今天"+useTime.substring(6,8)+"时"+useTime.substring(8,10)+"分");
          //}else if (days==1){
          //  stringBuilder.append("明天"+time.substring(6,8)+"时"+time.substring(8,10)+"分,");
          }
          else {
            descrTimer=useTime.substring(2,4)+"月"+useTime.substring(4,6)+"日"+useTime.substring(6,8)+"时"+useTime.substring(8,10)+"分";
            stringBuilder.append(useTime.substring(2,4)+"月"+useTime.substring(4,6)+"日"+useTime.substring(6,8)+"时"+useTime.substring(8,10)+"分,");
          }
        }
        catch (Exception e)
        {
          stringBuilder.append("预约，");
        }
        break;
    }

    if (fromAddr != null && toAddr != null) {
      //stringBuilder.append("距您车程5公里，");//平台计算距离比较合适
      stringBuilder.append("从" + xfromAddr + "到" + xtoAddr);
    }
    if (description!=null&&!description.equals("无")){
      stringBuilder.append(",乘客需求 "+description);
    }
    return stringBuilder.toString();
  }

  @Override public String toString() {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("***** Order Model Details *****\n");
    stringBuilder.append("id=" + this.oid + "\n");
    stringBuilder.append("status=" + this.status + "\n");
    stringBuilder.append("type=" + this.type + "\n");
    stringBuilder.append("fromAddr=" + this.fromAddr + "\n");
    stringBuilder.append("toAddr=" + this.toAddr + "\n");
    stringBuilder.append("useTime=" + this.useTime + "\n");
    stringBuilder.append("description=" + this.description + "\n");
    stringBuilder.append("*******************************");

    return stringBuilder.toString();
  }

  @Override public int compareTo(Order order) {
    if (order == null) {
      return 1;
    }
    if (this != order) {
      return oid.compareTo(order.oid);
    }
    return 0;
  }
}
