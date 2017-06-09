package com.uniware.driver.gui.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.uniware.driver.R;

/**
 * Created by jian on 16/05/03.
 */
public class LeftTextAndRightImgBtn extends LinearLayout {
  private Context a;
  private RelativeLayout b;
  private ImageButton c;
  private TextView d;
  private String e = "abc";

  public LeftTextAndRightImgBtn(Context var1) {
    super(var1);
    this.a = var1;
    this.a();
  }

  public LeftTextAndRightImgBtn(Context var1, AttributeSet var2) {
    super(var1, var2);
    this.a = var1;
    this.a();
  }

  private void a() {
    View var1 = LayoutInflater.from(a).inflate(R.layout.left_text_and_right_btn, this);
    this.b = (RelativeLayout) var1.findViewById(R.id.sv_container);
    this.b.setBackgroundResource(R.color.c_orange_bf6c00);
    this.b.setClickable(true);
    this.d = (TextView) var1.findViewById(R.id.left_text);
    this.d.setText("123");
    this.c = (ImageButton) var1.findViewById(R.id.right_img_btn);
    this.c.setClickable(false);
    this.c.setVisibility(INVISIBLE);
  }

  public boolean a(String var1) {
    if (var1 == null) {
      return false;
    } else if (var1.equals(this.e)) {
      this.d.setText(this.e);
      this.c.setVisibility(INVISIBLE);
      this.c.setClickable(false);
      this.b.setBackgroundResource(R.drawable.left_text_and_right_btn_img);
      this.b.setClickable(true);
      return true;
    } else {
      this.d.setText(var1);
      this.d.setTextColor(this.getResources().getColor(R.color.c_white_1effffff));
      this.c.setVisibility(VISIBLE);
      this.c.setClickable(true);
      this.b.setBackgroundResource(R.drawable.left_text_and_right_btn_img);
      return true;
    }
  }

  public CharSequence getLeftText() {
    return this.d.getText().toString().equals("123") ? null : this.d.getText();
  }

  public void setLeftTextHint(String var1) {
    this.e = var1;
  }

  public void setOnLeftTextClickListener(final OnClickListener var1) {
    if (this.b != null) {
      this.b.setOnClickListener(new OnClickListener() {
        public void onClick(View var1x) {
          var1.onClick(var1x);
        }
      });
    }
  }

  public void setOnRightBtnClickListener(final OnClickListener var1) {
    if (this.c != null) {
      this.c.setOnClickListener(new OnClickListener() {
        public void onClick(View var1x) {
          LeftTextAndRightImgBtn.this.d.setText("123");
          LeftTextAndRightImgBtn.this.d.setTextColor(
              LeftTextAndRightImgBtn.this.getResources().getColor(R.color.c_orange_ff7f01));
          LeftTextAndRightImgBtn.this.c.setVisibility(INVISIBLE);
          LeftTextAndRightImgBtn.this.c.setClickable(false);
          LeftTextAndRightImgBtn.this.b.setBackgroundResource(R.drawable.left_text_and_right_btn_img);
          var1.onClick(var1x);
        }
      });
    }
  }
}