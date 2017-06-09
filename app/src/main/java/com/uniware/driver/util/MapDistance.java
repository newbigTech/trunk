package com.uniware.driver.util;

/**
 * Created by ayue on 2016/10/9.
 */

public class MapDistance {
  private double DEF_PI = 3.14159265359; // PI
  private double DEF_2PI = 6.28318530712; // 2*PI
  private double DEF_PI180 = 0.01745329252; // PI/180.0
  private double DEF_R = 6370693.5; // radius of earth
  private MapDistance(){}
  private static MapDistance instance;

  public static synchronized MapDistance getInstance(){
    if(instance == null){
      instance = new MapDistance();
    }
    return instance;
  }
  public int getLongDistance(double lon1, double lat1, double lon2, double lat2) {
    double ew1, ns1, ew2, ns2;
    double distance;
    // 角度转换为弧度
    ew1 = lon1 * DEF_PI180;
    ns1 = lat1 * DEF_PI180;
    ew2 = lon2 * DEF_PI180;
    ns2 = lat2 * DEF_PI180;
    // 求大圆劣弧与球心所夹的角(弧度)
    distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1) * Math.cos(ns2)
        * Math.cos(ew1 - ew2);
    // 调整到[-1..1]范围内，避免溢出
    if (distance > 1.0)
      distance = 1.0;
    else if (distance < -1.0)
      distance = -1.0;
    // 求大圆劣弧长度
    distance = DEF_R * Math.acos(distance);
    int dis=(int)distance/1000;
    return dis;
  }
  public double getDoubleDistance(double lon1, double lat1, double lon2, double lat2) {
    double ew1, ns1, ew2, ns2;
    double distance;
    // 角度转换为弧度
    ew1 = lon1 * DEF_PI180;
    ns1 = lat1 * DEF_PI180;
    ew2 = lon2 * DEF_PI180;
    ns2 = lat2 * DEF_PI180;
    // 求大圆劣弧与球心所夹的角(弧度)
    distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1) * Math.cos(ns2)
        * Math.cos(ew1 - ew2);
    // 调整到[-1..1]范围内，避免溢出
    if (distance > 1.0)
      distance = 1.0;
    else if (distance < -1.0)
      distance = -1.0;
    // 求大圆劣弧长度
    distance = DEF_R * Math.acos(distance);
    double dis= distance/1000;
    dis=Math.ceil(dis*10);
    dis=dis/10;
    return dis;
  }

}
