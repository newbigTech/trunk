package com.uniware.driver.gui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.uniware.driver.AppApplication;
import com.uniware.driver.R;
import com.uniware.driver.config.LDPreferences;
import com.uniware.driver.gui.ui.AppDialog;
import com.uniware.driver.gui.ui.TimerTextView;
import com.uniware.driver.mvp.injector.components.DaggerRegisterComponent;
import com.uniware.driver.mvp.injector.components.RegisterComponent;
import com.uniware.driver.mvp.presenter.RegisterPresenter;
import com.uniware.driver.mvp.view.RegisterView;
import com.uniware.driver.util.ToastUtil;
import com.uniware.driver.util.Tools;
import javax.inject.Inject;

/**
 * Created by jian on 15/11/12.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener, RegisterView {

  private RegisterComponent registerComponent;
  @Inject RegisterPresenter registerPresenter;

  private Button backBtn;
  private TextView centerTile;
  private EditText pidEdit;
  private EditText phoneEdit;
  private EditText pswdEdit;
  private Button showPswdBtn;
  private EditText codeEdit;
  private TimerTextView codeText;
  private Button registerBtn;
  private boolean pswdIsGone = false;

  private String phoneStr;

  private AppDialog mDialog;
  private LDPreferences ldPreferences;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_register);
    this.ldPreferences = new LDPreferences(AppApplication.getAppContext());

    this.initializeInjector();
    this.initView();
  }

  private void initializeInjector() {
    registerComponent = DaggerRegisterComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
    registerComponent.inject(this);
  }

  private void initView() {
    registerPresenter.setView(this);
    this.mDialog = new AppDialog(this);
    pidEdit= (EditText) findViewById(R.id.pid_num);
    backBtn = (Button) findViewById(R.id.back_btn);
    backBtn.setOnClickListener(this);
    centerTile = (TextView) findViewById(R.id.center_title);
    centerTile.setText("注册");
    phoneEdit = (EditText) findViewById(R.id.phone_num);
    pswdEdit = (EditText) findViewById(R.id.pswd_edit);
    showPswdBtn = (Button) findViewById(R.id.btn_show_pswd);
    showPswdBtn.setOnClickListener(this);
    codeEdit = (EditText) findViewById(R.id.code_edit);
    codeText = (TimerTextView) findViewById(R.id.get_code);
    codeText.setOnClickListener(this);
    registerBtn = (Button) findViewById(R.id.register_btn);
    registerBtn.setOnClickListener(this);
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.back_btn:
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        RegisterActivity.this.finish();
        break;
      case R.id.btn_show_pswd:
        if (pswdIsGone) {
          showPswdBtn.setBackgroundResource(R.drawable.ico_eye_pressed);
          pswdEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        } else {
          showPswdBtn.setBackgroundResource(R.drawable.ico_eye_normal);
          pswdEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        pswdIsGone = !pswdIsGone;
        break;
      case R.id.get_code:
        phoneStr = phoneEdit.getText().toString();
        if (phoneStr == null || !Tools.isMobile(phoneStr)) {
          showErrorDialog("请输入正确的手机号");
        } else {
          showDialog("点击确定将通过短信向您的手机发送验证码");
        }
        break;
      case R.id.register_btn:
        phoneStr = phoneEdit.getText().toString();
        if(pidEdit.length()!=18){
          showErrorDialog("请输入正确的身份证号码");
        }
        else if (phoneStr.length() != 11) {
          showErrorDialog("请输入手机号作为用户名");
        } else if (!Tools.matchingText("[0-9]*", phoneStr)) {
          showErrorDialog("请输入正确的手机号");
        } else if (pswdEdit.getText().toString().length() < 6) {
          showErrorDialog("密码不足6位");
        } else if (codeEdit.getText().toString().equals("")) {
          showErrorDialog("请正确输入验证码");
        } else {
          registerPresenter.register(pidEdit.getText().toString(),phoneEdit.getText().toString(), pswdEdit.getText().toString(),
              codeEdit.getText().toString());
          mDialog.show("注册中...", false);
        }
        break;
      default:
        break;
    }
  }

  private void showErrorDialog(String msg) {
    AlertDialog dialog = new AlertDialog.Builder(RegisterActivity.this).setTitle("温馨提示：")
        .setMessage(msg)
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
          }
        })
        .create();
    dialog.show();
  }

  private void showDialog(String msg) {
    AlertDialog dialog = new AlertDialog.Builder(RegisterActivity.this).setTitle("温馨提示：")
        .setMessage(msg)
        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
          }
        })
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            codeText.beginRun();
            registerPresenter.getCode(phoneStr);
          }
        })
        .create();
    dialog.show();
  }

  @Override public Context getContext() {
    return getApplicationContext();
  }

  @Override public void registerSuccess() {
    mDialog.hide();
    ToastUtil.getToast().show("注册成功!");
    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
    RegisterActivity.this.startActivity(intent);
    RegisterActivity.this.finish();
    ldPreferences.setUserPhone(phoneEdit.getText().toString());
    ldPreferences.setUserPassword(pswdEdit.getText().toString());
  }

  @Override public void registerFailure(String reason) {
    mDialog.hide();
    ToastUtil.getToast().show(reason);
  }

  @Override public void getCodeSuccess() {
    mDialog.hide();
    ToastUtil.getToast().show("验证码发送成功!");
  }

  @Override public void getCodeFailure(String reason) {
    mDialog.hide();
    ToastUtil.getToast().show(reason);
  }
}
