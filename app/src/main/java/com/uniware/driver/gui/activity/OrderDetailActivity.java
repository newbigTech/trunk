package com.uniware.driver.gui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.uniware.driver.AppApplication;
import com.uniware.driver.R;
import com.uniware.driver.domain.Order;
import com.uniware.driver.gui.fragment.order.OrderMapView;
import com.uniware.driver.gui.ui.TitleView;
import com.uniware.driver.mvp.injector.components.DaggerOrderComponent;
import com.uniware.driver.mvp.injector.components.OrderComponent;
import com.uniware.driver.mvp.presenter.OrderDetailPresenter;
import com.uniware.driver.mvp.view.OrderDetailView;
import com.uniware.driver.util.ToastUtil;
import javax.inject.Inject;

public class OrderDetailActivity extends OrderPopActivity implements OrderDetailView {
  private static final String INTENT_EXTRA_PARAM_ORDER = "com.uniware.driver.INTENT_PARAM_ORDER";
  private static final String INSTANCE_STATE_PARAM_ORDER = "com.uniware.driver.STATE_PARAM_ORDER";
  private OrderMapView mOrderMapView;
  private Order order;
  private int issue;

  @Inject OrderDetailPresenter orderDetailPresenter;

  @Bind(R.id.go_pick_from_txt) TextView tvFrom;
  @Bind(R.id.go_pick_to_txt) TextView tvTo;
  @Bind(R.id.order_detail_title_view) TitleView titleView;
  @Bind(R.id.title_end_order_txt) TextView titleLeftBtn;

  @OnClick(R.id.go_pick_call_btn) void callBtnClick() {
    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + order.getPhone()));
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
        != PackageManager.PERMISSION_GRANTED) {
      return;
    }
    startActivity(intent);
  }

  public static Intent getCallingIntent(Context context, Order order,int issue) {
    Intent callingIntent = new Intent(context, OrderDetailActivity.class);
    callingIntent.putExtra(INTENT_EXTRA_PARAM_ORDER, order);
    callingIntent.putExtra("issue",issue);
    return callingIntent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_order_detail);
    //mOrderMapView = (OrderMapView)findViewById(R.id.order_fragment_map);
    ButterKnife.bind(this);
    this.initializeActivity(savedInstanceState);
    this.initializeInjector();
    orderDetailPresenter.setOrderDetailView(this);
    findViewById(R.id.go_pick_call_btn).setVisibility(View.GONE);
    titleView.setTitle("订单内容", new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    }, new View.OnClickListener() {
      @Override public void onClick(View v) {
        orderDetailPresenter.deleteOrder(order.getOid());
      }
    });
    titleLeftBtn.setText("删除订单");
    if (AppApplication.isNavUtil) {
      new Thread(new Runnable() {
        @Override public void run() {
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          runOnUiThread(new Runnable() {
            @Override public void run() {
              Log.e("====", "asdfghjfgh");

            }
          });
        }
      }).start();
      //mOrderMapView.setVisibility(View.VISIBLE);
      //mOrderMapView.loadMap();
      //findViewById(R.id.content).setVisibility(View.GONE);
      //mOrderMapView.closeDrawer();
      //mOrderMapView.loadRoute(order.getFromLat(), order.getFromLng(), order.getToLat(),
      //    order.getToLng());

      //mOrderMapView.up
    }
  }

  @Override protected void onResume() {
    super.onResume();

    if (mOrderMapView != null) this.mOrderMapView.onMapResume();
  }

  @Override protected void onPause() {
    super.onPause();
    if (mOrderMapView != null) this.mOrderMapView.onMapPause();
  }

  @Override protected void onStop() {
    super.onStop();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (mOrderMapView != null) this.mOrderMapView.onMapDestroy();
    ButterKnife.unbind(this);
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    if (outState != null) {
      outState.putSerializable(INTENT_EXTRA_PARAM_ORDER, this.order);
    }
    super.onSaveInstanceState(outState);
  }

  public void onConfigurationChanged(android.content.res.Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
  }

  /**
   * Initializes this activity.
   */
  private void initializeActivity(Bundle savedInstanceState) {
    if (savedInstanceState == null) {
      this.order = (Order) getIntent().getSerializableExtra(INTENT_EXTRA_PARAM_ORDER);
      this.issue= (int) getIntent().getSerializableExtra("issue");
    } else {
      this.order = (Order) savedInstanceState.getSerializable(INTENT_EXTRA_PARAM_ORDER);
      this.issue=savedInstanceState.getInt("issue");
    }
    if (this.order == null) {
      this.finish();
    } else if (order.getStatus() < Order.STATUS_END) {
      Intent it = GoPickActivity.getCallingIntent(this, order,issue);
      startActivity(it);
      this.finish();
    }
    updateUI();
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
  }

  @Override public void orderDelete() {
    finish();
  }

  @Override public void showError(String errorMsg) {
    ToastUtil.getToast().show(errorMsg);
  }
}
