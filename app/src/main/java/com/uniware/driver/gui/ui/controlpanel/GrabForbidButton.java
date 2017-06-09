package com.uniware.driver.gui.ui.controlpanel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.uniware.driver.R;

/**
 * Created by jian on 15/11/13.
 */
public class GrabForbidButton extends CountdownButton {
  private TextView mBtnCountdown;

  public GrabForbidButton(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.initView();
  }

  private void initView() {
    inflate(this.getContext(), R.layout.main_control_panel_count_down_button, this);
    this.mBtnCountdown = (TextView) this.getChildAt(0);
    this.mAnimShow = AnimationUtils.loadAnimation(this.getContext(),
        R.anim.main_control_panel_count_down_btn_show);
    this.mAnimHide = AnimationUtils.loadAnimation(this.getContext(),
        R.anim.main_control_panel_count_down_btn_hide);
  }

  public void hide() {
    this.mBtnCountdown.setText("");
    if (this.getVisibility() != GONE) {
      this.animHide();
    }
  }

  public void show() {
    this.mBtnCountdown.setText("");
    if (this.getVisibility() != VISIBLE) {
      this.animShow();
    }
  }

  public void show(int countDown) {
    this.mBtnCountdown.setText(
        String.format(getContext().getString(R.string.main_control_panel_grab_count_down),
            countDown));
    if (this.getVisibility() != VISIBLE) {
      this.animShow();
    }
  }
}