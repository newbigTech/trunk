package com.uniware.driver.gui.adapter;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.uniware.driver.R;
import com.uniware.driver.domain.Order;

/**
 * Created by SJ on 15/12/01.
 */
public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  protected Cursor mCursor;
  protected ChangeObserver mChangeObserver;
  protected DataSetObserver mDataSetObserver;

  private final LayoutInflater layoutInflater;
  private OnItemClickListener orderItemClickListener;
  private OnItemClickListener1 onItemClickListener;
  private static final int TYPE_ITEM = 0;
  private static final int TYPE_FOOTER = 1;
  private static int i=1;
  private boolean isNoMoreData;

  public OrderAdapter(Context context, Cursor cursor) {
    this.layoutInflater = LayoutInflater.from(context);
    init(cursor);
  }

  private void init(Cursor c) {
    boolean cursorPresent = c != null;
    mCursor = c;
    mChangeObserver = new ChangeObserver();
    mDataSetObserver = new MyDataSetObserver();
    if (cursorPresent) {
      if (mChangeObserver != null) c.registerContentObserver(mChangeObserver);
      if (mDataSetObserver != null) c.registerDataSetObserver(mDataSetObserver);
    }
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == TYPE_ITEM) {
      View view = layoutInflater.inflate(R.layout.order_list_item, parent, false);
      return new OrderViewHolder(view);
    } else if (viewType == TYPE_FOOTER) {
      View view = layoutInflater.inflate(R.layout.listview_footer, parent, false);
      view.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (onItemClickListener!=null){
            onItemClickListener.onItemClickListener1();
          }
        }
      });
      return new FooterViewHolder(view);
    }
    return null;
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof OrderViewHolder) {
      if (!mCursor.moveToPosition(position)) {
        throw new IllegalStateException("couldn't move cursor to position " + position);
      }
      final Order order = new Order();
      order.setOid(mCursor.getString(0));
      order.setStatus(mCursor.getInt(1));
      order.setType((byte) mCursor.getInt(2));
      order.setUseTime(mCursor.getString(3));
      order.setFromAddr(mCursor.getString(4));
      order.setToAddr(mCursor.getString(5));
      order.setFromLng(Double.parseDouble(mCursor.getString(6)));
      order.setFromLat(Double.parseDouble(mCursor.getString(7)));
      order.setToLng(Double.parseDouble(mCursor.getString(8)));
      order.setToLat(Double.parseDouble(mCursor.getString(9)));
      order.setPhone(mCursor.getString(10));
      order.setCallFee(mCursor.getString(11));
      order.setDescription(mCursor.getString(12));
      //Log.e("====",order.getDescription());
      OrderViewHolder orderViewHolder = (OrderViewHolder) holder;
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
          if (orderItemClickListener != null) {
            orderItemClickListener.onOrderItemClicked(order);
          }
        }
      });
    } else if (holder instanceof FooterViewHolder) {
      FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
      if (getItemCount() < 4) {
        footerViewHolder.itemView.setVisibility(View.GONE);
      } else {
        footerViewHolder.itemView.setVisibility(View.VISIBLE);
      }
      if (isNoMoreData) {
        footerViewHolder.tvloading.setVisibility(View.GONE);
        footerViewHolder.tvNoMore.setVisibility(View.VISIBLE);
      } else {
        footerViewHolder.tvloading.setVisibility(View.VISIBLE);
        footerViewHolder.tvNoMore.setVisibility(View.GONE);
      }
    }
  }

  @Override public int getItemCount() {
    if (mCursor != null) {
      return mCursor.getCount() + 1;
    } else {
      return 0;
    }
  }

  @Override public int getItemViewType(int position) {
    // 最后一个item设置为footerView
    if (position + 1 == getItemCount()) {
      return TYPE_FOOTER;
    } else {
      return TYPE_ITEM;
    }
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.orderItemClickListener = onItemClickListener;
  }
  public void setOnItemClickListener1(OnItemClickListener1 onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  public void setNoMoreData(boolean b) {
    isNoMoreData = b;
  }

  public interface OnItemClickListener {
    void onOrderItemClicked(Order order);
  }
  public interface OnItemClickListener1 {
    void onItemClickListener1();
  }

  static class FooterViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.list_footer_content) RelativeLayout content;
    @Bind(R.id.list_footer_hint_loading) TextView tvloading;
    @Bind(R.id.list_footer_hint_no_more) TextView tvNoMore;

    public FooterViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  static class OrderViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.txt_title) TextView textTitle;
    @Bind(R.id.iv_carpool_icon) ImageView carpoolIcon;
    @Bind(R.id.tip_bonus) TextView tipBonus;
    @Bind(R.id.txt_order_from) TextView orderFrom;
    @Bind(R.id.txt_order_to) TextView orderTo;
    @Bind(R.id.txt_usetime) TextView userTime;

    public OrderViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  public Cursor swapCursor(Cursor newCursor) {
    if (newCursor == mCursor) {
      return null;
    }
    Cursor oldCursor = mCursor;
    if (oldCursor != null) {
      if (mChangeObserver != null) oldCursor.unregisterContentObserver(mChangeObserver);
      if (mDataSetObserver != null) oldCursor.unregisterDataSetObserver(mDataSetObserver);
    }
    mCursor = newCursor;
    if (newCursor != null) {
      if (mChangeObserver != null) newCursor.registerContentObserver(mChangeObserver);
      if (mDataSetObserver != null) newCursor.registerDataSetObserver(mDataSetObserver);
      notifyDataSetChanged();
    }
    return oldCursor;
  }

  private class ChangeObserver extends ContentObserver {
    public ChangeObserver() {
      super(new Handler());
    }

    @Override public boolean deliverSelfNotifications() {
      return true;
    }

    @Override public void onChange(boolean selfChange) {
      if (mCursor != null && !mCursor.isClosed()) {
        if (false) Log.v("Cursor", "Auto requerying " + mCursor + " due to update");
        mCursor.requery();
      }
    }
  }

  private class MyDataSetObserver extends DataSetObserver {
    @Override public void onChanged() {
      notifyDataSetChanged();
    }

    @Override public void onInvalidated() {
      //mDataValid = false;
      //notifyDataSetInvalidated();
    }
  }
}
