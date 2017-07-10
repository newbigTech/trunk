package com.uniware.driver.domain.interactor;

import com.uniware.driver.domain.executor.PostExecutionThread;
import com.uniware.driver.domain.executor.ThreadExecutor;
import com.uniware.driver.domain.repository.Repository;
import rx.Observable;

/**
 * Created by jian on 15/12/30.
 */
public class Login extends UseCase {
  private final Repository repository;
  private String phoneNo;
  private String pwd;

  public Login(Repository repository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.repository = repository;
  }

  @Override protected Observable buildUseCaseObservable() {
    return repository.login(phoneNo, pwd);
  }

  public String getPhoneNo() {
    return phoneNo;
  }

  public void setPhoneNo(String phoneNo) {
    this.phoneNo = phoneNo;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }
}
