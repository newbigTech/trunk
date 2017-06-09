package com.uniware.driver.gui.ui.controlpanel;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.uniware.driver.R;
import com.uniware.driver.gui.activity.MainActivity;

/**
 * Created by jian on 15/11/13.
 */
public class ControlPanel extends RelativeLayout {
  private StartOffButton mStartOffButton;
  private ListeningButton mListeningButton;
  private GrabButton mGrabButton;
  private GrabForbidButton mGrabForbidButton;
  private SettingScaleButton mEmptyCarSettingScaleButton;
  private SettingScaleButton mOnBoardSettingScaleButton;

  private ControlPanel.OrderSettingCallback mEmptyCarModeSettingCallback;
  private ControlPanel.OrderSettingCallback mOnBoardModeSettingCallback;
  private ControlPanel.StriveOrderCallback mStriveOrderCallback = null;

  public ControlPanel(Context context) {
    super(context);
    this.initViews();
    this.setupListeners();
  }

  public ControlPanel(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.initViews();
    this.setupListeners();
  }

  public ControlPanel(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.initViews();
    this.setupListeners();
  }

  private void blockScreenLock() {
    MainActivity activity = MainActivity.getMainActivity();
    if (activity != null) {
      activity.getWindow().addFlags(4718592);
    }
  }

  private void unblockScreenLock() {
    MainActivity activity = MainActivity.getMainActivity();
    if (activity != null) {
      activity.getWindow().clearFlags(4718592);
    }
  }

  private void checkGPS() {

  }

  private void initViews() {
    inflate(getContext(), R.layout.main_control_panel_layout, this);
    this.mStartOffButton = (StartOffButton) findViewById(R.id.btn_start_off);
    this.mGrabForbidButton = (GrabForbidButton) findViewById(R.id.btn_count_down);
    this.mListeningButton = (ListeningButton) findViewById(R.id.btn_listening);
    this.mGrabButton = (GrabButton) findViewById(R.id.btn_grab);
    this.mEmptyCarSettingScaleButton = (SettingScaleButton) findViewById(R.id.btn_empty_car);
    this.mOnBoardSettingScaleButton = (SettingScaleButton) findViewById(R.id.btn_on_board);

    String status = this.getResources().getString(R.string.main_control_panel_setting_empty_car);
    this.mEmptyCarSettingScaleButton.setBtnTxt(status);
    status = this.getResources().getString(R.string.main_control_panel_setting_on_board);
    this.mOnBoardSettingScaleButton.setBtnTxt(status);
  }

  private void playTTS(String txt) {
    if (txt != null) {

    }
  }

  private void setupListeners() {

  }

  private void showChangePhoneDialog() {

  }

  private void showOpenGpsDialog() {

  }

  private void updateAnnounceFragmentDestAndTimeTextView() {
    Intent intent = new Intent("action_fresh_order_setting_data");
    //d.a(BaseApplication.getAppContext()).a(var1);
  }

  public void onPause() {
  }

  public void resetStartOffListener() {
    //c.a().a(this.mStartOffListener);
  }

  public void setEmptyCarOrderSettingCallback(ControlPanel.OrderSettingCallback var1) {
    this.mEmptyCarModeSettingCallback = var1;
  }

  public void setOnBoardOrderSettingCallback(ControlPanel.OrderSettingCallback var1) {
    this.mOnBoardModeSettingCallback = var1;
  }

  public void setStriveOrderCallback(ControlPanel.StriveOrderCallback var1) {
    if (var1 != null) {
      this.mStriveOrderCallback = var1;
    }
  }

  public void showStartOffBtn() {
    this.mListeningButton.hide();
    this.mGrabForbidButton.hide();
    this.mGrabButton.hide();
    this.mEmptyCarSettingScaleButton.setVisibility(GONE);
    this.mOnBoardSettingScaleButton.setVisibility(GONE);
    this.mStartOffButton.show();
  }

  public void showListeningButton() {
    this.mStartOffButton.hide();
    this.mGrabForbidButton.hide();
    this.mGrabButton.hide();
    this.mEmptyCarSettingScaleButton.setVisibility(GONE);
    this.mOnBoardSettingScaleButton.setVisibility(GONE);
    this.mListeningButton.show();
  }

  public void showGrabButton() {
    this.mStartOffButton.hide();
    this.mListeningButton.hide();
    this.mGrabForbidButton.hide();
    this.mGrabButton.show();
  }

  public void showGrabButton(int count) {
    this.mStartOffButton.hide();
    this.mListeningButton.hide();
    this.mGrabForbidButton.hide();
    this.mGrabButton.show(count);
  }

  public void showGrabForbidButton(int countDown) {
    this.mStartOffButton.hide();
    this.mListeningButton.hide();
    this.mGrabButton.hide();
    this.mGrabForbidButton.show(countDown);
  }

  public void startLinking() {
    if (this.mListeningButton != null) {
      this.mListeningButton.startLinking();
    }
  }

  public void stopLinking() {
    if (this.mListeningButton != null) {
      this.mListeningButton.stopLinking();
    }
  }

  public interface GoOfflineCallback {
    void onGoOffline();
  }

  public interface ListenModeListener {
    void onChange(int var1);
  }

  public interface OrderSettingCallback {
    void onClick();
  }

  public interface StriveOrderCallback {
    void onOrderStrive();
  }
}
