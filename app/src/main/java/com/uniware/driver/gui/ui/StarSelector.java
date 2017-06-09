package com.uniware.driver.gui.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.uniware.driver.R;

/**
 * Created by jian on 15/12/14.
 */
public class StarSelector extends LinearLayout {
  private ImageView a;
  private ImageView b;
  private ImageView c;
  private ImageView d;
  private ImageView e;
  private LinearLayout f;
  private int g;
  private boolean h;
  private boolean i;
  private boolean j;
  private boolean k;
  private boolean l;
  private OnTouchListener m = new OnTouchListener() {
    public boolean onTouch(View var1, MotionEvent var2) {
      switch(var2.getAction()) {
        case 2:
          StarSelector.this.a(var2.getX());
        case 0:
        case 1:
        default:
          return true;
      }
    }
  };

  public StarSelector(Context var1, AttributeSet var2) {
    super(var1, var2);
    inflate(var1, R.layout.star_selector, this);
    this.f = (LinearLayout)this.findViewById(R.id.selector_layout);
    this.a = (ImageView)this.findViewById(R.id.star_one);
    this.b = (ImageView)this.findViewById(R.id.star_two);
    this.c = (ImageView)this.findViewById(R.id.star_three);
    this.d = (ImageView)this.findViewById(R.id.star_four);
    this.e = (ImageView)this.findViewById(R.id.star_five);
    this.a.setImageResource(R.drawable.five_star_pro);
    this.b.setImageResource(R.drawable.five_star_pro);
    this.c.setImageResource(R.drawable.five_star_pro);
    this.g = 5;
    this.d.setImageResource(R.drawable.five_star_pro);
    this.e.setImageResource(R.drawable.five_star_pro);
  }

  private int a(float var1) {
    if(var1 >= (float)this.a.getLeft() && var1 < (float)this.b.getLeft()) {
      this.a.setImageResource(R.drawable.five_star_pro);
      this.g = 1;
      if(!this.h) {
        this.h = true;
      }
    } else {
      this.h = false;
    }

    if(var1 >= (float)this.b.getLeft() && var1 < (float)this.c.getLeft()) {
      this.a.setImageResource(R.drawable.five_star_pro);
      this.b.setImageResource(R.drawable.five_star_pro);
      this.g = 2;
      if(!this.i) {
        this.i = true;
      }
    } else {
      this.b.setImageResource(R.drawable.five_star_bg);
      this.i = false;
    }

    if(var1 >= (float)this.c.getLeft() && var1 < (float)this.d.getLeft()) {
      this.c.setImageResource(R.drawable.five_star_pro);
      this.a.setImageResource(R.drawable.five_star_pro);
      this.b.setImageResource(R.drawable.five_star_pro);
      this.g = 3;
      if(!this.j) {
        this.j = true;
      }
    } else {
      this.c.setImageResource(R.drawable.five_star_bg);
      this.j = false;
    }

    if(var1 >= (float)this.d.getLeft() && var1 < (float)this.e.getLeft()) {
      this.d.setImageResource(R.drawable.five_star_pro);
      this.c.setImageResource(R.drawable.five_star_pro);
      this.a.setImageResource(R.drawable.five_star_pro);
      this.b.setImageResource(R.drawable.five_star_pro);
      this.g = 4;
      if(!this.k) {
        this.k = true;
      }
    } else {
      this.d.setImageResource(R.drawable.five_star_bg);
      this.k = false;
    }

    if(var1 >= (float)this.e.getLeft()) {
      this.d.setImageResource(R.drawable.five_star_pro);
      this.c.setImageResource(R.drawable.five_star_pro);
      this.a.setImageResource(R.drawable.five_star_pro);
      this.b.setImageResource(R.drawable.five_star_pro);
      this.e.setImageResource(R.drawable.five_star_pro);
      this.g = 5;
      if(!this.l) {
        this.l = true;
      }
    } else {
      this.e.setImageResource(R.drawable.five_star_bg);
      this.l = false;
    }

    return this.g;
  }

  public int getStarLevel() {
    return this.g;
  }

  public void setIsCanTouch(boolean var1) {
    if(var1) {
      this.f.setOnTouchListener(this.m);
    } else {
      this.f.setOnClickListener((OnClickListener)null);
    }
  }

  public void setLevel(int var1) {
    this.g = var1;
    if(this.g >= 1) {
      this.a.setImageResource(R.drawable.five_star_pro);
    } else {
      this.a.setImageResource(R.drawable.five_star_bg);
    }

    if(this.g >= 2) {
      this.b.setImageResource(R.drawable.five_star_pro);
    } else {
      this.b.setImageResource(R.drawable.five_star_bg);
    }

    if(this.g >= 3) {
      this.c.setImageResource(R.drawable.five_star_pro);
    } else {
      this.c.setImageResource(R.drawable.five_star_bg);
    }

    if(this.g >= 4) {
      this.d.setImageResource(R.drawable.five_star_pro);
    } else {
      this.d.setImageResource(R.drawable.five_star_bg);
    }

    if(this.g >= 5) {
      this.e.setImageResource(R.drawable.five_star_pro);
    } else {
      this.e.setImageResource(R.drawable.five_star_bg);
    }
  }
}
