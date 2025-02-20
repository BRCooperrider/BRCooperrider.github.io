package com.example.project_2_bryce_cooperrider_inventory_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InventoryHelper extends SQLiteOpenHelper {

    //Variable declaration
    private static final String DATABASE_NAME = "Inventory.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "inventory";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_QUANTITY = "quantity";

    public static final String COLUMN_DESCRIPTION = "description";

    public static final String SORT_BY_NAME_ASC = "name_asc";

    public static final String SORT_BY_NAME_DESC = "name_desc";

    public static final String SORT_BY_QUANTITY_ASC = "quantity_asc";

    public static final String SORT_BY_QUANTITY_DESC = "quantity_desc";

    //Constructor for the InventoryHelper
    public InventoryHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creates the database using sqlite
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_QUANTITY + " INTEGER,"
                + COLUMN_DESCRIPTION + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    //Drops the existing database and creates a new one when needed
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Adds a new item to the inventory
    public boolean addItem(String name, int quantity, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_DESCRIPTION, description);

        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    //Retrieves all items from the inventory
    public Cursor getAllItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    //Updates an existing item
    public boolean updateItem(int id, String name, int quantity, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_DESCRIPTION, description);

        int result = db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    //Deletes an item by name
    public boolean deleteItemByName(String itemName) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_NAME, COLUMN_NAME + "=?", new String[]{itemName});
        return rowsAffected > 0;
    }

    // Delete an item by id
    public boolean deleteItem(int itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COLUMN_ID + " = ?";
        String[] whereArgs = new String[]{String.valueOf(itemId)};
        int rowsAffected = db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();
        return rowsAffected > 0;
    }

    //Get Sorted Items
    public Cursor getSortedItems(String sortOrder) {

        SQLiteDatabase db = this.getReadableDatabase();
        String orderBy = null;

        switch (sortOrder) {
            case SORT_BY_NAME_ASC:
                orderBy = COLUMN_NAME + " ASC";
                break;
            case SORT_BY_NAME_DESC:
                orderBy = COLUMN_NAME + " DESC";
                break;
            case SORT_BY_QUANTITY_ASC:
                orderBy = COLUMN_QUANTITY + " ASC";
                break;
            case SORT_BY_QUANTITY_DESC:
                orderBy = COLUMN_QUANTITY + " DESC";
                break;
        }

        return db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                orderBy
        );
    }

    //Get Filtered Items
    public Cursor getFilteredItems(String filterText) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_NAME + " Like ? ";
        String[] selectionArgs = new String[]{"%" + filterText + "%"};
        String sortOrder = COLUMN_NAME + " ASC";

        return db.query(
                TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }
}