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
public class SlideSwitch2State extends LinearLayout {
  private Context a;
  private RelativeLayout b;
  private ImageButton c;
  private TextView[] d = new TextView[2];
  private int e =
      (int) (AppApplication.getAppContext().getResources().getDisplayMetrics().density * 3.0F
          + 0.5F);
  private int f;
  private int g;
  private int h;
  private int i;
  private int j;
  private int k;
  private Animation l;
  private int m = 0;
  private int n = -1;
  private SliderListener o;
  private OnTouchListener p = new OnTouchListener() {
    int a;

    public boolean onTouch(View var1, MotionEvent var2) {
      switch (var2.getAction()) {
        case 0:
          SlideSwitch2State.this.b.getParent().requestDisallowInterceptTouchEvent(true);
          this.a = (int) var2.getX();
          return true;
        case 1:
          SlideSwitch2State.this.b.getParent().requestDisallowInterceptTouchEvent(false);
          SlideSwitch2State.this.a(this.a);
          return true;
        case 2:
          SlideSwitch2State.this.b.getParent().requestDisallowInterceptTouchEvent(true);
          return true;
        default:
          return true;
      }
    }
  };
  private OnTouchListener q = new OnTouchListener() {
    int a;

    public boolean onTouch(View var1, MotionEvent var2) {
      switch (var2.getAction()) {
        case 0:
          SlideSwitch2State.this.b.getParent().requestDisallowInterceptTouchEvent(true);
          SlideSwitch2State.this.c.setBackgroundResource(R.drawable.button_slider_press);
          this.a = (int) var2.getRawX();
          SlideSwitch2State.this.h = var1.getLeft();
          SlideSwitch2State.this.i = var1.getTop();
          SlideSwitch2State.this.j = var1.getRight();
          SlideSwitch2State.this.k = var1.getBottom();
          return true;
        case 1:
          SlideSwitch2State.this.b.getParent().requestDisallowInterceptTouchEvent(false);
          SlideSwitch2State.this.c.setBackgroundResource(R.drawable.button_slider_normal);
          SlideSwitch2State.this.b();
          return true;
        case 2:
          SlideSwitch2State.this.b.getParent().requestDisallowInterceptTouchEvent(true);
          int var3 = (int) var2.getRawX() - this.a;
          SlideSwitch2State.this.h = var1.getLeft() + var3;
          SlideSwitch2State.this.j = var3 + var1.getRight();
          if (SlideSwitch2State.this.h <= SlideSwitch2State.this.f + SlideSwitch2State.this.e) {
            SlideSwitch2State.this.h = SlideSwitch2State.this.f + SlideSwitch2State.this.e;
            SlideSwitch2State.this.j = SlideSwitch2State.this.h + var1.getWidth();
          }

          if (SlideSwitch2State.this.j >= SlideSwitch2State.this.g - SlideSwitch2State.this.e) {
            SlideSwitch2State.this.j = SlideSwitch2State.this.g - SlideSwitch2State.this.e;
            SlideSwitch2State.this.h = SlideSwitch2State.this.j - var1.getWidth();
          }

          var1.layout(SlideSwitch2State.this.h, SlideSwitch2State.this.i, SlideSwitch2State.this.j,
              SlideSwitch2State.this.k);
          this.a = (int) var2.getRawX();
          return true;
        default:
          return true;
      }
    }
  };

  public SlideSwitch2State(Context var1) {
    super(var1);
    this.a = var1;
    this.a();
  }

  public SlideSwitch2State(Context var1, AttributeSet var2) {
    super(var1, var2);
    this.a = var1;
    this.a();
  }

  private void a() {
    View var1 = LayoutInflater.from(a).inflate(R.layout.slide_switch_2_state, this);
    this.b = (RelativeLayout) var1.findViewById(R.id.sv_container);
    this.b.setOnTouchListener(this.p);
    this.d[0] = (TextView) var1.findViewById(R.id.switch_text_0);
    this.d[1] = (TextView) var1.findViewById(R.id.switch_text_1);
    this.c(this.m);
    this.c = (ImageButton) var1.findViewById(R.id.iv_switch_cursor);
    this.c.setClickable(false);
    this.c.setOnTouchListener(this.q);
  }

  private void a(int var1) {
    float var2 = (float) ((double) (this.g - this.f) / 2.0D);
    if ((float) var1 < var2) {
      this.b(0);
    } else {
      this.b(1);
    }
  }

  private void a(boolean var1) {
    if (this.m == 0) {
      this.h = this.e;
      this.j = this.h + this.c.getWidth();
    } else {
      this.j = this.g - this.e;
      this.h = this.j - this.c.getWidth();
    }

    this.c.layout(this.h, this.i, this.j, this.k);
    this.c(this.m);
    if (this.o != null && var1) {
      this.o.sliderTo(this.m);
      this.n = this.m;
    }
  }

  private void b() {
    float var1 = (float) ((double) (this.g - this.f) / 2.0D);
    if ((float) ((double) (this.j - this.h) / 2.0D) + (float) this.h < var1) {
      this.b(0);
    } else {
      this.b(1);
    }
  }

  private void b(int var1) {
    this.m = var1;
    this.c();
  }

  private void c() {
    this.l = null;
    int var1;
    if (this.m == 0) {
      var1 = this.e - this.h;
    } else {
      var1 = this.g - this.e - this.j;
    }

    this.l = new TranslateAnimation(0.0F, (float) var1, 0.0F, 0.0F);
    this.l.setDuration(150L);
    this.l.setFillEnabled(true);
    this.l.setInterpolator(new LinearInterpolator());
    this.l.setAnimationListener(new Animation.AnimationListener() {
      public void onAnimationEnd(Animation var1) {
        SlideSwitch2State.this.a(true);
      }

      public void onAnimationRepeat(Animation var1) {
      }

      public void onAnimationStart(Animation var1) {
      }
    });
    this.c.startAnimation(this.l);
  }

  private void c(int var1) {
    if (var1 == 0) {
      this.d[0].setTextColor(this.getResources().getColor(R.color.c_white_1effffff));
      this.d[1].setTextColor(this.getResources().getColor(R.color.c_gray_bababa));
    } else {
      this.d[0].setTextColor(this.getResources().getColor(R.color.c_gray_bababa));
      this.d[1].setTextColor(this.getResources().getColor(R.color.c_white_1effffff));
    }
  }

  public boolean a(int var1, boolean var2) {
    if (var1 > 2) {
      return false;
    } else {
      this.m = var1;
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
      this.o = var1;
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
    return this.m;
  }

  protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
    super.onLayout(var1, var2, var3, var4, var5);
    this.f = this.b.getLeft();
    this.g = this.b.getRight();
    this.h = this.c.getLeft();
    this.i = this.c.getTop();
    this.j = this.c.getRight();
    this.k = this.c.getBottom();
    this.a(false);
  }
}
