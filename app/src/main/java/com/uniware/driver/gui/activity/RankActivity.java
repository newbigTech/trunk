package com.uniware.driver.gui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.uniware.driver.R;
import com.uniware.driver.domain.RankResult;
import com.uniware.driver.gui.adapter.RankAdapter;
import com.uniware.driver.mvp.injector.components.AddComponent;
import com.uniware.driver.mvp.injector.components.DaggerAddComponent;
import com.uniware.driver.mvp.presenter.RankPresenter;
import com.uniware.driver.mvp.view.RankResultView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class RankActivity extends BaseActivity implements RankResultView {

  @Bind(R.id.rv_rank) RecyclerView rvRank;
  @Bind(R.id.iv_back) ImageView ivBack;
  @Bind(R.id.tv_type) TextView tvType;
  private AddComponent component;
  @Inject RankPresenter presenter;
  private List<RankResult.RankingListBean> list = new ArrayList<RankResult.RankingListBean>();
  private RankAdapter adapter;
  private LinearLayoutManager linearLayoutManager;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_rank);
    ButterKnife.bind(this);
    initializeInjector();
    init();
  }

  private void init() {
    presenter.setView(this);
    presenter.rankSearch(1);
    linearLayoutManager = new LinearLayoutManager(this);
    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    rvRank.setLayoutManager(linearLayoutManager);
    adapter = new RankAdapter(list, this);
    rvRank.setAdapter(adapter);
    ivBack.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
    tvType.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        showChoise();
      }
    });
  }

  private void initializeInjector() {
    component = DaggerAddComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
    component.inject(this);
  }

  private void showChoise() {

    final AlertDialog.Builder builder =
        new AlertDialog.Builder(this, android.R.style.Theme_Holo_Light_Dialog);
    //builder.setIcon(R.drawable.ic_launcher);

    builder.setTitle("选择排行方式");
    //    指定下拉列表的显示数据
    final String[] cities = { "司机周榜", "司机月榜", "分司周榜", "分司月榜" };
    //    设置一个下拉的列表选择项
    builder.setItems(cities, new DialogInterface.OnClickListener() {
      @Override public void onClick(DialogInterface dialog, int which) {
        presenter.rankSearch(which + 1);
        adapter.setShowType(which+1);
        tvType.setText(cities[which]);
        Log.e("which",which+"");
        //buil
      }
    });
    AlertDialog r_dialog = builder.create();
    r_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    r_dialog.show();
  }

  @Override public Context getContext() {
    return null;
  }

  @Override public void inSuccess(RankResult result) {
    list.clear();
    list.addAll(result.getRankingList());
    adapter.notifyDataSetChanged();
    Log.e("RankList", result.getRankingList().toString());
  }

  @Override public void inFailure(String reason) {

  }
}
