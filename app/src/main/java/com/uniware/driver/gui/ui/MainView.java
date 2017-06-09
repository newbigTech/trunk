package com.uniware.driver.gui.ui;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import com.uniware.driver.R;
import com.uniware.driver.gui.fragment.AnnounceFragment;
import com.uniware.driver.gui.fragment.DriverFragment;
import com.uniware.driver.gui.fragment.GridFragment;
import com.uniware.driver.gui.fragment.MainFragment;

/**
 * Created by jian on 15/11/13.
 */
public class MainView extends RelativeLayout {
  private OnClickListener appListener = new OnClickListener() {
    public void onClick(View view) {
      MainView.this.mViewPager.setCurrentItem(2);
    }
  };
  private OnClickListener backListener = new OnClickListener() {
    public void onClick(View view) {
      MainView.this.mViewPager.setCurrentItem(1);
    }
  };
  private MainViewPagerAdapter mPageAdapter;
  private ViewPager.OnPageChangeListener mPageChangeListener =
      new ViewPager.OnPageChangeListener() {
        @Override public void onPageSelected(int position) {
          mMainTitleView.switchTitle(position);
          switch (position) {
            case 0:
              //MainView.this.mTitleView.setDriverTitle(MainView.this.backListener);
              break;
            case 1:
              //MainView.this.mTitleView.setTitle(MainView.this.myListener, MainView.this.appListener);
              break;
            case 2:
              //MainView.this.mTitleView.setGridTitle(MainView.this.backListener);
          }

          ((MainFragment) MainView.this.mPageAdapter.getItem(position)).onPageSelected();
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override public void onPageScrollStateChanged(int state) {

        }
      };
  //private TitleView mTitleView;
  private MainTitleView mMainTitleView;
  private ViewPager mViewPager;
  private OnClickListener myListener = new OnClickListener() {
    public void onClick(View view) {
      MainView.this.mViewPager.setCurrentItem(0);
    }
  };

  public MainView(Context context) {
    super(context);
    this.initView();
  }

  public MainView(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.initView();
  }

  public MainView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.initView();
  }

  private void initView() {
    inflate(this.getContext(), R.layout.main_view_layout, this);
    this.mMainTitleView = (MainTitleView) this.findViewById(R.id.main_title_view);
    TitleView title1 = new TitleView(getContext());
    title1.setDriverTitle(MainView.this.backListener);
    TitleView title2 = new TitleView(getContext());
    title2.setTitle(MainView.this.myListener, MainView.this.appListener);
    TitleView title3 = new TitleView(getContext());
    title3.setGridTitle(MainView.this.backListener);
    mMainTitleView.addTitle(title1);
    mMainTitleView.addTitle(title2);
    mMainTitleView.addTitle(title3);
    mMainTitleView.switchTitle(1);
    this.mViewPager = (ViewPager) this.findViewById(R.id.main_pager);
  }

  public int getCurrentItem() {
    return this.mViewPager.getCurrentItem();
  }

  public void setFragmentManager(FragmentManager fragmentManager) {
    this.mPageAdapter = new MainViewPagerAdapter(fragmentManager);
    this.mViewPager.setAdapter(this.mPageAdapter);
    this.mViewPager.setCurrentItem(1);
    //this.mTitleView.setTitle(this.myListener, this.appListener);
    this.mViewPager.addOnPageChangeListener(this.mPageChangeListener);
  }

  public void switchToDriverInfo() {
    if (this.mViewPager != null) {
      this.mViewPager.setCurrentItem(0);
    }
  }

  public DriverFragment getDriverFragment() {
    return (DriverFragment) mPageAdapter.getItem(0);
  }

  public AnnounceFragment getAnnounceFragment() {
    return (AnnounceFragment) mPageAdapter.getItem(1);
  }

  public GridFragment getGridFragment() {
    return (GridFragment) mPageAdapter.getItem(2);
  }
}