package com.uniware.driver.domain.repository;

import com.uniware.driver.domain.AddressResult;
import com.uniware.driver.domain.BizObject;
import com.uniware.driver.domain.CallRecord;
import com.uniware.driver.domain.HttpResult;
import com.uniware.driver.domain.LoginResult;
import com.uniware.driver.domain.NetBiz;
import com.uniware.driver.domain.Order;
import com.uniware.driver.domain.RankResult;
import com.uniware.driver.domain.StriveStatus;
import com.uniware.driver.domain.VersionCodeResult;
import java.util.List;
import rx.Observable;

/**
 * Interface that represents a Repository for related data.
 */
public interface Repository {

  Observable<LoginResult> login(String phoneNo, String pwd);


  Observable< NetBiz> register(String pidNo,String phoneNo, String pwd, String code);

  Observable<LoginResult> forgetPswd(String phoneNo, String pwd, String code);

  Observable<NetBiz> getCode(String phoneNo);

  Observable<VersionCodeResult>  VersionRequest();

  Observable<Boolean> startPush(String url, int port);

  Observable<BizObject> recvPush();

  Observable<Void> sendPush(byte[] bytes);

  Observable<Void> updateLocation(int flag);

  Observable<StriveStatus> grabOrder(String orderId);

  Observable<NetBiz> completeOrder(String orderId);

  Observable<NetBiz> cancelOrder(String orderId);

  Observable<List<Order>> orders();

 // Observable<Order> findOrder(int oid);

  Observable<?> insertOrder(Order order);

  Observable<Integer> updateOrder(Order order);

  Observable<Integer> updateOrder(String oid,int status);

  Observable<Integer> deleteOrder(String oid);

  Observable<AddressResult> queryAddressd(String oid);

  Observable<NetBiz> updateAddress(String phone,int addressId,String address,double lat, double lon,int type, String des);

  Observable<HttpResult<List<CallRecord>>> unfinishOrder(String useName,String currPage);

  Observable<HttpResult<List<CallRecord>>> finishOrder(String useName,String currPage);

  Observable<NetBiz> modelApply(int type);

  Observable<RankResult> rankSearch(int type);
}
