package com.uniware.driver.gui.ui.controlpanel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.uniware.driver.R;

/**
 * Created by jian on 15/11/13.
 */
public class GrabButton extends CountdownButton {
  private TextView mTxtGrabCountdown;

  public GrabButton(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.initView();
  }

  private void initView() {
    inflate(this.getContext(), R.layout.main_control_panel_grab_button, this);
    this.mTxtGrabCountdown = (TextView) findViewById(R.id.txt_grab_count_down);
    this.mAnimShow =
        AnimationUtils.loadAnimation(this.getContext(), R.anim.main_control_panel_grab_btn_show);
    this.mAnimHide = AnimationUtils.loadAnimation(this.getContext(),
        R.anim.main_control_panel_count_down_btn_hide);
  }

  public void hide() {
    if (this.getVisibility() != INVISIBLE) {
      this.clearAnimation();
      this.setVisibility(INVISIBLE);
    }
    this.mTxtGrabCountdown.setVisibility(INVISIBLE);
  }

  public void show() {
    if (this.getVisibility() != VISIBLE) {
      this.animShow();
    }
  }

  public void show(int countDown) {
    if (countDown <= 0) {
      this.mTxtGrabCountdown.setVisibility(GONE);
      this.mTxtGrabCountdown.setText("");
    } else {
      this.mTxtGrabCountdown.setVisibility(VISIBLE);
      this.mTxtGrabCountdown.setText(
          String.format(getContext().getString(R.string.main_control_panel_grab_count_down),
              countDown));
    }

    if (this.getVisibility() != VISIBLE) {
      this.animShow();
    }
  }
}