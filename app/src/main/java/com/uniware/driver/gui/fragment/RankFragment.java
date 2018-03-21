package com.uniware.driver.gui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.uniware.driver.R;
import com.uniware.driver.domain.RankResult;
import com.uniware.driver.gui.adapter.RankAdapter;
import com.uniware.driver.mvp.injector.components.AddComponent;
import com.uniware.driver.mvp.presenter.RankPresenter;
import com.uniware.driver.mvp.view.RankResultView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by ayue on 2018/3/5.
 */

public class RankFragment extends BaseFragment implements RankResultView {
  @Bind(R.id.rv_rank) RecyclerView rvRank;
  @Inject RankPresenter presenter;
  private List<RankResult.RankingListBean> list = new ArrayList<RankResult.RankingListBean>();
  private RankAdapter adapter;
  private LinearLayoutManager linearLayoutManager;
  int type;

  private void initialize() {
    this.getComponent(AddComponent.class).inject(this);
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initialize();
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    Bundle bundle = getArguments();
    type = bundle.getInt("key");
    View view = inflater.inflate(R.layout.fragment_rank, container, false);
    ButterKnife.bind(this, view);
    init();
    return view;
  }

  private void init() {
    presenter.setView(this);
    presenter.rankSearch(type);
    linearLayoutManager = new LinearLayoutManager(getActivity());
    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    rvRank.setLayoutManager(linearLayoutManager);
    adapter = new RankAdapter(list, getActivity());
    adapter.setShowType(type);
    rvRank.setAdapter(adapter);
  }

  @Override public void inSuccess(RankResult result, int type) {
    if (this.type == type) {
      list.clear();
      list.addAll(result.getRankingList());
      adapter.notifyDataSetChanged();
      Log.e("RankList", result.getRankingList().toString());
    }
  }

  @Override public void inFailure(String reason, int type) {

  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }
}
