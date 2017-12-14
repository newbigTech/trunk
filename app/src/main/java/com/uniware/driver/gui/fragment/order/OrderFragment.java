package com.uniware.driver.gui.fragment.order;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.uniware.driver.R;
import com.uniware.driver.config.LoginConfig;
import com.uniware.driver.data.database.Provider;
import com.uniware.driver.domain.AddressResult;
import com.uniware.driver.domain.Order;
import com.uniware.driver.gui.activity.OrderDetailActivity;
import com.uniware.driver.gui.activity.ShowAddressActivity;
import com.uniware.driver.gui.fragment.BaseFragment;
import com.uniware.driver.gui.ui.AppDialog;
import com.uniware.driver.gui.ui.controlpanel.ControlPanel;
import com.uniware.driver.gui.ui.controlpanel.GrabButton;
import com.uniware.driver.gui.ui.controlpanel.ListeningButton;
import com.uniware.driver.gui.ui.controlpanel.StartOffButton;
import com.uniware.driver.mvp.injector.components.DriverComponent;
import com.uniware.driver.mvp.presenter.OrderPanelPresenter;
import com.uniware.driver.mvp.view.ControlPanelView;
import com.uniware.driver.mvp.view.OrderFragmentView;
import com.uniware.driver.mvp.view.OrderStateView;
import com.uniware.driver.util.ToastUtil;
import com.uniware.driver.util.Tools;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import javax.inject.Inject;

/**
 * Created by jian on 15/11/13.
 */
public class OrderFragment extends BaseFragment implements OrderFragmentView, ControlPanelView ,OrderStateView{
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  @Inject OrderPanelPresenter orderPanelPresenter;
  private static final Uri orderUri = Provider.Orders.CONTENT_URI;
  private static final int POLL_INTERVAL = 1000;
  private static final int WAIT_HTTP_STRIVE_RESULT = 3000;
  public static int mWaitPushConsultResult = 7000;
  public static int mWaitPushStriveResult = 6000;
  private boolean isShow = false;
  private View mContentView;
  private int mCurPollNum;
  private long mEnterTenderTime;
  private String mParam1;
  private boolean isPopOrder;

  private boolean mGrabSuccess = false;
  private OrderFragment.OrderFragmentInvalidListener mInvalidListener = null;
  private OrderFragmentStateListener mOrderFragmentStateListener;
  private long mJoinConsultTime;
  private long mJoinTenderTime;
  private OrderFragment.OrderFragmentEnableGrabListener mOrderFragmentEnableGrabListener = null;
  private boolean mOrderHasStriveResult = false;
  private AppDialog mDialog;
  private Order order;

  @Bind(R.id.order_fragment_title) OrderTitleView mTitleView;
  @Bind(R.id.order_fragment_address) CommonOrderAddressView mAddressView;
  @Bind(R.id.order_fragment_carpool_address) CarpoolOrderAddressView mCarpoolAddressView;
  @Bind(R.id.order_fragment_layout_close) RelativeLayout mLayoutClose;
  @Bind(R.id.order_fragment_img_close) ImageView mImgClose;
  private TextView mTipOffTextView;
  private OrderMapView mOrderMapView;
  private View mGrayCover;
  private OrderStrivedBriefView mOrderStrivedBriefView;
  private OrderStrivedDetailView mOrderStrivedDetailView;
  private ImageView mImgOrderCover;
  private ImageView mIndicatorImageView;
  private ControlPanel mControlPanel;
  private StartOffButton mStartOffButton;
  private ListeningButton mListeningButton;
  @Bind(R.id.btn_grab) GrabButton mGrabButton;

  @OnClick(R.id.btn_grab) void btnGrabClick() {
    orderPanelPresenter.grabOrder();
  }

  private View.OnClickListener controlPanelBtnClickListener = new View.OnClickListener() {
    @Override public void onClick(View v) {
      switch (v.getId()) {
        case R.id.btn_start_off:
          orderPanelPresenter.startOff();
          new Handler().postDelayed(new Runnable() {
            @Override public void run() {
              getActivity().findViewById(R.id.submenu_peacock_empty)
                  .setOnClickListener(peacockSubMenuClickListener);
              getActivity().findViewById(R.id.submenu_peacock_carry)
                  .setOnClickListener(peacockSubMenuClickListener);
              getActivity().findViewById(R.id.submenu_peacock_wind)
                  .setOnClickListener(peacockSubMenuClickListener);
              getActivity().findViewById(R.id.submenu_peacock_setting)
                  .setOnClickListener(peacockSubMenuClickListener);
            }
          }, 350);
          mOrderFragmentStateListener.onStartOff();
          break;
        case R.id.btn_listening:
          orderPanelPresenter.stopOff();
          mOrderFragmentStateListener.onStopOff();
          break;
      }
    }
  };

