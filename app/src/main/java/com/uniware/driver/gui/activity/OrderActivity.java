package com.uniware.driver.gui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.uniware.driver.R;
import com.uniware.driver.domain.CallRecord;
import com.uniware.driver.domain.HttpResult;
import com.uniware.driver.gui.adapter.AllOrderAdapter;
import com.uniware.driver.mvp.injector.components.DaggerOrderComponent;
import com.uniware.driver.mvp.injector.components.OrderComponent;
import com.uniware.driver.mvp.presenter.AllOrderPresenter;
import com.uniware.driver.mvp.view.RefreshView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by ayue on 2017/5/24.
 */

public class OrderActivity extends BaseActivity implements RefreshView {

  @Bind(android.R.id.list) RecyclerView listView;
  @Bind(R.id.swipe_refresh_widget) SwipeRefreshLayout swipeRefreshWidget;
  @Bind(R.id.back_btn) ImageView backBtn;
  private LinearLayoutManager linearLayoutManager;
  private int lastVisibleItem;
  private AllOrderAdapter adapter;
  private int page = 1;
  private List<CallRecord> list = new ArrayList<CallRecord>();
  @Inject AllOrderPresenter presenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_order);
    initializeInjector();
    ButterKnife.bind(this);
    initView();
    presenter.setView(this);
    presenter.requestOrder(1);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    ButterKnife.unbind(this);
  }

  private void initView() {
    backBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
    linearLayoutManager = new LinearLayoutManager(this);
    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    listView.setLayoutManager(linearLayoutManager);
    adapter = new AllOrderAdapter(this, list);
    listView.setAdapter(adapter);
    swipeRefreshWidget.setColorSchemeColors(R.color.c_orange_ffd801);
    swipeRefreshWidget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        page = 1;
        presenter.requestOrder(page);
      }
    });
    listView.setOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE
            && lastVisibleItem + 1 == adapter.getItemCount()) {
          page++;
          presenter.requestOrder(page);
        }
      }

      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
      }
    });
  }

  private void initializeInjector() {
    OrderComponent orderComponent = DaggerOrderComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
    orderComponent.inject(this);
  }

  @Override public void reFreshOrder(HttpResult<List<CallRecord>> listHttpResult) {
    swipeRefreshWidget.setRefreshing(false);
    list.clear();
    list.addAll(listHttpResult.getCallRecords());
    adapter.notifyDataSetChanged();
    Log.e("====aall", list.toString());
  }

  @Override public void loadMore(HttpResult<List<CallRecord>> listHttpResult) {
    list.addAll(listHttpResult.getCallRecords());
    adapter.notifyDataSetChanged();
  }
}
