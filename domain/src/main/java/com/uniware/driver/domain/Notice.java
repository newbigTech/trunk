package com.uniware.driver.domain;

/**
 * Created by ayue on 2018/1/23.
 *
 * 通知
 */

  public class Notice extends NetBiz{

  int isUrgency;
  int isShow;
  int isPlay;
  String content;

  public int getIsUrgency() {
    return isUrgency;
  }

  public void setIsUrgency(int isUrgency) {
    this.isUrgency = isUrgency;
  }

  public int getIsShow() {
    return isShow;
  }

  public void setIsShow(int isShow) {
    this.isShow = isShow;
  }

  public int getIsPlay() {
    return isPlay;
  }

  public void setIsPlay(int isPlay) {
    this.isPlay = isPlay;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override public String toString() {
    return "Notice{" +
        "isUrgency=" + isUrgency +
        ", isShow=" + isShow +
        ", isPlay=" + isPlay +
        ", content='" + content + '\'' +
        '}';
  }
}
