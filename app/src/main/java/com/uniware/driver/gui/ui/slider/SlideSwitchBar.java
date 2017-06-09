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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.uniware.driver.AppApplication;
import com.uniware.driver.R;

/**
 * Created by jian on 16/05/03.
 */
public class SlideSwitchBar extends LinearLayout {
  private TextView[] A = new TextView[5];
  private int B;
  private int C;
  private int D;
  private int E;
  private int F;
  private int G;
  private Animation H;
  private SliderListener I;
  private int J = 1;
  private int K = 0;
  private boolean L = false;
  private OnTouchListener M = new OnTouchListener() {
    int a;

    public boolean onTouch(View var1, MotionEvent var2) {
      switch (var2.getAction()) {
        case 0:
          SlideSwitchBar.this.L = true;
          SlideSwitchBar.this.c.getParent().requestDisallowInterceptTouchEvent(true);
          this.a = (int) var2.getX();
          return true;
        case 1:
          SlideSwitchBar.this.c.getParent().requestDisallowInterceptTouchEvent(false);
          SlideSwitchBar.this.c(this.a);
          return true;
        case 2:
          SlideSwitchBar.this.c.getParent().requestDisallowInterceptTouchEvent(true);
          return true;
        default:
          return true;
      }
    }
  };
  private OnTouchListener N = new OnTouchListener() {
    int a;

    public boolean onTouch(View var1, MotionEvent var2) {
      switch (var2.getAction()) {
        case 0:
          SlideSwitchBar.this.L = true;
          SlideSwitchBar.this.c.getParent().requestDisallowInterceptTouchEvent(true);
          SlideSwitchBar.this.f.setVisibility(VISIBLE);
          SlideSwitchBar.this.d.setVisibility(VISIBLE);
          this.a = (int) var2.getRawX();
          SlideSwitchBar.this.D = var1.getLeft();
          var1.getTop();
          SlideSwitchBar.this.E = var1.getRight();
          var1.getBottom();
          SlideSwitchBar.this.F = (SlideSwitchBar.this.D + SlideSwitchBar.this.E) / 2;
          return true;
        case 1:
          SlideSwitchBar.this.c.getParent().requestDisallowInterceptTouchEvent(false);
          SlideSwitchBar.this.f();
          return true;
        case 2:
          SlideSwitchBar.this.c.getParent().requestDisallowInterceptTouchEvent(true);
          int var3 = (int) var2.getRawX() - this.a;
          SlideSwitchBar.this.D = var1.getLeft() + var3;
          SlideSwitchBar.this.E = var3 + var1.getRight();
          SlideSwitchBar.this.d();
          SlideSwitchBar.this.F = (SlideSwitchBar.this.D + SlideSwitchBar.this.E) / 2;
          SlideSwitchBar.this.f.layout(SlideSwitchBar.this.s, SlideSwitchBar.this.f.getTop(),
              SlideSwitchBar.this.E - SlideSwitchBar.this.G / 2, SlideSwitchBar.this.f.getBottom());
          SlideSwitchBar.this.a(SlideSwitchBar.this.a, SlideSwitchBar.this.F);
          this.a = (int) var2.getRawX();
          return true;
        default:
          return true;
      }
    }
  };
  private int a = 5;
  private Context b;
  private LinearLayout c;
  private ImageView d;
  private ImageButton e;
  private ImageButton f;
  private ImageButton[] g = new ImageButton[2];
  private ImageButton[] h = new ImageButton[2];
  private ImageButton[] i = new ImageButton[3];
  private ImageButton[] j = new ImageButton[3];
  private ImageButton[] k = new ImageButton[4];
  private ImageButton[] l = new ImageButton[4];
  private ImageButton[] m = new ImageButton[5];
  private ImageButton[] n = new ImageButton[5];
  private int[] o = new int[2];
  private int[] p = new int[3];
  private int[] q = new int[4];
  private int[] r = new int[5];
  private int s =
      (int) (AppApplication.getAppContext().getResources().getDisplayMetrics().density * 20.0F
          + 0.5F);
  private TextView[] t = new TextView[2];
  private TextView[] u = new TextView[3];
  private TextView[] v = new TextView[4];
  private TextView[] w = new TextView[5];
  private TextView[] x = new TextView[2];
  private TextView[] y = new TextView[3];
  private TextView[] z = new TextView[4];

