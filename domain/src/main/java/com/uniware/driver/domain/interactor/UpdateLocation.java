package com.uniware.driver.domain.interactor;

import com.uniware.driver.domain.executor.PostExecutionThread;
import com.uniware.driver.domain.executor.ThreadExecutor;
import com.uniware.driver.domain.repository.Repository;
import javax.inject.Inject;
import rx.Observable;

/**
 * Created by jian on 15/11/20.
 */
public class UpdateLocation extends UseCase {
  private final Repository repository;
  private int statusFlag = 0x08;

  public int getStatusFlag() {
    return statusFlag;
  }

  public void setStatusFlag(int statusFlag) {
    this.statusFlag = statusFlag;
  }

  @Inject public UpdateLocation(Repository repository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.repository = repository;
  }

  @Override protected Observable buildUseCaseObservable() {
    return repository.updateLocation(statusFlag);
  }
}
