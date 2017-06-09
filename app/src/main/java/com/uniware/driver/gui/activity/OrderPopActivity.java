package com.uniware.driver.gui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.uniware.driver.R;
import com.uniware.driver.gui.fragment.order.OrderFragment;
import com.uniware.driver.mvp.injector.HasComponent;
import com.uniware.driver.mvp.injector.components.DaggerDriverComponent;
import com.uniware.driver.mvp.injector.components.DriverComponent;

/**
 * Created by SJ on 16/04/27.
 */
public class OrderPopActivity extends BaseActivity implements HasComponent<DriverComponent> {

  private DriverComponent driverComponent;

  @Override public void setContentView(int layoutResID) {
    super.setContentView(R.layout.activity_order_pop);
    View view = LayoutInflater.from(this).inflate(layoutResID, null);
    ((FrameLayout) findViewById(R.id.content)).addView(view);
    OrderFragment fragment = OrderFragment.newInstance("pop", true);
    getFragmentManager().beginTransaction()
        .add(R.id.order_pop_content, fragment)
        .hide(fragment)
        .commit();
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeInjector();

  }

  @Override protected void onStart() {
    super.onStart();
  }

  @Override protected void onResume() {
    super.onResume();
  }

  @Override protected void onPause() {
    super.onPause();
  }

  @Override protected void onStop() {
    super.onStop();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
  }

  private void initializeInjector() {
    driverComponent = DaggerDriverComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
    driverComponent.inject(this);
  }

  @Override public DriverComponent getComponent() {
    return driverComponent;
  }
}