  private View.OnClickListener peacockSubMenuClickListener = new View.OnClickListener() {
    @Override public void onClick(View v) {
      switch (v.getId()) {
        case R.id.submenu_peacock_empty:
          orderPanelPresenter.ttsSpeaking("普通模式");
          //mMainView.getAnnounceFragment().setListeningMode(0);
          orderPanelPresenter.setModelApply(1);
          LoginConfig.getInstance().setModel(1);
          Tools.LISTEN_MODE= Tools.ListenMode.NOMAL;
          EventBus.getDefault().post(Tools.LISTEN_MODE);
          break;
        case R.id.submenu_peacock_carry:
          orderPanelPresenter.ttsSpeaking("重车模式");
          //mMainView.getAnnounceFragment().setListeningMode(1);
          Tools.LISTEN_MODE= Tools.ListenMode.WEIGHT;
          EventBus.getDefault().post(Tools.LISTEN_MODE);
          break;
        case R.id.submenu_peacock_wind:
          orderPanelPresenter.ttsSpeaking("定点模式");
          //mMainView.getAnnounceFragment().setListeningMode(2);
          Intent intent=new Intent(getActivity(), ShowAddressActivity.class);
          intent.getIntExtra("from",1);
          startActivity(intent);
          break;
        case R.id.submenu_peacock_setting:
          orderPanelPresenter.ttsSpeaking("指派模式");
          orderPanelPresenter.setModelApply(2);
          LoginConfig.getInstance().setModel(2);
          //ModeSettingFragment.newInstance().show(getFragmentManager(), "orderSetting");
          Tools.LISTEN_MODE= Tools.ListenMode.ASSIGN;
          EventBus.getDefault().post(Tools.LISTEN_MODE);
          break;
      }
    }
  };

  @OnClick(R.id.order_fragment_layout_close) void onClose() {
    this.dismissDialog();
    this.hide();
    if (this.mOrderFragmentStateListener != null) {
      this.mOrderFragmentStateListener.onHideOrderView();
    }
    orderPanelPresenter.cancelCountdown1();
    showListeningBtn();
    mGrabButton.setVisibility(View.INVISIBLE);
  }

