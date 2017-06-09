package com.uniware.driver.gui.fragment.order;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.uniware.driver.R;
import com.uniware.driver.gui.ui.CircleImageView;

/**
 * Created by SJ on 15/11/14.
 */
public class OrderStrivedDetailView extends RelativeLayout {
  private CircleImageView mCivHisPhoto;
  private CircleImageView mCivMyPhoto;
  private ImageView mIvTotalDivBg;
  private TextView mTvDistance;
  private TextView mTvHisDistance;
  private TextView mTvHisName;
  private TextView mTvHisScore;
  private TextView mTvHisTotalResult;
  private TextView mTvMyDistance;
  private TextView mTvMyScore;
  private TextView mTvMyTotalResult;
  private TextView mTvScore;
  private TextView mTvTipOff;
  private TextView mTvTitle;
  private TextView mTvTitleExplain;
  private TextView mTvTotalResult;

  public OrderStrivedDetailView(Context context) {
    super(context);
    this.initView();
  }

  public OrderStrivedDetailView(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.initView();
  }

  public OrderStrivedDetailView(Context context, AttributeSet attrs, int def) {
    super(context, attrs, def);
    this.initView();
  }

  private void initView() {
    inflate(this.getContext(), R.layout.main_order_fragment_grabbed_by_others_detailed_layout,
        this);
    this.mTvTitle = (TextView) this.findViewById(R.id.tv_title);
    this.mTvTitleExplain = (TextView) this.findViewById(R.id.tv_title_explain);
    this.mTvDistance = (TextView) this.findViewById(R.id.tv_distance);
    this.mTvScore = (TextView) this.findViewById(R.id.tv_score);
    this.mIvTotalDivBg = (ImageView) this.findViewById(R.id.iv_total_div_bg);
    this.mTvTotalResult = (TextView) this.findViewById(R.id.tv_total_result);
    this.mCivMyPhoto = (CircleImageView) this.findViewById(R.id.civ_my_photo);
    this.mTvMyDistance = (TextView) this.findViewById(R.id.tv_my_distance);
    this.mTvMyScore = (TextView) this.findViewById(R.id.tv_my_score);
    this.mTvMyTotalResult = (TextView) this.findViewById(R.id.tv_my_total_result);
    this.mCivHisPhoto = (CircleImageView) this.findViewById(R.id.civ_his_photo);
    this.mTvHisName = (TextView) this.findViewById(R.id.tv_his_name);
    this.mTvHisDistance = (TextView) this.findViewById(R.id.tv_his_distance);
    this.mTvHisScore = (TextView) this.findViewById(R.id.tv_his_score);
    this.mTvHisTotalResult = (TextView) this.findViewById(R.id.tv_his_total_result);
    this.mTvTipOff = (TextView) this.findViewById(R.id.tv_detailed_tip_off);
  }

  public TextView getTipOffButton() {
    return this.mTvTipOff;
  }

  public void setOnTipOffButtonListener(OnClickListener var1) {
    this.mTvTipOff.setOnClickListener(var1);
  }
}