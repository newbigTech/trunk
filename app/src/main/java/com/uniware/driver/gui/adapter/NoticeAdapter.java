package com.uniware.driver.gui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.uniware.driver.R;
import com.uniware.driver.domain.NoticeResult;
import java.util.List;

/**
 * Created by ayue on 2018/1/23.
 */

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.MyViewHolder> {

  private Context context;
  private List<NoticeResult.MessagesBean> list;
  private OnItemClickListener onItemClickListener;

  public NoticeAdapter(Context context,List<NoticeResult.MessagesBean> list){
    this.context=context;
    this.list=list;
  }

  @Override public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    MyViewHolder viewHolder = new MyViewHolder(
        LayoutInflater.from(context).inflate(R.layout.item_notice_adapter, parent, false));
    return viewHolder;
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener){
    this.onItemClickListener=onItemClickListener;
  }
  @Override public void onBindViewHolder(MyViewHolder holder, final int position) {
    if (position>=20){
      holder.ivNotice.setImageResource(R.drawable.ic_noticed);
    }
    else {
      if (list.get(position).isRead()){
        holder.ivNotice.setImageResource(R.drawable.ic_noticed);
      }
      else {
        holder.ivNotice.setImageResource(R.drawable.ic_notice_new);
      }
    }
    holder.time.setText(list.get(position).getSendTime());
    holder.message.setText(list.get(position).getMessage());
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (onItemClickListener!=null){
          onItemClickListener.onItemClicked(list.get(position));
        }
      }
    });
  }

  @Override public int getItemCount() {
    return list.size();
  }

  public interface OnItemClickListener{
    void onItemClicked(NoticeResult.MessagesBean notice);
  }

  class MyViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.iv_notice) ImageView ivNotice;
    @Bind(R.id.time) TextView time;
    @Bind(R.id.message) TextView message;
    public MyViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }
}
