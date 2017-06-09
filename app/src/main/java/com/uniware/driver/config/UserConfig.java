package com.uniware.driver.config;

import android.content.Context;
import android.content.SharedPreferences;
import com.uniware.driver.AppApplication;

/**
 * Created by jian on 15/11/11.
 */
public class UserConfig {
  private static UserConfig userConfig;
  private SharedPreferences mPref;
  private SharedPreferences.Editor mEditor;
  static {
    userConfig = new UserConfig(AppApplication.getAppContext());
  }

  private UserConfig(Context context){
    mPref = context.getSharedPreferences(Config.USER_CONFIG_NAME, Context.MODE_PRIVATE);
    mEditor = mPref.edit();
  }

  public static UserConfig getInstance(){
    return userConfig;
  }
}
