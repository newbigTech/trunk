package com.uniware.driver.data.repository.datasource;

import com.uniware.driver.domain.NetBiz;
import com.uniware.driver.domain.StriveStatus;
import rx.Observable;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public interface OrderDataStore {

  Observable<StriveStatus> grabOrder(String orderId);

  Observable<NetBiz> completeOrder(String orderId);

  Observable<NetBiz> cancelOrder(String orderId);
}
