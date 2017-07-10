package com.uniware.driver.data.entity;

import com.uniware.util.ClassUtils;
import com.uniware.util.Tools;

/**
 *
 */
public class JT905MessageFactory {

  public static JT905MessageBody Create(short messageType, byte[] messageBodyBytes) {
    String nameSpace = JT905MessageFactory.class.getPackage().getName();
    String className = nameSpace + ".Ox" + Tools.ToHexString(messageType);
    JT905MessageBody body = null;
    Object object = ClassUtils.getBean(className);
    if (object != null) {
      body = (JT905MessageBody) object;
      body.ReadFromBytes(messageBodyBytes);
    } else {
      System.out.println(className + "加载失败！");
    }
    return body;
  }
}
