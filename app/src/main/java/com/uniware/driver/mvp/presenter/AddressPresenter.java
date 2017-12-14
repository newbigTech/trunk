package com.uniware.driver.mvp.presenter;

import android.util.Log;
import com.uniware.driver.domain.AddressResult;
import com.uniware.driver.domain.NetBiz;
import com.uniware.driver.domain.interactor.DefaultSubscriber;
import com.uniware.driver.domain.interactor.QueryAddress;
import com.uniware.driver.domain.interactor.UpdateAddress;
import com.uniware.driver.domain.interactor.UseCase;
import com.uniware.driver.mvp.exception.ErrorMessageFactory;
import com.uniware.driver.mvp.view.AddressResultView;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by ayue on 2017/7/26.
 */

public class AddressPresenter implements Presenter {

  private final UseCase queryAddress ;
  private final UseCase updateAddress;
  private AddressResultView view;
  @Inject public AddressPresenter(@Named("queryAddress") UseCase queryAddress,@Named("updateAddress") UseCase updateAddress){
    this.queryAddress=queryAddress;
    this.updateAddress=updateAddress;
  }
  public void setView(AddressResultView view){
    this.view=view;
  }
  public void queryAddressd(String phoneNo){
    ((QueryAddress)queryAddress).setPhoneNo(phoneNo);
    queryAddress.execute(new QuerySubscriber());
  }
  public void updateAddress(String phone,int addressId,String address,double lon,double lat,int type,int des){
    ((UpdateAddress)updateAddress).setPhone(phone);
    ((UpdateAddress)updateAddress).setAddressId(addressId);
    ((UpdateAddress)updateAddress).setAddress(address);
    ((UpdateAddress)updateAddress).setLon(lon);
    ((UpdateAddress)updateAddress).setLat(lat);
    ((UpdateAddress)updateAddress).setType(type);
    if (des==0){
      ((UpdateAddress)updateAddress).setDes("家");
    }
    else {
      ((UpdateAddress)updateAddress).setDes("公司");
    }
    updateAddress.execute(new UpdateSubscriber());
  }
  private final class UpdateSubscriber extends DefaultSubscriber<NetBiz>{
    @Override public void onCompleted() {
      super.onCompleted();
    }

    @Override public void onNext(NetBiz netBiz) {
      super.onNext(netBiz);
      Log.e("result",netBiz.toString());
      if (netBiz.getErrno()==0){
        view.updateSuccess();
      }
      else {
        view.updateFails(netBiz.getErrmsg());
      }
    }

    @Override public void onError(Throwable e) {
      super.onError(e);
      Log.e("resultupdate",".....");
      String errorMessage = ErrorMessageFactory.create(view.getContext(), (Exception) e);
      view.updateFails(errorMessage);
    }
  }
  private final class QuerySubscriber extends DefaultSubscriber<AddressResult>{
    @Override public void onCompleted() {
      super.onCompleted();
    }

    @Override public void onNext(AddressResult netBiz) {
      super.onNext(netBiz);
      Log.e("result",netBiz.toString());
      if (netBiz.getErrno()==0){
        view.querySuccess(netBiz);
      }else {
        view.queryFails(netBiz.getErrmsg());
      }
    }

    @Override public void onError(Throwable e) {
      super.onError(e);
      String errorMessage = ErrorMessageFactory.create(view.getContext(), (Exception) e);
      view.queryFails(errorMessage);
    }
  }
  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void destroy() {

  }
}
