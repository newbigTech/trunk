package com.uniware.driver.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jian on 15/12/03.
 */
class DatabaseHelper extends SQLiteOpenHelper {

  public DatabaseHelper(Context context) {
    super(context, "yj_driver.db3", null, 3);
  }

  @Override public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE IF NOT EXISTS announce(_id integer primary key autoincrement,type integer default 0,portal_type integer default -1,push_time INT8,expire_time INT8,read integer default 0,data text not null);");
    db.execSQL("CREATE TABLE IF NOT EXISTS orders(_id integer primary key autoincrement,oid text,status integer default 0,type integer default 0,time INT8,get_off_time INT8,from_addr text,to_addr text,phone text,callfree text,description text,tip float default 0,bonus integer default 0,exp2 text,input integer default 0,comment_level integer default 0,comment_sub_level text,comment_txt text,cancel_type integer default 0,cancel_txt text,from_lng text,from_lat text,to_lng text,to_lat text,complaint_type integer default 0,complaint_txt text,audio_url text,fare float,other_fare float,total_fee float,base_total_fee float,pay_status integer default 0,comment_status integer default 0,online_pay_status integer default 0,my_comment integer default 0);");
    db.execSQL("CREATE TABLE IF NOT EXISTS session(_id integer primary key autoincrement,oid text,session_id INT8 NOT NULL,session_title TEXT,session_update_time INT8,unread_cnt INTEGER default 0,UNIQUE(oid,session_id));");
    db.execSQL("CREATE TABLE IF NOT EXISTS usr(_id integer primary key autoincrement,session_id INT8 NOT NULL,usr_id INT8,usr_nick_name TEXT,avatar TEXT,UNIQUE(session_id,usr_id));");
    db.execSQL("CREATE TABLE IF NOT EXISTS message(_id integer primary key autoincrement,session_id INT8 NOT NULL,usr_id INT8,msg_id INT8,request_id INT8,msg_time INT8,msg_status INTEGER,msg_fetch_type INTEGER default 0,content_type INTEGER,msg_body BLOB,UNIQUE(session_id,msg_id,request_id));");
    db.execSQL("CREATE TABLE IF NOT EXISTS trip_detail(_id integer primary key autoincrement,trip_id text,rec_first_get_off_index integer default 0,first_get_off_index integer default 0,price_accuracy int default -1,price_desc text,temp_price_1 float default 0,temp_price_2 float default 0,pay_info_1 blob,pay_info_2 blob,base_total_fee_1 float default -1,base_total_fee_2 float default -1,clicked_getoff_other_psg integer default -1,UNIQUE(trip_id));");
    db.execSQL("CREATE TABLE IF NOT EXISTS trip_order(_id integer primary key autoincrement,trip_id text,type integer default 0,oid_1 text,oid_2 text,text text,bonus integer default 0,score integer default 0,price integer default 0,time INT8,UNIQUE(trip_id));");
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    //if (newVersion == 2){
    //  if (oldVersion == 1){
        dropTable(db, "orders");
     // }
    //}
  }

  private void dropTable(SQLiteDatabase database, String tableName) {
    database.execSQL("DROP TABLE IF EXISTS " + tableName);
    onCreate(database);
  }
}