  public SlideSwitchBar(Context var1) {
    super(var1);
    this.b = var1;
    this.c();
  }

  public SlideSwitchBar(Context var1, AttributeSet var2) {
    super(var1, var2);
    this.b = var1;
    this.c();
  }

  private int a(int var1, int[] var2) {
    int var3 = 1;
    int var4 = '\ufffa';

    int var5;
    int var6;
    for (var5 = 1; var3 < var2.length; var4 = var6) {
      var6 = var4;
      if (Math.abs(var1 - var2[var3]) <= var4) {
        var6 = Math.abs(var1 - var2[var3]);
        var5 = var3;
      }

      ++var3;
    }

    return var5;
  }

  private void a() {
    this.m();
    this.n();
    this.setGroupWhiteCircleVisible(this.a);
    this.i();
    this.setGroupSmallTextViewVisible(this.a);
    this.f.setVisibility(INVISIBLE);
    this.f.layout(this.s, this.f.getTop(), this.s, this.f.getBottom());
    this.d.setVisibility(INVISIBLE);
    this.d.layout(this.o[0] - this.d.getWidth() / 2, this.d.getTop(),
        this.o[0] + this.d.getWidth() / 2, this.d.getBottom());
  }

  private void a(int var1, int var2) {
    switch (var1) {
      case 2:
        this.a(this.o, this.g, this.h, var2);
        return;
      case 3:
        this.a(this.p, this.i, this.j, var2);
        return;
      case 4:
        this.a(this.q, this.k, this.l, var2);
        return;
      case 5:
        this.a(this.r, this.m, this.n, var2);
        return;
      default:
    }
  }

  private void a(View var1) {
    this.t[0] = (TextView) var1.findViewById(R.id.text_2_1_small);
    this.t[1] = (TextView) var1.findViewById(R.id.text_2_1_big);
    this.x[0] = (TextView) var1.findViewById(R.id.text_2_2_small);
    this.x[1] = (TextView) var1.findViewById(R.id.text_2_2_big);
    this.u[0] = (TextView) var1.findViewById(R.id.text_3_1_small);
    this.u[1] = (TextView) var1.findViewById(R.id.text_3_1_big);
    this.u[2] = (TextView) var1.findViewById(R.id.text_3_2_small);
    this.y[0] = (TextView) var1.findViewById(R.id.text_3_2_big);
    this.y[1] = (TextView) var1.findViewById(R.id.text_3_3_small);
    this.y[2] = (TextView) var1.findViewById(R.id.text_3_3_big);
    this.v[0] = (TextView) var1.findViewById(R.id.text_4_1_small);
    this.v[1] = (TextView) var1.findViewById(R.id.text_4_1_big);
    this.v[2] = (TextView) var1.findViewById(R.id.text_4_2_small);
    this.v[3] = (TextView) var1.findViewById(R.id.text_4_2_big);
    this.z[0] = (TextView) var1.findViewById(R.id.text_4_3_small);
    this.z[1] = (TextView) var1.findViewById(R.id.text_4_3_big);
    this.z[2] = (TextView) var1.findViewById(R.id.text_4_4_small);
    this.z[3] = (TextView) var1.findViewById(R.id.text_4_4_big);
    this.w[0] = (TextView) var1.findViewById(R.id.text_5_1_small);
    this.w[1] = (TextView) var1.findViewById(R.id.text_5_1_big);
    this.w[2] = (TextView) var1.findViewById(R.id.text_5_2_small);
    this.w[3] = (TextView) var1.findViewById(R.id.text_5_2_big);
    this.w[4] = (TextView) var1.findViewById(R.id.text_5_3_small);
    this.A[0] = (TextView) var1.findViewById(R.id.text_5_3_big);
    this.A[1] = (TextView) var1.findViewById(R.id.text_5_4_small);
    this.A[2] = (TextView) var1.findViewById(R.id.text_5_4_big);
    this.A[3] = (TextView) var1.findViewById(R.id.text_5_5_small);
    this.A[4] = (TextView) var1.findViewById(R.id.text_5_5_big);
  }

