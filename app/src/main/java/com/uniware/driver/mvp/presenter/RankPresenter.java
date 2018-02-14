package com.uniware.driver.mvp.presenter;

import com.uniware.driver.config.LoginConfig;
import com.uniware.driver.domain.RankResult;
import com.uniware.driver.domain.interactor.DefaultSubscriber;
import com.uniware.driver.domain.interactor.Rank;
import com.uniware.driver.domain.interactor.UseCase;
import com.uniware.driver.mvp.view.RankResultView;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by ayue on 2017/12/1.
 */

public class RankPresenter implements Presenter {

  private final UseCase rank;
  private RankResultView view;
  @Inject
  public RankPresenter(@Named("rank")UseCase rank){
    this.rank = rank;
  }
  public void setView(RankResultView view){
    this.view=view;
  }

  public void rankSearch(int type){
    ((Rank)rank).setType(type);
    ((Rank)rank).setTel(LoginConfig.getInstance().getUserName());
    rank.execute(new RankSearchSubscribe());
  }

  private final class RankSearchSubscribe extends DefaultSubscriber<RankResult>{
    @Override public void onNext(RankResult rankResult) {
      super.onNext(rankResult);
      view.inSuccess(rankResult);
    }

    @Override public void onError(Throwable e) {
      super.onError(e);
      view.inFailure(e.getMessage());
    }

    @Override public void onCompleted() {
      super.onCompleted();
    }
  }

  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void destroy() {

  }
}
