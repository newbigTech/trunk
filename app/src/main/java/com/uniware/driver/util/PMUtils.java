package com.uniware.driver.util;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;

/**
 * Created by ayue on 2016/12/2.
 */

public class PMUtils {

  public static boolean isScreenOn(Context context) {
    PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
    if (pm.isScreenOn()) {
      return true;
    }
    return false;
  }
  private static KeyguardManager km;
  private static KeyguardManager.KeyguardLock kl;
  private static PowerManager pm;
  private static PowerManager.WakeLock wl;
  public static void  wakeAndUnlock(Context b) {
    if (true) {
      //获取电源管理器对象
      pm = (PowerManager) b.getSystemService(b.getApplicationContext().POWER_SERVICE);

      //获取PowerManager.WakeLock对象，后面的参数|表示同时传入两个值，最后的是调试用的Tag
      wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_BRIGHT_WAKE_LOCK,
          "bright");

      //点亮屏幕
      wl.acquire();

      //得到键盘锁管理器对象
      km = (KeyguardManager) b.getSystemService(b.getApplicationContext().KEYGUARD_SERVICE);
      kl = km.newKeyguardLock("unLock");

      //解锁
      kl.disableKeyguard();
    } else {
      //锁屏
      kl.reenableKeyguard();

      //释放wakeLock，关灯
      wl.release();
    }
  }
}