  private void a(int[] var1, ImageButton var2, int var3) {
    var2.setClickable(false);
    var2.setFocusable(false);
    var2.layout(var1[0], var2.getTop(), var1[var3], var2.getBottom());
  }

  private void a(int[] var1, ImageButton[] var2) {
    for (int var3 = 0; var3 < var1.length; ++var3) {
      var2[var3].setClickable(false);
      var2[var3].setFocusable(false);
      var2[var3].layout(var1[var3] - var2[var3].getWidth() / 2, var2[var3].getTop(),
          var1[var3] + var2[var3].getWidth() / 2, var2[var3].getBottom());
    }
  }

  private void a(int[] var1, TextView[] var2) {
    for (int var3 = 0; var3 < var1.length; ++var3) {
      var2[var3].setClickable(false);
      var2[var3].setFocusable(false);
      var2[var3].layout(var1[var3] - var2[var3].getWidth() / 2, var2[var3].getTop(),
          var1[var3] + var2[var3].getWidth() / 2, var2[var3].getBottom());
    }
  }

  private boolean a(int[] var1, ImageButton[] var2, ImageButton[] var3, int var4) {
    this.l();

    int var5;
    for (var5 = 1; var5 < var2.length; ++var5) {
      if (var1[var5] > var4) {
        var2[var5].setVisibility(VISIBLE);
      }
    }

    for (var5 = 1; var5 < var3.length; ++var5) {
      if (var1[var5] <= var4) {
        var3[var5].setVisibility(VISIBLE);
      }
    }

    this.f.layout(this.s, this.f.getTop(), var4, this.f.getBottom());
    this.d.layout(var4 - this.d.getWidth() / 2, this.d.getTop(), this.d.getWidth() / 2 + var4,
        this.d.getBottom());
    return true;
  }

  private boolean a(int[] var1, TextView[] var2, TextView[] var3, ImageButton[] var4,
      ImageButton[] var5, int var6) {
    this.o();
    var2[var6].setVisibility(VISIBLE);

    int var7;
    for (var7 = 0; var7 < var3.length; ++var7) {
      var3[var7].setVisibility(VISIBLE);
    }

    var3[var6].setVisibility(INVISIBLE);

    for (var7 = var6 + 1; var7 < var4.length; ++var7) {
      var4[var7].setVisibility(VISIBLE);
    }

    for (var7 = 1; var7 < var6; ++var7) {
      var5[var7].setVisibility(VISIBLE);
    }

    this.f.layout(this.s, this.f.getTop(), var1[var6], this.f.getBottom());
    this.d.layout(var1[var6] - this.d.getWidth() / 2, this.d.getTop(),
        var1[var6] + this.d.getWidth() / 2, this.d.getBottom());
    if (this.J > 0) {
      this.d.setVisibility(VISIBLE);
      return true;
    } else {
      this.d.setVisibility(INVISIBLE);
      return true;
    }
  }

  private int b(int var1, int var2) {
    switch (var2) {
      case 2:
        return this.a(var1, this.o);
      case 3:
        return this.a(var1, this.p);
      case 4:
        return this.a(var1, this.q);
      case 5:
        return this.a(var1, this.r);
      default:
        return 1;
    }
  }

  private void b() {
    this.a(this.o, this.e, 1);
    this.a(this.o, this.g);
    this.a(this.p, this.i);
    this.a(this.q, this.k);
    this.a(this.r, this.m);
    this.a(this.o, this.h);
    this.a(this.p, this.j);
    this.a(this.q, this.l);
    this.a(this.r, this.n);
    this.a(this.o, this.x);
    this.a(this.p, this.y);
    this.a(this.q, this.z);
    this.a(this.r, this.A);
    this.a(this.o, this.t);
    this.a(this.p, this.u);
    this.a(this.q, this.v);
    this.a(this.r, this.w);
  }

