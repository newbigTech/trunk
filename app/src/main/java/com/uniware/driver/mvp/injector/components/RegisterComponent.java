package com.uniware.driver.mvp.injector.components;

import com.uniware.driver.gui.activity.RegisterActivity;
import com.uniware.driver.mvp.injector.PerActivity;
import com.uniware.driver.mvp.injector.modules.ActivityModule;
import com.uniware.driver.mvp.injector.modules.RegisterModule;
import dagger.Component;

/**
 * Created by jian on 15/12/30.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = { ActivityModule.class, RegisterModule.class })
public interface RegisterComponent extends ActivityComponent {
  void inject(RegisterActivity activity);
}
