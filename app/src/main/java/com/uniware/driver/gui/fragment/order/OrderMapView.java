package com.uniware.driver.gui.fragment.order;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.uniware.driver.R;
import com.uniware.driver.gui.ui.baidu.DrivingRouteOverlay;

/**
 * Created by SJ on 15/11/14.
 */
public class OrderMapView extends RelativeLayout implements OnGetRoutePlanResultListener {
  private ImageView mDrawerFootImageView;
  private ImageView mImgHandleDown;
  private ImageView mImgHandleUp;
  private boolean mIsMapViewDestroyed = false;
  private RelativeLayout mLayoutHandle;
  private LinearLayout mMapLayout;
  private MapView mMapView;
  private BaiduMap mBaiduMap;
  // 搜索相关
  RoutePlanSearch mSearch = null;    // 搜索模块，也可去掉地图模块独立使用
  private boolean mNightMode = false;
  private SlidingDrawer mSlidingDrawer;
  private TextView mTxtHandle;

  public OrderMapView(Context context) {
    super(context);
    this.initView();
  }

  public OrderMapView(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.initView();
  }

  public OrderMapView(Context context, AttributeSet attrs, int def) {
    super(context, attrs, def);
    this.initView();
  }

/*  private void calcRoute(LatLng var1, LatLng var2) {
    this.calcRoute(var1, var2, (List)null);
  }

  private void calcRoute(LatLng var1, LatLng var2, List<LatLng> var3) {
    TencentNavigationManager var4 = TencentNavigationManager.getInstance(BaseApplication.getAppContext());
    var4.setMarkerOvelayVisible(false);
    var4.setAutoChooseNaviRoute(false);
    var4.startExtraRoutesearch(new OrderMapView.a(this.mOrder.a, var1, var2), var1, var2, 0.0F, false, false, true, false, var3);
  }*/

  private void initView() {
    inflate(this.getContext(), R.layout.main_order_fragment_map_layout, this);
    this.mMapLayout = (LinearLayout) this.findViewById(R.id.viewstub_content);
    this.mSlidingDrawer = (SlidingDrawer) this.findViewById(R.id.main_order_map_sliding_drawer);
    this.mLayoutHandle = (RelativeLayout) this.findViewById(R.id.main_order_map_handle);
    this.mTxtHandle = (TextView) this.findViewById(R.id.txt_main_order_map_handle);
    this.mImgHandleUp = (ImageView) this.findViewById(R.id.img_main_order_map_handle_up);
    this.mImgHandleDown = (ImageView) this.findViewById(R.id.img_main_order_map_handle_down);
    this.mDrawerFootImageView = (ImageView) this.findViewById(R.id.main_order_map_drawer_foot);
    this.mSlidingDrawer.lock();
    mSlidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
      @Override public void onDrawerClosed() {
        mTxtHandle.setText(R.string.main_order_flip_to_show_map);
      }
    });
    mSlidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
      @Override public void onDrawerOpened() {
        mTxtHandle.setText(R.string.main_order_flip_to_show_order);
      }
    });
  }

  private void showMap() {

  }

  public void closeDrawer() {
    this.mLayoutHandle.clearFocus();
    this.mSlidingDrawer.clearFocus();
    if (this.mSlidingDrawer.isOpened()) {
      this.mSlidingDrawer.close();
    }
  }

  public void loadMap() {
    if (this.mMapView == null) {
      BaiduMapOptions mapOptions = new BaiduMapOptions();
      mapOptions.scaleControlEnabled(false); // 隐藏比例尺控件
      mapOptions.zoomControlsEnabled(false);//隐藏缩放按钮
      mMapView = new MapView(getContext(), mapOptions);
      mMapView.removeViewAt(1);
      mBaiduMap = mMapView.getMap();
      // 初始化搜索模块，注册事件监听
      mSearch = RoutePlanSearch.newInstance();
      mSearch.setOnGetRoutePlanResultListener(this);
      //mBaiduMap.setBaiduHeatMapEnabled(true);
      this.mMapView.setVisibility(GONE);
      this.mMapLayout.addView(this.mMapView);
    }

    this.mMapLayout.postDelayed(new Runnable() {
      public void run() {
        if (mMapView != null) {
          mMapView.setVisibility(VISIBLE);
        }
      }
    }, 500L);

    this.mSlidingDrawer.unlock();
  }

  public void loadRoute(double sLat, double sLng, double eLat, double eLng) {
    mBaiduMap.clear();
    PlanNode stNode = PlanNode.withLocation(new LatLng(sLat, sLng));
    PlanNode enNode = PlanNode.withLocation(new LatLng(eLat, eLng));
    DrivingRoutePlanOption routePlanOption = new DrivingRoutePlanOption();
    routePlanOption.from(stNode).to(enNode);
    mSearch.drivingSearch(routePlanOption);
  }

  public void onMapDestroy() {
    if (this.mMapView != null) {
      this.mMapView.onDestroy();
    }
    this.mIsMapViewDestroyed = true;
  }

  public void onMapPause() {
    if (this.mMapView != null) {
      this.mMapView.onPause();
    }
  }

  public void onMapResume() {
    if (this.mMapView != null) {
      this.mMapView.onResume();
    }
  }

  public void setNightMode(boolean isNight) {
    this.mNightMode = isNight;
  }

  @Override public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

  }

  @Override public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

  }

  @Override public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

  }

  @Override public void onGetDrivingRouteResult(DrivingRouteResult result) {
    if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
      Toast.makeText(getContext(), "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
    }
    if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
      // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
      // result.getSuggestAddrInfo()
      return;
    }
    if (result.error == SearchResult.ERRORNO.NO_ERROR) {
      //nodeIndex = -1;
      //RouteLine route = result.getRouteLines().get(0);
      //route.getDistance();
      DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap);
      //routeOverlay = overlay;
      //mBaidumap.setOnMarkerClickListener(overlay);
      overlay.setData(result.getRouteLines().get(0));
      overlay.addToMap();
      overlay.zoomToSpan();
    }
  }

  @Override public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

  }

  @Override public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

  }
}