package com.uniware.driver.mvp.view;

import android.content.Context;
import com.uniware.driver.domain.RankResult;

/**
 * Created by ayue on 2017/12/2.
 */

public interface RankResultView {
  Context getContext();

  void inSuccess(RankResult result,int type);

  void inFailure(String reason,int type);
}
