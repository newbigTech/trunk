package com.uniware.driver.mvp.presenter;

import com.uniware.driver.domain.LoginResult;
import com.uniware.driver.domain.NetBiz;
import com.uniware.driver.domain.interactor.Code;
import com.uniware.driver.domain.interactor.DefaultSubscriber;
import com.uniware.driver.domain.interactor.ForgetPswd;
import com.uniware.driver.domain.interactor.UseCase;
import com.uniware.driver.mvp.exception.ErrorMessageFactory;
import com.uniware.driver.mvp.view.ForgetPswdView;
import com.uniware.driver.mvp.view.RegisterView;
import com.uniware.driver.util.LogUtils;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by jian on 15/12/30.
 */
public class ForgetPswdPresenter implements Presenter {

  private final UseCase forgetPswd;
  private ForgetPswdView forgetPswdView;
  private final UseCase code;
  //private RegisterView registerView;
  @Inject public ForgetPswdPresenter(@Named("forgetPswd") UseCase forgetPswd,@Named("code") UseCase code) {
    this.forgetPswd = forgetPswd;
    this.code=code;
  }

  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void destroy() {
    forgetPswd.unsubscribe();
  }

  public void setView(ForgetPswdView forgetPswdView) {
    this.forgetPswdView = forgetPswdView;
  }

  public void forgetPswd(String phoneNo, String pwd, String code) {
    ((ForgetPswd) forgetPswd).setPhoneNo(phoneNo);
    ((ForgetPswd) forgetPswd).setPwd(pwd);
    ((ForgetPswd) forgetPswd).setCode(code);
    forgetPswd.execute(new ForgetPswdSubscriber());
  }

  private final class ForgetPswdSubscriber extends DefaultSubscriber<LoginResult> {
    @Override public void onCompleted() {

    }

    @Override public void onError(Throwable e) {
      String errorMessage = ErrorMessageFactory.create(forgetPswdView.getContext(), (Exception) e);
      forgetPswdView.forgetPswdCommiteFailure(errorMessage);
    }

    @Override public void onNext(LoginResult registerInfo) {
      if (registerInfo.getErrno() == 0) {
        forgetPswdView.forgetPswdCommiteSuccess();
      } else {
        LogUtils.e(this, registerInfo.getErrmsg());
        forgetPswdView.forgetPswdCommiteFailure(registerInfo.getErrmsg());
      }
    }
  }
  public void getCode(String phoneNo) {
    ((Code) code).setPhoneNo(phoneNo);
    code.execute(new ForgetPswdPresenter.GetCodeSubscriber());
  }

  private final class GetCodeSubscriber extends DefaultSubscriber<NetBiz> {
    @Override public void onCompleted() {

    }

    @Override public void onError(Throwable e) {
      String errorMessage = ErrorMessageFactory.create(forgetPswdView.getContext(), (Exception) e);
      forgetPswdView.getCodeFailure(errorMessage);
    }

    @Override public void onNext(NetBiz registerInfo) {
      if (registerInfo.getErrno() == 0) {
        forgetPswdView.getCodeSuccess();
      } else {
        LogUtils.e(this, registerInfo.getErrmsg());
        forgetPswdView.getCodeFailure(registerInfo.getErrmsg());
      }
    }
  }
}
