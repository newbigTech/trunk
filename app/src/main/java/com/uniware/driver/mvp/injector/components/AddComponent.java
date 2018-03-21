package com.uniware.driver.mvp.injector.components;

import com.uniware.driver.gui.activity.AddressActivity;
import com.uniware.driver.gui.activity.RankActivity;
import com.uniware.driver.gui.activity.ShowAddressActivity;
import com.uniware.driver.gui.fragment.RankFragment;
import com.uniware.driver.mvp.injector.PerActivity;
import com.uniware.driver.mvp.injector.modules.ActivityModule;
import com.uniware.driver.mvp.injector.modules.AddressModule;
import dagger.Component;

/**
 * Created by ayue on 2017/7/26.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = { ActivityModule.class, AddressModule.class })
public interface AddComponent extends ActivityComponent {
  void inject(AddressActivity activity);
  void inject(ShowAddressActivity activity);
  void inject(RankActivity activity);
  void inject(RankFragment fragment);
}
