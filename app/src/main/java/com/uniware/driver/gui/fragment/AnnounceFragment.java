package com.uniware.driver.gui.fragment;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.uniware.driver.R;
import com.uniware.driver.config.LoginConfig;
import com.uniware.driver.data.database.Provider;
import com.uniware.driver.domain.Order;
import com.uniware.driver.gui.activity.OrderDetailActivity;
import com.uniware.driver.gui.adapter.OrderAdapter;
import com.uniware.driver.mvp.injector.components.DriverComponent;
import com.uniware.driver.mvp.presenter.AnnouncePresenter;
import com.uniware.driver.mvp.view.AnnounceFragmentView;
import com.uniware.driver.util.Tools;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import javax.inject.Inject;

/**
 * Created by SJ on 15/11/14.
 */
public class AnnounceFragment extends MainFragment
    implements LoaderManager.LoaderCallbacks<Cursor>, AnnounceFragmentView {

  private int listeningMode = 0;

  /**
   * Interface for listening user list events.
   */
  public interface OrderListListener {
    void onOrderClicked(final Order order);
  }

  private OrderListListener orderListListener;

  private final Uri orderUri = Provider.Orders.CONTENT_URI;
  private final String[] PROJECTION = new String[] {
      Provider.Orders.COLUMN_OID, Provider.Orders.COLUMN_STATUS, Provider.Orders.COLUMN_TYPE,
      Provider.Orders.COLUMN_TIME, Provider.Orders.COLUMN_FROM_ADDR, Provider.Orders.COLUMN_TO_ADDR,
      Provider.Orders.COLUMN_FROM_LNG, Provider.Orders.COLUMN_FROM_LAT,
      Provider.Orders.COLUMN_TO_LNG, Provider.Orders.COLUMN_TO_LAT, Provider.Orders.COLUMN_PHONE,
      Provider.Orders.COLUMN_FREE,Provider.Orders.COLUMN_description
  };
  private OrderAdapter orderAdapter;

  @Inject AnnouncePresenter announcePresenter;

  @Bind(R.id.tv_listen_mode) TextView tvListenMode;
  @Bind(R.id.tv_listen_hobby) TextView tvListenHobby;
  @Bind(R.id.tv_listen_range) TextView tvListenRange;
  @Bind(R.id.tv_car_mode) TextView tvCarMode;
  @Bind(R.id.rv_current_orders) RecyclerView rvOrders;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EventBus.getDefault().register(this);
/*    if (getActivity() instanceof OrderListListener){
      orderListListener = (OrderListListener) getActivity();
    }*/
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View fragmentView = inflater.inflate(R.layout.announce_fragment_layout, container, false);
    ButterKnife.bind(this, fragmentView);
    initViews();
    //announcePresenter.updateOrder(LoginConfig.getInstance().getUserName(),"1");
    return fragmentView;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    this.initialize();
    getLoaderManager().initLoader(0, null, this);
  }

  @Override public void onResume() {
    super.onResume();
    announcePresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    announcePresenter.pause();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    announcePresenter.destroy();
    EventBus.getDefault().unregister(this);
  }

  private void initViews() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    orderAdapter = new OrderAdapter(getActivity(), null);
    orderAdapter.setOnItemClickListener(onItemClickListener);
    rvOrders.setLayoutManager(layoutManager);
    rvOrders.setAdapter(orderAdapter);
    LoginConfig loginConfig=LoginConfig.getInstance();
    int hobby=loginConfig.getHobby();
    int distance=loginConfig.getDistance();
    String hobbys=null;
    String distances=null;
    switch (hobby){
      case 0:
        hobbys="全部";break;
      case 1:
        hobbys="实时";break;
      case 2:
        hobbys="短约";break;
      case 3:
        hobbys="长约";break;
    }
    switch (distance){
      case 0:
        distances="5";break;
      case 1:
        distances="1";break;
      case 2:
        distances="2";break;
      case 3:
        distances="3";break;
      case 4:
        distances="4";break;
      case 5:
        distances="5";break;
    }
    tvListenMode.setText(String.format(getString(R.string.listening_mode), "空车模式"));
    tvListenHobby.setText(String.format(getString(R.string.listening_hobby), hobbys));
    tvListenRange.setText(String.format(getString(R.string.listening_range), distances));
    tvCarMode.setText(String.format(getString(R.string.car_mode), "空车模式"));
    tvCarMode.setVisibility(View.GONE);
  }

  private void initialize() {
    this.getComponent(DriverComponent.class).inject(this);
    this.announcePresenter.setView(this);
  }

  @Subscribe(threadMode = ThreadMode.PostThread)public void messageHandler(Tools.ListenMode mode){
    switch (mode){
      case WIND:
        tvListenMode.setText(String.format(getString(R.string.listening_mode), "顺风模式"));
        break;
      case WEIGHT:
        tvListenMode.setText(String.format(getString(R.string.listening_mode), "重车模式"));
        break;
      case CARNULL:
        tvListenMode.setText(String.format(getString(R.string.listening_mode), "空车模式"));
        break;
      case HOME:
        tvListenMode.setText(String.format(getString(R.string.listening_mode), "回家模式"));
        break;
    }
  }

  @Subscribe(threadMode = ThreadMode.PostThread)public void messageHandler(Tools.ListenHobby hobby){
    switch (hobby){
      case NOW:
        tvListenHobby.setText(String.format(getString(R.string.listening_hobby), "即时"));
        break;
      case SHORT:
        tvListenHobby.setText(String.format(getString(R.string.listening_hobby), "短约"));
        break;
      case LONG:
        tvListenHobby.setText(String.format(getString(R.string.listening_hobby), "长约"));
        break;
      case ALL:
        tvListenHobby.setText(String.format(getString(R.string.listening_hobby), "全部"));
        break;
    }
  }

  @Subscribe(threadMode = ThreadMode.PostThread)public void messageHandler(Tools.ListenDistance distance){
    switch (distance){
      case ONE_KM:
        tvListenRange.setText(String.format(getString(R.string.listening_range), "2"));
        break;
      case TWO_KM:
        tvListenRange.setText(String.format(getString(R.string.listening_range), "3"));
        break;
      case THREE_KM:
        tvListenRange.setText(String.format(getString(R.string.listening_range), "4"));
        break;
      case FOUR_KM:
        tvListenRange.setText(String.format(getString(R.string.listening_range), "5"));
        break;
    }
  }

  @Subscribe(threadMode = ThreadMode.PostThread)public void messageHandler(Tools.CarMode carMode){
    switch (carMode){
      case EMPTY:
        tvCarMode.setText(String.format(getString(R.string.car_mode), "空车"));
        break;
      case LOADED:
        tvCarMode.setText(String.format(getString(R.string.car_mode), "重车"));
        break;
      case WIND:
        tvCarMode.setText(String.format(getString(R.string.car_mode), "顺风车"));
        break;
    }
  }

  public void setListeningMode(int mode) {
    listeningMode = mode;
    switch (mode) {
      case 0:
        tvCarMode.setText("空车");
        break;
      case 1:
        tvCarMode.setText("载客");
        break;
      case 2:
        tvCarMode.setText("顺风");
        break;
    }
  }

  @Override public void onPageSelected() {

  }

  @Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    return new CursorLoader(getActivity(), orderUri, PROJECTION, "status < ?",
        new String[] { Order.STATUS_ARRIVED + "" }, null);
  }

  @Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    orderAdapter.swapCursor(data);
  }

  @Override public void onLoaderReset(Loader<Cursor> loader) {
    orderAdapter.swapCursor(null);
  }

  private OrderAdapter.OnItemClickListener onItemClickListener =
      new OrderAdapter.OnItemClickListener() {
        @Override public void onOrderItemClicked(Order order) {
          announcePresenter.onOrderClicked(order);
        }
      };

  @Override public void viewOrder(Order order) {
    Intent it = OrderDetailActivity.getCallingIntent(getActivity(), order,1);
    startActivity(it);
  }
}
