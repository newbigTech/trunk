package com.uniware.driver.domain.interactor;

import com.uniware.driver.domain.executor.PostExecutionThread;
import com.uniware.driver.domain.executor.ThreadExecutor;
import com.uniware.driver.domain.repository.Repository;
import javax.inject.Inject;
import rx.Observable;

/**
 * Created by jian on 15/12/07.
 */
public class DeleteOrder extends UseCase {

  private final Repository repository;
  private String oid;

  @Inject public DeleteOrder(Repository repository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.repository = repository;
  }

  @Override protected Observable buildUseCaseObservable() {
    return repository.deleteOrder(oid);
  }

  public String getOid() {
    return oid;
  }

  public void setOid(String oid) {
    this.oid = oid;
  }
}
