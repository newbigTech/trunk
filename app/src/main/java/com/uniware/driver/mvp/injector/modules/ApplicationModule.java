package com.uniware.driver.mvp.injector.modules;

import android.content.Context;

import com.uniware.driver.data.cache.DataCache;
import com.uniware.driver.data.cache.DataCacheImpl;
import com.uniware.driver.data.executor.JobExecutor;
import com.uniware.driver.data.repository.DataRepository;
import com.uniware.driver.mvp.UIThread;
import com.uniware.driver.AppApplication;
import com.uniware.driver.domain.executor.PostExecutionThread;
import com.uniware.driver.domain.executor.ThreadExecutor;
import com.uniware.driver.domain.repository.Repository;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module public class ApplicationModule {
  private final AppApplication application;

  public ApplicationModule(AppApplication application) {
    this.application = application;
  }

  @Provides @Singleton Context provideApplicationContext() {
    return this.application;
  }

  @Provides @Singleton ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
    return jobExecutor;
  }

  @Provides @Singleton PostExecutionThread providePostExecutionThread(UIThread uiThread) {
    return uiThread;
  }

  @Provides @Singleton DataCache provideDataCache(DataCacheImpl dataCache) {
    return dataCache;
  }

  @Provides @Singleton Repository provideRepository(DataRepository dataRepository) {
    return dataRepository;
  }
}
