package com.uniware.driver.gui.ui.slider;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.uniware.driver.AppApplication;
import com.uniware.driver.R;

/**
 * Created by jian on 16/05/03.
 */
public class SlideSwitch3State extends LinearLayout {
  private Context a;
  private RelativeLayout b;
  private ImageButton c;
  private TextView[] d = new TextView[3];
  private int e =
      (int) (AppApplication.getAppContext().getResources().getDisplayMetrics().density * 3.0F
          + 0.5F);
  private int f;
  private int g;
  private int h;
  private int i;
  private int j;
  private int k;
  private int l;
  private int m;
  private int n;
  private Animation o;
  private int p = 0;
  private int q = -1;
  private SliderListener r;
  private OnTouchListener s = new OnTouchListener() {
    int a;

    public boolean onTouch(View var1, MotionEvent var2) {
      switch (var2.getAction()) {
        case 0:
          SlideSwitch3State.this.b.getParent().requestDisallowInterceptTouchEvent(true);
          this.a = (int) var2.getX();
          return true;
        case 1:
          SlideSwitch3State.this.b.getParent().requestDisallowInterceptTouchEvent(false);
          SlideSwitch3State.this.a(this.a);
          return true;
        case 2:
          SlideSwitch3State.this.b.getParent().requestDisallowInterceptTouchEvent(true);
          return true;
        default:
          return true;
      }
    }
  };
  private OnTouchListener t = new OnTouchListener() {
    int a;

    public boolean onTouch(View var1, MotionEvent var2) {
      switch (var2.getAction()) {
        case 0:
          SlideSwitch3State.this.b.getParent().requestDisallowInterceptTouchEvent(true);
          SlideSwitch3State.this.c.setBackgroundResource(R.drawable.button_slider_press);
          this.a = (int) var2.getRawX();
          SlideSwitch3State.this.i = var1.getLeft();
          SlideSwitch3State.this.j = var1.getTop();
          SlideSwitch3State.this.k = var1.getRight();
          SlideSwitch3State.this.n = var1.getBottom();
          SlideSwitch3State.this.l = (SlideSwitch3State.this.i + SlideSwitch3State.this.k) / 2;
          return true;
        case 1:
          SlideSwitch3State.this.b.getParent().requestDisallowInterceptTouchEvent(false);
          SlideSwitch3State.this.c.setBackgroundResource(R.drawable.button_slider_normal);
          SlideSwitch3State.this.b();
          return true;
        case 2:
          SlideSwitch3State.this.b.getParent().requestDisallowInterceptTouchEvent(true);
          int var3 = (int) var2.getRawX() - this.a;
          SlideSwitch3State.this.i = var1.getLeft() + var3;
          SlideSwitch3State.this.k = var3 + var1.getRight();
          if (SlideSwitch3State.this.i <= SlideSwitch3State.this.f + SlideSwitch3State.this.e) {
            SlideSwitch3State.this.i = SlideSwitch3State.this.f + SlideSwitch3State.this.e;
            SlideSwitch3State.this.k = SlideSwitch3State.this.i + var1.getWidth();
          }

          if (SlideSwitch3State.this.k >= SlideSwitch3State.this.h - SlideSwitch3State.this.e) {
            SlideSwitch3State.this.k = SlideSwitch3State.this.h - SlideSwitch3State.this.e;
            SlideSwitch3State.this.i = SlideSwitch3State.this.k - var1.getWidth();
          }

          SlideSwitch3State.this.l = (SlideSwitch3State.this.i + SlideSwitch3State.this.k) / 2;
          var1.layout(SlideSwitch3State.this.i, SlideSwitch3State.this.j, SlideSwitch3State.this.k,
              SlideSwitch3State.this.n);
          this.a = (int) var2.getRawX();
          return true;
        default:
          return true;
      }
    }
  };

  public SlideSwitch3State(Context var1) {
    super(var1);
    this.a = var1;
    this.a();
  }

  public SlideSwitch3State(Context var1, AttributeSet var2) {
    super(var1, var2);
    this.a = var1;
    this.a();
  }

  private void a() {
    View var1 = LayoutInflater.from(a).inflate(R.layout.slide_switch_3_state, this);
    this.b = (RelativeLayout) var1.findViewById(R.id.sv_container);
    this.b.setOnTouchListener(this.s);
    this.d[0] = (TextView) var1.findViewById(R.id.switch_text_0);
    this.d[1] = (TextView) var1.findViewById(R.id.switch_text_1);
    this.d[2] = (TextView) var1.findViewById(R.id.switch_text_2);
    this.c(this.p);
    this.c = (ImageButton) var1.findViewById(R.id.iv_switch_cursor);
    this.c.setClickable(true);
    this.c.setOnTouchListener(this.t);
  }

