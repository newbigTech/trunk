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
import com.uniware.driver.mvp.injector.components.DaggerForgetPswdComponent;
import com.uniware.driver.mvp.injector.components.ForgetPswdComponent;
import com.uniware.driver.mvp.presenter.ForgetPswdPresenter;
import com.uniware.driver.mvp.view.ForgetPswdView;
import com.uniware.driver.util.ToastUtil;
import com.uniware.driver.util.Tools;
import javax.inject.Inject;

/**
 * Created by jian on 15/11/12.
 */
public class  ForgetPswdActivity extends BaseActivity implements View.OnClickListener, ForgetPswdView {

  private ForgetPswdComponent forgetPswdComponent;
  @Inject ForgetPswdPresenter forgetPswdPresenter;

  private Button backBtn;
  private TextView centerTile;
  private EditText phoneEdit;
  private EditText codeEdit;
  private TimerTextView codeText;
  private EditText pswdEdit;
  private Button showPswdBtn;
  private Button forgetPswdBtn;
  private boolean pswdIsGone = false;

  private String phoneStr;

  private AppDialog mDialog;
  private LDPreferences ldPreferences;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_forget_pswd);
    this.ldPreferences = new LDPreferences(AppApplication.getAppContext());

    this.initializeInjector();
    this.initView();
  }

  private void initializeInjector() {
    forgetPswdComponent = DaggerForgetPswdComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
    forgetPswdComponent.inject(this);
  }

  private void initView() {
    forgetPswdPresenter.setView(this);
    this.mDialog = new AppDialog(this);

    backBtn = (Button) findViewById(R.id.back_btn);
    backBtn.setOnClickListener(this);
    centerTile = (TextView) findViewById(R.id.center_title);
    centerTile.setText("忘记密码");
    phoneEdit = (EditText) findViewById(R.id.forget_pswd_phone_num);
    codeEdit = (EditText) findViewById(R.id.forget_pswd_code_edit);
    codeText = (TimerTextView) findViewById(R.id.forget_pswd_get_code);
    codeText.setOnClickListener(this);
    pswdEdit = (EditText) findViewById(R.id.forget_pswd_pswd_edit);
    showPswdBtn = (Button) findViewById(R.id.forget_pswd_btn_show_pswd);
    showPswdBtn.setOnClickListener(this);
    forgetPswdBtn = (Button) findViewById(R.id.forget_pswd_btn);
    forgetPswdBtn.setOnClickListener(this);
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.back_btn:
        Intent intent = new Intent(ForgetPswdActivity.this, LoginActivity.class);
        startActivity(intent);
        ForgetPswdActivity.this.finish();
        break;
      case R.id.forget_pswd_btn_show_pswd:
        if (pswdIsGone) {
          showPswdBtn.setBackgroundResource(R.drawable.ico_eye_pressed);
          pswdEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        } else {
          showPswdBtn.setBackgroundResource(R.drawable.ico_eye_normal);
          pswdEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        pswdIsGone = !pswdIsGone;
        break;
      case R.id.forget_pswd_get_code:
        phoneStr = phoneEdit.getText().toString();
        if (phoneStr == null || !Tools.isMobile(phoneStr)) {
          showErrorDialog("请输入正确的手机号");
        } else {
          showDialog("点击确定将通过短信向您的手机发送验证码");
        }
        break;
      case R.id.forget_pswd_btn:
        String phoneStr = phoneEdit.getText().toString();
        if (phoneStr.length() != 11) {
          showErrorDialog("请输入手机号");
        } else if (!Tools.matchingText("[0-9]*", phoneStr)) {
          showErrorDialog("请输入正确的手机号");
        } else if (pswdEdit.getText().toString().length() < 6) {
          showErrorDialog("密码不足6位");
        } else if (codeEdit.getText().toString().equals("")) {
          showErrorDialog("请正确输入验证码");
        } else {
          forgetPswdPresenter.forgetPswd(phoneEdit.getText().toString(), pswdEdit.getText().toString(),
              codeEdit.getText().toString());
          mDialog.show("注册中...", false);
        }
        break;
      default:
        break;
    }
  }

  private void showErrorDialog(String msg) {
    AlertDialog dialog = new AlertDialog.Builder(ForgetPswdActivity.this).setTitle("温馨提示：")
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
    AlertDialog dialog = new AlertDialog.Builder(ForgetPswdActivity.this).setTitle("温馨提示：")
        .setMessage(msg)
        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
          }
        })
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
             forgetPswdPresenter.getCode(phoneEdit.getText().toString());
             codeText.beginRun();
             dialog.dismiss();
          }
        })
        .create();
    dialog.show();
  }

  @Override public Context getContext() {
    return getAppApplication();
  }

  @Override public void forgetPswdCommiteSuccess() {
    mDialog.hide();
    ToastUtil.getToast().show("修改密码成功!");
    Intent intent = new Intent(ForgetPswdActivity.this, LoginActivity.class);
    ForgetPswdActivity.this.startActivity(intent);
    ForgetPswdActivity.this.finish();
    ldPreferences.setUserPhone(phoneEdit.getText().toString());
    ldPreferences.setUserPassword(pswdEdit.getText().toString());
  }

  @Override public void forgetPswdCommiteFailure(String reason) {
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
