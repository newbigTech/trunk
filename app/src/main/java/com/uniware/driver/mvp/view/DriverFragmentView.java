package com.uniware.driver.mvp.view;

import com.uniware.driver.domain.Order;
import java.util.List;

/**
 * Created by jian on 15/12/07.
 */
public interface DriverFragmentView {
  void loadOrders(List<Order> orders);

  void viewOrder(Order order);

  void deleteOrder(int oid);
}
