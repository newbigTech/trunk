package com.uniware.driver.domain.interactor;

import com.uniware.driver.domain.executor.PostExecutionThread;
import com.uniware.driver.domain.executor.ThreadExecutor;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import rx.Observable;

/**
 * Created by SJ on 16/04/23.
 */
public class Countdown extends UseCase {

  @Inject public Countdown(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
  }

  @Override protected Observable buildUseCaseObservable() {
    return Observable.interval(1, TimeUnit.SECONDS);
  }
}
