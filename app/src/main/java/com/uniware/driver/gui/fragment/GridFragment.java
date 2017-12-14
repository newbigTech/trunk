package com.uniware.driver.gui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.uniware.driver.R;
import com.uniware.driver.gui.activity.RankActivity;
import com.uniware.driver.gui.activity.ShowAddressActivity;
import com.uniware.driver.gui.adapter.GridAdapter;
import com.uniware.driver.gui.fragment.setting.ModeSettingFragment;
import com.uniware.driver.mvp.injector.components.DriverComponent;
import com.uniware.driver.mvp.presenter.GridPresenter;
import com.uniware.driver.mvp.view.GridFragmentView;
import javax.inject.Inject;

/**
 * Created by SJ on 15/11/14.
 */
public class GridFragment extends MainFragment implements GridFragmentView {

  private int[] rId = new int[] {
      R.drawable.grid_accept_fare1_ic, R.drawable.grid_apply_recommand1_ic,
      R.drawable.grid_apply_square1_ic, R.drawable.grid_apply_traffic1_ic,
      R.drawable.grid_apply_setting1_ic,R.drawable.ic_address
  };
  @Inject GridPresenter gridPresenter;

  @Bind(R.id.rv_grid_setting) RecyclerView rvSetting;
  private GridAdapter adapter;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View fragmentView = inflater.inflate(R.layout.grid_fragment_layout, container, false);
    ButterKnife.bind(this, fragmentView);
    initViews();
    return fragmentView;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    this.initialize();
  }

  @Override public void onResume() {
    super.onResume();
    gridPresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    gridPresenter.pause();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    gridPresenter.destroy();
  }

  private void initViews() {
    rvSetting.setLayoutManager(
        new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false));
    //rvSetting.setLayoutManager(new LinearLayoutManager(getActivity()));
    rvSetting.setHasFixedSize(true);
    adapter=new GridAdapter(rId);
    rvSetting.setAdapter(adapter);
    adapter.setOnItemClickListener(new GridAdapter.OnItemClickListener() {
      @Override public void onOrderItemClicked(int id) {
        if (id==4){
          ModeSettingFragment.newInstance().show(getFragmentManager(), "orderSetting");
        }
        if (id==5){
          Intent intent=new Intent(getActivity(), ShowAddressActivity.class);
          intent.putExtra("from",2);
          startActivity(intent);
        }
        if (id==2){
          Intent intent=new Intent(getActivity(), RankActivity.class);
          startActivity(intent);
        }
      }
    });
  }

  private void initialize() {
    this.getComponent(DriverComponent.class).inject(this);
    this.gridPresenter.setView(this);
  }

  @Override public void onPageSelected() {

  }
}
