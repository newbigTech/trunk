package com.uniware.driver.gui.fragment.order;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.uniware.driver.R;

/**
 * Created by SJ on 15/11/14.
 */
public class CarpoolOrderAddressView extends LinearLayout {
  private ImageView mIVFrom1;
  private ImageView mIVFrom2;
  private ImageView mIVTo1;
  private ImageView mIVTo2;
  private ImageView mIvExtraInfo1;
  private ImageView mIvExtraInfo2;
  private LinearLayout mLltFrom1;
  private LinearLayout mLltFrom2;
  private LinearLayout mLltPartner1ExtraInfo;
  private LinearLayout mLltPartner2ExtraInfo;
  private LinearLayout mLltTo1;
  private LinearLayout mLltTo2;
  private TextView mTvExtraInfo1;
  private TextView mTvExtraInfo2;
  private TextView mTvFrom1;
  private TextView mTvFrom2;
  private TextView mTvTo1;
  private TextView mTvTo2;

  public CarpoolOrderAddressView(Context context) {
    super(context);
    this.initView();
  }

  public CarpoolOrderAddressView(Context var1, AttributeSet attrs) {
    super(var1, attrs);
    this.initView();
  }

  private void initView() {
    inflate(this.getContext(), R.layout.carpool_order_address_view, this);
    this.mLltFrom1 = (LinearLayout)findViewById(R.id.llt_from_1);
    this.mTvFrom1 = (TextView)findViewById(R.id.tv_from_1);
    this.mIVFrom1 = (ImageView)findViewById(R.id.iv_from_1);
    this.mLltFrom2 = (LinearLayout)findViewById(R.id.llt_from_2);
    this.mTvFrom2 = (TextView)findViewById(R.id.tv_from_2);
    this.mIVFrom2 = (ImageView)findViewById(R.id.iv_from_2);
    this.mLltTo1 = (LinearLayout)findViewById(R.id.llt_to_1);
    this.mTvTo1 = (TextView)findViewById(R.id.tv_to_1);
    this.mIVTo1 = (ImageView)findViewById(R.id.iv_to_1);
    this.mLltTo2 = (LinearLayout)findViewById(R.id.llt_to_2);
    this.mTvTo2 = (TextView)findViewById(R.id.tv_to_2);
    this.mIVTo2 = (ImageView)findViewById(R.id.iv_to_2);
    this.mLltPartner1ExtraInfo = (LinearLayout)findViewById(R.id.llt_partner_1_extra_info);
    this.mIvExtraInfo1 = (ImageView)findViewById(R.id.iv_partner_1_extra_info_icon);
    this.mTvExtraInfo1 = (TextView)findViewById(R.id.tv_partner_1_extra_info);
    this.mLltPartner2ExtraInfo = (LinearLayout)findViewById(R.id.llt_partner_2_extra_info);
    this.mIvExtraInfo2 = (ImageView)findViewById(R.id.iv_partner_2_extra_info_icon);
    this.mTvExtraInfo2 = (TextView)findViewById(R.id.tv_partner_2_extra_info);
  }

  public void setNightMode(boolean var1) {
    int var2;
    if(var1) {
      var2 = this.getResources().getColor(R.color.text_color_dark_gray);
      this.mTvFrom1.setTextColor(var2);
      this.mTvFrom2.setTextColor(var2);
      this.mTvTo1.setTextColor(var2);
      this.mTvTo2.setTextColor(var2);
      var2 = this.getResources().getColor(R.color.text_color_light_gray);
      this.mTvExtraInfo1.setTextColor(var2);
      this.mTvExtraInfo2.setTextColor(var2);
      this.mIVFrom1.setBackgroundResource(R.drawable.go_pick_navigate_from_ic);
      this.mIVTo2.setBackgroundResource(R.drawable.go_pick_navigate_to_ic);
/*      this.mIvExtraInfo1.setBackgroundResource(2130837778);
      this.mIvExtraInfo2.setBackgroundResource(2130837780);
      this.getChildAt(0).setBackgroundResource(2130837913);*/
    } else {
/*      var2 = this.getResources().getColor(2131099726);
      this.mTvFrom1.setTextColor(var2);
      this.mTvFrom2.setTextColor(var2);
      this.mTvTo1.setTextColor(var2);
      this.mTvTo2.setTextColor(var2);
      var2 = this.getResources().getColor(2131099727);
      this.mTvExtraInfo1.setTextColor(var2);
      this.mTvExtraInfo2.setTextColor(var2);
      this.mIVFrom1.setBackgroundResource(2130837889);
      this.mIVTo2.setBackgroundResource(2130837890);
      this.mIvExtraInfo1.setBackgroundResource(2130837777);
      this.mIvExtraInfo2.setBackgroundResource(2130837779);
      this.getChildAt(0).setBackgroundResource(2130837912);*/
    }
  }
}