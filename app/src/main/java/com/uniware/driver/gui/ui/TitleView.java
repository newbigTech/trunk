package com.uniware.driver.gui.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.uniware.driver.R;
import com.uniware.driver.util.InPutTools;

/**
 * Created by SJ on 15/11/11.
 */
public class TitleView extends RelativeLayout {
  private TextView mTitleName;
  private ImageView mTitleIc;
  private RelativeLayout mTitleLayoutBack;
  private RelativeLayout mTitleLayoutEndOrder;
  private RelativeLayout mTitleLayoutLeft;
  private RelativeLayout mTitleLayoutRight;
  private RelativeLayout mTitleLayoutRightBack;
  private RelativeLayout mTitleApplyLayout;
  private RelativeLayout mTitleLayoutRightDelBtn;
  private TextView mTitleIcLeftBack;
  private Animation mAnimationAlphaIn;
  private Animation mAnimationAlphaOut;
  private ImageView mTitleBottomLine;
  private OnClickListener backListener = new OnClickListener() {
    public void onClick(View view) {
      InPutTools.a((Activity) TitleView.this.getContext());
      ((Activity) TitleView.this.getContext()).finish();
    }
  };

  public TitleView(Context context) {
    super(context);
    initView();
  }

  public TitleView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView();
  }

  public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initView();
  }

  private void initView() {
    inflate(this.getContext(), R.layout.title_view, this);
    this.mTitleName = (TextView) findViewById(R.id.title_name);
    this.mTitleIc = (ImageView) findViewById(R.id.title_ic);
    this.mTitleLayoutBack = (RelativeLayout) findViewById(R.id.title_layout_back);
    this.mTitleLayoutEndOrder = (RelativeLayout) findViewById(R.id.title_layout_end_order);
    this.mTitleIcLeftBack = (TextView) findViewById(R.id.title_ic_left_back);
    this.mTitleLayoutLeft = (RelativeLayout) findViewById(R.id.title_layout_left);
    this.mTitleLayoutRight = (RelativeLayout) findViewById(R.id.title_layout_right);
    this.mTitleApplyLayout = (RelativeLayout) findViewById(R.id.title_apply_layout);
    this.mTitleLayoutRightBack = (RelativeLayout) findViewById(R.id.title_layout_right_back);
    this.mTitleLayoutRightDelBtn = (RelativeLayout) findViewById(R.id.title_layout_right_del_btn);
    this.mTitleBottomLine = (ImageView) findViewById(R.id.title_bottom_line);
    this.mAnimationAlphaIn = AnimationUtils.loadAnimation(this.getContext(), R.anim.alpha_in);
    this.mAnimationAlphaOut = AnimationUtils.loadAnimation(this.getContext(), R.anim.alpha_out);
  }

  public void setTitle(OnClickListener listener) {
    this.mTitleName.setVisibility(VISIBLE);
    this.mTitleName.setText(this.getContext().getString(R.string.login_title_name_txt));
    Typeface mtypeface=Typeface.createFromAsset(getContext().getAssets(),"youyuan.TTF");
    this.mTitleName.setTypeface(mtypeface);
    this.mTitleLayoutBack.setVisibility(GONE);
    this.mTitleLayoutEndOrder.setVisibility(VISIBLE);
    this.mTitleLayoutEndOrder.setOnClickListener(listener);
    this.mTitleLayoutLeft.setVisibility(GONE);
    this.mTitleLayoutRight.setVisibility(GONE);
    this.mTitleIc.setVisibility(GONE);
    this.mTitleLayoutRightBack.setVisibility(GONE);
    this.mTitleApplyLayout.setVisibility(GONE);
    this.mTitleLayoutRightDelBtn.setVisibility(GONE);
    this.mTitleBottomLine.setBackgroundColor(
        this.getContext().getResources().getColor(R.color.c_white_c0ffffff));
  }

  public void setTitle(String title) {
    this.mTitleName.setVisibility(VISIBLE);
    this.mTitleName.setText(title);
    this.mTitleLayoutBack.setVisibility(GONE);
    this.mTitleLayoutLeft.setVisibility(GONE);
    this.mTitleLayoutRight.setVisibility(GONE);
    this.mTitleLayoutEndOrder.setVisibility(GONE);
    this.mTitleIc.setVisibility(GONE);
    this.mTitleLayoutRightBack.setVisibility(GONE);
    this.mTitleApplyLayout.setVisibility(GONE);
    this.mTitleLayoutRightDelBtn.setVisibility(GONE);
    this.mTitleBottomLine.setBackgroundColor(
        this.getContext().getResources().getColor(R.color.c_white_c0ffffff));
  }

  public void setTitle(String titleName, OnClickListener listener) {
    this.mTitleName.setVisibility(VISIBLE);
    this.mTitleName.setText(titleName);
    this.mTitleLayoutBack.setVisibility(VISIBLE);
    if (listener == null) {
      this.mTitleLayoutBack.setOnClickListener(this.backListener);
    } else {
      this.mTitleLayoutBack.setOnClickListener(listener);
    }

    this.mTitleLayoutEndOrder.setVisibility(GONE);
    this.mTitleLayoutLeft.setVisibility(GONE);
    this.mTitleLayoutRight.setVisibility(GONE);
    this.mTitleIc.setVisibility(GONE);
    this.mTitleLayoutRightBack.setVisibility(GONE);
    this.mTitleApplyLayout.setVisibility(GONE);
    this.mTitleLayoutRightDelBtn.setVisibility(GONE);
    this.mTitleBottomLine.setBackgroundColor(
        this.getContext().getResources().getColor(R.color.c_white_c0ffffff));
  }

  public void setTitle(String title, OnClickListener listener, boolean isTrue) {
    this.mTitleName.setText(title);
    this.mTitleLayoutBack.setVisibility(GONE);
    this.mTitleLayoutEndOrder.setVisibility(GONE);
    this.mTitleLayoutLeft.setVisibility(GONE);
    this.mTitleLayoutRight.setVisibility(GONE);
    this.mTitleIc.setVisibility(GONE);
    this.mTitleLayoutRightBack.setVisibility(GONE);
    this.mTitleApplyLayout.setVisibility(GONE);
    this.mTitleLayoutRightDelBtn.setVisibility(VISIBLE);
    this.mTitleLayoutRightDelBtn.setOnClickListener(listener);
    if (isTrue) {
      this.mTitleBottomLine.setBackgroundColor(
          this.getContext().getResources().getColor(R.color.c_white_c0ffffff));
    } else {
      this.mTitleBottomLine.setBackgroundColor(
          this.getContext().getResources().getColor(R.color.c_orange_bf6c00));
    }
  }

  public void setTitle(String title, String back, OnClickListener listener) {
    this.mTitleName.setVisibility(VISIBLE);
    this.mTitleName.setText(title);
    this.mTitleLayoutBack.setVisibility(GONE);
    this.mTitleLayoutBack.setOnClickListener(null);
    this.mTitleLayoutEndOrder.setVisibility(VISIBLE);
    this.mTitleLayoutEndOrder.setOnClickListener(listener);
    this.mTitleIcLeftBack.setText(back);
    this.mTitleLayoutLeft.setVisibility(GONE);
    this.mTitleLayoutRight.setVisibility(GONE);
    this.mTitleIc.setVisibility(GONE);
    this.mTitleLayoutRightBack.setVisibility(GONE);
    this.mTitleApplyLayout.setVisibility(GONE);
    this.mTitleLayoutRightDelBtn.setVisibility(GONE);
    this.mTitleBottomLine.setBackgroundColor(
        this.getContext().getResources().getColor(R.color.c_white_c0ffffff));
  }

  public void setTitle(String titleName, OnClickListener backListener,
      OnClickListener endListener) {
    this.mTitleName.setVisibility(VISIBLE);
    this.mTitleName.setText(titleName);
    this.mTitleLayoutBack.setVisibility(VISIBLE);
    this.mTitleLayoutBack.setOnClickListener(backListener);
    this.mTitleLayoutEndOrder.setVisibility(GONE);
    this.mTitleLayoutEndOrder.setOnClickListener(endListener);
    this.mTitleLayoutLeft.setVisibility(GONE);
    this.mTitleLayoutRight.setVisibility(GONE);
    this.mTitleIc.setVisibility(GONE);
    this.mTitleLayoutRightBack.setVisibility(GONE);
    this.mTitleApplyLayout.setVisibility(GONE);
    this.mTitleLayoutRightDelBtn.setVisibility(GONE);
    this.mTitleBottomLine.setBackgroundColor(
        this.getContext().getResources().getColor(R.color.c_white_c0ffffff));
  }

  public void setTitle(OnClickListener leftListener, OnClickListener rightListener) {
    this.mTitleName.setVisibility(GONE);
    this.mTitleName.startAnimation(this.mAnimationAlphaOut);
    this.mTitleLayoutBack.setVisibility(GONE);
    this.mTitleLayoutBack.startAnimation(this.mAnimationAlphaOut);
    this.mTitleLayoutEndOrder.setVisibility(GONE);
    this.mTitleLayoutLeft.setVisibility(VISIBLE);
    this.mTitleLayoutLeft.startAnimation(this.mAnimationAlphaIn);
    this.mTitleLayoutLeft.setOnClickListener(leftListener);
    this.mTitleLayoutRight.setVisibility(VISIBLE);
    this.mTitleLayoutRight.startAnimation(this.mAnimationAlphaIn);
    this.mTitleLayoutRight.setOnClickListener(rightListener);
    this.mTitleIc.setVisibility(VISIBLE);
    this.mTitleIc.startAnimation(this.mAnimationAlphaIn);
    this.mTitleLayoutRightBack.setVisibility(GONE);
    this.mTitleLayoutRightBack.startAnimation(this.mAnimationAlphaOut);
    this.mTitleApplyLayout.setVisibility(GONE);
    this.mTitleLayoutRightDelBtn.setVisibility(GONE);
    this.mTitleBottomLine.setBackgroundColor(
        this.getContext().getResources().getColor(R.color.c_white_c0ffffff));
  }

  public void setDriverTitle(OnClickListener listener) {
    this.mTitleName.setVisibility(VISIBLE);
    this.mTitleName.setText(this.getContext().getString(R.string.title_my_order_txt));
    this.mTitleName.startAnimation(this.mAnimationAlphaIn);
    this.mTitleLayoutBack.setVisibility(GONE);
    this.mTitleLayoutEndOrder.setVisibility(GONE);
    this.mTitleLayoutLeft.setVisibility(GONE);
    this.mTitleLayoutLeft.startAnimation(this.mAnimationAlphaOut);
    this.mTitleLayoutRight.setVisibility(GONE);
    this.mTitleLayoutRight.startAnimation(this.mAnimationAlphaOut);
    this.mTitleIc.setVisibility(GONE);
    this.mTitleIc.startAnimation(this.mAnimationAlphaOut);
    this.mTitleLayoutRightBack.setVisibility(VISIBLE);
    this.mTitleLayoutRightBack.startAnimation(this.mAnimationAlphaIn);
    this.mTitleLayoutRightBack.setOnClickListener(listener);
    this.mTitleApplyLayout.setVisibility(GONE);
    this.mTitleLayoutRightDelBtn.setVisibility(GONE);
    this.mTitleBottomLine.setBackgroundColor(
        this.getContext().getResources().getColor(R.color.c_white_c0ffffff));
  }

  public void setGridTitle(OnClickListener listener) {
    this.mTitleName.setVisibility(VISIBLE);
    this.mTitleName.setText(this.getContext().getString(R.string.title_more_txt));
    this.mTitleName.startAnimation(this.mAnimationAlphaIn);
    this.mTitleApplyLayout.setVisibility(VISIBLE);
    this.mTitleApplyLayout.startAnimation(this.mAnimationAlphaIn);
    this.mTitleApplyLayout.setOnClickListener(listener);
    this.mTitleLayoutEndOrder.setVisibility(GONE);
    this.mTitleLayoutLeft.setVisibility(GONE);
    this.mTitleLayoutLeft.startAnimation(this.mAnimationAlphaOut);
    this.mTitleLayoutRight.setVisibility(GONE);
    this.mTitleLayoutRight.startAnimation(this.mAnimationAlphaOut);
    this.mTitleIc.setVisibility(GONE);
    this.mTitleIc.startAnimation(this.mAnimationAlphaOut);
    this.mTitleLayoutRightBack.setVisibility(GONE);
    this.mTitleBottomLine.setBackgroundColor(
        this.getContext().getResources().getColor(R.color.c_white_c0ffffff));
  }

  public void setTitleName(String name) {
    this.mTitleName.setText(name);
  }
}