  private void a(int var1) {
    float var2 = (float) ((double) (this.h - this.f) / 3.0D);
    float var3 = (float) ((double) (this.h - this.f) / 3.0D * 2.0D);
    if ((float) var1 <= var2) {
      this.b(0);
    } else if ((float) var1 <= var3) {
      this.b(1);
    } else {
      this.b(2);
    }
  }

  private void a(boolean var1) {
    if (this.p == 0) {
      this.i = this.e;
      this.k = this.i + this.c.getWidth();
    } else if (this.p == 1) {
      this.i = this.g - this.m / 2;
      this.k = this.g + this.m / 2;
    } else {
      this.k = this.h - this.e;
      this.i = this.k - this.c.getWidth();
    }

    this.l = this.i + this.m / 2;
    this.c.layout(this.i, this.j, this.k, this.n);
    this.c(this.p);
    if (this.r != null && var1) {
      this.r.sliderTo(this.p);
      this.q = this.p;
    }
  }

  private void b() {
    float var1 = (float) ((double) (this.h - this.f) / 3.0D);
    float var2 = (float) ((double) (this.h - this.f) / 3.0D * 2.0D);
    float var3 = (float) ((double) (this.k - this.i) / 2.0D);
    if ((float) this.i + var3 <= var1) {
      this.b(0);
    } else if ((float) this.i + var3 <= var2) {
      this.b(1);
    } else {
      this.b(2);
    }
  }

  private void b(int var1) {
    this.p = var1;
    this.c();
  }

  private void c() {
    this.o = null;
    int var1;
    if (this.p == 0) {
      var1 = this.e - this.i;
    } else if (this.p == 1) {
      var1 = this.g - this.l;
    } else {
      var1 = this.h - this.e - this.k;
    }

    this.o = new TranslateAnimation(0.0F, (float) var1, 0.0F, 0.0F);
    this.o.setDuration(150L);
    this.o.setFillEnabled(true);
    this.o.setInterpolator(new LinearInterpolator());
    this.o.setAnimationListener(new Animation.AnimationListener() {
      public void onAnimationEnd(Animation var1) {
        SlideSwitch3State.this.a(true);
      }

      public void onAnimationRepeat(Animation var1) {
      }

      public void onAnimationStart(Animation var1) {
      }
    });
    this.c.startAnimation(this.o);
  }

  private void c(int var1) {
    if (var1 == 0) {
      this.d[0].setTextColor(this.getResources().getColor(R.color.c_white_1effffff));
      this.d[1].setTextColor(this.getResources().getColor(R.color.c_gray_bababa));
      this.d[2].setTextColor(this.getResources().getColor(R.color.c_gray_bababa));
    } else if (var1 == 1) {
      this.d[0].setTextColor(this.getResources().getColor(R.color.c_gray_bababa));
      this.d[1].setTextColor(this.getResources().getColor(R.color.c_white_1effffff));
      this.d[2].setTextColor(this.getResources().getColor(R.color.c_gray_bababa));
    } else {
      this.d[0].setTextColor(this.getResources().getColor(R.color.c_gray_bababa));
      this.d[1].setTextColor(this.getResources().getColor(R.color.c_gray_bababa));
      this.d[2].setTextColor(this.getResources().getColor(R.color.c_white_1effffff));
    }
  }

  public boolean a(int var1, boolean var2) {
    if (var1 > 2) {
      return false;
    } else {
      this.p = var1;
      if (var2) {
        this.b(var1);
      } else {
        this.a(false);
      }

      return true;
    }
  }

  public boolean a(SliderListener var1) {
    if (var1 == null) {
      return false;
    } else {
      this.r = var1;
      return true;
    }
  }

  public int getCursorCenterLocationInParent() {
    return this.c.getLeft() + this.c.getWidth() / 2;
  }

  public int getCursorCenterLocationOnScreen() {
    int[] var1 = new int[2];
    this.c.getLocationOnScreen(var1);
    return var1[0] + this.c.getWidth() / 2;
  }

  public int getSelected() {
    return this.p;
  }

  protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
    super.onLayout(var1, var2, var3, var4, var5);
    this.f = this.b.getLeft();
    this.h = this.b.getRight();
    this.g = (this.f + this.h) / 2;
    this.i = this.c.getLeft();
    this.j = this.c.getTop();
    this.k = this.c.getRight();
    this.n = this.c.getBottom();
    this.l = (this.i + this.k) / 2;
    this.m = this.k - this.i;
    this.a(false);
  }
}