package com.uniware.driver.mvp.presenter;

import com.uniware.driver.mvp.view.GridFragmentView;
import javax.inject.Inject;

/**
 * Created by jian on 15/12/07.
 */
public class GridPresenter implements Presenter {

  private GridFragmentView gridFragmentView;

  @Inject public GridPresenter() {

  }

  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void destroy() {

  }

  public void setView(GridFragmentView gridFragmentView) {
    this.gridFragmentView = gridFragmentView;
  }
}
