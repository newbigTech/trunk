package com.uniware.driver.mvp.injector.modules;

import com.uniware.driver.domain.executor.PostExecutionThread;
import com.uniware.driver.domain.executor.ThreadExecutor;
import com.uniware.driver.domain.interactor.Login;
import com.uniware.driver.domain.interactor.UseCase;
import com.uniware.driver.domain.interactor.VersionDetail;
import com.uniware.driver.domain.repository.Repository;
import com.uniware.driver.mvp.injector.PerActivity;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

/**
 * Created by jian on 15/12/30.
 */
@Module public class LoginModule {

  @Provides @PerActivity @Named("login") UseCase providerLoginUseCase(Repository repository,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new Login(repository, threadExecutor, postExecutionThread);
  }
  @Provides @PerActivity @Named("versionDetail") UseCase providerVersionDetailUseCase(Repository repository,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new VersionDetail(repository, threadExecutor, postExecutionThread);
  }
}
