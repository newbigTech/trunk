package com.uniware.driver.gui.ui.controlpanel;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Arrays;

/**
 * Created by jian on 15/11/13.
 */
public abstract class CountdownLayout extends RelativeLayout {
  private static final int COUNT_DOWN_INTERVAL = 1000;
  private int countX;
  private boolean mCanceled;
  private int mCountStringResId;
  private CountdownListener mCountdownListener;
  private Runnable mCountdownRunnable;
  private boolean mIsCounting;
  protected int mMaxCount;
  private int mPresetMaxCount;
  private boolean mStop;
  private TextView mTxtCountdown;

  public CountdownLayout(Context context) {
    super(context);
    this.initView();
  }

  public CountdownLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.initView();
  }

  public CountdownLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.initView();
  }

  private void countdown() {
    int var1 = this.mMaxCount;
    this.mMaxCount = var1 - 1;
    this.updateCountdown(var1);
    this.postDelayed(this.mCountdownRunnable, 1000L);
  }

  private void createCountdownRunnable() {
    this.mCountdownRunnable = new Runnable() {
      private int b;

      {
        this.b = CountdownLayout.this.countX;
      }

      public void run() {
        if(this.b == CountdownLayout.this.countX) {
          if(CountdownLayout.this.mCanceled) {
            CountdownLayout.this.removeCallbacks(CountdownLayout.this.mCountdownRunnable);
          } else if(CountdownLayout.this.mMaxCount > 0 && !CountdownLayout.this.mStop) {
            CountdownLayout.this.countdown();
          } else {
            CountdownLayout.this.onCountdownFinish();
            CountdownLayout.this.removeCallbacks(CountdownLayout.this.mCountdownRunnable);
          }
        }
      }
    };
  }

  private void initView() {
    this.createCountdownRunnable();
  }

  private void notifyCountdown(int count) {
    if(this.mCountdownListener != null) {
      this.mCountdownListener.onCountdown(count);
    }
  }

  private void onCountdownFinish() {
    this.mIsCounting = false;
    this.mMaxCount = this.mPresetMaxCount;
    if(this.mCountdownListener != null) {
      this.mCountdownListener.onCountdownFinish();
    }
  }

  private void updateCountdown(int count) {
    String countText = String.valueOf(count);
    if(this.mCountStringResId != 0) {
      countText = getContext().getString(mCountStringResId, count);
    }

    this.mTxtCountdown.setText(countText);
    this.notifyCountdown(count);
  }

  public void cancelCountdown() {
    this.mCanceled = true;
  }

  public void dismiss() {
    this.cancelCountdown();
    this.setVisibility(GONE);
  }

  public void hide() {
    this.stopCountdown();
    this.setVisibility(GONE);
  }

  public void init(TextView textView, int count) {
    this.mTxtCountdown = textView;
    this.mMaxCount = count;
    this.mPresetMaxCount = count;
  }

  public void init(TextView textView, int resId, int count) {
    this.mTxtCountdown = textView;
    this.mCountStringResId = resId;
    this.mMaxCount = count;
    this.mPresetMaxCount = count;
  }

  protected boolean isCounting() {
    return this.mIsCounting;
  }

  public void onDestroy() {
    this.mCountdownRunnable = null;
    this.mCountdownListener = null;
    this.stopCountdown();
  }

  public void setCountDownListener(CountdownListener listener) {
    this.mCountdownListener = listener;
  }

  public void setMaxCountdown(int count) {
    this.mMaxCount = count;
    this.mPresetMaxCount = count;
  }

  public void show() {
    this.setVisibility(VISIBLE);
  }

  public void startCountdown() {
    this.mStop = false;
    this.mCanceled = false;
    this.mIsCounting = true;
    ++this.countX;
    this.createCountdownRunnable();
    this.countdown();
  }

  public void stopCountdown() {
    this.mStop = true;
  }
}