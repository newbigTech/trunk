package com.uniware.driver.gui.fragment.order;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.uniware.driver.R;

/**
 * Created by SJ on 15/11/14.
 */
public class CommonOrderAddressView extends RelativeLayout {
  private RelativeLayout mDestinationVoicePlayRelativeLayout;
  private TextView mDestinationVoicePlayTimeLengthTextView;
  private ViewFlipper mDestinationVoicePlayViewFlipper;
  private ImageView mEndIcon;
  private ImageView mImgAddrFrom;
  private ImageView mImgAddrTo;
  private RelativeLayout mLayoutFrom;
  private RelativeLayout mLayoutTo;
  private int mTaskId = -1;
  private TextView mTxtExtInfo;
  private TextView mTxtNameFrom;
  private TextView mTxtNameTo;

  public CommonOrderAddressView(Context context) {
    super(context);
    this.initView();
  }

  public CommonOrderAddressView(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.initView();
    this.mDestinationVoicePlayViewFlipper.stopFlipping();
    this.mDestinationVoicePlayViewFlipper.setDisplayedChild(2);
    //this.mDestinationVoicePlayRelativeLayout.setOnClickListener(this.playListener);
  }

  public CommonOrderAddressView(Context context, AttributeSet attrs, int def) {
    super(context, attrs, def);
    this.initView();
  }

  private void initView() {
    inflate(this.getContext(), R.layout.main_order_fragment_address_layout, this);
    this.mTxtNameFrom = (TextView) this.findViewById(R.id.txt_name_from);
    this.mTxtNameTo = (TextView) this.findViewById(R.id.txt_name_to);
    this.mEndIcon = (ImageView) this.findViewById(R.id.img_address_to);
    this.mTxtExtInfo = (TextView) this.findViewById(R.id.txt_extinfo);
    this.mLayoutFrom = (RelativeLayout) this.findViewById(R.id.llt_from);
    this.mLayoutTo = (RelativeLayout) this.findViewById(R.id.llt_to);
    this.mImgAddrFrom = (ImageView) this.findViewById(R.id.img_address_from);
    this.mImgAddrTo = (ImageView) this.findViewById(R.id.img_address_to);
    this.mDestinationVoicePlayRelativeLayout =
        (RelativeLayout) this.findViewById(R.id.rl_destination_voice_play);
    this.mDestinationVoicePlayViewFlipper =
        (ViewFlipper) this.findViewById(R.id.viewflipper_destination_voice_play);
    this.mDestinationVoicePlayTimeLengthTextView =
        (TextView) this.findViewById(R.id.tv_voice_time_length);
  }

  public void setAddress(String fromAddr, String toAddr, String extInfo){
    mTxtNameFrom.setText(fromAddr);
    mTxtNameTo.setText(toAddr);
    mTxtExtInfo.setText(extInfo);
    if (!extInfo.equals("")&&!extInfo.trim().equals("无")){
      mTxtExtInfo.setVisibility(View.VISIBLE);
    }
    else {
      mTxtExtInfo.setVisibility(View.GONE);
    }
  }

  public void setNightMode(boolean nightMode) {
    Drawable var2;
    Drawable var3;
    if (nightMode) {
      this.mTxtNameFrom.setTextColor(this.getResources().getColor(R.color.text_color_dark_gray));
      this.mTxtNameTo.setTextColor(this.getResources().getColor(R.color.text_color_dark_gray));
      this.mTxtExtInfo.setTextColor(this.getResources().getColor(R.color.text_color_dark_gray));
      this.mImgAddrFrom.setImageResource(R.drawable.common_ic_address_from);
      this.mImgAddrTo.setImageResource(R.drawable.common_ic_address_to);
      this.mDestinationVoicePlayRelativeLayout.setBackgroundResource(R.drawable.common_bg_night);
      var2 = this.getResources().getDrawable(R.drawable.common_bg_night);
      var2.setBounds(0, 0, var2.getIntrinsicWidth(), var2.getIntrinsicHeight());
      var3 = this.getResources().getDrawable(R.drawable.common_bg_night);
      var3.setBounds(0, 0, var3.getIntrinsicWidth(), var3.getIntrinsicHeight());
      this.mTxtExtInfo.setCompoundDrawables(var2, (Drawable) null, var3, (Drawable) null);
      this.getChildAt(0).setBackgroundResource(R.drawable.common_bg_night);
    } else {
      this.mTxtNameFrom.setTextColor(this.getResources().getColor(R.color.text_color_white));
      this.mTxtNameTo.setTextColor(this.getResources().getColor(R.color.text_color_white));
      this.mTxtExtInfo.setTextColor(this.getResources().getColor(R.color.text_color_white));
      this.mImgAddrFrom.setImageResource(R.drawable.common_ic_address_from);
      this.mImgAddrTo.setImageResource(R.drawable.common_ic_address_to);
      this.mDestinationVoicePlayRelativeLayout.setBackgroundResource(R.drawable.common_bg_noon);
      var2 = this.getResources().getDrawable(R.drawable.common_bg_noon);
      var2.setBounds(0, 0, var2.getIntrinsicWidth(), var2.getIntrinsicHeight());
      var3 = this.getResources().getDrawable(R.drawable.common_bg_noon);
      var3.setBounds(0, 0, var3.getIntrinsicWidth(), var3.getIntrinsicHeight());
      this.mTxtExtInfo.setCompoundDrawables(var2, (Drawable) null, var3, (Drawable) null);
      this.getChildAt(0).setBackgroundResource(R.drawable.common_bg_noon);
    }
  }

  public void startVoiceFlip() {
    this.mDestinationVoicePlayViewFlipper.startFlipping();
  }

  public void stopPlay() {

  }

  public void stopVoiceFlip() {
    this.mDestinationVoicePlayViewFlipper.stopFlipping();
    this.mDestinationVoicePlayViewFlipper.setDisplayedChild(2);
  }
}