package com.uniware.driver.gui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.uniware.driver.R;
import com.uniware.driver.domain.AddressResult;
import java.util.List;

/**
 * Created by ayue on 2017/11/29.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

  List<AddressResult.AddressBean> list;
  private Context context;
  private OnItemClickListener onItemClickListener;

  public AddressAdapter(List<AddressResult.AddressBean> list, Context context) {
    this.context = context;
    this.list = list;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    ViewHolder viewHolder = new ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.item_adapter_address, parent, false));
    return viewHolder;
  }

  @Override public void onBindViewHolder(final ViewHolder holder, final int position) {
    if (position==0){
      holder.tvName.setText("家");
    }
    if (position==1){
      holder.tvName.setText("公司");
    }

    holder.tvAddress.setText("设置地址");


      if (list.get(0).getDescription().equals("家")){
        if (position==0){
          holder.tvAddress.setText(list.get(0).getAddress());
        }
      }
      if (list.get(0).getDescription().equals("公司")){
        if (position==1){
          holder.tvAddress.setText(list.get(0).getAddress());
        }
      }
      if (list.get(1).getDescription().equals("家")){
        if (position==0){
          holder.tvAddress.setText(list.get(1).getAddress());
        }
      }
      if (list.get(1).getDescription().equals("公司")){
        if (position==1){
          holder.tvAddress.setText(list.get(1).getAddress());
        }
    }
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        boolean f=true;
        if (onItemClickListener != null) {
          if (holder.tvAddress.getText().equals("设置地址")){
            f=false;
          }
          onItemClickListener.onItemClicked(position,f);
        }
      }
    });
  }

  @Override public int getItemCount() {
    return list.size();
  }

  public interface OnItemClickListener {
    void onItemClicked(int b,boolean flag);
  }
  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }
  class ViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tv_name) TextView tvName;
    @Bind(R.id.tv_address) TextView tvAddress;
    @Bind(R.id.iv_back) TextView ivBack;
    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
