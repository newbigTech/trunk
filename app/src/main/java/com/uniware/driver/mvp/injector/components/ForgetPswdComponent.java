package com.uniware.driver.mvp.injector.components;

import com.uniware.driver.gui.activity.ForgetPswdActivity;
import com.uniware.driver.mvp.injector.PerActivity;
import com.uniware.driver.mvp.injector.modules.ActivityModule;
import com.uniware.driver.mvp.injector.modules.ForgetPswdModule;
import dagger.Component;

/**
 * Created by jian on 15/12/30.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = { ActivityModule.class, ForgetPswdModule.class })
public interface ForgetPswdComponent extends ActivityComponent {
  void inject(ForgetPswdActivity activity);
}
