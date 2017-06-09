package com.uniware.driver.gui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.PixelFormat;
import android.os.*;

import android.os.Process;
import com.umeng.analytics.MobclickAgent;
import com.uniware.driver.AppApplication;
import com.uniware.driver.mvp.injector.components.ApplicationComponent;
import com.uniware.driver.mvp.injector.modules.ActivityModule;

import java.util.List;

/**
 * Base {@link Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends Activity {

  private AppApplication appApplication;

  /**
   * Adds a {@link Fragment} to this activity's layout.
   *
   * @param containerViewId The container view to where add the fragment.
   * @param fragment The fragment to be added.
   */
  protected void addFragment(int containerViewId, Fragment fragment) {
    FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
    fragmentTransaction.add(containerViewId, fragment);
    fragmentTransaction.commit();
  }

  /**
   * Get the Main Application component for dependency injection.
   *
   * @return {@link ApplicationComponent}
   */
  protected ApplicationComponent getApplicationComponent() {
    return ((AppApplication)getApplication()).getApplicationComponent();
  }

  /**
   * Get an Activity module for dependency injection.
   *
   * @return {@link ActivityModule}
   */
  protected ActivityModule getActivityModule() {
    return new ActivityModule(this);
  }

  public AppApplication getAppApplication() {
    return appApplication;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getWindow().setFormat(PixelFormat.TRANSLUCENT);
    this.getApplicationComponent().inject(this);
    appApplication = (AppApplication) getApplication();
    appApplication.getActivityStack().add(this);
  }

  @Override protected void onResume() {
    super.onResume();
    //统计时长
    MobclickAgent.onResume(this);
  }

  @Override protected void onPause() {
    super.onPause();
    //统计时长
    MobclickAgent.onPause(this);
  }

  @Override protected void onDestroy() {
    appApplication.getActivityStack().remove(this);
    super.onDestroy();
  }

  public void exitApp(){
    List activityList = appApplication.getActivityStack();
    while (activityList.size()>0){
      ((Activity)activityList.remove(0)).finish();
    }
    MobclickAgent.onKillProcess(this);
    //tips: 关闭服务
    Process.killProcess(Process.myPid());
  }
}
