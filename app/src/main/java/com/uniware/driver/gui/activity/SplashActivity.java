package com.uniware.driver.gui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.uniware.driver.R;
import com.uniware.driver.util.Tools;

/**
 * Created by jian on 15/11/11.
 */
public class SplashActivity extends BaseActivity {
  private ImageView mIvDisplay;
  private ImageView mIvSplashLogo;
  private TextView mTvVersion;
  private Intent mIntent;
  private Runnable runnable = new Runnable() {
    public void run() {
      startActivity(mIntent);
      finish();
    }
  };

  public SplashActivity() {
  }

  private void initView() {
    this.setContentView(R.layout.activity_splash);
    this.mIvDisplay = (ImageView) findViewById(R.id.iv_display);
    this.mIvSplashLogo = (ImageView) findViewById(R.id.iv_splash_logo);
    this.mIvSplashLogo.setVisibility(View.VISIBLE);
    this.mTvVersion = (TextView) findViewById(R.id.tv_version);
    this.mTvVersion.setVisibility(View.VISIBLE);
    this.mTvVersion.setText(getString(R.string.app_version_head) + Tools.getVersionName());
  }

  protected void onCreate(Bundle var1) {
    super.onCreate(var1);
    initView();
    mIntent = this.getIntent().getParcelableExtra("targetIntent");
    mIvDisplay.postDelayed(this.runnable, 2000L);
  }

  protected void onDestroy() {
    super.onDestroy();
  }

  @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
    return keyCode != 4 && super.onKeyDown(keyCode, event);
  }
}
