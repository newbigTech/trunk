package com.uniware.driver.gui.ui;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jian on 15/11/16.
 */
public class MainTitleView extends ViewPager {

  private List<View> mList;

  public MainTitleView(Context context) {
    super(context);
    initView();
  }

  public MainTitleView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView();
  }

  private void initView() {
    mList = new ArrayList<>();
    setAdapter(new TitleViewAdapter());
  }

  @Override public boolean onTouchEvent(MotionEvent ev) {
    return true;
  }

  public void addTitle(View titleView){
    mList.add(titleView);
    getAdapter().notifyDataSetChanged();
  }

  public void addTitle(int resId){
    LayoutInflater factory = LayoutInflater.from(getContext());
    View titleView = factory.inflate(resId, null);
    mList.add(titleView);
    getAdapter().notifyDataSetChanged();
  }

  public void switchTitle(int id){
    setCurrentItem(id);
  }

  class TitleViewAdapter extends PagerAdapter {

    @Override public int getCount() {
      return mList.size();
    }

    @Override public boolean isViewFromObject(View view, Object object) {
      return view == object;
    }

    @Override public Object instantiateItem(ViewGroup container, int position) {
      View view = mList.get(position);
      container.addView(view);
      return view;
    }

    @Override public void destroyItem(ViewGroup container, int position, Object object) {
      View view = mList.get(position);
      container.removeView(view);
    }
  }
}
