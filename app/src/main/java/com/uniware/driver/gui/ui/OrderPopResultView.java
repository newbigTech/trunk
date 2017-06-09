package com.uniware.driver.gui.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.uniware.driver.R;

/**
 * Created by jian on 16/05/03.
 */
public class OrderPopResultView extends RelativeLayout {
  private ImageView ivResultIcon;
  private TextView tvResult;
  private ProgressBar pbResult;

  public OrderPopResultView(Context context) {
    super(context);
    this.initViews(context);
  }

  public OrderPopResultView(Context context, AttributeSet attributeSet) {
    super(context, attributeSet);
    this.initViews(context);
  }

  public OrderPopResultView(Context context, AttributeSet attributeSet, int defStyleAttr) {
    super(context, attributeSet, defStyleAttr);
    this.initViews(context);
  }

  private void initViews(Context context) {
    View view = LayoutInflater.from(context).inflate(R.layout.order_pop_result_layout, this);
    this.ivResultIcon = (ImageView) view.findViewById(R.id.order_pop_result_icon);
    this.tvResult = (TextView) view.findViewById(R.id.order_pop_result_text);
    this.pbResult = (ProgressBar) view.findViewById(R.id.order_pop_result_progress);
  }

  public void show(int res) {
    this.ivResultIcon.setVisibility(GONE);
    this.pbResult.setVisibility(VISIBLE);
    this.tvResult.setText(res);
  }

  public void show(int imageRes, int textRes) {
    String text;
    if (textRes == 0) {
      text = "";
    } else {
      text = this.getContext().getString(textRes);
    }

    this.show(imageRes, text);
  }

  public void show(int imageRes, String text) {
    this.ivResultIcon.setVisibility(VISIBLE);
    this.pbResult.setVisibility(GONE);
    this.ivResultIcon.setImageResource(imageRes);
    this.tvResult.setText(text);
  }
}