  private void b(int var1) {
    switch (var1) {
      case 2:
        this.a(this.o, this.t, this.x, this.g, this.h, this.J);
        return;
      case 3:
        this.a(this.p, this.u, this.y, this.i, this.j, this.J);
        return;
      case 4:
        this.a(this.q, this.v, this.z, this.k, this.l, this.J);
        return;
      case 5:
        this.a(this.r, this.w, this.A, this.m, this.n, this.J);
        return;
      default:
    }
  }

  private void b(View var1) {
    this.g[0] = (ImageButton) var1.findViewById(R.id.bg_small_circle_yellow_2_1);
    this.g[1] = (ImageButton) var1.findViewById(R.id.bg_small_circle_write_2_1);
    this.h[0] = (ImageButton) var1.findViewById(R.id.bg_small_circle_yellow_2_2);
    this.h[1] = (ImageButton) var1.findViewById(R.id.bg_small_circle_write_2_2);
    this.i[0] = (ImageButton) var1.findViewById(R.id.bg_small_circle_yellow_3_1);
    this.i[1] = (ImageButton) var1.findViewById(R.id.bg_small_circle_write_3_1);
    this.i[2] = (ImageButton) var1.findViewById(R.id.bg_small_circle_yellow_3_2);
    this.j[0] = (ImageButton) var1.findViewById(R.id.bg_small_circle_write_3_2);
    this.j[1] = (ImageButton) var1.findViewById(R.id.bg_small_circle_yellow_3_3);
    this.j[2] = (ImageButton) var1.findViewById(R.id.bg_small_circle_write_3_3);
    this.k[0] = (ImageButton) var1.findViewById(R.id.bg_small_circle_yellow_4_1);
    this.k[1] = (ImageButton) var1.findViewById(R.id.bg_small_circle_write_4_1);
    this.k[2] = (ImageButton) var1.findViewById(R.id.bg_small_circle_yellow_4_2);
    this.k[3] = (ImageButton) var1.findViewById(R.id.bg_small_circle_write_4_2);
    this.l[0] = (ImageButton) var1.findViewById(R.id.bg_small_circle_yellow_4_3);
    this.l[1] = (ImageButton) var1.findViewById(R.id.bg_small_circle_write_4_3);
    this.l[2] = (ImageButton) var1.findViewById(R.id.bg_small_circle_yellow_4_4);
    this.l[3] = (ImageButton) var1.findViewById(R.id.bg_small_circle_write_4_4);
    this.m[0] = (ImageButton) var1.findViewById(R.id.bg_small_circle_yellow_5_1);
    this.m[1] = (ImageButton) var1.findViewById(R.id.bg_small_circle_write_5_1);
    this.m[2] = (ImageButton) var1.findViewById(R.id.bg_small_circle_yellow_5_2);
    this.m[3] = (ImageButton) var1.findViewById(R.id.bg_small_circle_write_5_2);
    this.m[4] = (ImageButton) var1.findViewById(R.id.bg_small_circle_yellow_5_3);
    this.n[0] = (ImageButton) var1.findViewById(R.id.bg_small_circle_write_5_3);
    this.n[1] = (ImageButton) var1.findViewById(R.id.bg_small_circle_yellow_5_4);
    this.n[2] = (ImageButton) var1.findViewById(R.id.bg_small_circle_write_5_4);
    this.n[3] = (ImageButton) var1.findViewById(R.id.bg_small_circle_yellow_5_5);
    this.n[4] = (ImageButton) var1.findViewById(R.id.bg_small_circle_write_5_5);
  }

  private void c() {
    View var1 = LayoutInflater.from(b).inflate(R.layout.slide_switch_bar, this);
    this.a(var1);
    this.b(var1);
    this.c = (LinearLayout) var1.findViewById(R.id.sv_container);
    this.c.setOnTouchListener(this.M);
    this.e = (ImageButton) var1.findViewById(R.id.bg_line_white);
    this.e.setClickable(false);
    this.e.setFocusable(false);
    this.f = (ImageButton) var1.findViewById(R.id.bg_line_yellow);
    this.f.setClickable(false);
    this.f.setFocusable(false);
    this.d = (ImageView) var1.findViewById(R.id.iv_switch_cursor);
    this.d.setClickable(true);
    this.d.setOnTouchListener(this.N);
  }

