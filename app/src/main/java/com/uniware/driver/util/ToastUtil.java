package com.uniware.driver.util;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.uniware.driver.AppApplication;
import com.uniware.driver.R;

/**
 * Created by jian on 15/11/12.
 */
public class ToastUtil {
  private static ToastUtil toastUtil = null;
  private Toast mToast = null;

  private ToastUtil() {
    this.mToast = Toast.makeText(AppApplication.getAppContext(), "", Toast.LENGTH_SHORT);
  }

  public static ToastUtil getToast() {
    if (toastUtil == null) {
      synchronized (ToastUtil.class) {
        if (toastUtil == null) {
          toastUtil = new ToastUtil();
        }
      }
    }
    return toastUtil;
  }

  public void show(int rId, String text, int gravity, int duration) {
    View view =
        LayoutInflater.from(AppApplication.getAppContext()).inflate(R.layout.toast_layout, null);
    ImageView imageView = (ImageView) view.findViewById(R.id.iv_icon);
    if (rId <= 0) {
      imageView.setVisibility(View.GONE);
    } else {
      imageView.setVisibility(View.VISIBLE);
      imageView.setBackgroundResource(rId);
    }

    TextView textView = (TextView) view.findViewById(R.id.txt_value);
    if (TextUtils.isEmpty(text)) {
      textView.setVisibility(View.GONE);
    } else {
      textView.setVisibility(View.VISIBLE);
      textView.setText(text);
    }

    this.mToast.setGravity(gravity, 0, 0);
    this.mToast.setDuration(duration);
    this.mToast.setView(view);
    this.mToast.show();
  }

  public void show(int rId) {
    this.show(0, AppApplication.getAppContext().getString(rId), 17, 0);
  }

  public void show(String text) {
    this.show(0, text, 17, 0);
  }

  public void show(int rId, int duration) {
    this.show(0, AppApplication.getAppContext().getString(rId), 17, duration);
  }

  public void show(String text, int duration) {
    this.show(0, text, 17, duration);
  }
}
