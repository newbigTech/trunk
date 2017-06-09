package com.uniware.driver.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.uniware.driver.AppApplication;
import com.uniware.driver.data.entity.Ox0200;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jian on 15/11/11.
 */
public class Tools {
  public static ListenMode LISTEN_MODE = ListenMode.CARNULL;
  public static ListenHobby LISTEN_HOBBY = ListenHobby.NOW;
  public static ListenDistance LISTEN_DISTANCE = ListenDistance.TWO_KM;
  public static CarMode CAR_MODE = CarMode.EMPTY;
  public static Ox0200 ox0200 = new Ox0200();

  public static void SetTaxiStatus( boolean is1) {
    int index;
    if (is1){
      index=32768;
    }else {
      index=0;
    }
    SharedPreferences sp = AppApplication.getAppContext().getSharedPreferences("userYJ", 0);
    SharedPreferences.Editor editor = sp.edit();
    editor.putInt("taxi_flag", index).commit();
  }


  public static int getVersionNum(){
    try {
      return AppApplication.getAppContext().getPackageManager().getPackageInfo(AppApplication.getAppContext().getPackageName(), PackageManager.GET_CONFIGURATIONS).versionCode;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return 0;
  }

  public static String getVersionName(){
    try {
      return AppApplication.getAppContext().getPackageManager().getPackageInfo(AppApplication.getAppContext().getPackageName(), PackageManager.GET_CONFIGURATIONS).versionName;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }


  public static String getDeviceInfo(Context context) {
    try{
      org.json.JSONObject json = new org.json.JSONObject();
      android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
          .getSystemService(Context.TELEPHONY_SERVICE);

      String device_id = tm.getDeviceId();

      android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

      String mac = wifi.getConnectionInfo().getMacAddress();
      json.put("mac", mac);

      if( TextUtils.isEmpty(device_id) ){
        device_id = mac;
      }

      if( TextUtils.isEmpty(device_id) ){
        device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
      }

      json.put("device_id", device_id);

      return json.toString();
    }catch(Exception e){
      e.printStackTrace();
    }
    return null;
  }
  /**
   * 将时间戳转换为时间
   */
  public static String stampToDate(Long s){
    String res;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    //long lt = new Long(s);
    Date date = new Date(s);
    res = simpleDateFormat.format(date);
    return res;
  }
  /**
   * 字符串匹配
   *
   * @param expression
   *            正则表达式字符串
   * @param text
   *            要进行匹配的字符串
   */
  public static boolean matchingText(String expression, String text) {
    Pattern p = Pattern.compile(expression); // 正则表达式(使用compile方法得到Pattern实例，expression为正则规范)
    Matcher m = p.matcher(text); // 操作的字符串(使用matcher方法得到Matcher实例，验证一个字符串是否符合正则规范)
    boolean b = m.matches();//使用matches方法进行验证
    return b;
  }
  /**
   * 判断是否符合手机号码
   *
   * @param mobiles
   * @return
   */
  public static boolean isMobile(String mobiles) {
    Pattern p = Pattern.compile("^(2|1)\\d{10}$");
    Matcher m = p.matcher(mobiles);
    return m.matches();
  }
  public enum ListenMode {
    STANDARD("标准听单"),
    QUICK("快速看单"),
    WEIGHT("重车模式"),
    CARNULL("空车模式"),
    WIND("顺风模式"),
    HOME("回家模式");

    ListenMode(String mode) {
      this.mode = mode;
    }

    final String mode;

    @Override public String toString() {
      return mode;
    }
  }
  public enum ListenHobby {
    NOW("实时"),
    SHORT("短约"),
    LONG("长约"),
    ALL("全部");

    ListenHobby(String hobby) {
      this.hobby = hobby;
    }

    final String hobby;

    @Override public String toString() {
      return hobby;
    }
  }
  /**
   * 车状态
   */
  public enum CarMode {
    EMPTY("空车"),
    LOADED("重车"),
    WIND("顺风车");

    CarMode(String stats) {
      this.stats = stats;
    }

    final String stats;

    @Override public String toString() {
      return stats;
    }
  }
  public enum ListenDistance {
    ONE_KM("2"),
    TWO_KM("3"),
    THREE_KM("4"),
    FOUR_KM("5");

    ListenDistance(String distance) {
      this.distance = distance;
    }

    final String distance;

    @Override public String toString() {
      return distance;
    }

  }
}
