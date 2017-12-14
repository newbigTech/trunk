package com.uniware.driver.gui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.umeng.update.UmengUpdateAgent;
import com.uniware.driver.AppApplication;
import com.uniware.driver.R;
import com.uniware.driver.config.LoginConfig;
import com.uniware.driver.data.database.Provider;
import com.uniware.driver.gui.fragment.order.OrderFragment;
import com.uniware.driver.gui.ui.MainView;
import com.uniware.driver.gui.ui.controlpanel.ControlPanel;
import com.uniware.driver.mvp.injector.HasComponent;
import com.uniware.driver.mvp.injector.components.DaggerDriverComponent;
import com.uniware.driver.mvp.injector.components.DriverComponent;
import com.uniware.driver.mvp.presenter.MainPresenter;
import com.uniware.driver.mvp.view.MainActivityView;
import com.uniware.driver.service.LocationService;
import com.uniware.driver.util.BaiduNaviUtil;
import java.lang.ref.SoftReference;
import javax.inject.Inject;

/**
 * Main application screen. This is the app entry point.
 */
public class MainActivity extends BaseActivity
    implements HasComponent<DriverComponent>, MainActivityView {

  private DriverComponent driverComponent;
  @Inject MainPresenter mainPresenter;
  @Inject BaiduNaviUtil baiduNaviUtil;

  public static final String ACTION_CANCEL_REMIND_SWITCH_TO_EMPTY_CAR =
      "cancel_remind_switch_to_empty_car";
  public static final String ACTION_CHARGE_POP_URL = "action_charge_pop_url";
  public static final String ACTION_DISABLE_TRACE_SDK = "disable_trace_sdk";
  public static final String ACTION_GO_OFFLINE = "action_go_offline";
  public static final String ACTION_LINK_STATE_OFFLINE = "action_link_state_offline";
  public static final String ACTION_LINK_STATE_ONLINE = "action_link_state_online";
  public static final String ACTION_NO_LOCATION_HINT = "action_no_locaiton_hint";
  public static final String ACTION_ONLINE_MODE_CHANGED = "action_online_mode_changed";
  public static final String ACTION_ORDER_RELATIVE_MSG = "action_order_relative_msg";
  public static final String ACTION_REMIND_SWITCH_TO_EMPTY_CAR = "remind_switch_to_empty_car";
  public static final String ACTION_SET_ON_BOARD_DEST = "action_set_on_board_dest";
  public static final String ACTION_SHOW_OPEN_GPS_TIPS = "show_open_gps_tips";
  public static final String ACTION_START_OFF_SUCCESS = "action_start_off_success";
  public static final String ACTION_STRIVE_ORDER = "action_strive_order";
  private static final String ON_BOARD_MODE_SET_FRAGMENT_TAG = "OnBoardOrderModeSet";
  private static SoftReference<MainActivity> mActivity;
  private boolean hasShownLocFailDialog = false;
  private boolean hasShownNoLocPermDialog = false;
  private boolean mActivityIsVisble;
  private LocationService locationService;
  private OrderFragment mOrderFragment;
  @Bind(R.id.main_view) MainView mMainView;
  @Bind(R.id.main_background) ImageView mBackground;
  @Bind(R.id.main_user_edu_layout) RelativeLayout mUserEduLayout;
  @Bind(R.id.main_user_edu_image) ImageView mUserEduImageView;
  private int mUserEduStep;
  PowerManager powerManager = null;
  PowerManager.WakeLock wakeLock = null;

  private ControlPanel.OrderSettingCallback mEmptyCarOrderSettingCallback =
      new ControlPanel.OrderSettingCallback() {
        public void onClick() {
          MainActivity.this.mOrderFragment.destroyOrder(false);
        }
      };
  private ControlPanel.OrderSettingCallback mOnBoardOrderSettingCallback =
      new ControlPanel.OrderSettingCallback() {
        public void onClick() {
          //AppState.mIsSetting = true;
          MainActivity.this.mOrderFragment.destroyOrder(false);
        }
      };

  private OrderFragment.OrderFragmentStateListener mOrderFragmentStateListener =
      new OrderFragment.OrderFragmentStateListener() {
        @Override public void onStartOff() {
          mainPresenter.startOff();
        }

        @Override public void onStopOff() {
          mainPresenter.stopOff();
        }

        @Override public void onShowOrderView() {
          if (mMainView != null) {
            mMainView.setVisibility(View.GONE);
          }
        }

        @Override public void onHideOrderView() {
          if (mMainView != null) {
            mMainView.setVisibility(View.VISIBLE);
          }
        }
      };

  private LoginConfig mPref;
  private String mRemindSwitchToEmptyContent;
  private String mRemindSwitchToEmptyOid;

  private void blockScreenLock() {
    this.getWindow().addFlags(4718592);
  }

  private void checkCheatTool() {

  }

  private void checkLocateModel() {
    if (!this.mOrderFragment.isShown()) {
    }
  }

  private void checkMockGpsSwitch() {

  }

  public static MainActivity getMainActivity() {
    return mActivity != null ? (MainActivity) mActivity.get() : null;
  }

  private void handleIntent(Intent intent) {

  }

  private void initViews() {
    mMainView.setFragmentManager(this.getFragmentManager());
    mOrderFragment =
        (OrderFragment) this.getFragmentManager().findFragmentById(R.id.main_order_fragment);
    mOrderFragment.hide();
    mOrderFragment.setOrderViewStateListener(mOrderFragmentStateListener);
    mainPresenter.setMainActivityView(this);
    updateBackground(0);
  }

  private void remindSwitchToEmptyCar() {
    this.remindSwitchToEmptyCar(false);
  }

  private void remindSwitchToEmptyCar(boolean var1) {

  }

  private void setBackgroundResource(int rId, ImageView.ScaleType scaleType) {
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    options.inSampleSize = 2;
    options.inJustDecodeBounds = false;
    Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), rId, options);
    this.mUserEduImageView.setScaleType(scaleType);
    this.mUserEduImageView.setImageBitmap(bitmap);
  }

  private void setSyncTime() {
    try {
      android.provider.Settings.System.putInt(this.getContentResolver(), "auto_time", 1);
      android.provider.Settings.System.putInt(this.getContentResolver(), "auto_time_zone", 1);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void showLocAccuracyDialog() {

  }

  private void showLocFailDialog() {

  }

  private void showNoLocPermDialog() {

  }

  private void uploadTraceLog() {
  }

  public int getCurrentItem() {
    return this.mMainView != null ? this.mMainView.getCurrentItem() : 1;
  }

  public void hideUserEdu() {
    this.mUserEduLayout.setVisibility(View.GONE);
  }

  public void onBackPressed() {
    if (this.mOrderFragment.isShown()) {
      this.mOrderFragment.destroyOrder(true);
    }
    if (false) {
      exitApp();
    } else {
      moveTaskToBack(true);
    }
  }

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeInjector();
    this.setContentView(R.layout.main_activity_layout);
    ButterKnife.bind(this);
    powerManager = (PowerManager)this.getSystemService(this.POWER_SERVICE);
    wakeLock = this.powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Lock");
    mainPresenter.startPush();
    this.initViews();
    this.setSyncTime();
    mActivity = new SoftReference(this);
    AppApplication.getAppContext().getContentResolver()
        .delete(Provider.Orders.CONTENT_URI,null,null );
    mainPresenter.updateOrder();
    this.handleIntent(this.getIntent());
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("action_link_state_offline");
    intentFilter.addAction("action_link_state_online");
    intentFilter.addAction("action_strive_order");
    intentFilter.addAction("action_online_mode_changed");
    intentFilter.addAction("action_order_relative_msg");
    intentFilter.addAction("action.change.background");
    intentFilter.addAction("action_go_offline");
    intentFilter.addAction("action_set_on_board_dest");
    intentFilter.addAction("remind_switch_to_empty_car");
    intentFilter.addAction("cancel_remind_switch_to_empty_car");
    intentFilter.addAction("show_open_gps_tips");
    intentFilter.addAction("disable_trace_sdk");
    intentFilter.addAction("action_charge_pop_url");
    //友盟更新
    UmengUpdateAgent.update(this);
    baiduNaviUtil.init();
    //LocationResult.setMovementMethod(ScrollingMovementMethod.getInstance());
  }

  @Override protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    this.handleIntent(intent);
  }

  @Override protected void onStart() {

    locationService = ((AppApplication) getApplication()).locationService;
    //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
    locationService.registerListener(mListener);
    ////注册监听
    int type = getIntent().getIntExtra("from", 0);
    if (type == 0) {
      locationService.setLocationOption(locationService.getDefaultLocationClientOption());
    } else if (type == 1) {
      locationService.setLocationOption(locationService.getOption());
    }
    locationService.start();// 定位SDK

    super.onStart();
    this.mActivityIsVisble = true;
  }

  @Override protected void onResume() {
    super.onResume();
    wakeLock.acquire();
    mainPresenter.resume();
    this.checkCheatTool();
    this.checkMockGpsSwitch();

    this.checkLocateModel();
    this.remindSwitchToEmptyCar();

    this.blockScreenLock();
  }

  @Override protected void onPause() {
    super.onPause();
    wakeLock.release();
    mainPresenter.pause();
    this.blockScreenLock();
  }

  @Override protected void onStop() {
   locationService.unregisterListener(mListener); //注销掉监听
    locationService.stop(); //停止定位服务
    super.onStop();
    this.mActivityIsVisble = false;
  }

  protected void onDestroy() {
    super.onDestroy();
    ButterKnife.unbind(this);
    mainPresenter.destroy();
    baiduNaviUtil.destroy();
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
  }

  //用户使用提示指南
  public void showUserEdu() {
    this.mUserEduLayout.setVisibility(View.VISIBLE);
    this.mUserEduLayout.setBackgroundColor(Color.parseColor("#be171717"));
    this.mUserEduStep = 0;
    this.setBackgroundResource(2130838122, ImageView.ScaleType.FIT_END);
    this.mUserEduLayout.setOnClickListener(new View.OnClickListener() {
      public void onClick(View var1) {
        //MainActivity.access$008(MainActivity.this);
        switch (MainActivity.this.mUserEduStep) {
          case 1:
            MainActivity.this.setBackgroundResource(2130838123, ImageView.ScaleType.FIT_START);
            return;
          case 2:
            MainActivity.this.setBackgroundResource(2130838124, ImageView.ScaleType.FIT_START);
            return;
          case 3:
            MainActivity.this.setBackgroundResource(2130838125, ImageView.ScaleType.FIT_END);
            return;
          case 4:
            MainActivity.this.hideUserEdu();
          default:
        }
      }
    });
  }

  public void switchToDriverInfo() {
    this.mMainView.switchToDriverInfo();
  }

  public void updateBackground(int bg) {
    switch (bg) {
      case 0:
        this.mBackground.setImageResource(R.drawable.bg_morning);
        return;
      case 1:
        this.mBackground.setImageResource(R.drawable.common_bg_noon);
        return;
      case 2:
        this.mBackground.setImageResource(R.drawable.common_bg_evening);
        return;
      default:
        this.mBackground.setImageResource(R.drawable.common_bg_night);
    }
  }

  private void initializeInjector() {
    driverComponent = DaggerDriverComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
    driverComponent.inject(this);
  }

  @Override public DriverComponent getComponent() {
    return driverComponent;
  }

  @Override public Context getContext() {
    return this;
  }

  @Override public void showError(String message) {
    //Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }
  private BDLocationListener mListener = new BDLocationListener() {

    @Override
    public void onReceiveLocation(BDLocation location) {
      // TODO Auto-generated method stub
      if (null != location && location.getLocType() != BDLocation.TypeServerError) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("time : ");
        /**
         * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
         * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
         */
        sb.append(location.getTime());
        sb.append("\nlocType : ");// 定位类型
        sb.append(location.getLocType());
        sb.append("\nlocType description : ");// *****对应的定位类型说明*****
        sb.append(location.getLocTypeDescription());
        sb.append("\nlatitude : ");// 纬度
        sb.append(location.getLatitude());
        sb.append("\nlontitude : ");// 经度
        sb.append(location.getLongitude());
        sb.append("\nradius : ");// 半径
        sb.append(location.getRadius());
        sb.append("\nCountryCode : ");// 国家码
        sb.append(location.getCountryCode());
        sb.append("\nCountry : ");// 国家名称
        sb.append(location.getCountry());
        sb.append("\ncitycode : ");// 城市编码
        sb.append(location.getCityCode());
        sb.append("\ncity : ");// 城市
        sb.append(location.getCity());
        sb.append("\nDistrict : ");// 区
        sb.append(location.getDistrict());
        sb.append("\nStreet : ");// 街道
        sb.append(location.getStreet());
        sb.append("\naddr : ");// 地址信息
        sb.append(location.getAddrStr());
        sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
        sb.append(location.getUserIndoorState());
        sb.append("\nDirection(not all devices have value): ");
        sb.append(location.getDirection());// 方向
        sb.append("\nlocationdescribe: ");
        sb.append(location.getLocationDescribe());// 位置语义化信息
        sb.append("\nPoi: ");// POI信息
        if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
          for (int i = 0; i < location.getPoiList().size(); i++) {
            Poi poi = (Poi) location.getPoiList().get(i);
            sb.append(poi.getName() + ";");
          }
        }
        if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
          sb.append("\nspeed : ");
          sb.append(location.getSpeed());// 速度 单位：km/h
          sb.append("\nsatellite : ");
          sb.append(location.getSatelliteNumber());// 卫星数目
          sb.append("\nheight : ");
          sb.append(location.getAltitude());// 海拔高度 单位：米
          sb.append("\ngps status : ");
          sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
          sb.append("\ndescribe : ");
          sb.append("gps定位成功");
        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
          // 运营商信息
          if (location.hasAltitude()) {// *****如果有海拔高度*****
            sb.append("\nheight : ");
            sb.append(location.getAltitude());// 单位：米
          }
          sb.append("\noperationers : ");// 运营商信息
          sb.append(location.getOperators());
          sb.append("\ndescribe : ");
          sb.append("网络定位成功");
        } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
          sb.append("\ndescribe : ");
          sb.append("离线定位成功，离线定位结果也是有效的");
        } else if (location.getLocType() == BDLocation.TypeServerError) {
          sb.append("\ndescribe : ");
          sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
        } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
          sb.append("\ndescribe : ");
          sb.append("网络不同导致定位失败，请检查网络是否通畅");
        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
          sb.append("\ndescribe : ");
          sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
        }
        Log.e("====",sb.toString());
        LoginConfig loginConfig=LoginConfig.getInstance();
        loginConfig.setLocation(location.getLatitude()+"",location.getLongitude()+"");
      }
    }

  };

}
