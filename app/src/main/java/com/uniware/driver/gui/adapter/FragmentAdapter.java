package com.uniware.driver.gui.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import com.uniware.driver.gui.fragment.FragmentPagerAdapter;
import java.util.List;

/**
 * Created by ayue on 2018/3/5.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
  private List<Fragment> mFragments;
  private List<String> titleList;

  public FragmentAdapter(FragmentManager fm,List<Fragment> fragments,List<String> titleList) {
    super(fm);
    // TODO Auto-generated constructor stub
    mFragments=fragments;
    this.titleList=titleList;
  }


  @Override public CharSequence getPageTitle(int position) {
    return titleList.get(position);
  }

  @Override
  public Fragment getItem(int arg0) {
    // TODO Auto-generated method stub
    return mFragments.get(arg0);
  }

  @Override
  public int getCount() {
    // TODO Auto-generated method stub
    return mFragments.size();
  }
}
