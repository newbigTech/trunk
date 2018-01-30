package com.uniware.driver.data.net;

import com.uniware.driver.domain.AddressResult;
import com.uniware.driver.domain.CallRecord;
import com.uniware.driver.domain.HttpResult;
import com.uniware.driver.domain.LoginResult;
import com.uniware.driver.domain.NetBiz;
import com.uniware.driver.domain.NoticeResult;
import com.uniware.driver.domain.RankResult;
import com.uniware.driver.domain.StriveStatus;
import com.uniware.driver.domain.VersionCodeResult;
import java.util.List;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApi {

    //String API_BASE_URL = "http://223.72.209.142:9000/905platform/";
    //String API_BASE_URL = "http://uniwaredev.6655.la:9100/905platform/";

    //jyj ceshi
     String API_BASE_URL = "http://223.72.209.140:10580/905platform/";
    //
    //String API_BASE_URL = "http://180.213.117.47:9000/905platform/";
  @GET("DriverAppController/login.do") Observable<LoginResult> login(@Query("username") String phoneNum, @Query("password") String pwd);

  @GET("DriverAppController/loginwithtoken.do") Observable<LoginResult> login(@Query("token") String token);

  @GET("DriverAppController/register.do") Observable<NetBiz> register(@Query("pidnum") String pidNo,@Query("username") String phoneNum, @Query("password") String pwd,
      @Query("code") String code);

  @GET("AppVersionController/getVersion.do") Observable<VersionCodeResult> VersionRequest();

  @GET("DriverAppController/forgetpassword.do") Observable<LoginResult> forgetPswd(@Query("username") String phoneNum,
      @Query("password") String pwd, @Query("code") String code);

  @GET("DriverAppController/getCode.do") Observable<NetBiz> getCode(@Query("username") String phoneNum);

  @GET("orders/{oid}/grab") Observable<StriveStatus> grabOrder(@Path("oid") String orderId,
      @Query("token") String token);

  @GET("orders/{oid}/complete") Observable<NetBiz> completeOrder(@Path("oid") String orderId,
      @Query("token") String token);
  @GET("orders/{oid}/complete") Observable<NetBiz> modelApply (@Path("oid") String orderId);

  @GET("DriverAppController/callConform.do") Observable<HttpResult<List<CallRecord>>> getUnfinishOrder(
      @Query("driverTel") String phoneNum,@Query("currPage") String currPage, @Query("pageSize") String pageSize );

  @GET("DriverAppController/callHistory.do") Observable<HttpResult<List<CallRecord>>> getFinishOrder(
      @Query("driverTel") String phoneNum,@Query("currPage") String currPage, @Query("pageSize") String pageSize );

  @GET("orders/{oid}/cancel") Observable<NetBiz> cancelOrder(@Path("oid") String orderId, @Query("token") String token);

  @GET("DriverAppController/queryNewAddress.do") Observable<AddressResult> queryAddressd(@Query("driverTel") String username);

  @GET("DriverAppController/setNewAddress.do") Observable<NetBiz> updateAddress(@Query("driverTel") String username,
      @Query("addressId") int addressId,@Query("address") String address,@Query("lon") double lon,@Query("lat") double lat,@Query("type") int type
      ,@Query("description") String description);
  @GET("DriverAppController/askForRankingList.do")Observable<RankResult>rankSearch(@Query("type") int type);

  @GET("DriverAppController/messageHistory.do")Observable<NoticeResult>noticeSearch(@Query("driverTel") String driverTel,
      @Query("currPage") int currPage,@Query("pageSize") int pageSize);
}
