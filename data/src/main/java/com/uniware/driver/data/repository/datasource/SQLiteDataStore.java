package com.uniware.driver.data.repository.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uniware.driver.data.database.Provider;
import com.uniware.driver.domain.Order;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by jian on 15/12/04.
 */
@Singleton public class SQLiteDataStore implements LocalDataStore {

  private final Context context;

  @Inject public SQLiteDataStore(Context context) {
    this.context = context;
  }

  @Override public Observable<List<Order>> orders() {
    return Observable.create(new Observable.OnSubscribe<List<Order>>() {
      @Override public void call(Subscriber<? super List<Order>> subscriber) {
        List<Order> list = null;
        Cursor cursor =
            context.getContentResolver().query(Provider.Orders.CONTENT_URI, new String[] {
                Provider.Orders.COLUMN_OID, Provider.Orders.COLUMN_TYPE,
                Provider.Orders.COLUMN_FROM_ADDR, Provider.Orders.COLUMN_TO_ADDR,
                Provider.Orders.COLUMN_TIME,Provider.Orders.COLUMN_FREE,Provider.Orders.COLUMN_description,Provider.Orders.COLUMN_STATUS
            }, null, null, null);
        // If the Cursor is not null, and it contains at least one record
        // (moveToFirst() returns true), then this gets the note data from it.
        if (cursor != null) {
          if (cursor.moveToFirst()) {
            list = new ArrayList<>();
            while (!cursor.isLast()) {
              Order order = new Order();
              order.setOid(cursor.getString(0));
              order.setType((byte) cursor.getInt(1));
              order.setFromAddr(cursor.getString(2));
              order.setToAddr(cursor.getString(3));
              order.setUseTime(cursor.getString(4));
              order.setCallFee(cursor.getString(5));
              order.setDescription(cursor.getString(6));
              order.setStatus(cursor.getInt(7));
              list.add(order);
              cursor.moveToNext();
            }
          }
          // Closes the cursor.
          //Log.e("zhiqian",list.toString());
          Collections.reverse(list);
          //Log.e("zhihou",list.toString());
          cursor.close();
          subscriber.onNext(list);
        }
      }
    });
  }

   public Order findOrder(int oid) {
    Cursor cursor =
        context.getContentResolver().query(Provider.Orders.CONTENT_URI, new String[] {
            Provider.Orders.COLUMN_OID, Provider.Orders.COLUMN_TYPE,
            Provider.Orders.COLUMN_FROM_ADDR, Provider.Orders.COLUMN_TO_ADDR,
            Provider.Orders.COLUMN_TIME,Provider.Orders.COLUMN_FREE,Provider.Orders.COLUMN_description,Provider.Orders.COLUMN_STATUS
        }, "oid = ?", new String[]{oid+""}, null);
    Order order = new Order();
     Log.e("=====",cursor.getCount()+"");
    if (cursor != null) {
      if (cursor.moveToFirst()) {
        //list = new ArrayList<>();
        //while (!cursor.isLast()) {
          order.setOid(cursor.getString(0));
          order.setType((byte) cursor.getInt(1));
          order.setFromAddr(cursor.getString(2));
          order.setToAddr(cursor.getString(3));
          order.setUseTime(cursor.getString(4));
          order.setCallFee(cursor.getString(5));
          order.setDescription(cursor.getString(6));
          order.setStatus(cursor.getInt(7));
          //list.add(order);
          //cursor.moveToNext();
         // break;
       // }
      }
    }
      // Closes the cursor.
      cursor.close();
    return order;
  }

  @Override public Observable<Uri> insertOrder(final Order order) {
    return Observable.create(new Observable.OnSubscribe<Uri>() {
      @Override public void call(Subscriber<? super Uri> subscriber) {
        ContentValues values = new ContentValues();
        values.put(Provider.Orders.COLUMN_OID, order.getOid());
        values.put(Provider.Orders.COLUMN_TYPE, order.getType());
        values.put(Provider.Orders.COLUMN_TIME, order.getUseTime());
        values.put(Provider.Orders.COLUMN_FROM_ADDR, order.getFromAddr());
        values.put(Provider.Orders.COLUMN_TO_ADDR, order.getToAddr());
        values.put(Provider.Orders.COLUMN_FROM_LNG, order.getFromLng() + "");
        values.put(Provider.Orders.COLUMN_FROM_LAT, order.getFromLat() + "");
        values.put(Provider.Orders.COLUMN_TO_LNG, order.getToLng() + "");
        values.put(Provider.Orders.COLUMN_TO_LAT, order.getToLat() + "");
        values.put(Provider.Orders.COLUMN_PHONE, order.getPhone());
        values.put(Provider.Orders.COLUMN_FREE, order.getCallFee()+"");
        values.put(Provider.Orders.COLUMN_description, order.getDescription()+"");
        Uri uri = context.getContentResolver().insert(Provider.Orders.CONTENT_URI, values);
        subscriber.onNext(uri);
      }
    });
  }

  @Override public Observable<Integer> updateOrder(final Order order) {
    return Observable.create(new Observable.OnSubscribe<Integer>() {
      @Override public void call(Subscriber<? super Integer> subscriber) {
        ContentValues cv = new ContentValues();
        cv.put("status", Order.STATUS_END);
        int i = context.getContentResolver()
            .update(Provider.Orders.CONTENT_URI, cv, "oid = ?", new String[] { order.getOid() });
        subscriber.onNext(i);
      }
    });
  }
  @Override public Observable<Integer> updateOrder(final String oid, final int status) {
    return Observable.create(new Observable.OnSubscribe<Integer>() {
      @Override public void call(Subscriber<? super Integer> subscriber) {
        ContentValues cv = new ContentValues();
        //if (status==0){
          cv.put("status", 100+status);
          int i = context.getContentResolver()
              .update(Provider.Orders.CONTENT_URI, cv, "oid = ?", new String[] { oid });
          subscriber.onNext(i);
        //}
        //else if(status==1){
        //  cv.put("status", Order.STATUS_END);
        //  int i = context.getContentResolver()
        //      .update(Provider.Orders.CONTENT_URI, cv, "oid = ?", new String[] { oid });
        //  subscriber.onNext(i);
        //}
      }
    });
  }

  @Override public Observable<Integer> updateUnfinishOrder(final List<Order> orderList) {
    return Observable.create(new Observable.OnSubscribe<Integer>() {
      @Override public void call(Subscriber<? super Integer> subscriber) {
        ContentValues cv = new ContentValues();
        cv.put("status", Order.STATUS_END);
        int s = context.getContentResolver()
            .update(Provider.Orders.CONTENT_URI, cv, "status < ?",
                new String[] { Order.STATUS_END + "" });
        //for (int i=0;i<orderList.size();i++){
        //  ContentValues cvs = new ContentValues();
        //  cv.put("status", orderList.get(i).getStatus());
        //  context.getContentResolver()
        //      .update(Provider.Orders.CONTENT_URI, cvs, "oid =?",
        //          new String[] { orderList.get(i).getOid() });
        //}
        subscriber.onNext(s);
      }
    });
  }

  @Override public Observable<Integer> deleteOrder(final String oid) {
    return Observable.create(new Observable.OnSubscribe<Integer>() {
      @Override public void call(Subscriber<? super Integer> subscriber) {
        int i = context.getContentResolver()
            .delete(Provider.Orders.CONTENT_URI, "oid = ?", new String[] { oid });
        subscriber.onNext(i);
      }
    });
  }
}
