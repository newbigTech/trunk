package com.uniware.driver.gui.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import com.uniware.driver.R;

/**
 * Created by jian on 15/12/14.
 */
public class Speaker extends ImageView {
  private AnimationDrawable a = null;
  private Drawable b;
  private Speaker.a c;
  private boolean d = false;
  private OnClickListener e = new OnClickListener() {
    public void onClick(View var1) {
      if (!Speaker.this.d) {
        Speaker.this.d = true;
        Speaker.this.setBackgroundDrawable(Speaker.this.a);
        Speaker.this.a.start();
        if (Speaker.this.c != null) {
          Speaker.this.c.a();
        }
      } else {
        Speaker.this.d = false;
        Speaker.this.a.stop();
        Speaker.this.setBackgroundDrawable(Speaker.this.b);
        if (Speaker.this.c != null) {
          Speaker.this.c.b();
          return;
        }
      }
    }
  };

  public Speaker(Context var1, AttributeSet var2) {
    super(var1, var2);
    this.setClickable(false);
    TypedArray var3 = var1.obtainStyledAttributes(var2, new int[] { R.attr.animDrawable });
    if (var3.hasValue(R.styleable.Speaker_animDrawable)) {
      this.a = (AnimationDrawable) var3.getDrawable(R.styleable.Speaker_animDrawable);
    } else {
      this.a =
          (AnimationDrawable) this.getResources().getDrawable(R.drawable.didi_msg_list_speaker);
    }

    var3.recycle();
    this.b = this.a.getFrame(0);
    this.setBackgroundDrawable(this.b);
    this.d = false;
  }

  public void a() {
    if (this.a != null) {
      this.d = true;
      this.setBackgroundDrawable(this.a);
      this.a.start();
    }
  }

  public void b() {
    this.d = false;
    this.a.stop();
    this.setBackgroundDrawable(this.b);
  }

  public boolean c() {
    return this.d;
  }

  public void setSpeaker(Speaker.a var1) {
    this.c = var1;
  }

  public interface a {
    void a();

    void b();
  }
}