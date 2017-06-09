package com.uniware.driver.gui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.uniware.driver.R;

/**
 * Created by jian on 15/12/23.
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridViewHolder> {

  private int[] rId;
  private OnItemClickListener orderItemClickListener;

  public interface OnItemClickListener {
    void onOrderItemClicked(int id);
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.orderItemClickListener = onItemClickListener;
  }

  public GridAdapter(@NonNull int[] rId) {
    this.rId = rId;
  }

  @Override public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new GridViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false));
  }

  @Override public void onBindViewHolder(GridViewHolder holder, final int position) {
/*    DisplayMetrics displayMetrics = holder.itemView.getContext().getResources().getDisplayMetrics();
    Log.e("grid1", displayMetrics.heightPixels + " " + displayMetrics.widthPixels);*/
    holder.gridItem.setImageResource(rId[position]);
/*    Log.e("grid2", displayMetrics.heightPixels + " " + displayMetrics.widthPixels);
    ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
    Log.e("grid3", layoutParams.height + " " + layoutParams.width);
    Log.e("grid4", holder.itemView.getHeight() + " " + holder.itemView.getWidth());*/
    //layoutParams.height = displayMetrics.heightPixels;
    //layoutParams.width = displayMetrics.widthPixels;
    //holder.itemView.setLayoutParams(layoutParams);
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (orderItemClickListener != null) {
          orderItemClickListener.onOrderItemClicked(position);
        }
      }
    });
  }

  @Override public int getItemCount() {
    return rId.length;
  }

  static class GridViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.iv_grid_item) ImageView gridItem;

    public GridViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
