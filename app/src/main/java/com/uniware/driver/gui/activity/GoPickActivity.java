package com.uniware.driver.gui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.baidu.navisdk.adapter.BNRouteGuideManager;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviBaseCallbackModel;
import com.baidu.navisdk.adapter.BaiduNaviCommonModule;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.baidu.navisdk.adapter.NaviModuleFactory;
import com.baidu.navisdk.adapter.NaviModuleImpl;
import com.uniware.driver.AppApplication;
import com.uniware.driver.R;
import com.uniware.driver.data.DriverLocation;
import com.uniware.driver.data.database.Provider;
import com.uniware.driver.domain.Order;
import com.uniware.driver.gui.ui.TitleView;
import com.uniware.driver.mvp.injector.components.DaggerOrderComponent;
import com.uniware.driver.mvp.injector.components.OrderComponent;
import com.uniware.driver.mvp.presenter.GoPickPresenter;
import com.uniware.driver.mvp.view.GoPickView;
import com.uniware.driver.util.LogUtils;
import com.uniware.driver.util.ToastUtil;
import com.uniware.driver.util.XFSpeech;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by jian on 16/04/21.
 */
public class GoPickActivity extends OrderPopActivity implements GoPickView {
  public static List<Activity> activityList = new LinkedList<Activity>();
  private static final String INTENT_EXTRA_PARAM_ORDER = "com.uniware.driver.INTENT_PARAM_ORDER";
  private static final String INSTANCE_STATE_PARAM_ORDER = "com.uniware.driver.STATE_PARAM_ORDER";

  public static final String ROUTE_PLAN_NODE = "routePlanNode";
  public static final String SHOW_CUSTOM_ITEM = "showCustomItem";
  public static final String RESET_END_NODE = "resetEndNode";
  public static final String VOID_MODE = "voidMode";

  private static final int MSG_SHOW = 1;
  private static final int MSG_HIDE = 2;
  private static final int MSG_RESET_NODE = 3;
  private static final Uri orderUri = Provider.Orders.CONTENT_URI;
  List<BNRoutePlanNode> list = new ArrayList<>();
  BNRoutePlanNode sNode;
  BNRoutePlanNode mNode;
  BNRoutePlanNode eNode;
  @Bind(R.id.btn_go_guid) Button btnGoGuid;
  private BNRoutePlanNode mBNRoutePlanNode = null;
  private BaiduNaviCommonModule mBaiduNaviCommonModule = null;
  private Handler hd = null;
  private boolean guid=true;

  private Order order;
  private int isssue = 1;
  private XFSpeech speech;
  @Inject GoPickPresenter goPickPresenter;
  @Inject DriverLocation driverLocation;

  @Bind(R.id.go_pick_from_txt) TextView tvFrom;
  @Bind(R.id.go_pick_to_txt) TextView tvTo;
  @Bind(R.id.title_go_pick) TitleView titleView;
  @Bind(R.id.btn_go_pick) Button btnGoPick;
  @Bind(R.id.text_descr) TextView tvDesc;

  @OnClick(R.id.btn_go_pick) void orderCompleteClick() {
    if (order.getStatus() == Order.STATUS_GO_PICK) {
      order.setStatus(Order.STATUS_ARRIVE_PSNGER1_LOCATION);
    } else if (order.getStatus() == Order.STATUS_ARRIVE_PSNGER1_LOCATION) {
      order.setStatus(Order.STATUS_DRIVING);
    } else if (order.getStatus() == Order.STATUS_DRIVING) {
      goPickPresenter.completeOrder(order.getOid());
    }
    updateUI();
  }

