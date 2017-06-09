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
import butterknife.Bind;
import butterknife.ButterKnife;
import com.uniware.driver.R;
import com.uniware.driver.data.database.Provider;
import com.uniware.driver.domain.Order;
import com.uniware.driver.gui.activity.OrderActivity;
import com.uniware.driver.gui.activity.OrderDetailActivity;
import com.uniware.driver.gui.adapter.OrderAdapter;
import com.uniware.driver.mvp.injector.components.DriverComponent;
import com.uniware.driver.mvp.presenter.DriverPresenter;
import com.uniware.driver.mvp.view.DriverFragmentView;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by SJ on 15/11/14.
 */
public class DriverFragment extends MainFragment
    implements LoaderManager.LoaderCallbacks<Cursor>, DriverFragmentView {

  private final Uri orderUri = Provider.Orders.CONTENT_URI;
  private final String[] PROJECTION = new String[] {
      Provider.Orders.COLUMN_OID, Provider.Orders.COLUMN_STATUS, Provider.Orders.COLUMN_TYPE,
      Provider.Orders.COLUMN_TIME, Provider.Orders.COLUMN_FROM_ADDR, Provider.Orders.COLUMN_TO_ADDR,
      Provider.Orders.COLUMN_FROM_LNG, Provider.Orders.COLUMN_FROM_LAT,
      Provider.Orders.COLUMN_TO_LNG, Provider.Orders.COLUMN_TO_LAT, Provider.Orders.COLUMN_PHONE,
      Provider.Orders.COLUMN_FREE,Provider.Orders.COLUMN_description
  };

  private OrderAdapter orderAdapter;
  private int limit = 4;

  @Inject DriverPresenter driverPresenter;

  @Bind(R.id.rv_history_orders) RecyclerView rvOrders;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View fragmentView = inflater.inflate(R.layout.driver_fragment_layout, container, false);
    ButterKnife.bind(this, fragmentView);
    initViews();
    return fragmentView;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    this.initialize();

    Bundle bundle = new Bundle();
    bundle.putString("limit", "_id DESC limit " + limit);
    getLoaderManager().initLoader(0, bundle, this);
  }

  @Override public void onResume() {
    super.onResume();
    driverPresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    driverPresenter.pause();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    driverPresenter.destroy();
  }

  private void initViews() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    orderAdapter = new OrderAdapter(getActivity(), null);
    orderAdapter.setOnItemClickListener(onItemClickListener);
    rvOrders.setLayoutManager(layoutManager);
    rvOrders.setAdapter(orderAdapter);
    orderAdapter.setOnItemClickListener1(new OrderAdapter.OnItemClickListener1() {
      @Override public void onItemClickListener1() {
        Intent it = new Intent(getActivity(), OrderActivity.class);
        startActivity(it);
      }
    });
    rvOrders.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
          int lastItem = manager.findLastCompletelyVisibleItemPosition();
          int countItem = manager.getItemCount();
          if (lastItem == countItem - 1) {
            Bundle bundle = new Bundle();
            limit = limit + 4;
            bundle.putString("limit", "_id DESC limit " + limit);
            getLoaderManager().restartLoader(0, bundle, DriverFragment.this);
          }
        }
      }

      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
      }
    });
  }

  private void initialize() {
    this.getComponent(DriverComponent.class).inject(this);
    this.driverPresenter.setView(this);
  }

  @Override public void onPageSelected() {

  }

  @Override public void loadOrders(List<Order> orders) {
    //this.orders.addAll(orders);
    rvOrders.getAdapter().notifyDataSetChanged();
  }

  @Override public void viewOrder(Order order) {
    Intent it = OrderDetailActivity.getCallingIntent(getActivity(), order,1);
    startActivity(it);
  }

  @Override public void deleteOrder(int oid) {

  }

  @Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    String orderBy = null;
    if (args != null) orderBy = args.getString("limit");
    return new CursorLoader(getActivity(), orderUri, PROJECTION, "status >= ?",
        new String[] { Order.STATUS_END + "" }, orderBy);
  }

  @Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    if (data.getCount() < limit) {
      orderAdapter.setNoMoreData(true);
      limit = data.getCount();
    } else {
      orderAdapter.setNoMoreData(false);
    }
    orderAdapter.swapCursor(data);
  }

  @Override public void onLoaderReset(Loader<Cursor> loader) {
    orderAdapter.swapCursor(null);
  }

  private OrderAdapter.OnItemClickListener onItemClickListener =
      new OrderAdapter.OnItemClickListener() {
        @Override public void onOrderItemClicked(Order order) {
          driverPresenter.onOrderClicked(order);
        }
      };
}
