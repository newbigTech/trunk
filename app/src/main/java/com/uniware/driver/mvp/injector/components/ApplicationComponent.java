package com.uniware.driver.mvp.injector.components;

import android.content.Context;

import com.uniware.driver.gui.activity.BaseActivity;
import com.uniware.driver.mvp.injector.modules.ApplicationModule;
import com.uniware.driver.domain.executor.PostExecutionThread;
import com.uniware.driver.domain.executor.ThreadExecutor;
import com.uniware.driver.domain.repository.Repository;

import dagger.Component;
import javax.inject.Singleton;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
  void inject(BaseActivity baseActivity);

  //Exposed to sub-graphs.
  Context context();
  ThreadExecutor threadExecutor();
  PostExecutionThread postExecutionThread();
  Repository Repository();
}
