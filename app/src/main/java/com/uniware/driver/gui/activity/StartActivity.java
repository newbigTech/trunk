package com.uniware.driver.gui.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import com.igexin.sdk.PushManager;
import com.uniware.driver.R;
import com.uniware.driver.config.LoginConfig;
import com.uniware.driver.util.TextUtil;
import com.uniware.driver.util.Tools;

public class StartActivity extends BaseActivity {

  private LoginConfig loginConfig;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    BaseActivity topActivity = getAppApplication().getTopActivity();
    Intent gotoIntent;
    if (topActivity != null) {
      gotoIntent = topActivity.getIntent();
      gotoIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
      //gotoIntent.removeExtra("xxxxx");
      try {
        this.startActivity(gotoIntent);
        this.finish();
        return;
      } catch (ActivityNotFoundException e) {
        e.printStackTrace();
      }
    }
    setContentView(R.layout.activity_start);
    loginConfig = LoginConfig.getInstance();
    if (Tools.getVersionNum() > 0 && loginConfig.isFirstLogin()) {
      startActivity(new Intent(this, GuideActivity.class));
    } else {
      Intent intent;
      if (!TextUtil.isEmpty(loginConfig.getUserName()) && TextUtil.isEmpty(
          loginConfig.getUserPassword())) {
        gotoIntent = new Intent(this, MainActivity.class);
        intent = new Intent(this, SplashActivity.class);
        intent.putExtra("targetIntent", gotoIntent);
        this.startActivity(intent);
      } else {
        gotoIntent = new Intent(this, LoginActivity.class);
        gotoIntent.putExtra("to_main", true);
        intent = new Intent(this, SplashActivity.class);
        intent.putExtra("targetIntent", gotoIntent);
        this.startActivity(intent);
      }
    }
    //个推初始化
    PushManager.getInstance().initialize(this.getApplicationContext());
    this.finish();
  }
}
