package com.uniware.driver.gui.ui.controlpanel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.uniware.driver.R;

/**
 * Created by jian on 15/11/13.
 */
public class SettingScaleButton extends RelativeLayout {
  private ImageView mBtn_view;
  private TextView mTxtBtnView;
  private Animation mAnimation;
  private Animation.AnimationListener animationShowListener = new Animation.AnimationListener() {
    public void onAnimationEnd(Animation var1) {
      SettingScaleButton.this.mBtn_view.setImageResource(
          R.drawable.main_control_panel_btn_mode_normal);
    }

    public void onAnimationRepeat(Animation var1) {
    }

    public void onAnimationStart(Animation var1) {
    }
  };
  private Animation.AnimationListener animationHideListener = new Animation.AnimationListener() {
    public void onAnimationEnd(Animation var1) {
      SettingScaleButton.this.mBtn_view.setImageResource(
          R.drawable.main_control_panel_btn_mode_normal);
      SettingScaleButton.this.mTxtBtnView.setTextColor(
          SettingScaleButton.this.getResources().getColor(R.color.setting_order_mode_gray));
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
    }
  };

  public SettingScaleButton(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView();
  }

  private void initView() {
    inflate(this.getContext(), R.layout.main_control_panel_setting_scale_button, this);
    this.mTxtBtnView = (TextView) findViewById(R.id.txt_btn_view);
    this.mTxtBtnView.setTextColor(this.getResources().getColor(R.color.setting_order_mode_gray));
    this.mBtn_view = (ImageView) findViewById(R.id.btn_view);
    this.mBtn_view.setImageResource(R.drawable.main_control_panel_btn_mode_normal);
    this.setClickable(true);
  }

  public boolean onTouchEvent(MotionEvent motionEvent) {
    switch (motionEvent.getAction()) {
      case MotionEvent.ACTION_DOWN:
        this.mTxtBtnView.setTextColor(
            this.getResources().getColor(R.color.setting_order_mode_gray));
        this.mBtn_view.setImageResource(R.drawable.main_control_panel_btn_mode_normal);
        break;
      case MotionEvent.ACTION_UP:
        this.mTxtBtnView.setTextColor(
            this.getResources().getColor(R.color.setting_order_mode_gray));
        this.mBtn_view.setImageResource(R.drawable.main_control_panel_btn_mode_normal);
      case MotionEvent.ACTION_MOVE:
    }
    return super.onTouchEvent(motionEvent);
  }

  public void setBtnTxt(String btnTxt) {
    if (btnTxt != null) {
      this.mTxtBtnView.setText(btnTxt);
    }
  }
}
