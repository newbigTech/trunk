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
import com.uniware.driver.domain.CallRecord;
import com.uniware.driver.domain.Order;
import com.uniware.driver.util.Tools;
import java.util.List;

/**
 * Created by ayue on 2017/5/24.
 */

public class AllOrderAdapter extends RecyclerView.Adapter<AllOrderAdapter.MyViewHolder> {

  private List<CallRecord> listHttpResult;
  private Context context;
  private OnItemClickListener onItemClickListener;

  public AllOrderAdapter(Context context,List<CallRecord> listHttpResult) {
      this.context=context;
      this.listHttpResult=listHttpResult;
  }

  @Override public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    MyViewHolder viewHolder = new MyViewHolder(
        LayoutInflater.from(context).inflate(R.layout.order_list_item, parent, false));
    return viewHolder;
  }

  @Override public void onBindViewHolder(MyViewHolder orderViewHolder, int position) {
    //Log.e("pppppppppp","---"+position);
    final Order order = new Order();
    order.setOid(listHttpResult.get(position).getBizId()+"");
    order.setStatus(100+listHttpResult.get(position).getStatus());
    order.setType(listHttpResult.get(position).getBizType()==0 ? 0:1);
    String ss= Tools.stampToDate(listHttpResult.get(position).getUseTime());
    ss=ss.substring(2);
    order.setUseTime(ss);
    order.setFromAddr(listHttpResult.get(position).getUserLocation());
    order.setToAddr(listHttpResult.get(position).getDestLocation());
    order.setFromLng(listHttpResult.get(position).getUserLon());
    order.setFromLat(listHttpResult.get(position).getUserLat());
    order.setToLng(listHttpResult.get(position).getDestLon());
    order.setToLat(listHttpResult.get(position).getDestLat());
    order.setPhone(listHttpResult.get(position).getPassengerTel());
    order.setCallFee(listHttpResult.get(position).getCallFee()+"");
    order.setDescription(listHttpResult.get(position).getPassengerAttr()+"");

    orderViewHolder.textTitle.setText(order.getType() == 0 ? "即时" : "预约");
    if (order.getType()==1){
      if (order.getCallFee().equals("0500")){
        orderViewHolder.textTitle.setText("短约");
      }else {
        orderViewHolder.textTitle.setText("长约");
      }
    }
    switch (order.getStatus()){
      case  0:
        orderViewHolder.tipBonus.setText("未完成");break;
      case 100:
        orderViewHolder.tipBonus.setText("完成");break;
      case 99:
        orderViewHolder.tipBonus.setText("乘客已送达");break;
      case 103:
        orderViewHolder.tipBonus.setText("超时");break;
      case 104:
        orderViewHolder.tipBonus.setText("完成");break;
      case 105:
        orderViewHolder.tipBonus.setText("司机毁约");break;
      case 106:
        orderViewHolder.tipBonus.setText("乘客毁约");break;
      case 107:
      case 108:
        orderViewHolder.tipBonus.setText("乘客取消");break;
      case 109:
        orderViewHolder.tipBonus.setText("司机取消");break;
      default:
        orderViewHolder.tipBonus.setText("未完成");break;
    }

    //orderViewHolder.userTime.setVisibility(View.GONE);
    String s=order.getUseTime();
    orderViewHolder.userTime.setText(s.substring(2,4)+"月"+s.substring(4,6)+"日"+s.substring(6,8)+"点"+s.substring(8,10)+"分");
    orderViewHolder.orderFrom.setText(order.getFromAddr());
    orderViewHolder.orderTo.setText(order.getToAddr());
    orderViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (onItemClickListener != null) {
          onItemClickListener.onOrderItemClicked(order);
        }
      }
    });
  }

  @Override public int getItemCount() {
    return listHttpResult.size();
  }

  public interface OnItemClickListener {
    void onOrderItemClicked(Order order);
  }
  class MyViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.txt_title) TextView textTitle;
    @Bind(R.id.iv_carpool_icon) ImageView carpoolIcon;
    @Bind(R.id.tip_bonus) TextView tipBonus;
    @Bind(R.id.txt_order_from) TextView orderFrom;
    @Bind(R.id.txt_order_to) TextView orderTo;
    @Bind(R.id.txt_usetime) TextView userTime;
    public MyViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }
}
