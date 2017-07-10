package com.uniware.driver.domain;

import java.io.Serializable;

/**
 * Created by jian on 16/03/24.
 */
public class BizObject implements Serializable, Cloneable {

  @Override public BizObject clone() {
    try {
      BizObject object = (BizObject) super.clone();
      return object;
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
      return null;
    }
  }
}
