package com.uniware.driver.mvp.injector.components;

import com.uniware.driver.gui.activity.GoPickActivity;
import com.uniware.driver.gui.activity.OrderActivity;
import com.uniware.driver.gui.activity.OrderDetailActivity;
import com.uniware.driver.gui.fragment.AnnounceFragment;
import com.uniware.driver.gui.fragment.DriverFragment;
import com.uniware.driver.mvp.injector.PerActivity;
import com.uniware.driver.mvp.injector.modules.ActivityModule;
import com.uniware.driver.mvp.injector.modules.OrderModule;
import dagger.Component;

/**
 * Created by jian on 15/11/20.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, OrderModule.class})
public interface OrderComponent extends ActivityComponent {
  void inject(OrderDetailActivity activity);
  void inject(GoPickActivity activity);
  void inject(OrderActivity activity);
  //void inject(OrderFragment orderFragment);
  void inject(DriverFragment driverFragment);
  void inject(AnnounceFragment announceFragment);
}
