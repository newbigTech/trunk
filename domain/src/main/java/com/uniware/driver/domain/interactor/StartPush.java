package com.uniware.driver.domain.interactor;

import com.uniware.driver.domain.executor.PostExecutionThread;
import com.uniware.driver.domain.executor.ThreadExecutor;
import com.uniware.driver.domain.repository.Repository;
import javax.inject.Inject;
import rx.Observable;

/**
 * Created by jian on 15/11/20.
 */
public class StartPush extends UseCase {
  private final Repository repository;

  @Inject public StartPush(Repository repository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.repository = repository;
  }

  @Override protected Observable buildUseCaseObservable() {
     //return repository.startPush("223.72.209.142", 6000);//
    return repository.startPush("36.106.8.163", 6000);
    //jyj ceshi
    //return repository.startPush("60.247.30.196", 10581);
  }
}
