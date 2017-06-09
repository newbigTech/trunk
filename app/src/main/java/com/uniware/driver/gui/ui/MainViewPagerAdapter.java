package com.uniware.driver.gui.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.view.ViewGroup;
import com.uniware.driver.gui.fragment.AnnounceFragment;
import com.uniware.driver.gui.fragment.DriverFragment;
import com.uniware.driver.gui.fragment.FragmentPagerAdapter;
import com.uniware.driver.gui.fragment.GridFragment;

/**
 * Created by SJ on 15/11/14.
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {
  private static final int PAGER_COUNT = 3;
  private AnnounceFragment mAnnounceFragment = new AnnounceFragment();
  private int mCount = 3;
  private DriverFragment mDriverFragment = new DriverFragment();
  private GridFragment mGridFragment = new GridFragment();

  public MainViewPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  public void destroy() {
    this.mCount = 0;
    this.notifyDataSetChanged();
    if (this.mDriverFragment != null) {
      this.mDriverFragment.onDestroy();
    }

    if (this.mAnnounceFragment != null) {
      this.mAnnounceFragment.onDestroy();
    }

    if (this.mGridFragment != null) {
      this.mGridFragment.onDestroy();
    }
  }

  public void destroyItem(ViewGroup var1, int var2, Object var3) {
    super.destroyItem(var1, var2, var3);
  }

  public int getCount() {
    return this.mCount;
  }

  public Fragment getItem(int position) {
    switch (position) {
      case 0:
        return this.mDriverFragment;
      case 1:
        return this.mAnnounceFragment;
      case 2:
        return this.mGridFragment;
      default:
        return null;
    }
  }
}