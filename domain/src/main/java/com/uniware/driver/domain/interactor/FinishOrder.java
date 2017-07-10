package com.uniware.driver.domain.interactor;

import com.uniware.driver.domain.executor.PostExecutionThread;
import com.uniware.driver.domain.executor.ThreadExecutor;
import com.uniware.driver.domain.repository.Repository;
import javax.inject.Inject;
import rx.Observable;

/**
 * Created by ayue on 2017/3/29.
 */

public class FinishOrder extends  UseCase {
  private final Repository repository;
  private String phoneNo;
  private String currPage;

  @Inject
  public FinishOrder(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.repository = repository;
  }

  @Override protected Observable buildUseCaseObservable() {
    return repository.finishOrder(phoneNo,currPage);
  }

  public Repository getRepository() {
    return repository;
  }

  public String getPhoneNo() {
    return phoneNo;
  }

  public void setPhoneNo(String phoneNo) {
    this.phoneNo = phoneNo;
  }

  public String getCurrPage() {
    return currPage;
  }

  public void setCurrPage(String currPage) {
    this.currPage = currPage;
  }
}
