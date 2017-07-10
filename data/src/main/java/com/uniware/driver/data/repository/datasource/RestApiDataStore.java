package com.uniware.driver.data.repository.datasource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.uniware.driver.data.DriverLocation;
import com.uniware.driver.data.cache.DataCache;
import com.uniware.driver.data.exception.NetworkConnectionException;
import com.uniware.driver.data.net.RestApi;
import com.uniware.driver.domain.CallRecord;
import com.uniware.driver.domain.HttpResult;
import com.uniware.driver.domain.LoginResult;
import com.uniware.driver.domain.NetBiz;
import com.uniware.driver.domain.RegisterInfo;
import com.uniware.driver.domain.StriveStatus;
import com.uniware.driver.domain.VersionCodeResult;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.functions.Func1;

/**
 * {@link CloudDataStore} implementation based on connections to the api (Cloud).
 */
@Singleton public class RestApiDataStore implements OrderDataStore, CloudDataStore {

  private final RestApi restApi;
  private final DataCache dataCache;
  @Inject DriverLocation driverLocation;
  private String token;

  /**
   * Construct a {@link CloudDataStore} based on connections to the api (Cloud).
   *
   * @param dataCache A {@link DataCache} to cache data retrieved from the api.
   */
  @Inject public RestApiDataStore(DataCache dataCache) {
    this.dataCache = dataCache;
    OkHttpClient client = new OkHttpClient();
    Gson gson = new GsonBuilder().create();
    Retrofit restApiAdapter = new Retrofit.Builder().baseUrl(RestApi.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .client(client)
        .build();
    restApi = restApiAdapter.create(RestApi.class);
  }

  public Observable<LoginResult> login(String phoneNo, String pwd) {
    return restApi.login(phoneNo, pwd).map(new Func1<LoginResult, LoginResult>() {
      @Override public LoginResult call(LoginResult loginResult) {
        if (loginResult.getErrno() == 0) {
          token = loginResult.getToken();
        }
        return loginResult;
      }
    }).onErrorResumeNext(new Func1<Throwable, Observable<? extends LoginResult>>() {
      @Override public Observable<? extends LoginResult> call(Throwable throwable) {
        return Observable.error(new NetworkConnectionException(throwable));
      }
    });
    //return Observable.create(new Observable.OnSubscribe<LoginResult>() {
    //  @Override public void call(Subscriber<? super LoginResult> subscriber) {
    //    LoginResult loginResult = new LoginResult();
    //    loginResult.setErrno(0);
    //    loginResult.setErrmsg("OK");
    //    try {
    //      Thread.sleep(1000);
    //    } catch (InterruptedException e) {
    //      e.printStackTrace();
    //    }
    //    subscriber.onNext(loginResult);
    //  }
    //});
  }

  public Observable<NetBiz> register(String pidNo,String phoneNo, String pwd, String code) {
    return restApi.register(pidNo,phoneNo, pwd, code)
        .onErrorResumeNext(new Func1<Throwable, Observable<? extends NetBiz>>() {
          @Override public Observable<? extends NetBiz> call(Throwable throwable) {
            return Observable.error(new NetworkConnectionException(throwable));
          }
        });
  }
  public Observable<VersionCodeResult> VersionRequest(){
    return  restApi.VersionRequest()
        .onErrorResumeNext(new Func1<Throwable, Observable<? extends VersionCodeResult>>() {
          @Override public Observable<? extends VersionCodeResult> call(Throwable throwable) {
            return Observable.error(new NetworkConnectionException(throwable));
          }
        });
  }
  public Observable<HttpResult<List<CallRecord>>> getUnfinishOrder(String useName,String currTag){
    return restApi.getUnfinishOrder(useName,currTag,"20")
        .onErrorResumeNext(new Func1<Throwable, Observable<? extends HttpResult<List<CallRecord>>>>() {
          @Override public Observable<? extends HttpResult<List<CallRecord>>> call(Throwable throwable) {
            return Observable.error(new NetworkConnectionException(throwable));
          }
        });
  }
  public Observable<HttpResult<List<CallRecord>>> getFinishOrder(String useName,String currTag){
    return restApi.getFinishOrder(useName,currTag,"20")
        .onErrorResumeNext(new Func1<Throwable, Observable<? extends HttpResult<List<CallRecord>>>>() {
          @Override public Observable<? extends HttpResult<List<CallRecord>>> call(Throwable throwable) {
            return Observable.error(new NetworkConnectionException(throwable));
          }
        });
  }

  public Observable<LoginResult> forgetPswd(String phoneNo, String pwd, String code) {
    return restApi.forgetPswd(phoneNo, pwd, code)
        .onErrorResumeNext(new Func1<Throwable, Observable<? extends LoginResult>>() {
          @Override public Observable<? extends LoginResult> call(Throwable throwable) {
            return Observable.error(new NetworkConnectionException(throwable));
          }
        });
  }

  public Observable<NetBiz> getCode(String phoneNo) {
    return restApi.getCode(phoneNo).onErrorResumeNext(new Func1<Throwable, Observable<? extends RegisterInfo>>() {
      @Override public Observable<? extends RegisterInfo> call(Throwable throwable) {
        return Observable.error(new NetworkConnectionException(throwable));
      }
    });
  }

  @Override public Observable<StriveStatus> grabOrder(String orderId) {
    return restApi.grabOrder(orderId, token)
        .onErrorResumeNext(new Func1<Throwable, Observable<? extends StriveStatus>>() {
          @Override public Observable<? extends StriveStatus> call(Throwable throwable) {
            return Observable.error(new NetworkConnectionException(throwable));
          }
        });
  }

  @Override public Observable<NetBiz> completeOrder(String orderId) {
    return restApi.completeOrder(orderId, token)
        .onErrorResumeNext(new Func1<Throwable, Observable<? extends StriveStatus>>() {
          @Override public Observable<? extends StriveStatus> call(Throwable throwable) {
            return Observable.error(new NetworkConnectionException(throwable));
          }
        });
  }

  @Override public Observable<NetBiz> cancelOrder(String orderId) {
    return restApi.cancelOrder(orderId, token)
        .onErrorResumeNext(new Func1<Throwable, Observable<? extends StriveStatus>>() {
          @Override public Observable<? extends StriveStatus> call(Throwable throwable) {
            return Observable.error(new NetworkConnectionException(throwable));
          }
        });
  }
}
