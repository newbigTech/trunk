package com.uniware.driver.mvp.injector.modules;

import com.uniware.driver.domain.executor.PostExecutionThread;
import com.uniware.driver.domain.executor.ThreadExecutor;
import com.uniware.driver.domain.interactor.Code;
import com.uniware.driver.domain.interactor.Register;
import com.uniware.driver.domain.interactor.UseCase;
import com.uniware.driver.domain.repository.Repository;
import com.uniware.driver.mvp.injector.PerActivity;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

/**
 * Created by jian on 15/12/30.
 */
@Module public class RegisterModule {

  @Provides @PerActivity @Named("register") UseCase providerRegisterUseCase(Repository repository,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new Register(repository, threadExecutor, postExecutionThread);
  }
  @Provides @PerActivity @Named("code") UseCase providerCodeUseCase(Repository repository,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new Code(repository, threadExecutor, postExecutionThread);
  }
}
