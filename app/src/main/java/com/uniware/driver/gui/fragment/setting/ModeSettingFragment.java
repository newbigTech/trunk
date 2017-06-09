package com.uniware.driver.gui.fragment.setting;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.uniware.driver.R;
import com.uniware.driver.config.LoginConfig;
import com.uniware.driver.util.Tools;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModeSettingFragment extends DialogFragment implements View.OnClickListener {

  private TextView resetText;
  private TextView btn1text1;
  private TextView btn1text2;
  private TextView btn2text1;
  private TextView btn2text2;
  private TextView btn2text3;
  private TextView btn2text4;
  private Button btn_commit;
  private TextView text_one_km;
  private TextView text_two_km;
  private TextView text_three_km;
  private TextView text_four_km;
  private LinearLayout one_km_layout;
  private LinearLayout two_km_layout;
  private LinearLayout three_km_layout;
  private LinearLayout four_km_layout;
  private int hobby=0;
  private int distance=0;


  public ModeSettingFragment() {
    // Required empty public constructor
  }

  public static ModeSettingFragment newInstance() {
    synchronized (ModeSettingFragment.class) {
      ModeSettingFragment fragment = new ModeSettingFragment();
      return fragment;
    }
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setStyle(STYLE_NORMAL, R.style.SampleTheme_Light);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.mode_setting_empty_car, container, false);
    resetText = (TextView) view.findViewById(R.id.full_reset_default);
    resetText.setOnClickListener(this);
    btn1text1 = (TextView) view.findViewById(R.id.switch_text_1_1);
    btn1text2 = (TextView) view.findViewById(R.id.switch_text_1_2);
    btn2text1 = (TextView) view.findViewById(R.id.switch_text_2_1);
    btn2text2 = (TextView) view.findViewById(R.id.switch_text_2_2);
    btn2text3 = (TextView) view.findViewById(R.id.switch_text_2_3);
    btn2text4 = (TextView) view.findViewById(R.id.switch_text_2_4);
    btn1text1.setOnClickListener(this);
    btn1text2.setOnClickListener(this);
    btn2text1.setOnClickListener(this);
    btn2text2.setOnClickListener(this);
    btn2text3.setOnClickListener(this);
    btn2text4.setOnClickListener(this);
    btn1text1.setBackgroundResource(R.drawable.button_slider_normal);
    btn2text1.setBackgroundResource(R.drawable.button_slider_normal);
    btn_commit = (Button) view.findViewById(R.id.btn_commit);
    btn_commit.setOnClickListener(this);
    Tools.LISTEN_MODE = Tools.ListenMode.STANDARD;
    Tools.LISTEN_HOBBY = Tools.ListenHobby.ALL;
    text_one_km = (TextView) view.findViewById(R.id.text_one_km);
    text_two_km = (TextView) view.findViewById(R.id.text_two_km);
    text_three_km = (TextView) view.findViewById(R.id.text_three_km);
    text_four_km = (TextView) view.findViewById(R.id.text_four_km);
    one_km_layout = (LinearLayout) view.findViewById(R.id.one_km_layout);
    two_km_layout = (LinearLayout) view.findViewById(R.id.two_km_layout);
    three_km_layout = (LinearLayout) view.findViewById(R.id.three_km_layout);
    four_km_layout = (LinearLayout) view.findViewById(R.id.four_km_layout);
    one_km_layout.setOnClickListener(this);
    two_km_layout.setOnClickListener(this);
    three_km_layout.setOnClickListener(this);
    four_km_layout.setOnClickListener(this);
    Tools.LISTEN_DISTANCE = Tools.ListenDistance.FOUR_KM;
    //setColor();
    text_one_km.setBackgroundResource(R.drawable.slide_bar_white_small_circle_bg);
    text_two_km.setBackgroundResource(R.drawable.slide_bar_white_small_circle_bg);
    text_three_km.setBackgroundResource(R.drawable.slide_bar_white_small_circle_bg);
    text_four_km.setBackgroundResource(R.drawable.slide_bar_yellow_small_circle_bg);
    Tools.LISTEN_DISTANCE = Tools.ListenDistance.FOUR_KM;
    distance=5;
    LoginConfig loginConfig=LoginConfig.getInstance();
    hobby=loginConfig.getHobby();
    distance=loginConfig.getDistance();
    if (hobby==0){
      btn2text1.setBackgroundResource(R.color.transparent);
      btn2text2.setBackgroundResource(R.color.transparent);
      btn2text3.setBackgroundResource(R.color.transparent);
      btn2text4.setBackgroundResource(R.drawable.button_slider_normal);
      Tools.LISTEN_HOBBY = Tools.ListenHobby.ALL;
    }
    else if (hobby==1){
      btn2text1.setBackgroundResource(R.drawable.button_slider_normal);
      btn2text2.setBackgroundResource(R.color.transparent);
      btn2text3.setBackgroundResource(R.color.transparent);
      btn2text4.setBackgroundResource(R.color.transparent);
      Tools.LISTEN_HOBBY = Tools.ListenHobby.NOW;
      hobby=1;
    }
    else if (hobby==2){
      btn2text1.setBackgroundResource(R.color.transparent);
      btn2text2.setBackgroundResource(R.drawable.button_slider_normal);
      btn2text3.setBackgroundResource(R.color.transparent);
      btn2text4.setBackgroundResource(R.color.transparent);
      Tools.LISTEN_HOBBY = Tools.ListenHobby.SHORT;
    }
    else if (hobby==3){
      btn2text1.setBackgroundResource(R.color.transparent);
      btn2text2.setBackgroundResource(R.color.transparent);
      btn2text3.setBackgroundResource(R.drawable.button_slider_normal);
      btn2text4.setBackgroundResource(R.color.transparent);
      Tools.LISTEN_HOBBY = Tools.ListenHobby.LONG;
    }
    if (distance==0||distance==5){
      text_one_km.setBackgroundResource(R.drawable.slide_bar_white_small_circle_bg);
      text_two_km.setBackgroundResource(R.drawable.slide_bar_white_small_circle_bg);
      text_three_km.setBackgroundResource(R.drawable.slide_bar_white_small_circle_bg);
      text_four_km.setBackgroundResource(R.drawable.slide_bar_yellow_small_circle_bg);
      Tools.LISTEN_DISTANCE = Tools.ListenDistance.FOUR_KM;
      distance=5;
    }
    else if (distance==2){
      text_one_km.setBackgroundResource(R.drawable.slide_bar_yellow_small_circle_bg);
      text_two_km.setBackgroundResource(R.drawable.slide_bar_white_small_circle_bg);
      text_three_km.setBackgroundResource(R.drawable.slide_bar_white_small_circle_bg);
      text_four_km.setBackgroundResource(R.drawable.slide_bar_white_small_circle_bg);
      Tools.LISTEN_DISTANCE = Tools.ListenDistance.ONE_KM;
    }
    else if (distance==3){
      setColor();
    }
    else if (distance==4){
      text_one_km.setBackgroundResource(R.drawable.slide_bar_white_small_circle_bg);
      text_two_km.setBackgroundResource(R.drawable.slide_bar_white_small_circle_bg);
      text_three_km.setBackgroundResource(R.drawable.slide_bar_yellow_small_circle_bg);
      text_four_km.setBackgroundResource(R.drawable.slide_bar_white_small_circle_bg);
      Tools.LISTEN_DISTANCE = Tools.ListenDistance.THREE_KM;
    }
    return view;
  }

  private void setColor() {
    text_one_km.setBackgroundResource(R.drawable.slide_bar_white_small_circle_bg);
    text_two_km.setBackgroundResource(R.drawable.slide_bar_yellow_small_circle_bg);
    text_three_km.setBackgroundResource(R.drawable.slide_bar_white_small_circle_bg);
    text_four_km.setBackgroundResource(R.drawable.slide_bar_white_small_circle_bg);
    Tools.LISTEN_DISTANCE = Tools.ListenDistance.TWO_KM;
    distance=3;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (this.getDialog() != null) {
      this.getDialog().getWindow().addFlags(4718592);
    }
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.full_reset_default:
        btn1text1.setBackgroundResource(R.drawable.button_slider_normal);
        btn1text2.setBackgroundResource(R.color.transparent);
        btn2text1.setBackgroundResource(R.drawable.button_slider_normal);
        btn2text2.setBackgroundResource(R.color.transparent);
        btn2text3.setBackgroundResource(R.color.transparent);
        btn2text4.setBackgroundResource(R.color.transparent);
        Tools.LISTEN_MODE = Tools.ListenMode.STANDARD;
        Tools.LISTEN_HOBBY = Tools.ListenHobby.ALL;
        hobby=0;
        distance=5;
        text_one_km.setBackgroundResource(R.drawable.slide_bar_white_small_circle_bg);
        text_two_km.setBackgroundResource(R.drawable.slide_bar_white_small_circle_bg);
        text_three_km.setBackgroundResource(R.drawable.slide_bar_white_small_circle_bg);
        text_four_km.setBackgroundResource(R.drawable.slide_bar_yellow_small_circle_bg);
        Tools.LISTEN_DISTANCE = Tools.ListenDistance.FOUR_KM;
        //setColor();
        break;
      case R.id.switch_text_1_1:
        btn1text1.setBackgroundResource(R.drawable.button_slider_normal);
        btn1text2.setBackgroundResource(R.color.transparent);
        Tools.LISTEN_MODE = Tools.ListenMode.STANDARD;
        break;
      case R.id.switch_text_1_2:
        btn1text1.setBackgroundResource(R.color.transparent);
        btn1text2.setBackgroundResource(R.drawable.button_slider_normal);
        Tools.LISTEN_MODE = Tools.ListenMode.QUICK;
        break;
      case R.id.switch_text_2_1:
        btn2text1.setBackgroundResource(R.drawable.button_slider_normal);
        btn2text2.setBackgroundResource(R.color.transparent);
        btn2text3.setBackgroundResource(R.color.transparent);
        btn2text4.setBackgroundResource(R.color.transparent);
        Tools.LISTEN_HOBBY = Tools.ListenHobby.NOW;
        hobby=1;
        break;
      case R.id.switch_text_2_2:
        btn2text1.setBackgroundResource(R.color.transparent);
        btn2text2.setBackgroundResource(R.drawable.button_slider_normal);
        btn2text3.setBackgroundResource(R.color.transparent);
        btn2text4.setBackgroundResource(R.color.transparent);
        Tools.LISTEN_HOBBY = Tools.ListenHobby.SHORT;
        hobby=2;
        break;
      case R.id.switch_text_2_3:
        btn2text1.setBackgroundResource(R.color.transparent);
        btn2text2.setBackgroundResource(R.color.transparent);
        btn2text3.setBackgroundResource(R.drawable.button_slider_normal);
        btn2text4.setBackgroundResource(R.color.transparent);
        Tools.LISTEN_HOBBY = Tools.ListenHobby.LONG;
        hobby=3;
        break;
      case R.id.switch_text_2_4:
        btn2text1.setBackgroundResource(R.color.transparent);
        btn2text2.setBackgroundResource(R.color.transparent);
        btn2text3.setBackgroundResource(R.color.transparent);
        btn2text4.setBackgroundResource(R.drawable.button_slider_normal);
        Tools.LISTEN_HOBBY = Tools.ListenHobby.ALL;
        hobby=0;
        break;
      case R.id.one_km_layout:
        text_one_km.setBackgroundResource(R.drawable.slide_bar_yellow_small_circle_bg);
        text_two_km.setBackgroundResource(R.drawable.slide_bar_white_small_circle_bg);
        text_three_km.setBackgroundResource(R.drawable.slide_bar_white_small_circle_bg);
        text_four_km.setBackgroundResource(R.drawable.slide_bar_white_small_circle_bg);
        Tools.LISTEN_DISTANCE = Tools.ListenDistance.ONE_KM;
        distance=2;
        break;
      case R.id.two_km_layout:
        setColor();
        break;
      case R.id.three_km_layout:
        text_one_km.setBackgroundResource(R.drawable.slide_bar_white_small_circle_bg);
        text_two_km.setBackgroundResource(R.drawable.slide_bar_white_small_circle_bg);
        text_three_km.setBackgroundResource(R.drawable.slide_bar_yellow_small_circle_bg);
        text_four_km.setBackgroundResource(R.drawable.slide_bar_white_small_circle_bg);
        Tools.LISTEN_DISTANCE = Tools.ListenDistance.THREE_KM;
        distance=4;
        break;
      case R.id.four_km_layout:
        text_one_km.setBackgroundResource(R.drawable.slide_bar_white_small_circle_bg);
        text_two_km.setBackgroundResource(R.drawable.slide_bar_white_small_circle_bg);
        text_three_km.setBackgroundResource(R.drawable.slide_bar_white_small_circle_bg);
        text_four_km.setBackgroundResource(R.drawable.slide_bar_yellow_small_circle_bg);
        Tools.LISTEN_DISTANCE = Tools.ListenDistance.FOUR_KM;
        distance=5;
        break;
      case R.id.btn_commit:
        this.dismiss();
        EventBus.getDefault().post(Tools.LISTEN_MODE);
        EventBus.getDefault().post(Tools.LISTEN_HOBBY);
        EventBus.getDefault().post(Tools.LISTEN_DISTANCE);
        LoginConfig loginConfig=LoginConfig.getInstance();
        loginConfig.setSetting(hobby,distance);
        break;
    }
  }
}
