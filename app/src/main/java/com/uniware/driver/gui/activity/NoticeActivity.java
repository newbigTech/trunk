package com.uniware.driver.gui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.uniware.driver.R;
import com.uniware.driver.config.UserConfig;
import com.uniware.driver.domain.NoticeResult;
import com.uniware.driver.gui.adapter.NoticeAdapter;
import com.uniware.driver.mvp.injector.components.DaggerOrderComponent;
import com.uniware.driver.mvp.injector.components.OrderComponent;
import com.uniware.driver.mvp.presenter.AllOrderPresenter;
import com.uniware.driver.mvp.view.NoticeView;
import com.uniware.driver.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by ayue on 2017/5/24.
 */

public class NoticeActivity extends BaseActivity implements NoticeView {

  @Bind(android.R.id.list) RecyclerView listView;
  @Bind(R.id.swipe_refresh_widget) SwipeRefreshLayout swipeRefreshWidget;
  @Bind(R.id.back_btn) ImageView backBtn;
  private LinearLayoutManager linearLayoutManager;
  private int lastVisibleItem;
  private NoticeAdapter adapter;
  private int page = 1;
  private List<NoticeResult.MessagesBean> list = new ArrayList<NoticeResult.MessagesBean>();
  @Inject AllOrderPresenter presenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_notice);
    initializeInjector();
    ButterKnife.bind(this);
    initView();
    presenter.setNoticeView(this);
    presenter.requestNotice(1);
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
    adapter = new NoticeAdapter(this, list);
    listView.setAdapter(adapter);
    adapter.setOnItemClickListener(new NoticeAdapter.OnItemClickListener() {
      @Override public void onItemClicked(NoticeResult.MessagesBean notice) {
        Intent intent=new Intent(NoticeActivity.this,NoticeDetailActivity.class);
        intent.putExtra("time",notice.getSendTime());
        intent.putExtra("id",notice.getId());
        intent.putExtra("text",notice.getMessage());
        startActivity(intent);
      }
    });
    swipeRefreshWidget.setColorSchemeColors(R.color.c_orange_ffd801);
    swipeRefreshWidget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        page = 1;
        presenter.requestNotice(page);
      }
    });
    listView.setOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE
            && lastVisibleItem + 1 == adapter.getItemCount()) {
          page++;
          presenter.requestNotice(page);
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


  @Override public void reFreshOrder(NoticeResult listHttpResult) {
    swipeRefreshWidget.setRefreshing(false);
    list.clear();
    List<String> lists= StringUtils.stringToList(UserConfig.getInstance().getNoticeTag());
    for (int i=0;i<listHttpResult.getMessages().size();i++){
      String id=String.valueOf(listHttpResult.getMessages().get(i).getId());
      for (int j=0;j<lists.size();j++){
        if (id.equals(lists.get(j))){
          listHttpResult.getMessages().get(i).setRead(true);
        }
      }
    }
    list.addAll(listHttpResult.getMessages());
    adapter.notifyDataSetChanged();
    //Log.e("====aall", list.toString());

  }

  @Override public void loadMore(NoticeResult listHttpResult) {
    list.addAll(listHttpResult.getMessages());
    adapter.notifyDataSetChanged();
  }
}
