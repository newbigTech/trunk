package com.uniware.driver.gui.ui.controlpanel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.uniware.driver.R;
import com.xfdsj.peacock.PeacockMenu;

/**
 * Created by jian on 15/11/13.
 */
public class ListeningButton extends CountdownButton {
  private Animation mAnimationBreathe;
  private Animation mAnimationRotate;
  private ImageView mRingView;
  private TextView mTxtListening;
  private PeacockMenu mPeacockMenu;

  public ListeningButton(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.initView();
  }

  private void initView() {
    inflate(this.getContext(), R.layout.main_control_panel_listening_button, this);
    this.mRingView = (ImageView) this.findViewById(R.id.btn_listening_ring);
    this.mRingView.setBackgroundResource(R.drawable.main_control_panel_btn_listening_ring);
    this.mPeacockMenu = (PeacockMenu) findViewById(R.id.menu_peacock_listening);
    this.mPeacockMenu.setClickable(false);
    this.mTxtListening = (TextView) this.findViewById(R.id.txt_listening_order);
    this.mAnimShow = AnimationUtils.loadAnimation(this.getContext(),
        R.anim.main_control_panel_count_down_btn_show);
    this.mAnimHide = AnimationUtils.loadAnimation(this.getContext(),
        R.anim.main_control_panel_count_down_btn_hide);
    this.mAnimationRotate = AnimationUtils.loadAnimation(this.getContext(),
        R.anim.main_control_panel_listening_btn_rotation);
    this.mAnimationBreathe = AnimationUtils.loadAnimation(this.getContext(), R.anim.breathe);
    this.startRotateAnimation();
  }

  private void startRotateAnimation() {
    this.mAnimationRotate.reset();
    this.mRingView.startAnimation(this.mAnimationRotate);
  }

  private void stopRotateAnimation() {
    this.mRingView.clearAnimation();
    this.mAnimationRotate.cancel();
  }

  public void hide() {
    this.stopRotateAnimation();
    if (this.getVisibility() != GONE) {
      this.animHide();
      mPeacockMenu.closeMenu();
    }
  }

  public void show() {
    this.startRotateAnimation();
    if (this.getVisibility() != VISIBLE) {
      this.animShow(new AnimEndListener() {
        @Override public void onAnimEnd() {
          post(new Runnable() {
            @Override public void run() {
              mPeacockMenu.openMenu();
            }
          });
        }
      });
    }
  }

  public void startLinking() {
    this.mTxtListening.setText(R.string.main_control_panel_linking);
    this.mAnimationBreathe.reset();
    this.mTxtListening.startAnimation(this.mAnimationBreathe);
  }

  public void stopLinking() {
    this.mTxtListening.setText(R.string.main_control_panel_listening);
    this.mTxtListening.clearAnimation();
    this.mAnimationBreathe.cancel();
  }
}