  public static OrderFragment newInstance(String param1, boolean param2) {
    OrderFragment fragment = new OrderFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putBoolean(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  public OrderFragment() {
  }

  private void clearDialog() {
    if (this.mDialog != null) {
      this.mDialog.hide();
    }
    this.mDialog = new AppDialog(getActivity());
  }

  private void dismissDialog() {
    if (this.mDialog != null) {
      this.mDialog.hide();
    }
  }

  private void setBgByOrderType(int type) {
    int defaultType = R.drawable.main_order_ic_close_instant_selector;
    switch (type) {
      case 0:
        type = defaultType;
        if (!isPopOrder) {
          this.mGrayCover.setVisibility(View.GONE);
          this.mOrderStrivedDetailView.setVisibility(View.GONE);
          this.mOrderStrivedBriefView.setVisibility(View.GONE);
          //this.mIndicatorImageView.setBackgroundResource(R.drawable.order_fragment_type_instant);
        }
        break;
      case 1:
        type = R.drawable.main_order_ic_close_preorder_selector;
        if (!isPopOrder) {
          this.mGrayCover.setVisibility(View.GONE);
          this.mOrderStrivedDetailView.setVisibility(View.GONE);
          this.mOrderStrivedBriefView.setVisibility(View.GONE);
          //this.mIndicatorImageView.setBackgroundResource(R.drawable.order_fragment_type_preorder);
        }
        break;
      case 2:
        type = R.drawable.main_order_ic_close_invalid_selector;
        if (!isPopOrder) {
          this.mOrderMapView.closeDrawer();
          this.mGrayCover.setVisibility(View.VISIBLE);
          //this.mIndicatorImageView.setBackgroundResource(R.drawable.order_invaild_cover);
        }
        break;
      default:
        type = defaultType;
    }

    this.mImgClose.setBackgroundResource(type);
  }

  private void setListeners() {
/*    this.mLayoutClose.setOnClickListener(this.mOnCloseClickListener);
    this.mTipOffTextView.setOnClickListener(this.mTipOffClickListener);
    this.mOrderStrivedBriefView.getTipOffButton().setOnClickListener(this.mTipOffClickListener);
    this.mOrderStrivedDetailView.getTipOffButton().setOnClickListener(this.mTipOffClickListener);*/
  }

  private void updateAddressView() {
    this.mAddressView.setVisibility(View.VISIBLE);
    this.mCarpoolAddressView.setVisibility(View.GONE);
  }

  private void updateMapView() {
    this.mOrderMapView.closeDrawer();
    this.mOrderMapView.loadRoute(order.getFromLat(), order.getFromLng(), order.getToLat(),
        order.getToLng());
  }

  private void updateUI() {
    this.updateAddressView();
    if (!isPopOrder) this.updateMapView();
  }

  public void showOrder(Order order) {
    this.order = order;
    if (order.getType() == 0) {
      mTitleView.setOrderTitle(order.getType(), "距您" + order.getDistances() + "公里",
          order.getIsAssign());
    } else {
      mTitleView.setOrderTitle(order.getType(), order.getDescrTimer(), order.getIsAssign());
    }
    mAddressView.setAddress(order.getXfromAddr(), order.getXtoAddr(), order.getDescription());
    setBgByOrderType(order.getType());
    updateUI();
    clearDialog();
    show();
  }

  public void closeOrder() {
    this.mAddressView.stopPlay();
    if (mOrderMapView != null) this.mOrderMapView.closeDrawer();
    this.onClose();
  }

  public void destroyOrder(boolean isDestroy) {
    if (isDestroy) {
      order = null;
    }
    this.closeOrder();
  }

  public void show() {
    if (mContentView != null) {
      mContentView.setVisibility(View.VISIBLE);
    } else {
      getFragmentManager().beginTransaction().show(this).commit();
    }
    isShow = true;
  }

  public void hide() {
    if (mContentView != null) {
      mContentView.setVisibility(View.GONE);
    } else {
      getFragmentManager().beginTransaction().hide(this).commit();
    }
    isShow = false;
  }

  @Override public void grabOrder() {
    mDialog.show("抢单中", false);
  }

  @Override public void grabSuccess() {
    this.closeOrder();
    ToastUtil.getToast().show(R.drawable.dialog_ok, "抢单成功！", 17, Toast.LENGTH_SHORT);
    if (isPopOrder) return;
    //if (order.getType()==1){return;}
    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        int i = 1;
        if (order.getType() == 1) {
          i = 0;
        }
        Intent it = OrderDetailActivity.getCallingIntent(getActivity(), order, i);
        startActivity(it);
      }
    }, 1000);
  }

  @Override public void grabFailure() {
    this.closeOrder();
  }

  @Override public Order getCurrentOrder() {
    return order;
  }

  public boolean isShown() {
    return this.isShow;
  }

  private void initialize() {
    this.getComponent(DriverComponent.class).inject(this);
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      isPopOrder = getArguments().getBoolean(ARG_PARAM2);
    }
    initialize();
    orderPanelPresenter.setControlPanelView(this);
    orderPanelPresenter.setOrderFragmentView(this);
    orderPanelPresenter.setOrderStateView(this);
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view;
    EventBus.getDefault().register(this);
    if (!isPopOrder) {
      view = inflater.inflate(R.layout.order_fragment_layout, container, false);
      mContentView = view.findViewById(R.id.order_fragment_layout);
      mTipOffTextView = (TextView) view.findViewById(R.id.btn_tip_off);
      mOrderMapView = (OrderMapView) view.findViewById(R.id.order_fragment_map);
      mGrayCover = view.findViewById(R.id.view_order_fragment_gray_cover);
      mOrderStrivedBriefView =
          (OrderStrivedBriefView) view.findViewById(R.id.view_order_fragment_strived_brief_cover);
      mOrderStrivedDetailView =
          (OrderStrivedDetailView) view.findViewById(R.id.view_order_fragment_strived_detail_cover);
      mImgOrderCover = (ImageView) view.findViewById(R.id.view_order_fragment_cover);
      mIndicatorImageView = (ImageView) view.findViewById(R.id.order_fragment_indicator);
      mControlPanel = (ControlPanel) view.findViewById(R.id.main_control_panel);
      mStartOffButton = (StartOffButton) view.findViewById(R.id.btn_start_off);
      mStartOffButton.setOnClickListener(controlPanelBtnClickListener);
      mListeningButton = (ListeningButton) view.findViewById(R.id.btn_listening);
      mListeningButton.setOnClickListener(controlPanelBtnClickListener);
      mOrderMapView.loadMap();
    } else {
      view = inflater.inflate(R.layout.order_pop_fragment_layout, container, false);
    }
    ButterKnife.bind(this, view);
    setListeners();
    return view;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  public void onStart() {
    super.onStart();
  }

  public void onResume() {
    super.onResume();
    if (mOrderMapView != null) this.mOrderMapView.onMapResume();
    orderPanelPresenter.resume();
  }

  public void onPause() {
    super.onPause();
    if (mOrderMapView != null) this.mOrderMapView.onMapPause();
    orderPanelPresenter.pause();
  }

  public void onStop() {
    super.onStop();
  }

  public void onDestroyView() {
    super.onDestroyView();
    if (mOrderMapView != null) this.mOrderMapView.onMapDestroy();
    ButterKnife.unbind(this);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
    orderPanelPresenter.destroy();
  }

  public void setVoiceFlipShow(boolean isShow) {
    if (isShow) {
      this.mAddressView.startVoiceFlip();
    } else {
      this.mAddressView.stopVoiceFlip();
    }
  }

  @Override public void showOrderView(Order order) {
    if (this.mOrderFragmentStateListener != null) {
      mOrderFragmentStateListener.onShowOrderView();
    }
    showOrder(order);
  }

  @Override public void hideOrderView() {
    closeOrder();
  }

  @Override public void orderComplete(String oid,int state) {
    //ContentValues cv = new ContentValues();
    //cv.put("status", Order.STATUS_END);
    //getActivity().getContentResolver().update(orderUri, cv, "oid = ?", new String[] { oid });
  }

  @Override public void orderCancel(String oid,int state) {
    //ContentValues cv = new ContentValues();
    //cv.put("status", Order.STATUS_CANCEL);
    //getActivity().getContentResolver().update(orderUri, cv, "oid = ?", new String[] { oid });
  }

  @Override public void showError(String message) {
    ///Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
  }

  @Override public Context getContext() {
    return getActivity();
  }

  @Override public void showGrabBtn(int countdown) {
    if (mControlPanel != null) {
      mControlPanel.showGrabButton(countdown);
    } else {
      mGrabButton.show(countdown);
    }
  }

  @Override public void showForbidGrabBtn() {
    if (mControlPanel != null) mControlPanel.showGrabForbidButton(12);
  }

  @Override public void showStartOffBtn() {
    if (mControlPanel != null) mControlPanel.showStartOffBtn();
  }

  @Override public void showListeningBtn() {
    if (mControlPanel != null) mControlPanel.showListeningButton();
  }

  @Override public void startLinking() {
    if (mControlPanel != null) mControlPanel.startLinking();
  }

  @Override public void stopLinking() {
    if (mControlPanel != null) mControlPanel.stopLinking();
  }

  public void setOrderFragmentEnableGrabListener(OrderFragmentEnableGrabListener listener) {
    this.mOrderFragmentEnableGrabListener = listener;
  }

  public void setOrderFragmentInvalidListener(OrderFragmentInvalidListener listener) {
    this.mInvalidListener = listener;
  }

  public void setOrderViewStateListener(OrderFragmentStateListener listener) {
    this.mOrderFragmentStateListener = listener;
  }

  public interface OrderFragmentStateListener {
    void onStartOff();

    void onStopOff();

    void onShowOrderView();

    void onHideOrderView();
  }

  public interface OrderFragmentEnableGrabListener {
    void onOrderFragmentEnableGrab();
  }

  public interface OrderFragmentInvalidListener {
    void onOrderFragmentInvalid();
  }
  @Subscribe(threadMode = ThreadMode.PostThread)public void messageHandler(AddressResult.AddressBean data){
    LoginConfig.getInstance().setModel(3);
    LoginConfig.getInstance().setAddressId(data.getId());
    LoginConfig.getInstance().setAddressLat(data.getLat()+"");
    LoginConfig.getInstance().setAddressLon(data.getLon()+"");
    orderPanelPresenter.setModelApply(3);
    Tools.LISTEN_MODE= Tools.ListenMode.ADDRESS;
    EventBus.getDefault().post(Tools.LISTEN_MODE);
  }
}