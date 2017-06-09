package com.uniware.driver.mvp.injector.components;

import com.uniware.driver.gui.activity.LoginActivity;
import com.uniware.driver.mvp.injector.PerActivity;
import com.uniware.driver.mvp.injector.modules.ActivityModule;
import com.uniware.driver.mvp.injector.modules.LoginModule;
import dagger.Component;

/**
 * Created by jian on 15/12/30.
 */
@PerActivity @Component(dependencies = ApplicationComponent.class, modules = { ActivityModule.class, LoginModule.class })
public interface LoginComponent extends ActivityComponent {
  void inject(LoginActivity activity);
}