  private void c(int var1) {
    this.e();
    var1 = this.b(var1, this.a);
    if (var1 >= 1) {
      switch (this.a) {
        case 2:
          this.F = this.o[this.K];
          break;
        case 3:
          this.F = this.p[this.K];
          break;
        case 4:
          this.F = this.q[this.K];
          break;
        case 5:
          this.F = this.r[this.K];
      }

      this.f.setVisibility(VISIBLE);
      this.d.setVisibility(VISIBLE);
      this.d(var1);
    }
  }

  private void d() {
    switch (this.a) {
      case 2:
        if (this.D <= this.o[1] - this.d.getWidth() / 2) {
          this.D = this.o[1] - this.d.getWidth() / 2;
          this.E = this.D + this.d.getWidth();
        }
        break;
      case 3:
        if (this.D <= this.p[1] - this.d.getWidth() / 2) {
          this.D = this.p[1] - this.d.getWidth() / 2;
          this.E = this.D + this.d.getWidth();
        }
        break;
      case 4:
        if (this.D <= this.q[1] - this.d.getWidth() / 2) {
          this.D = this.q[1] - this.d.getWidth() / 2;
          this.E = this.D + this.d.getWidth();
        }
        break;
      case 5:
        if (this.D <= this.r[1] - this.d.getWidth() / 2) {
          this.D = this.r[1] - this.d.getWidth() / 2;
          this.E = this.D + this.d.getWidth();
        }
    }

    if (this.E >= this.C + this.d.getWidth() / 2 - this.s) {
      this.E = this.C + this.d.getWidth() / 2 - this.s;
      this.D = this.E - this.d.getWidth();
    }
  }

  private void d(int var1) {
    this.J = var1;
    this.g();
  }

  private void e() {
    int var1 = this.C - this.B - this.s * 2;
    int var2 = var1 / 1;
    this.o[0] = this.s;
    this.o[1] = var2 + this.s;
    var2 = var1 / 2;
    this.p[0] = this.s;
    this.p[1] = this.s + var2;
    this.p[2] = var2 * 2 + this.s;
    var2 = var1 / 3;
    this.q[0] = this.s;
    this.q[1] = this.s + var2;
    this.q[2] = this.s + var2 * 2;
    this.q[3] = var2 * 3 + this.s;
    var1 /= 4;
    this.r[0] = this.s;
    this.r[1] = this.s + var1;
    this.r[2] = this.s + var1 * 2;
    this.r[3] = this.s + var1 * 3;
    this.r[4] = var1 * 4 + this.s;
  }

  private void f() {
    this.e();
    this.J = this.b(this.F, this.a);
    this.d(this.J);
  }

  private void g() {
    this.H = null;
    int var1 = 0;
    switch (this.a) {
      case 2:
        var1 = this.o[this.J] - (this.d.getLeft() + this.d.getRight()) / 2;
        break;
      case 3:
        var1 = this.p[this.J] - (this.d.getLeft() + this.d.getRight()) / 2;
        break;
      case 4:
        var1 = this.q[this.J] - (this.d.getLeft() + this.d.getRight()) / 2;
        break;
      case 5:
        var1 = this.r[this.J] - (this.d.getLeft() + this.d.getRight()) / 2;
    }

    this.H = new TranslateAnimation(0.0F, (float) var1, 0.0F, 0.0F);
    this.H.setDuration(150L);
    this.H.setFillEnabled(true);
    this.H.setInterpolator(new LinearInterpolator());
    this.H.setAnimationListener(new Animation.AnimationListener() {
      public void onAnimationEnd(Animation var1) {
        SlideSwitchBar.this.h();
      }

      public void onAnimationRepeat(Animation var1) {
      }

      public void onAnimationStart(Animation var1) {
      }
    });
    this.d.startAnimation(this.H);
  }

