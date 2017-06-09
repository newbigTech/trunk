package com.uniware.driver.config;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dukun on 2016/9/2.
 */

/**
 * 永不删除的信息缓存信息不会被删除
 */
public class NDPreferences {

  private static String TAG = "NDPreferences";
  private SharedPreferences preferences;
  private SharedPreferences.Editor editor;

  public NDPreferences(Context context) {
    preferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
    editor = preferences.edit();
  }

  /**
   * 存 客服电话
   */
  public void setServicePhone(String servicePhone) {
    editor.putString("service_phone", servicePhone);
    editor.commit();
  }

  public String getServicePhone() {
    return preferences.getString("uboxServicePhone", Config.SERVICE_MOBILE);
  }

  /**
   * 存 版本号
   */
  public void setAppVersion(String appVersion) {
    editor.putString("App_Version", appVersion);
    editor.commit();
  }

  public String getAppVersion() {
    return preferences.getString("App_Version", null);
  }

  /**
   * 设置升级类型
   */
  public void setUpgradeType(int type) {
    editor.putInt("upgrade_type", type);
    editor.commit();
  }

  public int getUpgradeType() {
    return preferences.getInt("upgrade_type", 0);
  }
}
