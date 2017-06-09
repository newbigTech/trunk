package com.uniware.driver.gui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.uniware.driver.R;
import com.uniware.driver.config.LoginConfig;
import com.uniware.driver.gui.ui.AppDialog;
import com.uniware.driver.gui.ui.TitleView;
import com.uniware.driver.mvp.injector.components.DaggerLoginComponent;
import com.uniware.driver.mvp.injector.components.LoginComponent;
import com.uniware.driver.mvp.presenter.LoginPresenter;
import com.uniware.driver.mvp.view.LoginView;
import com.uniware.driver.mvp.view.VersionView;
import com.uniware.driver.service.UpdateVersionService;
import com.uniware.driver.util.LogUtils;
import com.uniware.driver.util.ToastUtil;
import com.uniware.driver.util.XFSpeech;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.inject.Inject;

/**
 * Created by jian on 15/11/11.
 */
public class LoginActivity extends BaseActivity implements LoginView ,VersionView{

  private LoginComponent loginComponent;
  @Inject LoginPresenter loginPresenter;
  @Inject XFSpeech xfSpeech;
  @Bind(R.id.login_phone_edit) EditText mLoginPhoneEdit;
  @Bind(R.id.login_pwd_edit) EditText mLoginPwdEdit;
  @Bind(R.id.title_view) TitleView mTitleView;
  private LoginConfig loginConfig;
  private AppDialog mDialog;

  @OnClick(R.id.login_btn) void loginBtnClick() {
    if (!checkPhoneNumber(mLoginPhoneEdit.getText().toString())) {
      ToastUtil.getToast().show(R.string.input_phone_retry);
    } else if (!checkPwd(mLoginPwdEdit.getText().toString())) {
      ToastUtil.getToast().show(R.string.input_pwd_retry);
    } else {
      loginPresenter.login(mLoginPhoneEdit.getText().toString(),
          mLoginPwdEdit.getText().toString());
      mDialog.show("登录中...", false);
    }
  }

/*  @OnClick(R.id.login_regist_btn) void registerBtnClick() {
    Intent intent = new Intent(LoginActivity.this.getBaseContext(), RegisterActivity.class);
    LoginActivity.this.startActivity(intent);
  }*/
  @OnClick(R.id.login_regist_btn) void registerBtnClick() {
    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
    startActivity(intent);
    LoginActivity.this.finish();
    //xfSpeech.ttsSpeaking("[sound101]"+"及时，从天安门到");

  }
  @OnClick(R.id.login_forget_pwd) void forgetPswdClick() {
    Intent intent = new Intent(LoginActivity.this, ForgetPswdActivity.class);
    //intent.putExtra("extra_phone", LoginActivity.this.mLoginPhoneEdit.getText().toString());
    //LoginActivity.this.startActivityForResult(intent, 1);
    startActivity(intent);
    LoginActivity.this.finish();
  }
/*  @OnClick(R.id.login_forget_pwd) void forgetPwdClick() {
    Intent intent = new Intent(LoginActivity.this, ForgetPwdActivity.class);
    intent.putExtra("extra_phone", LoginActivity.this.mLoginPhoneEdit.getText().toString());
    LoginActivity.this.startActivityForResult(intent, 1);
  }*/

  @OnClick({ R.id.login_phone_edit, R.id.login_pwd_edit }) void editClick(View v) {
    switch (v.getId()) {
      case R.id.login_phone_edit:
        ToastUtil.getToast().show("请输入电话号码");
        break;
      case R.id.login_pwd_edit:
        ToastUtil.getToast().show("请输入登录密码");
        break;
    }
  }

  private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      if (!TextUtils.isEmpty(action) && action.equalsIgnoreCase("action_change_device_login")) {
        LoginActivity.this.finish();
      }
    }
  };

  private void initView() {
    xfSpeech.resume();
    this.mTitleView.setTitle(this.getString(R.string.login_title_name_txt));
    if (!TextUtils.isEmpty(loginConfig.getUserName())) {
      this.mLoginPhoneEdit.setText(loginConfig.getUserName());
    }
    if (!TextUtils.isEmpty(loginConfig.getUserPassword())) {
      this.mLoginPwdEdit.setText(loginConfig.getUserPassword());
    }
    loginPresenter.setView(this,this);
    this.mDialog = new AppDialog(this);
  }

  private boolean checkPhoneNumber(String phoneNumber) {
    return Pattern.compile("[0-9]*").matcher(phoneNumber).matches() && !TextUtils.isEmpty(
        phoneNumber) && phoneNumber.length() == 11 && phoneNumber.startsWith("1");
  }

  private boolean checkPwd(String pwd) {
    return !TextUtils.isEmpty(pwd) && pwd.length() >= 6 && pwd.length() <= 32;
  }

  @Override public void finish() {
    super.finish();
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == -1) {
      switch (requestCode) {
        case 1:
        default:
      }
    }
  }

  @Override public void onBackPressed() {
    exitApp();
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    this.setContentView(R.layout.activity_login);
    this.loginConfig = LoginConfig.getInstance();
    ButterKnife.bind(this);
    initializeInjector();
    this.initView();
    xfSpeech.resume();
    loginPresenter.versionRequest();
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("action_change_device_login");
    registerReceiver(broadcastReceiver, intentFilter);
    SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
    String time=sdf.format(new Date());
    Log.e("------",time);
  }

  @Override protected void onStop() {
    super.onStop();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    ButterKnife.unbind(this);
    unregisterReceiver(broadcastReceiver);
  }

  private void initializeInjector() {
    loginComponent = DaggerLoginComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
    loginComponent.inject(this);
  }

  @Override public Context getContext() {
    return getApplicationContext();
  }

  @Override public void inSuccess(int code, String detail) {
    LogUtils.e("====","code"+code+"detail"+detail);
    UpdateVersionService updateVersionService = new UpdateVersionService(this, detail, false);// 创建更新业务对象
    //String wel = sharedPreferences.getString("versionCode", "1");
    updateVersionService.checkUpdate(code+"");
  }

  @Override public void inFailure(String reason) {
    LogUtils.e("====",reason);
  }

  @Override public void loginSuccess() {
    mDialog.hide();
    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
    LoginActivity.this.startActivity(intent);
    LoginActivity.this.finish();
    loginConfig.setUserName(mLoginPhoneEdit.getText().toString());
    loginConfig.setUserPassword(mLoginPwdEdit.getText().toString());
  }

  @Override public void loginFailure(String reason) {
    mDialog.hide();
    ToastUtil.getToast().show(reason);
  }
}