  private void h() {
    switch (this.a) {
      case 2:
        this.D = this.o[this.J] - this.d.getWidth() / 2;
        this.E = this.D + this.d.getWidth();
        break;
      case 3:
        this.D = this.p[this.J] - this.d.getWidth() / 2;
        this.E = this.D + this.d.getWidth();
        break;
      case 4:
        this.D = this.q[this.J] - this.d.getWidth() / 2;
        this.E = this.D + this.d.getWidth();
        break;
      case 5:
        this.D = this.r[this.J] - this.d.getWidth() / 2;
        this.E = this.D + this.d.getWidth();
    }

    this.F = this.D + this.G / 2;
    this.b(this.a);
    if (this.L && this.I != null && this.J > 0) {
      this.I.sliderTo(this.J);
      this.K = this.J;
    }
  }

  private void i() {
    this.k();
    this.j();
  }

  private void j() {
    byte var3 = 0;

    int var1;
    for (var1 = 0; var1 < this.x.length; ++var1) {
      this.x[var1].setVisibility(INVISIBLE);
    }

    for (var1 = 0; var1 < this.y.length; ++var1) {
      this.y[var1].setVisibility(INVISIBLE);
    }

    var1 = 0;

    while (true) {
      int var2 = var3;
      if (var1 >= this.z.length) {
        while (var2 < this.A.length) {
          this.A[var2].setVisibility(INVISIBLE);
          ++var2;
        }

        return;
      }

      this.z[var1].setVisibility(INVISIBLE);
      ++var1;
    }
  }

  private void k() {
    byte var3 = 0;

    int var1;
    for (var1 = 0; var1 < this.t.length; ++var1) {
      this.t[var1].setVisibility(INVISIBLE);
    }

    for (var1 = 0; var1 < this.u.length; ++var1) {
      this.u[var1].setVisibility(INVISIBLE);
    }

    var1 = 0;

    while (true) {
      int var2 = var3;
      if (var1 >= this.v.length) {
        while (var2 < this.w.length) {
          this.w[var2].setVisibility(INVISIBLE);
          ++var2;
        }

        return;
      }

      this.v[var1].setVisibility(INVISIBLE);
      ++var1;
    }
  }

  private void l() {
    this.m();
    this.n();
  }

  private void m() {
    byte var3 = 0;

    int var1;
    for (var1 = 0; var1 < this.h.length; ++var1) {
      this.h[var1].setVisibility(INVISIBLE);
    }

    for (var1 = 0; var1 < this.j.length; ++var1) {
      this.j[var1].setVisibility(INVISIBLE);
    }

    var1 = 0;

    while (true) {
      int var2 = var3;
      if (var1 >= this.l.length) {
        while (var2 < this.n.length) {
          this.n[var2].setVisibility(INVISIBLE);
          ++var2;
        }

        return;
      }

      this.l[var1].setVisibility(INVISIBLE);
      ++var1;
    }
  }

  private void n() {
    byte var3 = 0;

    int var1;
    for (var1 = 0; var1 < this.g.length; ++var1) {
      this.g[var1].setVisibility(INVISIBLE);
    }

    for (var1 = 0; var1 < this.i.length; ++var1) {
      this.i[var1].setVisibility(INVISIBLE);
    }

    var1 = 0;

    while (true) {
      int var2 = var3;
      if (var1 >= this.k.length) {
        while (var2 < this.m.length) {
          this.m[var2].setVisibility(INVISIBLE);
          ++var2;
        }

        return;
      }

      this.k[var1].setVisibility(INVISIBLE);
      ++var1;
    }
  }

  private void o() {
    this.i();
    this.l();
  }

  private void setGroupSmallTextViewVisible(int var1) {
    switch (var1) {
      case 2:
        for (var1 = 0; var1 < this.x.length; ++var1) {
          this.x[var1].setVisibility(VISIBLE);
        }

        return;
      case 3:
        for (var1 = 0; var1 < this.y.length; ++var1) {
          this.y[var1].setVisibility(VISIBLE);
        }

        return;
      case 4:
        for (var1 = 0; var1 < this.z.length; ++var1) {
          this.z[var1].setVisibility(VISIBLE);
        }

        return;
      case 5:
        for (var1 = 0; var1 < this.A.length; ++var1) {
          this.A[var1].setVisibility(VISIBLE);
        }
    }
  }

