package com.uniware.driver.util;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by SJ on 15/11/11.
 */
public class InPutTools {
  public static void a(Activity var0) {
    if (var0.getCurrentFocus() != null) {
      ((InputMethodManager) var0.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
          var0.getCurrentFocus().getWindowToken(), 2);
    }
  }

  public static void a(View var0) {
    if (var0 != null) {
      ((InputMethodManager) var0.getContext()
          .getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(var0.getWindowToken(), 2);
    }
  }

  public static void b(View var0) {
    if (var0 != null) {
      ((InputMethodManager) var0.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(var0,
          1);
    }
  }

  public static void c(View var0) {
    if (var0 != null) {
      var0.setOnTouchListener(new View.OnTouchListener() {
        public boolean onTouch(View var1, MotionEvent var2) {
          if (var2.getAction() == 0) {
            InPutTools.a(var1);
          }

          return false;
        }
      });
    }
  }
}
