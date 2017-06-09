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
 * Created by jian on 16/05/04.
 */
public class TextViewWithDel extends LinearLayout {
  private Context a;
  private TextView b;
  private ImageButton c;
  private String d;
  private TextViewWithDel.a e;
  private RelativeLayout f;
  private OnClickListener g = new OnClickListener() {
    public void onClick(View var1) {
      TextViewWithDel.this.a();
      if (TextViewWithDel.this.e != null) {
        TextViewWithDel.this.e.a();
      }
    }
  };

  public TextViewWithDel(Context var1) {
    super(var1);
    this.a = var1;
    this.b();
  }

  public TextViewWithDel(Context var1, AttributeSet var2) {
    super(var1, var2);
    this.a = var1;
    this.b();
  }

  private void b() {
    View var1 = LayoutInflater.from(a).inflate(R.layout.textview_del, this);
    this.f = (RelativeLayout) var1.findViewById(R.id.textview_del_main);
    this.b = (TextView) var1.findViewById(R.id.textview_del_text);
    this.c = (ImageButton) var1.findViewById(R.id.textview_del_del);
    this.c.setOnClickListener(this.g);
  }

  public void a() {
    this.b.setText(this.d);
    this.b.setTextColor(this.getResources().getColor(R.color.c_white_99ffffff));
    this.f.setBackgroundResource(R.drawable.mode_advanced_setting_input_focus_bkg);
    this.c.setVisibility(GONE);
  }

  public void setContent(String var1) {
    this.b.setText(var1);
    this.b.setTextColor(this.getResources().getColor(R.color.c_white_1effffff));
    this.f.setBackgroundResource(R.drawable.mode_advanced_setting_input_bkg);
    this.c.setVisibility(VISIBLE);
  }

  public void setDefaultText(int var1) {
    this.setDefaultText(this.getResources().getString(var1));
  }

  public void setDefaultText(String var1) {
    this.d = var1;
    this.a();
  }

  public void setOnDelClickListener(TextViewWithDel.a var1) {
    this.e = var1;
  }

  public void setOnTextClickListener(OnClickListener var1) {
    this.b.setOnClickListener(var1);
  }

  public interface a {
    void a();
  }
}