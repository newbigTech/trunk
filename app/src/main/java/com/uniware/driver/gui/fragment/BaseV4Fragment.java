package com.uniware.driver.gui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;
import com.umeng.analytics.MobclickAgent;
import com.uniware.driver.mvp.injector.HasComponent;

/**
 * Base {@link Fragment} class for every fragment in this application.
 */
public abstract class BaseV4Fragment extends Fragment {

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  @Override public void onResume() {
    super.onResume();
    //统计页面，页面名称可自定义
    MobclickAgent.onPageStart(getClass().getSimpleName());
  }

  @Override public void onPause() {
    super.onPause();
    //统计页面，页面名称可自定义
    MobclickAgent.onPageEnd(getClass().getSimpleName());
  }

  /**
   * Shows a {@link Toast} message.
   *
   * @param message An string representing a message to be shown.
   */
  protected void showToastMessage(String message) {
    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
  }

  /**
   * Gets a component for dependency injection by its type.
   */
  @SuppressWarnings("unchecked")
  protected <C> C getComponent(Class<C> componentType) {
    return componentType.cast(((HasComponent<C>)getActivity()).getComponent());
  }
}
