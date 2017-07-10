package com.uniware.driver.data.repository;

import android.net.Uri;
import com.uniware.driver.data.cache.DataCache;
import com.uniware.driver.data.repository.datasource.CloudDataStore;
import com.uniware.driver.data.repository.datasource.RestApiDataStore;
import com.uniware.driver.data.repository.datasource.SQLiteDataStore;
import com.uniware.driver.data.repository.datasource.TcpDataStore;
import com.uniware.driver.domain.BizObject;
import com.uniware.driver.domain.CallRecord;
import com.uniware.driver.domain.HttpResult;
import com.uniware.driver.domain.LoginResult;
import com.uniware.driver.domain.NetBiz;
import com.uniware.driver.domain.Order;
import com.uniware.driver.domain.StriveStatus;
import com.uniware.driver.domain.VersionCodeResult;
import com.uniware.driver.domain.repository.Repository;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;

/**
 * {@link Repository} for retrieving user data.
 */
@Singleton public class DataRepository implements Repository {

  private final DataCache dataCache;
  private final RestApiDataStore restApiDataStore;
  private final TcpDataStore tcpDataStore;
  private final SQLiteDataStore sqLiteDataStore;

  /**
   * Constructs a {@link Repository}.
   *
   * @param dataCache {@link DataCache}
   * @param restApiDataStore {@link RestApiDataStore}
   * @param sqLiteDataStore {@link SQLiteDataStore}
   */
  @Inject public DataRepository(DataCache dataCache, RestApiDataStore restApiDataStore,
      TcpDataStore tcpDataStore, SQLiteDataStore sqLiteDataStore) {
    if (dataCache == null
        || restApiDataStore == null
        || tcpDataStore == null
        || sqLiteDataStore == null) {
      throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
    }
    this.dataCache = dataCache;
    this.sqLiteDataStore = sqLiteDataStore;
    this.tcpDataStore = tcpDataStore;
    this.restApiDataStore = restApiDataStore;
  }

  @Override public Observable<LoginResult> login(String phoneNo, String pwd) {
    tcpDataStore.setIsuID(phoneNo);
    return restApiDataStore.login(phoneNo, pwd);
  }

  @Override public Observable<NetBiz> register(String pidNo,String phoneNo, String pwd, String code) {
    return restApiDataStore.register(pidNo,phoneNo, pwd, code);
  }

  @Override public Observable<LoginResult> forgetPswd(String phoneNo, String pwd, String code) {
    return restApiDataStore.forgetPswd(phoneNo, pwd, code);
  }

  @Override public Observable<NetBiz> getCode(String phoneNo) {
    return restApiDataStore.getCode(phoneNo);
  }

  @Override public Observable<VersionCodeResult> VersionRequest() {
    return restApiDataStore.VersionRequest();
  }

  @Override public Observable<Boolean> startPush(String url, int port) {
    return tcpDataStore.startPush(url, port);
  }

  @Override public Observable<BizObject> recvPush() {
    return tcpDataStore.recvPush();
  }

  @Override public Observable<Void> sendPush(byte[] bytes) {
    return tcpDataStore.sendPush(bytes);
  }

  @Override public Observable<Void> updateLocation(int flag) {
    return tcpDataStore.updateLocation(flag);
  }

  @Override public Observable<StriveStatus> grabOrder(String orderId) {
    return tcpDataStore.grabOrder(orderId);
  }

  @Override public Observable<NetBiz> completeOrder(String orderId) {
    return tcpDataStore.completeOrder(orderId);
  }

  @Override public Observable<NetBiz> cancelOrder(String orderId) {
    return tcpDataStore.cancelOrder(orderId);
  }

  @Override public Observable<List<Order>> orders() {
    return sqLiteDataStore.orders();
  }

    public Order findOrder(int oid) {
    return sqLiteDataStore.findOrder(oid);
  }

  @Override public Observable<Uri> insertOrder(Order order) {
    return sqLiteDataStore.insertOrder(order);
  }

  @Override public Observable<Integer> updateOrder(Order order) {
    return sqLiteDataStore.updateOrder(order);
  }

  @Override public Observable<Integer> updateOrder(String oid,int status) {
    return sqLiteDataStore.updateOrder(oid,status);
  }

  @Override public Observable<Integer> deleteOrder(String oid) {
    return sqLiteDataStore.deleteOrder(oid);
  }

  @Override public Observable<HttpResult<List<CallRecord>>> unfinishOrder(String userName,String currTag) {
    return restApiDataStore.getUnfinishOrder(userName,currTag);
  }

  @Override
  public Observable<HttpResult<List<CallRecord>>> finishOrder(String useName, String currPage) {
    return restApiDataStore.getFinishOrder(useName,currPage);
  }

  /**
   * return a {@link CloudDataStore} from a user id.
   */
  private CloudDataStore getDataStore(int userId) {
    //判断是否缓存，没有缓存则网络获取数据
    if (!this.dataCache.isExpired() && this.dataCache.isCached(userId)) {
      return (CloudDataStore) dataCache;
    } else {
      return restApiDataStore;
    }
  }
}
