package com.uniware.driver.config;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dukun on 2016/9/2.
 */

/**
 * 登出的时候需删除的信息
 */
public class LDPreferences {

  private static String TAG = "LDPreferences";
  private Context mContext;
  private SharedPreferences preferences;
  private SharedPreferences.Editor editor;

  public LDPreferences(Context context) {
    this.mContext = context;
    preferences = mContext.getSharedPreferences(TAG, Context.MODE_PRIVATE);
    editor = preferences.edit();
  }

  /**
   * 登出时调用，清除所有的SP
   */
  public void clareLDPreferences(){
    if (editor != null){
      editor.clear();
      editor.commit();
    }
  }

  /**
   * 存储用户手机号
   */
  public void setUserPhone(String phone){
    editor.putString("user_phone", phone);
    editor.commit();
  }

  public String getUserPhone(){
    return preferences.getString("user_phone", "");
  }

  /**
   * 存储用户密码
   */
  public void setUserPassword(String password) {
    editor.putString("user_password", password);
    editor.commit();
  }

  public String getUserPassword() {
    return preferences.getString("user_password", "");
  }

  /**
   * 存uid
   */
  public void setUid(String uid) {
    editor.putString("uid", uid);
    editor.commit();
  }

  public String getUid() {
    return preferences.getString("uid", "");
  }

  /**
   * 存Token
   */
  public void setToken(String token) {
    editor.putString("token", token);
    editor.commit();
  }

  public String getToken() {
    return preferences.getString("token", "");
  }

  /**
   * 是否首次登陆
   */
  public void setIsFirstLogin(boolean is) {
    editor.putBoolean("IsFirstLogin", is);
    editor.commit();
  }

  public boolean getIsFirstLogin() {
    return preferences.getBoolean("IsFirstLogin", false);
  }
}
