package com.uniware.driver.gui.ui.controlpanel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

/**
 * Created by jian on 15/11/13.
 */
public abstract class CountdownButton extends RelativeLayout {
  protected Animation mAnimHide = null;
  protected Animation mAnimShow = null;
  private CountdownButton.AnimEndListener mEndHideListener = null;
  private CountdownButton.AnimEndListener mEndShowListener = null;

  public CountdownButton(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  private Animation.AnimationListener mHideAnimListener = new Animation.AnimationListener() {
    public void onAnimationStart(Animation animation) {
    }

    public void onAnimationEnd(Animation animation) {
      CountdownButton.this.setVisibility(GONE);
      if (CountdownButton.this.mEndHideListener != null) {
        CountdownButton.this.mEndHideListener.onAnimEnd();
      }
    }

    public void onAnimationRepeat(Animation animation) {
    }
  };
  private Animation.AnimationListener mShowAnimListener = new Animation.AnimationListener() {
    public void onAnimationStart(Animation animation) {
    }

    public void onAnimationEnd(Animation animation) {
      CountdownButton.this.setVisibility(VISIBLE);
      if (CountdownButton.this.mEndShowListener != null) {
        CountdownButton.this.mEndShowListener.onAnimEnd();
      }
    }

    public void onAnimationRepeat(Animation animation) {
    }
  };

  public void animHide() {
    this.mEndHideListener = null;
    if (this.mAnimHide != null) {
      this.mAnimHide.setAnimationListener(this.mHideAnimListener);
      this.startAnimation(this.mAnimHide);
    } else {
      this.setVisibility(GONE);
    }
  }

  public void animHide(CountdownButton.AnimEndListener listener) {
    this.mEndHideListener = listener;
    if (this.mAnimHide != null) {
      this.mAnimHide.setAnimationListener(this.mHideAnimListener);
      this.startAnimation(this.mAnimHide);
    } else {
      this.setVisibility(GONE);
    }
  }

  public void animShow() {
    this.mEndShowListener = null;
    if (this.mAnimShow != null) {
      this.mAnimShow.setAnimationListener(this.mShowAnimListener);
      this.startAnimation(this.mAnimShow);
    } else {
      this.setVisibility(VISIBLE);
    }
  }

  public void animShow(CountdownButton.AnimEndListener listener) {
    this.mEndShowListener = listener;
    if (this.mAnimShow != null) {
      this.mAnimShow.setAnimationListener(this.mShowAnimListener);
      this.startAnimation(this.mAnimShow);
    } else {
      this.setVisibility(VISIBLE);
    }
  }

  public interface AnimEndListener {
    void onAnimEnd();
  }
}