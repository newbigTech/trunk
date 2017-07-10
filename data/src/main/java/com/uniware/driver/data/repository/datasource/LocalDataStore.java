package com.uniware.driver.data.repository.datasource;

import android.net.Uri;
import com.uniware.driver.domain.Order;
import java.util.List;
import rx.Observable;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public interface LocalDataStore {
  /**
   * Get an {@link Observable} which will emit a List of {@link Order}.
   */
  Observable<List<Order>> orders();

  /**
   * Get an {@link Observable} which will emit a {@link Order} by its id.
   *
   * @param oid The id to retrieve order data.
   */
  //Observable<Order> findOrder(int oid);

  Observable<Uri> insertOrder(Order order);

  Observable<Integer> updateOrder(Order order);

  Observable<Integer> deleteOrder(String oid);

  Observable<Integer> updateOrder(String oid,int status);

  Observable<Integer> updateUnfinishOrder(List<Order> orderList);
}
