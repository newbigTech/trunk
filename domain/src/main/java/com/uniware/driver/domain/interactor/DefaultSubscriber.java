package com.uniware.driver.domain.interactor;

/**
 * Default subscriber base class to be used whenever you want default error handling.
 */
public class DefaultSubscriber<T> extends rx.Subscriber<T> {

  @Override public void onStart() {
    System.out.println(Thread.currentThread().getName() + " onStart");
    // no-op by default.
  }

  @Override public void onNext(T t) {
    System.out.println(Thread.currentThread().getName() + " onNext");
    // no-op by default.
  }

  @Override public void onCompleted() {
    System.out.println(Thread.currentThread().getName() + " onCompleted");
    // no-op by default.
  }

  @Override public void onError(Throwable e) {
    System.out.println(Thread.currentThread().getName() + " onError " + e);
    // no-op by default.
  }
}