  private void setGroupWhiteCircleVisible(int var1) {
    int var3 = 1;
    int var4 = 1;
    int var5 = 1;
    int var2 = 1;
    switch (var1) {
      case 2:
        while (var2 < this.g.length) {
          this.g[var2].setVisibility(VISIBLE);
          ++var2;
        }

        return;
      case 3:
        while (var3 < this.i.length) {
          this.i[var3].setVisibility(VISIBLE);
          ++var3;
        }

        return;
      case 4:
        while (var4 < this.k.length) {
          this.k[var4].setVisibility(VISIBLE);
          ++var4;
        }

        return;
      case 5:
        while (var5 < this.m.length) {
          this.m[var5].setVisibility(VISIBLE);
          ++var5;
        }
    }
  }

  public boolean a(int var1) {
    if (var1 > this.a) {
      return false;
    } else if (var1 == 0) {
      this.J = 0;
      this.K = 0;
      this.a();
      return true;
    } else {
      this.L = false;
      this.J = var1;
      this.K = var1;
      this.h();
      return true;
    }
  }

  public boolean a(SliderListener var1) {
    if (var1 == null) {
      return false;
    } else {
      this.I = var1;
      return true;
    }
  }

  public boolean a(String[] var1, int var2) {
    int var3;
    switch (var1.length) {
      case 2:
        for (var3 = 0; var3 < this.x.length; ++var3) {
          this.x[var3].setText(var1[var3]);
          this.t[var3].setText(var1[var3]);
        }

        this.a = 2;
        break;
      case 3:
        for (var3 = 0; var3 < this.y.length; ++var3) {
          this.y[var3].setText(var1[var3]);
          this.u[var3].setText(var1[var3]);
        }

        this.a = 3;
        break;
      case 4:
        for (var3 = 0; var3 < this.z.length; ++var3) {
          this.z[var3].setText(var1[var3]);
          this.v[var3].setText(var1[var3]);
        }

        this.a = 4;
        break;
      case 5:
        for (var3 = 0; var3 < this.A.length; ++var3) {
          this.A[var3].setText(var1[var3]);
          this.w[var3].setText(var1[var3]);
        }

        this.a = 5;
        break;
      default:
        return false;
    }

    this.L = false;
    if (var2 > 0 && var2 < var1.length) {
      this.J = var2;
      this.K = var2;
      this.h();
      return true;
    } else {
      this.J = 0;
      this.K = 0;
      this.h();
      return false;
    }
  }

  public int getCursorCenterLocationInParent() {
    return this.d.getLeft() + this.d.getWidth() / 2;
  }

  public int getCursorCenterLocationOnScreen() {
    int[] var1 = new int[2];
    this.d.getLocationOnScreen(var1);
    return var1[0] + this.d.getWidth() / 2;
  }

  public int getSelected() {
    return this.J;
  }

  public String[] getTextArray() {
    byte var2 = 0;
    byte var3 = 0;
    byte var4 = 0;
    int var1 = 0;
    String[] var5;
    switch (this.a) {
      case 2:
        for (var5 = new String[this.x.length]; var1 < this.x.length; ++var1) {
          var5[var1] = this.x[var1].getText().toString();
        }

        return var5;
      case 3:
        var5 = new String[this.y.length];

        for (var1 = var2; var1 < this.y.length; ++var1) {
          var5[var1] = this.y[var1].getText().toString();
        }

        return var5;
      case 4:
        var5 = new String[this.z.length];

        for (var1 = var3; var1 < this.z.length; ++var1) {
          var5[var1] = this.z[var1].getText().toString();
        }

        return var5;
      case 5:
        var5 = new String[this.A.length];

        for (var1 = var4; var1 < this.A.length; ++var1) {
          var5[var1] = this.A[var1].getText().toString();
        }

        return var5;
      default:
        return null;
    }
  }

  protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
    super.onLayout(var1, var2, var3, var4, var5);
    this.B = this.c.getLeft();
    this.C = this.c.getRight();
    this.D = this.d.getLeft();
    this.d.getTop();
    this.E = this.d.getRight();
    this.d.getBottom();
    this.F = (this.D + this.E) / 2;
    this.G = this.E - this.D;
    this.e();
    this.b();
    if (this.J == 0) {
      this.a();
    } else {
      this.b(this.a);
    }
  }
}