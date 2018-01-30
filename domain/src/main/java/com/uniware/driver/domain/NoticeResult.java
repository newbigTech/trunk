package com.uniware.driver.domain;

import java.util.List;

/**
 * Created by ayue on 2018/1/16.
 */

public class NoticeResult extends NetBiz{

  /**
   * errmsg : OK
   * errno : 0
   * messages : [{"driverTel":"13132505800","message":"13","sendTime":"2018/01/18 13:09:55"},{"driverTel":"13132505800","message":"12","sendTime":"2018/01/18 12:09:55"},{"driverTel":"13132505800","message":"11","sendTime":"2018/01/18 11:09:55"},{"driverTel":"13132505800","message":"1你好","sendTime":"2018/01/18 11:09:42"},{"driverTel":"13132505800","message":"0","sendTime":"2018/01/18 10:09:55"},{"driverTel":"13132505800","message":"9","sendTime":"2018/01/18 09:09:55"},{"driverTel":"13132505800","message":"8","sendTime":"2018/01/18 08:09:55"},{"driverTel":"13132505800","message":"7","sendTime":"2018/01/18 07:09:55"},{"driverTel":"13132505800","message":"6","sendTime":"2018/01/18 06:09:55"},{"driverTel":"13132505800","message":"5","sendTime":"2018/01/18 05:09:55"},{"driverTel":"13132505800","message":"4","sendTime":"2018/01/18 04:09:55"},{"driverTel":"13132505800","message":"3","sendTime":"2018/01/18 03:09:55"},{"driverTel":"13132505800","message":"2我很好","sendTime":"2018/01/18 02:09:55"}]
   */
  private List<MessagesBean> messages;


  public List<MessagesBean> getMessages() {
    return messages;
  }

  public void setMessages(List<MessagesBean> messages) {
    this.messages = messages;
  }

  public static class MessagesBean {
    /**
     * driverTel : 13132505800
     * message : 13
     * sendTime : 2018/01/18 13:09:55
     */
    private int id;
    private String driverTel;
    private String message;
    private String sendTime;
    private boolean isRead;

    public boolean isRead() {
      return isRead;
    }

    public void setRead(boolean read) {
      isRead = read;
    }

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getDriverTel() {
      return driverTel;
    }

    public void setDriverTel(String driverTel) {
      this.driverTel = driverTel;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

    public String getSendTime() {
      return sendTime;
    }

    public void setSendTime(String sendTime) {
      this.sendTime = sendTime;
    }
  }
}
