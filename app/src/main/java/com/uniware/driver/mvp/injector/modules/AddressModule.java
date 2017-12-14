package com.uniware.driver.mvp.injector.modules;

import com.uniware.driver.domain.executor.PostExecutionThread;
import com.uniware.driver.domain.executor.ThreadExecutor;
import com.uniware.driver.domain.interactor.QueryAddress;
import com.uniware.driver.domain.interactor.Rank;
import com.uniware.driver.domain.interactor.UpdateAddress;
import com.uniware.driver.domain.interactor.UseCase;
import com.uniware.driver.domain.repository.Repository;
import com.uniware.driver.mvp.injector.PerActivity;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

/**
 * Created by ayue on 2017/7/26.
 */
@Module
public class AddressModule {
  @Provides @PerActivity @Named("queryAddress") UseCase providerQueryUseCase(Repository repository,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new QueryAddress(repository, threadExecutor, postExecutionThread);
  }
  @Provides @PerActivity @Named("updateAddress") UseCase providerUpdatAddressUseCase(Repository repository,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new UpdateAddress(repository, threadExecutor, postExecutionThread);
  }
  @Provides @PerActivity @Named("rank") UseCase providerRankUseCase(Repository repository,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new Rank(repository, threadExecutor, postExecutionThread);
  }
}
