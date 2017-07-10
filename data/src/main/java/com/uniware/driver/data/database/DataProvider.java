package com.uniware.driver.data.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import java.util.HashMap;

/**
 * Created by jian on 15/12/04.
 */
public class DataProvider extends ContentProvider {

  // Handle to a new DatabaseHelper.
  private DatabaseHelper mOpenHelper;

  private static final int ORDER = 1;
  private static final int ORDER_ID = 2;

  /**
   * A UriMatcher instance
   */
  private static final UriMatcher sUriMatcher;
  /**
   * A projection map used to select columns from the database
   */
  private static HashMap<String, String> sOrderProjectionMap;

  static {
    // Create a new instance
    sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Add a pattern that routes URIs terminated with "order" to a order operation
    sUriMatcher.addURI(Provider.AUTHORITY, "orders", ORDER);

    // Add a pattern that routes URIs terminated with "order" plus an integer
    // to a order ID operation
    sUriMatcher.addURI(Provider.AUTHORITY, "orders/#", ORDER_ID);

    sOrderProjectionMap = new HashMap<>();
    sOrderProjectionMap.put(Provider.Orders.COLUMN_OID, Provider.Orders.COLUMN_OID);
    sOrderProjectionMap.put(Provider.Orders.COLUMN_STATUS, Provider.Orders.COLUMN_STATUS);
    sOrderProjectionMap.put(Provider.Orders.COLUMN_TYPE, Provider.Orders.COLUMN_TYPE);
    sOrderProjectionMap.put(Provider.Orders.COLUMN_TIME, Provider.Orders.COLUMN_TIME);
    sOrderProjectionMap.put(Provider.Orders.COLUMN_FROM_ADDR, Provider.Orders.COLUMN_FROM_ADDR);
    sOrderProjectionMap.put(Provider.Orders.COLUMN_TO_ADDR, Provider.Orders.COLUMN_TO_ADDR);
    sOrderProjectionMap.put(Provider.Orders.COLUMN_FROM_LNG, Provider.Orders.COLUMN_FROM_LNG);
    sOrderProjectionMap.put(Provider.Orders.COLUMN_FROM_LAT, Provider.Orders.COLUMN_FROM_LAT);
    sOrderProjectionMap.put(Provider.Orders.COLUMN_TO_LNG, Provider.Orders.COLUMN_TO_LNG);
    sOrderProjectionMap.put(Provider.Orders.COLUMN_TO_LAT, Provider.Orders.COLUMN_TO_LAT);
    sOrderProjectionMap.put(Provider.Orders.COLUMN_PHONE, Provider.Orders.COLUMN_PHONE);
    sOrderProjectionMap.put(Provider.Orders.COLUMN_FREE, Provider.Orders.COLUMN_FREE);
    sOrderProjectionMap.put(Provider.Orders.COLUMN_description, Provider.Orders.COLUMN_description);
  }

  @Override public boolean onCreate() {
    mOpenHelper = new DatabaseHelper(getContext());
    return true;
  }

  @Nullable @Override
  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
      String sortOrder) {
    // Constructs a new query builder and sets its table name
    SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
    String orderBy;
    switch (sUriMatcher.match(uri)) {
      case ORDER:
        qb.setTables(Provider.Orders.TABLE_NAME);
        qb.setProjectionMap(sOrderProjectionMap);
        orderBy = TextUtils.isEmpty(sortOrder) ? Provider.Orders.DEFAULT_SORT_ORDER : sortOrder;
        break;
      case ORDER_ID:
        qb.setTables(Provider.Orders.TABLE_NAME);
        qb.setProjectionMap(sOrderProjectionMap);
        qb.appendWhere(Provider.Orders._ID + "=" + uri.getPathSegments().get(1));
        orderBy = TextUtils.isEmpty(sortOrder) ? Provider.Orders.DEFAULT_SORT_ORDER : sortOrder;
        break;
      default:
        // If the URI doesn't match any of the known patterns, throw an exception.
        throw new IllegalArgumentException("Unknown URI " + uri);
    }

    // Opens the database object in "read" mode, since no writes need to be done.
    SQLiteDatabase db = mOpenHelper.getReadableDatabase();
    /**
     * Performs the query. If no problems occur trying to read the database, then a Cursor
     * object is returned; otherwise, the cursor variable contains null. If no records were
     * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
     */
    Cursor c = qb.query(db, // The database to query
        projection,         // The columns to return from the query
        selection,          // The columns for the where clause
        selectionArgs,      // The values for the where clause
        null,               // don't group the rows
        null,               // don't filter by row groups
        orderBy             // The sort order
    );

    // Tells the Cursor what URI to watch, so it knows when its source data changes
    c.setNotificationUri(getContext().getContentResolver(), uri);
    return c;
  }

  @Nullable @Override public String getType(Uri uri) {
    int match = sUriMatcher.match(uri);
    switch (match) {
      case ORDER:
        return Provider.Orders.CONTENT_TYPE;
      case ORDER_ID:
        return Provider.Orders.CONTENT_ITEM_TYPE;
      default:
        throw new IllegalArgumentException("Unknown URI " + uri);
    }
  }

  @Nullable @Override public Uri insert(Uri uri, ContentValues values) {
    // A map to hold the new record's values.
    String tableName;
    Uri tableUri;
    switch (sUriMatcher.match(uri)) {
      case ORDER:
        tableName = Provider.Orders.TABLE_NAME;
        tableUri = Provider.Orders.CONTENT_URI;
        break;
      default:
        // If the URI doesn't match any of the known patterns, throw an exception.
        throw new IllegalArgumentException("Unknown URI " + uri);
    }
    // Opens the database object in "write" mode.
    SQLiteDatabase db = mOpenHelper.getWritableDatabase();
    // Performs the insert and returns the ID of the new note.
    long rowId = db.insert(tableName,        // The table to insert into.
        null,  // A hack, SQLite sets this column value to null
        // if values is empty.
        values                           // A map of column names, and the values to insert
        // into the columns.
    );

    // If the insert succeeded, the row ID exists.
    if (rowId > 0) {
      // Creates a URI with the note ID pattern and the new row ID appended to it.
      tableUri = ContentUris.withAppendedId(tableUri, rowId);
      // Notifies observers registered against this provider that the data changed.
      getContext().getContentResolver().notifyChange(tableUri, null);
      return tableUri;
    }

    // If the insert didn't succeed, then the rowID is <= 0. Throws an exception.
    throw new SQLException("Failed to insert row into " + uri);
  }

  @Override public int bulkInsert(Uri uri, ContentValues[] values) {
    return super.bulkInsert(uri, values);
  }

  @Override public int delete(Uri uri, String selection, String[] selectionArgs) {
    SQLiteDatabase db = mOpenHelper.getWritableDatabase();
    int count;
    switch (sUriMatcher.match(uri)) {
      case ORDER:
        count = db.delete(Provider.Orders.TABLE_NAME, selection, selectionArgs);
        break;
      default:
        // If the URI doesn't match any of the known patterns, throw an exception.
        throw new IllegalArgumentException("Unknown URI " + uri);
    }
    getContext().getContentResolver().notifyChange(uri, null);
    return count;
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    SQLiteDatabase db = mOpenHelper.getWritableDatabase();
    int count;
    switch (sUriMatcher.match(uri)) {
      case ORDER:
        count = db.update(Provider.Orders.TABLE_NAME, values, selection, selectionArgs);
        break;
      default:
        // If the URI doesn't match any of the known patterns, throw an exception.
        throw new IllegalArgumentException("Unknown URI " + uri);
    }
    getContext().getContentResolver().notifyChange(uri, null);
    return count;
  }
}
