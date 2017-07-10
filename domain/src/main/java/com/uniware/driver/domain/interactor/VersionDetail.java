package com.uniware.driver.domain.interactor;

import com.uniware.driver.domain.executor.PostExecutionThread;
import com.uniware.driver.domain.executor.ThreadExecutor;
import com.uniware.driver.domain.repository.Repository;
import rx.Observable;

/**
 * Created by ayue on 2016/12/16.
 */

public class VersionDetail extends UseCase {
  private final Repository repository;
  private String s;
  public VersionDetail(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.repository = repository;
  }
  public void setS(String s){
   this.s=s;
  }

  @Override protected Observable buildUseCaseObservable() {
    return repository.VersionRequest();
  }
}
