package com.uniware.driver.mvp.presenter;

import com.uniware.driver.domain.LoginResult;
import com.uniware.driver.domain.NetBiz;
import com.uniware.driver.domain.RegisterInfo;
import com.uniware.driver.domain.interactor.Code;
import com.uniware.driver.domain.interactor.DefaultSubscriber;
import com.uniware.driver.domain.interactor.Register;
import com.uniware.driver.domain.interactor.UseCase;
import com.uniware.driver.mvp.exception.ErrorMessageFactory;
import com.uniware.driver.mvp.view.RegisterView;
import com.uniware.driver.util.LogUtils;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by jian on 15/12/30.
 */
public class RegisterPresenter implements Presenter {

  private final UseCase register;
  private final UseCase code;
  private RegisterView registerView;

  @Inject public RegisterPresenter(@Named("register") UseCase register,@Named("code") UseCase code) {
    this.register = register;
    this.code=code;
  }

  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void destroy() {
    register.unsubscribe();
  }

  public void setView(RegisterView registerView) {
    this.registerView = registerView;
  }

  public void register(String pidNo,String phoneNo, String pwd, String code) {
    ((Register) register).setPhoneNo(phoneNo);
    ((Register) register).setPwd(pwd);
    ((Register) register).setCode(code);
    ((Register) register).setPidNo(pidNo);
    register.execute(new RegisterSubscriber());
  }

  private final class RegisterSubscriber extends DefaultSubscriber<NetBiz> {
    @Override public void onCompleted() {

    }

    @Override public void onError(Throwable e) {
      String errorMessage = ErrorMessageFactory.create(registerView.getContext(), (Exception) e);
      registerView.registerFailure(errorMessage);
    }

    @Override public void onNext(NetBiz registerInfo) {
      if (registerInfo.getErrno() == 0) {
        registerView.registerSuccess();
      } else {
        if(registerInfo.getErrno() == 1){
          registerView.registerFailure("该账号已注册");
        }
        LogUtils.e(this, registerInfo.getErrmsg());
        registerView.registerFailure(registerInfo.getErrmsg());
      }
    }
  }

  public void getCode(String phoneNo) {
    ((Code) code).setPhoneNo(phoneNo);
    code.execute(new GetCodeSubscriber());
  }

  private final class GetCodeSubscriber extends DefaultSubscriber<NetBiz> {
    @Override public void onCompleted() {

    }

    @Override public void onError(Throwable e) {
      String errorMessage = ErrorMessageFactory.create(registerView.getContext(), (Exception) e);
      registerView.getCodeFailure(errorMessage);
    }

    @Override public void onNext(NetBiz registerInfo) {
      if (registerInfo.getErrno() == 0) {
        registerView.getCodeSuccess();
      } else {
        LogUtils.e(this, registerInfo.getErrmsg());
        registerView.getCodeFailure(registerInfo.getErrmsg());
      }
    }
  }
}
