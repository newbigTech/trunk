package com.uniware.driver.gui.activity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.uniware.driver.R;
import com.uniware.driver.domain.RankResult;
import com.uniware.driver.gui.adapter.FragmentAdapter;
import com.uniware.driver.gui.fragment.RankFragment;
import com.uniware.driver.gui.ui.MyDialog;
import com.uniware.driver.mvp.injector.HasComponent;
import com.uniware.driver.mvp.injector.components.AddComponent;
import com.uniware.driver.mvp.injector.components.DaggerAddComponent;
import java.util.ArrayList;
import java.util.List;

public class RankActivity extends BaseActivity implements HasComponent<AddComponent> {

  //@Bind(R.id.rv_rank) RecyclerView rvRank;
  @Bind(R.id.iv_back) ImageView ivBack;
  @Bind(R.id.tv_type) TextView tvType;
  @Bind(R.id.pagerTabStrip) PagerTabStrip pagerTabStrip;
  @Bind(R.id.viewPager) ViewPager viewPager;
  @Bind(R.id.tv_guize) TextView tvGuize;
  private AddComponent component;
  //@Inject RankPresenter presenter;
  private List<RankResult.RankingListBean> list = new ArrayList<RankResult.RankingListBean>();

  private FragmentAdapter adapter;
  private List<Fragment> fragmentList = new ArrayList<>();
  private List<String> titleList = new ArrayList<>();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_rank);
    ButterKnife.bind(this);
    initializeInjector();
    init();
  }

  private void init() {
    RankFragment fragment1 = new RankFragment();
    Bundle bundle1 = new Bundle();
    bundle1.putInt("key", 1);
    fragment1.setArguments(bundle1);
    fragmentList.add(fragment1);
    RankFragment fragment2 = new RankFragment();
    Bundle bundle2 = new Bundle();
    bundle2.putInt("key", 2);
    fragment2.setArguments(bundle2);
    fragmentList.add(fragment2);
    RankFragment fragment3 = new RankFragment();
    Bundle bundle3 = new Bundle();
    bundle3.putInt("key", 3);
    fragment3.setArguments(bundle3);
    fragmentList.add(fragment3);
    RankFragment fragment4 = new RankFragment();
    Bundle bundle4 = new Bundle();
    bundle4.putInt("key", 4);
    fragment4.setArguments(bundle4);
    fragmentList.add(fragment4);
    titleList.add("司机周榜");
    titleList.add("司机月榜");
    titleList.add("分司周榜");
    titleList.add("分司月榜");
    adapter = new FragmentAdapter(getFragmentManager(), fragmentList, titleList);
    viewPager.setOffscreenPageLimit(3);
    viewPager.setAdapter(adapter);

    //presenter.setView(this);
    //presenter.rankSearch(1);
    //linearLayoutManager = new LinearLayoutManager(this);
    //linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    //rvRank.setLayoutManager(linearLayoutManager);
    ////adapter = new RankAdapter(list, this);
    // rvRank.setAdapter(adapter);
    ivBack.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
    tvGuize.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        MyDialog.Builder builder = new MyDialog.Builder(RankActivity.this);
        builder.setTitle("榜单规则");
        builder.setMessage("\n1.月榜的统计周期为前一个月26日0点到当天的数据。\n" + "2.周榜的统计周期为本周周一0点到当天的数据。");
        // 更新
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
          }
        });
        builder.create().show();
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
        //presenter.rankSearch(which + 1);
        ////adapter.setShowType(which+1);
        //tvType.setText(cities[which]);
        //Log.e("which", which + "");
        ////buil
      }
    });
    AlertDialog r_dialog = builder.create();
    r_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    r_dialog.show();
  }

  @Override public AddComponent getComponent() {
    return component;
  }
}
