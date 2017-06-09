package com.uniware.driver.gui.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import com.uniware.driver.R;

/**
 * Created by jian on 16/05/04.
 */
public class SwitchButton extends ImageView {
  private boolean a = true;
  private SwitchButton.b b;
  private SwitchButton.a c;
  private final OnClickListener d = new OnClickListener() {
    public void onClick(View var1) {
      boolean var2 = false;
      if (SwitchButton.this.c != null) {
        var2 = SwitchButton.this.c.a();
      }

      if (!var2) {
        if (SwitchButton.this.a) {
          SwitchButton.this.d();
          if (SwitchButton.this.b != null) {
            SwitchButton.this.b.b();
            return;
          }
        } else {
          SwitchButton.this.c();
          if (SwitchButton.this.b != null) {
            SwitchButton.this.b.a();
            return;
          }
        }
      }
    }
  };

  public SwitchButton(Context var1) {
    super(var1);
    this.b();
  }

  public SwitchButton(Context var1, AttributeSet var2) {
    super(var1, var2);
    this.b();
  }

  public SwitchButton(Context var1, AttributeSet var2, int var3) {
    super(var1, var2, var3);
    this.b();
  }

  private void b() {
    this.setImageResource(R.drawable.btn_open);
    this.a = true;
    this.setOnClickListener(this.d);
  }

  private void c() {
    this.setImageResource(R.drawable.btn_open);
    this.a = true;
  }

  private void d() {
    this.setImageResource(R.drawable.btn_close);
    this.a = false;
  }

  public boolean a() {
    return this.a;
  }

  public void setInitState(boolean var1) {
    if (var1) {
      this.c();
    } else {
      this.d();
    }
  }

  public void setInterceptListener(SwitchButton.a var1) {
    this.c = var1;
  }

  public void setSwitchListener(SwitchButton.b var1) {
    this.b = var1;
  }

  public interface a {
    boolean a();
  }

  public interface b {
    void a();

    void b();
  }
}