package com.uniware.driver.mvp.injector.components;

import com.uniware.driver.gui.activity.MainActivity;
import com.uniware.driver.gui.activity.OrderPopActivity;
import com.uniware.driver.gui.fragment.AnnounceFragment;
import com.uniware.driver.gui.fragment.DriverFragment;
import com.uniware.driver.gui.fragment.GridFragment;
import com.uniware.driver.gui.fragment.RankFragment;
import com.uniware.driver.gui.fragment.order.OrderFragment;
import com.uniware.driver.mvp.injector.PerActivity;
import com.uniware.driver.mvp.injector.modules.ActivityModule;
import com.uniware.driver.mvp.injector.modules.AddressModule;
import com.uniware.driver.mvp.injector.modules.DriverModule;
import dagger.Component;

/**
 * Created by jian on 15/12/08.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, DriverModule.class,
    AddressModule.class})
public interface DriverComponent extends ActivityComponent{
  void inject(MainActivity activity);
  void inject(OrderPopActivity activity);


  void inject(OrderFragment orderFragment);
  void inject(DriverFragment driverFragment);
  void inject(AnnounceFragment announceFragment);
  void inject(GridFragment gridFragment);
}
