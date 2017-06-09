package com.uniware.driver.util;

import android.app.Activity;
import android.os.Environment;
import android.widget.Toast;
import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.uniware.driver.AppApplication;
import java.io.File;
import javax.inject.Inject;

/**
 * Created by SJ on 16/04/22.
 */
public class BaiduNaviUtil {

  private static final String APP_FOLDER_NAME = "yj_driver";

  private Activity activity;
  private String mSDCardPath = null;
  @Inject XFSpeech xfSpeech;

  @Inject public BaiduNaviUtil(Activity activity) {
    this.activity = activity;
  }

  public void init() {
    if (initDirs()) {
      initNavi();
    }
  }

  public void destroy() {
    BaiduNaviManager.getInstance().uninit();
  }

  private String getSdcardDir() {
    if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
      return Environment.getExternalStorageDirectory().toString();
    }
    return null;
  }

  private boolean initDirs() {
    mSDCardPath = getSdcardDir();
    if (mSDCardPath == null) {
      return false;
    }
    File f = new File(mSDCardPath, APP_FOLDER_NAME);
    if (!f.exists()) {
      try {
        return f.mkdir();
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }

  private void initNavi() {
    BaiduNaviManager.getInstance()
        .init(activity, mSDCardPath, APP_FOLDER_NAME, new BaiduNaviManager.NaviInitListener() {
          @Override public void onAuthResult(int status, String msg) {
            String authInfo = null;
            if (0 == status) {
              authInfo = "key校验成功!";
            } else {
              authInfo = "key校验失败, " + msg;
            }
            LogUtils.e(BaiduNaviUtil.this, authInfo);
          }

          public void initSuccess() {
            Toast.makeText(activity, "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
            initSetting();
            AppApplication.isNavUtil=true;
          }

          public void initStart() {
            Toast.makeText(activity, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
          }

          public void initFailed() {
            AppApplication.isNavUtil=false;
            Toast.makeText(activity, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
          }
        }, mTTSCallback, null, null);
  }

  private void initSetting() {
    BNaviSettingManager.setDayNightMode(BNaviSettingManager.DayNightMode.DAY_NIGHT_MODE_DAY);
    BNaviSettingManager.setShowTotalRoadConditionBar(
        BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
    BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
    BNaviSettingManager.setPowerSaveMode(BNaviSettingManager.PowerSaveMode.DISABLE_MODE);
    BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
  }

  private BNOuterTTSPlayerCallback mTTSCallback = new BNOuterTTSPlayerCallback() {

    @Override public void stopTTS() {
      LogUtils.e(this, "stopTTS");
    }

    @Override public void resumeTTS() {
      LogUtils.e(this, "resumeTTS");
    }

    @Override public void releaseTTSPlayer() {
      LogUtils.e(this, "releaseTTSPlayer");
    }

    @Override public int playTTSText(String speech, int bPreempt) {
      LogUtils.e(this, "playTTSText" + "_" + speech + "_" + bPreempt);
      xfSpeech.ttsSpeaking(speech);
      return 1;
    }

    @Override public void phoneHangUp() {
      LogUtils.e(this, "phoneHangUp");
    }

    @Override public void phoneCalling() {
      LogUtils.e(this, "phoneCalling");
    }

    @Override public void pauseTTS() {
      LogUtils.e(this, "pauseTTS");
    }

    @Override public void initTTSPlayer() {
      LogUtils.e(this, "initTTSPlayer");
    }

    @Override public int getTTSState() {
      //LogUtils.e(this, "getTTSState");
      return 1;
    }
  };
}
