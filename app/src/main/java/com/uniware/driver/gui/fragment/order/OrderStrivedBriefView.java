package com.uniware.driver.gui.fragment.order;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.uniware.driver.R;
import com.uniware.driver.gui.ui.CircleImageView;

/**
 * Created by SJ on 15/11/14.
 */
public class OrderStrivedBriefView extends RelativeLayout {
  private CircleImageView mCivHisPhoto;
  private CircleImageView mCivMyPhoto;
  private TextView mTvHisComment;
  private TextView mTvHisName;
  private TextView mTvHisTotalResult;
  private TextView mTvMyComment;
  private TextView mTvMyTotalResult;
  private TextView mTvTipOff;
  private TextView mTvTitle;
  private TextView mTvTitleExplain;
  private TextView mTvTotalResult;

  public OrderStrivedBriefView(Context var1) {
    super(var1);
    this.initView();
  }

  public OrderStrivedBriefView(Context var1, AttributeSet var2) {
    super(var1, var2);
    this.initView();
  }

  public OrderStrivedBriefView(Context var1, AttributeSet var2, int var3) {
    super(var1, var2, var3);
    this.initView();
  }

  private void initView() {
    inflate(this.getContext(), R.layout.main_order_fragment_grabbed_by_others_brief_layout, this);
    this.mTvTitle = (TextView)this.findViewById(R.id.tv_title);
    this.mTvTitleExplain = (TextView)this.findViewById(R.id.tv_title_explain);
    this.mTvTotalResult = (TextView)this.findViewById(R.id.tv_total_result);
    this.mCivMyPhoto = (CircleImageView)this.findViewById(R.id.civ_my_photo);
    this.mTvMyComment = (TextView)this.findViewById(R.id.tv_my_comment);
    this.mTvMyTotalResult = (TextView)this.findViewById(R.id.tv_my_total_result);
    this.mCivHisPhoto = (CircleImageView)this.findViewById(R.id.civ_his_photo);
    this.mTvHisName = (TextView)this.findViewById(R.id.tv_his_name);
    this.mTvHisComment = (TextView)this.findViewById(R.id.tv_his_comment);
    this.mTvHisTotalResult = (TextView)this.findViewById(R.id.tv_his_total_result);
    this.mTvTipOff = (TextView)this.findViewById(R.id.tv_strived_tip_off);
  }

  public TextView getTipOffButton() {
    return this.mTvTipOff;
  }

  public void setOnTipOffButtonListener(OnClickListener var1) {
    this.mTvTipOff.setOnClickListener(var1);
  }
}