package com.uniware.driver.gui.ui;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by jian on 15/11/12.
 */
public class AppDialog {
  private Context context;
  private BaseDialog baseDialog;
  private ProgressDialog progressDialog = null;

  public AppDialog(Context context) {
    this.context = context;
  }

  public void show(int title, int detail) {
    if (baseDialog != null) {
      baseDialog.dismiss();
      baseDialog = null;
    }
    baseDialog = new BaseDialog(context);
    baseDialog.show();
    baseDialog.setMsg(title, detail);
  }

  public void show(String title, String detail) {
    if (baseDialog != null) {
      baseDialog.dismiss();
      baseDialog = null;
    }
    baseDialog = new BaseDialog(context);
    baseDialog.show();
    baseDialog.setMsg(title, detail);
  }

  public void show(String msg, boolean isCancelable) {
    if (this.progressDialog != null) {
      try {
        this.progressDialog.dismiss();
      } catch (Exception e) {
        e.printStackTrace();
      }
      this.progressDialog = null;
    }
    this.progressDialog = new ProgressDialog(context);
    this.progressDialog.setMessage(msg);
    this.progressDialog.setCancelable(isCancelable);
    this.progressDialog.show();
  }

  public void hide() {
    if (baseDialog != null) {
      baseDialog.dismiss();
    }
    if (progressDialog != null) {
      progressDialog.dismiss();
    }
  }
}
