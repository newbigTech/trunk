package com.uniware.driver.domain.interactor;

import com.uniware.driver.domain.executor.PostExecutionThread;
import com.uniware.driver.domain.executor.ThreadExecutor;
import com.uniware.driver.domain.repository.Repository;
import javax.inject.Inject;
import rx.Observable;

/**
 * Created by ayue on 2018/1/16.
 */

public class AllNotice extends UseCase {
  private final Repository repository;
  private String tel;
  private int page;

  @Inject public AllNotice(Repository repository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.repository = repository;
  }

  @Override protected Observable buildUseCaseObservable() {
    return repository.searchNotice(tel,page);
  }

  public Repository getRepository() {
    return repository;
  }

  public String getTel() {
    return tel;
  }

  public void setTel(String tel) {
    this.tel = tel;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }
}
