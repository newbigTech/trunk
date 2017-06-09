package com.uniware.driver.mvp.injector.modules;

import com.uniware.driver.domain.executor.PostExecutionThread;
import com.uniware.driver.domain.executor.ThreadExecutor;
import com.uniware.driver.domain.interactor.Countdown;
import com.uniware.driver.domain.interactor.RecvPush;
import com.uniware.driver.domain.interactor.StartPush;
import com.uniware.driver.domain.interactor.UpdateLocation;
import com.uniware.driver.domain.interactor.UseCase;
import com.uniware.driver.domain.repository.Repository;
import com.uniware.driver.mvp.injector.PerActivity;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

/**
 * Created by jian on 15/12/08.
 */
@Module public class ControlModule {

  @Provides @PerActivity @Named("startPush") UseCase provideStartPushUseCase(Repository repository,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new StartPush(repository, threadExecutor, postExecutionThread);
  }

  @Provides @PerActivity @Named("updateLocation") UseCase provideUpdateLocationUseCase(
      Repository repository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    return new UpdateLocation(repository, threadExecutor, postExecutionThread);
  }

  @Provides @PerActivity @Named("recvPush") UseCase provideRecvPushUseCase(Repository repository,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new RecvPush(repository, threadExecutor, postExecutionThread);
  }

  @Provides @PerActivity @Named("countdown") UseCase provideCountdownUseCase(
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new Countdown(threadExecutor, postExecutionThread);
  }
}
