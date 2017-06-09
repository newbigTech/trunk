package com.uniware.driver.mvp.view;

import com.uniware.driver.domain.CallRecord;
import com.uniware.driver.domain.HttpResult;
import java.util.List;

/**
 * Created by ayue on 2017/5/24.
 */

public interface RefreshView {

  void reFreshOrder(HttpResult<List<CallRecord>> listHttpResult);

  void loadMore(HttpResult<List<CallRecord>> listHttpResult);
}