  @OnClick(R.id.go_pick_call_btn) void callBtnClick() {
    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + order.getPhone()));
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
        != PackageManager.PERMISSION_GRANTED) {
      return;
    }
    startActivity(intent);
  }

  public static Intent getCallingIntent(Context context, Order order, int i) {
    Intent callingIntent = new Intent(context, GoPickActivity.class);
    callingIntent.putExtra(INTENT_EXTRA_PARAM_ORDER, order);
    callingIntent.putExtra("issue", i);
    return callingIntent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_go_pick);
    ButterKnife.bind(this);
    this.initializeInjector();
    this.initializeActivity(savedInstanceState);
    speech = new XFSpeech(this);
    goPickPresenter.setGoPickView(this);
    if (!order.getDescription().equals("") && !order.getDescription().equals("无")) {
      tvDesc.setVisibility(View.VISIBLE);
      tvDesc.setText(order.getDescription());
    }
    //routePlanToNavi(BNRoutePlanNode.CoordinateType.GCJ02);
    if (order.getStatus() == Order.STATUS_START) {
      order.setStatus(Order.STATUS_GO_PICK);
    }
    updateUI();
    btnGoGuid.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (!AppApplication.isNavUtil) {
          new AlertDialog.Builder(GoPickActivity.this).setTitle("系统提示")//设置对话框标题
              .setMessage("请确认是否使用导航")//设置显示的内容
              .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                @Override public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                  // TODO Auto-generated method stub
                  Intent i1 = new Intent();
                  // 驾车导航
                  i1.setData(Uri.parse("baidumap://map/navi?query=" + order.getToAddr()));
                  startActivity(i1);
                  finish();
                }
              }).setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮
            @Override public void onClick(DialogInterface dialog, int which) {//响应事件
              // TODO Auto-generated method stub
              Log.i("alertdialog", " 请保存数据！");
            }
          }).show();//在按键响应事件中显示此对话框
          return;
        }
        if (guid){
          navi2destination();
        }
        else {
          sNode = new BNRoutePlanNode(driverLocation.getLocation().getLongitude(),
              driverLocation.getLocation().getLatitude(), "", null,
              BNRoutePlanNode.CoordinateType.BD09LL);
          //mNode = new BNRoutePlanNode(order.getFromLng(), order.getFromLat(), order.getFromAddr(), null,
          //    BNRoutePlanNode.CoordinateType.BD09LL);
          eNode = new BNRoutePlanNode(order.getToLng(), order.getToLat(), order.getToAddr(), null,
              BNRoutePlanNode.CoordinateType.BD09LL);
          list.clear();
          list.add(sNode);
          list.add(eNode);
          BaiduNaviManager.getInstance()
              .launchNavigator(GoPickActivity.this, list, 1, true, new NaviRoutePlanListener(sNode));
          mBNRoutePlanNode = sNode;
        }
      }
    });
    if (isssue != 0) {
      //使用通用接口
      mBaiduNaviCommonModule = NaviModuleFactory.getNaviModuleManager()
          .getNaviCommonModule(NaviModuleImpl.BNaviCommonModuleConstants.ROUTE_GUIDE_MODULE, this,
              BNaviBaseCallbackModel.BNaviBaseCallbackConstants.CALLBACK_ROUTEGUIDE_TYPE,
              mOnNavigationListener);
      //createHandler();
    } else {
      speech.ttsSpeaking(getString(R.string.yuyue_text));
      new Thread(new Runnable() {
        @Override public void run() {
          try {
            Thread.sleep(1000);
            runOnUiThread(new Runnable() {
              @Override public void run() {
                new AlertDialog.Builder(GoPickActivity.this).setTitle("系统提示")//设置对话框标题
                    .setMessage(getResources().getString(R.string.yuyue_text))//设置显示的内容
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                      @Override public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                        // TODO Auto-generated method stub
                        finish();
                      }
                    }).show();//在按键响应事件中显示此对话框
              }
            });
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }).start();
    }

  }

  @Override protected void onResume() {
    super.onResume();
    speech.resume();
    if (mBaiduNaviCommonModule != null) {
      mBaiduNaviCommonModule.onResume();
    }
  }

  @Override protected void onPause() {
    super.onPause();
    //speech.pause();
    if (mBaiduNaviCommonModule != null) {
      mBaiduNaviCommonModule.onPause();
    }
  }

  @Override protected void onStop() {
    super.onStop();
    speech.stop();
    if (mBaiduNaviCommonModule != null) {
      mBaiduNaviCommonModule.onStop();
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    ButterKnife.unbind(this);
    speech.destroy();
    if (mBaiduNaviCommonModule != null) {
      mBaiduNaviCommonModule.onDestroy();
    }
  }

  @Override public void onBackPressed() {
    if (mBaiduNaviCommonModule != null) {
      mBaiduNaviCommonModule.onBackPressed(false);
    }
    super.onBackPressed();
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    if (outState != null) {
      outState.putSerializable(INTENT_EXTRA_PARAM_ORDER, this.order);
    }
    super.onSaveInstanceState(outState);
  }

  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    if (mBaiduNaviCommonModule != null) {
      mBaiduNaviCommonModule.onConfigurationChanged(newConfig);
    }
  }

  /**
   * Initializes this activity.
   */
  private void initializeActivity(Bundle savedInstanceState) {
    if (savedInstanceState == null) {
      this.order = (Order) getIntent().getSerializableExtra(INTENT_EXTRA_PARAM_ORDER);
      this.isssue = getIntent().getIntExtra("issue", 1);
    } else {
      this.order = (Order) savedInstanceState.getSerializable(INTENT_EXTRA_PARAM_ORDER);
      this.isssue = savedInstanceState.getInt("issue", 1);
    }
  }

  private void initializeInjector() {
    OrderComponent orderComponent = DaggerOrderComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
    orderComponent.inject(this);
  }

  private void updateUI() {
    tvFrom.setText(order.getFromAddr());
    tvTo.setText(order.getToAddr());
    if (order.getStatus() == Order.STATUS_GO_PICK) {
      titleView.setTitle(getString(R.string.title_go_pick_picking_psnger),
          new View.OnClickListener() {
            @Override public void onClick(View v) {
              finish();
              BNRouteGuideManager.getInstance().forceQuitNaviWithoutDialog();
            }
          }, new View.OnClickListener() {
            @Override public void onClick(View v) {
              goPickPresenter.cancelOrder(order.getOid());
              BNRouteGuideManager.getInstance().forceQuitNaviWithoutDialog();
            }
          });
      btnGoPick.setText(R.string.btn_init_psnger);
    } else if (order.getStatus() == Order.STATUS_ARRIVE_PSNGER1_LOCATION) {
      titleView.setTitle(getString(R.string.title_go_pick_waiting_psnger),
          new View.OnClickListener() {
            @Override public void onClick(View v) {
              finish();
              BNRouteGuideManager.getInstance().forceQuitNaviWithoutDialog();
            }
          }, new View.OnClickListener() {
            @Override public void onClick(View v) {
              // goPickPresenter.cancelOrder(order.getOid());
              //BNRouteGuideManager.getInstance().forceQuitNaviWithoutDialog();
            }
          });
      guid=false;
      btnGoPick.setText(R.string.btn_in_place_psnger);
    } else if (order.getStatus() == Order.STATUS_DRIVING) {
      titleView.setTitle(getString(R.string.title_go_pick_driving), new View.OnClickListener() {
        @Override public void onClick(View v) {
          finish();
          BNRouteGuideManager.getInstance().forceQuitNaviWithoutDialog();
        }
      });
      btnGoPick.setText(R.string.btn_sel_onboard_psnger);
    }
  }

  @Override public void orderComplete() {
    ContentValues cv = new ContentValues();
    cv.put("status", Order.STATUS_ARRIVED);
    getContentResolver().update(orderUri, cv, "oid = ?", new String[] { order.getOid() });
    finish();
  }

  @Override public void orderCancel() {
    //ContentValues cv = new ContentValues();
    //cv.put("status", Order.STATUS_CANCEL_DRIVER);
    //getContentResolver().update(orderUri, cv, "oid = ?", new String[] { order.getOid() });
    //Tools.SetTaxiStatus(false);
    //finish();

  }

  @Override public void showError(String errorMsg) {
    ToastUtil.getToast().show(errorMsg);
  }

  private void navi2destination() {

    //BNRoutePlanNode eNode;
    sNode = new BNRoutePlanNode(driverLocation.getLocation().getLongitude(),
        driverLocation.getLocation().getLatitude(), "", null,
        BNRoutePlanNode.CoordinateType.BD09LL);
    mNode = new BNRoutePlanNode(order.getFromLng(), order.getFromLat(), order.getFromAddr(), null,
        BNRoutePlanNode.CoordinateType.BD09LL);
    eNode = new BNRoutePlanNode(order.getToLng(), order.getToLat(), order.getToAddr(), null,
        BNRoutePlanNode.CoordinateType.BD09LL);

    list.add(sNode);
    list.add(mNode);
    //list.add(eNode);
    BaiduNaviManager.getInstance()
        .launchNavigator(this, list, 1, true, new NaviRoutePlanListener(sNode));
    mBNRoutePlanNode = sNode;
  }

  public class NaviRoutePlanListener implements BaiduNaviManager.RoutePlanListener {
    private BNRoutePlanNode mBNRoutePlanNode = null;

    public NaviRoutePlanListener(BNRoutePlanNode node) {
      mBNRoutePlanNode = node;
    }

    @Override public void onJumpToNavigator() {
      //设置途径点以及resetEndNode会回调该接口
      //Toast.makeText(GoPickActivity.this, "开始导航", Toast.LENGTH_SHORT).show();
      //if (mBaiduNaviCommonModule != null) {
      //  mBaiduNaviCommonModule.onCreate();
      //  View view = mBaiduNaviCommonModule.getView();
      //  if (view != null) {
      //    FrameLayout frameLayout = (FrameLayout) view;
      //    frameLayout.removeViewAt(1);
      //    FrameLayout map = (FrameLayout) findViewById(R.id.go_pick_navi_map);
      //    map.removeAllViews();
      //    map.addView(view);
      //  }
      //}
      //if (hd != null) {
      //  hd.sendEmptyMessageAtTime(MSG_SHOW, 2000);
      //}
      Intent intent = new Intent(GoPickActivity.this, BNDemoGuideActivity.class);
      Bundle bundle = new Bundle();
      bundle.putSerializable(ROUTE_PLAN_NODE, (BNRoutePlanNode) mBNRoutePlanNode);
      intent.putExtras(bundle);
      startActivity(intent);
    }

    @Override public void onRoutePlanFailed() {
      // TODO Auto-generated method stub
      Toast.makeText(GoPickActivity.this, "算路失败", Toast.LENGTH_SHORT).show();
    }
  }

  private void addCustomizedLayerItems() {
    List<BNRouteGuideManager.CustomizedLayerItem> items = new ArrayList<>();
    BNRouteGuideManager.CustomizedLayerItem item1 = null;
    if (mBNRoutePlanNode != null) {
      item1 = new BNRouteGuideManager.CustomizedLayerItem(mBNRoutePlanNode.getLongitude(),
          mBNRoutePlanNode.getLatitude(), mBNRoutePlanNode.getCoordinateType(),
          getResources().getDrawable(R.drawable.logo),
          BNRouteGuideManager.CustomizedLayerItem.ALIGN_CENTER);
      items.add(item1);

      BNRouteGuideManager.getInstance().setCustomizedLayerItems(items);
    }
    BNRouteGuideManager.getInstance().showCustomizedLayer(true);
  }

  private void createHandler() {
    if (hd == null) {
      hd = new Handler(getMainLooper()) {
        public void handleMessage(Message msg) {
          if (msg.what == MSG_SHOW) {
            //addCustomizedLayerItems();
          } else if (msg.what == MSG_HIDE) {
            BNRouteGuideManager.getInstance().showCustomizedLayer(false);
          } else if (msg.what == MSG_RESET_NODE) {
            BNRouteGuideManager.getInstance()
                .resetEndNodeInNavi(new BNRoutePlanNode(116.21142, 40.85087, "百度大厦11", null,
                    BNRoutePlanNode.CoordinateType.GCJ02));
          }
        }
      };
    }
  }

  private BNRouteGuideManager.OnNavigationListener mOnNavigationListener =
      new BNRouteGuideManager.OnNavigationListener() {

        @Override public void onNaviGuideEnd() {
          finish();
        }

        @Override public void notifyOtherAction(int actionType, int arg1, int arg2, Object obj) {

          if (actionType == 0) {
            LogUtils.e(this, "notifyOtherAction actionType = " + actionType + ",导航到达目的地！");
          }

          LogUtils.e(this, "actionType:"
              + actionType
              + "arg1:"
              + arg1
              + "arg2:"
              + arg2
              + "obj:"
              + obj.toString());
        }
      };
}
