package com.uniware.driver.data.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jian on 15/12/04.
 */
public class Provider {
  /** The authority for the contacts provider */
  public static final String AUTHORITY = "com.uniware.driver";

  /** A content:// style uri to the authority for the contacts provider */
  public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

  public static final class Orders implements BaseColumns {
    /**
     * This utility class cannot be instantiated
     */
    private Orders() {
    }

    /**
     * The content:// style URI for this table
     */
    public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "orders");
    /**
     * The table name offered by this provider
     */
    public static final String TABLE_NAME = "orders";
    /**
     * The MIME type of {@link #CONTENT_URI} providing a directory of
     * people.
     */
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/driver_order";

    /**
     * The MIME type of a {@link #CONTENT_URI} item.
     */
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/driver_order";

    /**
     * The default sort orders for this table
     */
    public static final String DEFAULT_SORT_ORDER = "_id DESC";

    /**
     * Column definitions
     */
    public static final String COLUMN_OID = "oid";

    public static final String COLUMN_STATUS = "status";

    public static final String COLUMN_TYPE = "type";

    public static final String COLUMN_TIME = "time";

    public static final String COLUMN_FROM_ADDR = "from_addr";

    public static final String COLUMN_TO_ADDR = "to_addr";

    public static final String COLUMN_FROM_LNG = "from_lng";

    public static final String COLUMN_FROM_LAT = "from_lat";

    public static final String COLUMN_TO_LNG = "to_lng";

    public static final String COLUMN_TO_LAT = "to_lat";

    public static final String COLUMN_PHONE = "phone";

    public static final String COLUMN_FREE= "callfree";

    public static final String COLUMN_description= "description";
  }
}
