package com.uniware.driver.gui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.uniware.driver.R;
import com.uniware.driver.config.LoginConfig;
import com.uniware.driver.domain.AddressResult;
import com.uniware.driver.mvp.injector.components.AddComponent;
import com.uniware.driver.mvp.injector.components.DaggerAddComponent;
import com.uniware.driver.mvp.presenter.AddressPresenter;
import com.uniware.driver.mvp.view.AddressResultView;
import com.uniware.driver.util.ToastUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

public class AddressActivity extends BaseActivity
    implements OnGetGeoCoderResultListener, OnGetSuggestionResultListener ,AddressResultView {

  @Bind(R.id.tv_submit) TextView tvSubmit;
  private AddComponent addComponent;
  @Inject AddressPresenter presenter;
  @Bind(R.id.tv_back) TextView tvBack;
  @Bind(R.id.tv_address) TextView tvAddress;

  /**
   * 搜索关键字输入窗口
   */
  private List<String> suggest;
  GeoCoder mSearch = null;
  private SuggestionSearch mSuggestionSearch = null;
  private EditText editCity = null;
  private AutoCompleteTextView keyWorldsView = null;
  private ArrayAdapter<String> sugAdapter = null;
  private double lat;
  private double lon;
  private int type=1;
  private int des=1;
  private int addressId;
  //private double lat;
  //private double lon;
  private List<Map<String,Double>> list=new ArrayList<Map<String,Double>>();

  private void init() {
    tvBack.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });

    // 初始化建议搜索模块，注册建议搜索事件监听
    mSuggestionSearch = SuggestionSearch.newInstance();
    mSuggestionSearch.setOnGetSuggestionResultListener(this);
    mSearch = GeoCoder.newInstance();
    mSearch.setOnGetGeoCodeResultListener(this);
    editCity = (EditText) findViewById(R.id.city);
    keyWorldsView = (AutoCompleteTextView) findViewById(R.id.searchkey);
    sugAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);
    keyWorldsView.setAdapter(sugAdapter);
    keyWorldsView.setThreshold(1);
    /**
     * 当输入关键字变化时，动态更新建议列表
     */
    keyWorldsView.addTextChangedListener(new TextWatcher() {

      @Override public void afterTextChanged(Editable arg0) {

      }

      @Override public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

      }

      @Override public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
        if (cs.length() <= 0) {
          return;
        }

        /**
         * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
         */
        mSuggestionSearch.requestSuggestion((new SuggestionSearchOption()).keyword(cs.toString())
            .city(editCity.getText().toString()));
      }
    });
    tvSubmit.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        setOk();
        tvSubmit.setClickable(false);
      }
    });

  }

  public void setOk() {
    // Geo搜索
    Log.e("updateType","-----------"+type);
    Log.e("updates","-----------"+keyWorldsView.getText().toString());
    if (list!=null&&list.size()>0){
        int position=0;
        for (int j=0;j<suggest.size();j++){
            if (keyWorldsView.getText().toString().equals(suggest.get(j)))
            position=j;
       }
        presenter.updateAddress(LoginConfig.getInstance().getUserName(),
           addressId,keyWorldsView.getText().toString(),
           list.get(position).get("lon"),
           list.get(position).get("lat"),type,des);
     }
    else {
       mSearch.geocode(new GeoCodeOption().city(editCity.getText().toString())
          .address(keyWorldsView.getText().toString()));

    }


  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_address);
    ButterKnife.bind(this);
    Intent intent=getIntent();
    type=intent.getIntExtra("type",1);
    des=intent.getIntExtra("des",1);
    addressId=intent.getIntExtra("addressId",0);
    init();
    initializeInjector();
    //presenter.queryAddressd(LoginConfig.getInstance().getUserName());
    presenter.setView(this);
  }

  private void initializeInjector() {
    addComponent = DaggerAddComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
    addComponent.inject(this);
  }

  @Override protected void onDestroy() {
    mSuggestionSearch.destroy();
    super.onDestroy();
  }

  @Override public void onGetSuggestionResult(SuggestionResult res) {
    if (res == null || res.getAllSuggestions() == null) {
      return;
    }
    suggest = new ArrayList<String>();
    list.clear();
    for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
      if (info.key != null&& info.pt!=null) {
        suggest.add(info.key);
          Map<String,Double> map=new HashMap<String, Double>();
          //map.put("address",info.key)
          map.put("lat",info.pt.latitude);
          map.put("lon",info.pt.longitude);
          list.add(map);

      }
    }
    sugAdapter =
        new ArrayAdapter<String>(AddressActivity.this, android.R.layout.simple_dropdown_item_1line,
            suggest);
    keyWorldsView.setAdapter(sugAdapter);
    //keyWorldsView.getListSelection();
    sugAdapter.notifyDataSetChanged();
  }

  @Override public void onGetGeoCodeResult(GeoCodeResult result) {
    if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
      Toast.makeText(AddressActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
      return;
    }
    lat = result.getLocation().latitude;
    lon = result.getLocation().longitude;
    String strInfo =
        String.format("纬度：%f 经度：%f", result.getLocation().latitude, result.getLocation().longitude);
    Toast.makeText(AddressActivity.this, strInfo, Toast.LENGTH_LONG).show();
    presenter.updateAddress(LoginConfig.getInstance().getUserName(),addressId,keyWorldsView.getText().toString(),lon,lat,type,des);
  }

  @Override public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

  }

  @Override public Context getContext() {
    return getApplicationContext();
  }

  @Override public void querySuccess(AddressResult result) {
    //if (result.getResult().getAddress()!=null&&result.getResult().getAddress().length()>0)
    //{
    //  tvAddress.setText(result.getResult().getAddress());
    //}
    //else {
    //  tvAddress.setText("无");
    //}
  }

  @Override public void queryFails(String error) {
    //ToastUtil.getToast().show(error);

  }

  @Override public void updateSuccess() {
    ToastUtil.getToast().show("修改成功");
    //presenter.queryAddressd(LoginConfig.getInstance().getUserName());
    tvSubmit.setClickable(true);
    AddressActivity.this.finish();
  }

  @Override public void updateFails(String error) {
    ToastUtil.getToast().show(error);
    tvSubmit.setClickable(true);
  }
}
