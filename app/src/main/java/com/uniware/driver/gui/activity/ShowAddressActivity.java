package com.uniware.driver.gui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.uniware.driver.R;
import com.uniware.driver.config.LoginConfig;
import com.uniware.driver.domain.AddressResult;
import com.uniware.driver.domain.DriverResult;
import com.uniware.driver.gui.adapter.AddressAdapter;
import com.uniware.driver.mvp.injector.components.AddComponent;
import com.uniware.driver.mvp.injector.components.DaggerAddComponent;
import com.uniware.driver.mvp.presenter.AddressPresenter;
import com.uniware.driver.mvp.view.AddressResultView;
import com.uniware.driver.util.ToastUtil;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class ShowAddressActivity extends BaseActivity implements AddressResultView {

  @Bind(R.id.tv_back) TextView tvBack;
  @Bind(R.id.tv_submit) TextView tvSubmit;
  @Bind(R.id.tv_addresst) TextView tvAddresst;
  @Bind(R.id.tv_address) TextView tvAddress;
  @Bind(R.id.title) RelativeLayout title;
  @Bind(R.id.address_recycleview) RecyclerView recyclerView;
  private AddComponent addComponent;
  @Inject AddressPresenter presenter;
  int type = 1;
  int from=1;
  private DriverResult result;
  private AddressAdapter adapter;
  private List<AddressResult.AddressBean> list=new ArrayList<AddressResult.AddressBean>();
  private LinearLayoutManager linearLayoutManager;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_show_address);
    Intent intent=getIntent();
    from=intent.getIntExtra("from",1);
    ButterKnife.bind(this);
    initializeInjector();
    initView();
    presenter.setView(this);
  }

  private void initView() {

    tvBack.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
    linearLayoutManager = new LinearLayoutManager(this);
    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    recyclerView.setLayoutManager(linearLayoutManager);
    adapter=new AddressAdapter(list,this);
    recyclerView.setAdapter(adapter);
    adapter.setOnItemClickListener(new AddressAdapter.OnItemClickListener() {
      @Override public void onItemClicked(int b,boolean f) {
        if (from==1&&f)
        {
          if (b==0){
            if (list.get(0).getDescription().equals("家"))
              EventBus.getDefault().post(list.get(0));
            else if (list.get(1).getDescription().equals("家"))
              EventBus.getDefault().post(list.get(1));
          }
          if (b==1){
            if (list.get(0).getDescription().equals("其他"))
              EventBus.getDefault().post(list.get(0));
            else if (list.get(1).getDescription().equals("其他"))
              EventBus.getDefault().post(list.get(1));
          }
          ShowAddressActivity.this.finish();
        }
        else if (from==2||!f)
        {
          int des=1;
          Intent intent = new Intent(ShowAddressActivity.this, AddressActivity.class);
          if (b==0)
          {
            des=0;
            if (list.get(0).getDescription().equals("家"))
            {
              intent.putExtra("addressId",list.get(0).getId());
              if (list.get(0).getId()==0){
                type=1;
              }
              else{
                type=3;
              }
            }
            if (list.get(1).getDescription().equals("家"))
            {
              intent.putExtra("addressId",list.get(1).getId());
              if (list.get(1).getId()==0){
                type=1;
              }
              else{
                type=3;
              }
            }
          }
          if (b==1)
          {
            des=1;
            if (list.get(0).getDescription().equals("其他"))
            {
              intent.putExtra("addressId",list.get(0).getId());
              if (list.get(0).getId()==0){
                type=1;
              }
              else{
                type=3;
              }
            }
            if (list.get(1).getDescription().equals("其他"))
            {
              intent.putExtra("addressId",list.get(1).getId());
              if (list.get(1).getId()==0){
                type=1;
              }
              else{
                type=3;
              }
            }
          }
          intent.putExtra("type", type);
          intent.putExtra("des",b);
          startActivity(intent);
        }
      }
    });
    tvAddress.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = new Intent(ShowAddressActivity.this, AddressActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
      }
    });
  }

  @Override protected void onResume() {
    super.onResume();
    presenter.queryAddressd(LoginConfig.getInstance().getUserName());
  }

  private void showDialog(String msg) {
    //AlertDialog dialog = new AlertDialog.Builder(ShowAddressActivity.this).setTitle("温馨提示：")
    //    .setMessage(msg)
    //    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
    //      @Override public void onClick(DialogInterface dialog, int which) {
    //        dialog.dismiss();
    //      }
    //    })
    //    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
    //      @Override public void onClick(DialogInterface dialog, int which) {
    //        dialog.dismiss();
    //        presenter.updateAddress(LoginConfig.getInstance().getUserName(), result.getAddress(),
    //            result.getLon(), result.getLat(), 4);
    //      }
    //    })
    //    .create();
    //dialog.show();
  }

  private void initializeInjector() {
    addComponent = DaggerAddComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
    addComponent.inject(this);
  }

  @Override public Context getContext() {
    return getApplicationContext();
  }

  @Override public void querySuccess(AddressResult result) {
    Log.e("ADDRESS",result.toString());
    list.clear();
    if (result.getDriverAppAddressList() != null) {
      //this.result=result.getResult();
      //tvAddress.setText(result.getResult().getAddress());
      list.addAll(result.getDriverAppAddressList());
    }
    if (result.getDriverAppAddressList().size()==1){
      AddressResult.AddressBean bean=new AddressResult.AddressBean();
      bean.setAddress("设置地址");
      if (list.get(0).getDescription().equals("家")){
        bean.setDescription("其他");
      }
      else
        bean.setDescription("家");
      bean.setId(0);
      list.add(bean);
    }
    else if(result.getDriverAppAddressList().size()==0){
      //tvAddress.setText("添加常住地址");
      AddressResult.AddressBean bean=new AddressResult.AddressBean();
      bean.setAddress("设置地址");
      bean.setId(0);
      bean.setDescription("家");
      AddressResult.AddressBean bean1=new AddressResult.AddressBean();
      bean1.setAddress("设置地址");
      bean1.setDescription("其他");
      bean1.setId(0);
      list.add(bean);
      list.add(bean1);
    }
    adapter.notifyDataSetChanged();
    Log.e("refactorList",list.toString());
  }

  @Override public void queryFails(String error) {
    ToastUtil.getToast().show(error);
  }

  @Override public void updateSuccess() {
    ToastUtil.getToast().show("删除成功");
    presenter.queryAddressd(LoginConfig.getInstance().getUserName());
  }

  @Override public void updateFails(String error) {
    ToastUtil.getToast().show("删除失败");
  }
}
