package com.uniware.driver.gui.ui.controlpanel;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.uniware.driver.R;

/**
 * Created by jian on 15/11/13.
 */
public class StartOffButton extends RelativeLayout {

  public StartOffButton(Context context) {
    super(context);
    this.initView();
  }

  public StartOffButton(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.initView();
  }

  public StartOffButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.initView();
  }

  private void initView() {
    inflate(getContext(), R.layout.main_control_panel_start_off_button, this);
  }

  public void show() {
    this.setVisibility(VISIBLE);
  }

  public void hide() {
    this.setVisibility(GONE);
  }
}