package com.uniware.driver.gui.fragment.order;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.uniware.driver.R;

/**
 * Created by SJ on 15/11/14.
 */
public class OrderTitleView extends RelativeLayout {
  private int blinkCount = 0;
  private boolean blinkStoped = true;
  private View mContentView;
  private ImageView mImgTripType;
  private View mLayoutInvalid;
  private View mLayoutValid;
  private TextView mTxtInvalidText;
  private TextView mTxtInvalidTitle;
  private TextView mTxtValidText;
  private TextView mTxtValidTitle;

  public OrderTitleView(Context context) {
    super(context);
    this.initViews();
  }

  public OrderTitleView(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.initViews();
  }

  public OrderTitleView(Context context, AttributeSet attrs, int def) {
    super(context, attrs, def);
    this.initViews();
  }

  private void initViews() {
    inflate(getContext(), R.layout.main_order_fragment_title_layout, this);
    this.mContentView = this.findViewById(R.id.rlt_content);
    this.mImgTripType = (ImageView) this.findViewById(R.id.img_trip_type);
    this.mTxtValidTitle = (TextView) this.findViewById(R.id.txt_valid_title);
    this.mTxtValidText = (TextView) this.findViewById(R.id.txt_valid_text);
    this.mLayoutValid = this.findViewById(R.id.layout_order_valid);
    this.mLayoutInvalid = this.findViewById(R.id.layout_order_invalid);
    this.mTxtInvalidTitle = (TextView) this.findViewById(R.id.txt_invalid_title);
    this.mTxtInvalidText = (TextView) this.findViewById(R.id.txt_invalid_text);
  }

  public void setOrderTitle(int type, String title, int tip){
    setBgByOrderType(type);
    mTxtValidTitle.setText(title);
    if (tip==0){
      mTxtInvalidText.setVisibility(View.VISIBLE);
    }
    else {
      mTxtInvalidText.setVisibility(View.INVISIBLE);
    }
    //mTxtValidText.setText(tip+"");
    //if(!tip.equals("")&&!tip.equals("无")){
    //  mTxtInvalidText.setVisibility(View.VISIBLE);
    //}
  }

  private void setBgByOrderType(int type) {
    int defType = R.drawable.order_title_bkg;
    switch (type) {
      case 0:
        this.mLayoutValid.setVisibility(VISIBLE);
        this.mLayoutInvalid.setVisibility(GONE);
        break;
      case 1:
        this.mLayoutValid.setVisibility(VISIBLE);
        this.mLayoutInvalid.setVisibility(GONE);
        defType = R.drawable.order_preorder_title_bkg;
        break;
      case 2:
        this.mLayoutValid.setVisibility(GONE);
        this.mLayoutInvalid.setVisibility(VISIBLE);
        defType = R.drawable.order_invaild_cover;
        this.setInvalidTextColor(this.getContext()
            .getResources()
            .getColor(R.color.txt_color_main_order_fragment_grabbed_by_others_red));
        break;
      default:
        break;
    }
    setTagByTripType(type);
    this.mContentView.setBackgroundResource(defType);
  }

  private void setInvalidTextColor(int color) {
    this.mTxtInvalidTitle.setTextColor(color);
    this.mTxtInvalidText.setTextColor(color);
  }

  private void setTagByTripType(int type) {
    switch (type) {
      case 0:
        this.mImgTripType.setBackgroundResource(R.drawable.order_fragment_type_instant);
        break;
      case 1:
        this.mImgTripType.setBackgroundResource(R.drawable.order_fragment_type_preorder);
        break;
      default:
        break;
    }
  }
}