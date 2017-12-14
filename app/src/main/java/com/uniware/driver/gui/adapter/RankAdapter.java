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
import com.uniware.driver.domain.RankResult;
import java.util.List;

/**
 * Created by ayue on 2017/12/6.
 */

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {

  private List<RankResult.RankingListBean> list;
  private Context mContext;
  private int type=1;

  public RankAdapter(List<RankResult.RankingListBean> list, Context context) {
    this.list = list;
    this.mContext = context;
  }
  public void setShowType(int type){
    this.type=type;
  }
  @Override public RankAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    ViewHolder viewHolder =
        new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rv_rank, parent, false));
    return viewHolder;
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    holder.tvCompany.setText("公司:"+list.get(position).getCompany());
    holder.tvDepartment.setText("分司:"+list.get(position).getDepartment());
    if (type==1||type==2){
      holder.tvName.setVisibility(View.VISIBLE);
      holder.tvName.setText("车牌号："+list.get(position).getPlateNum()+"   接单数："+list.get(position).getCountNum());
    }else {
      holder.tvName.setText("接单数："+list.get(position).getCountNum());
    }
    holder.tvNum.setText(list.get(position).getRanking()+"");
  }


  @Override public int getItemCount() {
    return list.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.tv_num) TextView tvNum;
    @Bind(R.id.tv_name) TextView tvName;
    @Bind(R.id.tv_company) TextView tvCompany;
    @Bind(R.id.tv_department) TextView tvDepartment;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
  }
