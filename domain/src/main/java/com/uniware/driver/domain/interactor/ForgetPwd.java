package com.uniware.driver.domain.interactor;

import com.uniware.driver.domain.executor.PostExecutionThread;
import com.uniware.driver.domain.executor.ThreadExecutor;
import rx.Observable;

/**
 * Created by jian on 15/12/30.
 */
public class ForgetPwd extends UseCase {

  public ForgetPwd(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
  }

  @Override protected Observable buildUseCaseObservable() {
    return null;
  }
}
