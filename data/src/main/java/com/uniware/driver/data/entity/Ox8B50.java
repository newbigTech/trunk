package com.uniware.driver.data.entity;

import io.sting.buffer.StingBuffer;
import java.io.UnsupportedEncodingException;

/**
 * Created by ayue on 2018/1/23.
 *
 * 接收通知
 */

public class Ox8B50 extends JT905MessageBody {
  /**
   * 是否紧急  1 true ,0 false;
   */
  byte isUrgency;
  /**
   * 是否显示  1 true ,0 false;
   */
  byte isShow;
  /**
   * 是否播报  1 true ,0 false;
   */
  byte isPlay;
  /**
   *消息长度
   */
  byte noticeLength;
  /**
   *消息内容
   */
  String notice;

  @Override public byte[] WriteToBytes() {
    return new byte[0];
  }

  @Override public void ReadFromBytes(byte[] messageBodyBytes) {
    StingBuffer buff = new StingBuffer(messageBodyBytes);
    setIsUrgency(buff.getByte());
    setIsShow(buff.getByte());
    setIsPlay(buff.getByte());
    setNoticeLength(buff.getByte());
    try {
      setNotice(new String(buff.getBytes(getNoticeLength()), "GBK"));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }

  public byte getIsUrgency() {
    return isUrgency;
  }

  public void setIsUrgency(byte isUrgency) {
    this.isUrgency = isUrgency;
  }

  public byte getIsShow() {
    return isShow;
  }

  public void setIsShow(byte isShow) {
    this.isShow = isShow;
  }

  public byte getIsPlay() {
    return isPlay;
  }

  public void setIsPlay(byte isPlay) {
    this.isPlay = isPlay;
  }

  public byte getNoticeLength() {
    return noticeLength;
  }

  public void setNoticeLength(byte noticeLength) {
    this.noticeLength = noticeLength;
  }

  public String getNotice() {
    return notice;
  }

  public void setNotice(String notice) {
    this.notice = notice;
  }

  @Override public String toString() {
    return "Ox8B50{" +
        "isUrgency=" + isUrgency +
        ", isShow=" + isShow +
        ", isPlay=" + isPlay +
        ", noticeLength=" + noticeLength +
        ", 内容='" + notice + '\'' +
        '}';
  }
}
