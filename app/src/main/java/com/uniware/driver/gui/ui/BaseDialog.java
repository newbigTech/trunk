package com.uniware.driver.gui.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import com.uniware.driver.R;

/**
 * Created by jian on 15/11/12.
 */
public class BaseDialog extends Dialog {

  private TextView title;
  private TextView detail;

  public BaseDialog(Context context) {
    super(context, R.style.Dialog);
  }

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.didi_dialog);
    title = (TextView) findViewById(R.id.tvTitle);
    detail = (TextView) findViewById(R.id.tvDetail);
  }

  public void setMsg(String title, String detail){
    this.title.setText(title);
    this.detail.setText(detail);
  }

  public void setMsg(int title, int detail){
    this.title.setText(title);
    this.detail.setText(detail);
  }
}
