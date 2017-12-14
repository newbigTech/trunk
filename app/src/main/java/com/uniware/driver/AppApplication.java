package com.uniware.driver;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import com.baidu.mapapi.SDKInitializer;
import com.iflytek.cloud.SpeechUtility;
import com.umeng.analytics.MobclickAgent;
import com.uniware.driver.gui.activity.BaseActivity;
import com.uniware.driver.gui.activity.StartActivity;
import com.uniware.driver.mvp.injector.components.ApplicationComponent;
import com.uniware.driver.mvp.injector.components.DaggerApplicationComponent;
import com.uniware.driver.mvp.injector.modules.ApplicationModule;
import com.uniware.driver.service.LocationService;
import com.uniware.driver.util.LogUtils;
import com.uniware.driver.util.LogcatHelper;
import com.uniware.driver.util.Tools;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Android Main Application
 */
public class AppApplication extends Application {
  public LocationService locationService;
  public Vibrator mVibrator;
  private ApplicationComponent applicationComponent;
  private static Context mContext;
  public static boolean isNavUtil;
  private List<BaseActivity> mActivityStack =
      Collections.synchronizedList(new ArrayList<BaseActivity>());

  @Override public void onCreate() {
    super.onCreate();
    this.initializeInjector();
    LogcatHelper.getInstance(this).start();
    mContext = getApplicationContext();
    locationService = new LocationService(getApplicationContext());
    mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
    SDKInitializer.initialize(getApplicationContext());
    //友盟，禁止默认的页面统计方式，这样将不会再自动统计Activity
    MobclickAgent.openActivityDurationTrack(false);
    //讯飞初始化
    SpeechUtility.createUtility(this, "appid=584fbffe");
    //百度地图初始化
    //SDKInitializer.initialize(this);
    String deviceInfo = Tools.getDeviceInfo(this);
    LogUtils.e(this, deviceInfo);
  }

  private void initializeInjector() {
    this.applicationComponent =
        DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
  }

  public ApplicationComponent getApplicationComponent() {
    return this.applicationComponent;
  }

  public BaseActivity getTopActivity() {
    if (!mActivityStack.isEmpty()) {
      for (int i = mActivityStack.size() - 1; i >= 0; --i) {
        BaseActivity activity = mActivityStack.get(i);
        if (!(activity instanceof StartActivity)) return activity;
      }
    }
    return null;
  }

  public static Context getAppContext() {
    return mContext;
  }

  public List<BaseActivity> getActivityStack() {
    return mActivityStack;
  }
}
