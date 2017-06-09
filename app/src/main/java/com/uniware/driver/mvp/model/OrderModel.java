package com.uniware.driver.mvp.model;

import com.uniware.driver.domain.Order;

/**
 * Class that represents a user in the presentation layer.
 */
public class OrderModel {

  private final Order mOrder;

  public OrderModel(Order order) {
    mOrder = order;
  }

  public Order getOrder() {
    return mOrder;
  }

  @Override public String toString() {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("***** Order Model Details *****\n");
    stringBuilder.append("order=" + this.mOrder + "\n");
    stringBuilder.append("*******************************");

    return stringBuilder.toString();
  }
}
