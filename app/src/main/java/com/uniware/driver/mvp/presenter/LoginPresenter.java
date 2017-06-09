package com.uniware.driver.mvp.presenter;

import com.uniware.driver.domain.LoginResult;
import com.uniware.driver.domain.VersionCodeResult;
import com.uniware.driver.domain.interactor.DefaultSubscriber;
import com.uniware.driver.domain.interactor.Login;
import com.uniware.driver.domain.interactor.UseCase;
import com.uniware.driver.domain.interactor.VersionDetail;
import com.uniware.driver.mvp.exception.ErrorMessageFactory;
import com.uniware.driver.mvp.view.LoginView;
import com.uniware.driver.mvp.view.VersionView;
import com.uniware.driver.util.LogUtils;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by jian on 15/12/30.
 */
public class LoginPresenter implements Presenter {

  private final UseCase login;
  private final UseCase versionDetail;
  private LoginView loginView;
  private VersionView versionView;

  @Inject public LoginPresenter(@Named("login") UseCase login,@Named("versionDetail") UseCase versionDetail) {
    this.login = login;
    this.versionDetail=versionDetail;
  }

  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void destroy() {
    login.unsubscribe();
  }

  public void setView(LoginView loginView,VersionView versionView) {
    this.loginView = loginView;
    this.versionView=versionView;
  }

  public void login(String phoneNo, String pwd) {
    ((Login) login).setPhoneNo(phoneNo);
    ((Login) login).setPwd(pwd);
    login.execute(new LoginSubscriber());
  }

  public void versionRequest(){
    ((VersionDetail)versionDetail).setS("s");
    versionDetail.execute(new VersionSubscriber());
  }

  private final class VersionSubscriber extends DefaultSubscriber<VersionCodeResult>{
    @Override public void onCompleted() {

    }

    @Override public void onError(Throwable e) {
      String errorMessage = ErrorMessageFactory.create(loginView.getContext(), (Exception) e);
      versionView.inFailure(errorMessage);
    }

    @Override public void onNext(VersionCodeResult versionCodeResult) {
      if (versionCodeResult.getErrno()==0){
          versionView.inSuccess(versionCodeResult.getCodeVersion(),versionCodeResult.getDetail());
      }else {
        versionView.inFailure(versionCodeResult.getErrmsg());
      }
    }
  }
  private final class LoginSubscriber extends DefaultSubscriber<LoginResult> {
    @Override public void onCompleted() {

    }

    @Override public void onError(Throwable e) {
      String errorMessage = ErrorMessageFactory.create(loginView.getContext(), (Exception) e);
      loginView.loginFailure(errorMessage);
    }

    @Override public void onNext(LoginResult loginResult) {
      if (loginResult.getErrno() == 0) {
        LogUtils.e(this, loginResult.getToken());
        loginView.loginSuccess();
      } else {
        LogUtils.e(this, loginResult.getErrmsg());
        loginView.loginFailure(loginResult.getErrmsg());
      }
    }
  }
}
