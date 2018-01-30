package com.uniware.driver.gui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.uniware.driver.R;
import com.uniware.driver.config.UserConfig;
import com.uniware.driver.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayue on 2018/1/29.
 */

public class NoticeDetailActivity extends BaseActivity {

  @Bind(R.id.back_btn) ImageView backBtn;
  @Bind(R.id.time) TextView tv_time;
  @Bind(R.id.message) TextView tv_message;
  int id;
  String time;
  String message;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_noticedetail);
    ButterKnife.bind(this);
    initView();
  }

  private void initView() {
    backBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
    Intent it=getIntent();
    id=it.getIntExtra("id",0);
    time=it.getStringExtra("time");
    message=it.getStringExtra("text");
    tv_time.setText("时间："+time);
    tv_message.setText("内容："+message);
    Log.e("id--------",id+"");
    String tag=UserConfig.getInstance().getNoticeTag();
    List<String> list=new ArrayList<>();
    if (tag!=null&&tag.length()>0){
      //list= StringUtils.stringToList(tag);
      list.addAll(StringUtils.stringToList(tag));
    }
    list.add(id+"");
    tag=StringUtils.listToString(list);
    UserConfig.getInstance().setNoticeTag(tag);
  }
}
