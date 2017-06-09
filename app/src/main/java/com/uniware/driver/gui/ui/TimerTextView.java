package com.uniware.driver.gui.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by ayue on 2016/11/23.
 */

public class TimerTextView extends TextView implements Runnable{
  public TimerTextView(Context context) {
    super(context);
  }

  public TimerTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public TimerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }
  private long mday, mhour, mmin, msecond;//天，小时，分钟，秒
  private boolean run=false; //是否启动了
  private int time=90;

  public void setTime(int time){
    this.time=time;
  }

  public boolean isRun() {
    return run;
  }

  public void beginRun() {
    this.run = true;
    run();
  }

  public void stopRun(){
    this.run = false;
  }


  @Override
  public void run() {
    //标示已经启动
    if(run){
      this.setClickable(false);
      time--;
      String strTime= time+"s   ";
      this.setText(strTime);
      postDelayed(this, 1000);
      if (time<1){
        this.setText("重新获取");
        this.setClickable(true);
        run=false;
        time=90;
      }
    }else {
      run=false;
    }
  }

}
