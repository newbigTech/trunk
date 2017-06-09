package com.uniware.driver.mvp.view;

import com.uniware.driver.domain.Order;

/**
 * Created by SJ on 15/11/23.
 */
public interface OrderFragmentView {
  void grabOrder();

  void grabSuccess();

  void grabFailure();

  Order getCurrentOrder();
}
