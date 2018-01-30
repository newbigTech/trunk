package com.uniware.driver.mvp.injector.modules;

import com.uniware.driver.domain.executor.PostExecutionThread;
import com.uniware.driver.domain.executor.ThreadExecutor;
import com.uniware.driver.domain.interactor.AllNotice;
import com.uniware.driver.domain.interactor.CancelOrder;
import com.uniware.driver.domain.interactor.CompleteOrder;
import com.uniware.driver.domain.interactor.DeleteOrder;
import com.uniware.driver.domain.interactor.FinishOrder;
import com.uniware.driver.domain.interactor.GetBacklogList;
import com.uniware.driver.domain.interactor.GetOrderDetails;
import com.uniware.driver.domain.interactor.GetOrderList;
import com.uniware.driver.domain.interactor.GrabOrder;
import com.uniware.driver.domain.interactor.ModelApply;
import com.uniware.driver.domain.interactor.SaveOrder;
import com.uniware.driver.domain.interactor.UnfinishOrder;
import com.uniware.driver.domain.interactor.UpdateOrder;
import com.uniware.driver.domain.interactor.UseCase;
import com.uniware.driver.domain.repository.Repository;
import com.uniware.driver.mvp.injector.PerActivity;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

/**
 * Created by jian on 15/11/20.
 */
@Module public class OrderModule {

  @Provides @PerActivity @Named("grabOrder") UseCase provideGrabOrderUseCase(Repository repository,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new GrabOrder(repository, threadExecutor, postExecutionThread);
  }

  @Provides @PerActivity @Named("completeOrder") UseCase provideCompleteOrderUseCase(
      Repository repository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    return new CompleteOrder(repository, threadExecutor, postExecutionThread);
  }

  @Provides @PerActivity @Named("cancelOrder") UseCase provideCancelOrderUseCase(
      Repository repository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    return new CancelOrder(repository, threadExecutor, postExecutionThread);
  }

  @Provides @PerActivity @Named("backlogList") UseCase provideGetBacklogListUseCase(
      Repository repository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    return new GetBacklogList(repository, threadExecutor, postExecutionThread);
  }

  @Provides @PerActivity @Named("orderList") UseCase provideGetOrderListUseCase(
      Repository repository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    return new GetOrderList(repository, threadExecutor, postExecutionThread);
  }

  @Provides @PerActivity @Named("orderDetails") UseCase provideGetOrderDetailsUseCase(
      Repository repository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    return new GetOrderDetails(repository, threadExecutor, postExecutionThread);
  }

  @Provides @PerActivity @Named("saveOrder") UseCase provideSaveOrderUseCase(Repository repository,
      ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    return new SaveOrder(repository, threadExecutor, postExecutionThread);
  }

  @Provides @PerActivity @Named("deleteOrder") UseCase provideDeleteOrderUseCase(
      Repository repository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    return new DeleteOrder(repository, threadExecutor, postExecutionThread);
  }
  @Provides @PerActivity @Named("updateOrder") UseCase provideUpdateOrderUseCase(
      Repository repository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    return new UpdateOrder(repository, threadExecutor, postExecutionThread);
  }
  @Provides @PerActivity @Named("unfinishOrder") UseCase provideUnfinishOrderUseCase(
      Repository repository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    return new UnfinishOrder(repository, threadExecutor, postExecutionThread);
  }
  @Provides @PerActivity @Named("finishOrder") UseCase provideFinishOrderUseCase(
      Repository repository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    return new FinishOrder(repository, threadExecutor, postExecutionThread);
  }
  @Provides @PerActivity @Named("modelApply") UseCase provideModelApplyUseCase(
      Repository repository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    return new ModelApply(repository, threadExecutor, postExecutionThread);
  }
  @Provides @PerActivity @Named("allNotice") UseCase provideAllNoticeUseCase(
      Repository repository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    return new AllNotice(repository, threadExecutor, postExecutionThread);
  }
}
