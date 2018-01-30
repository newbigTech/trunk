package com.uniware.driver.mvp.view;

import com.uniware.driver.domain.NoticeResult;

/**
 * Created by ayue on 2018/1/29.
 */

public interface NoticeView {

  void reFreshOrder(NoticeResult listHttpResult);

  void loadMore(NoticeResult listHttpResult);
}
