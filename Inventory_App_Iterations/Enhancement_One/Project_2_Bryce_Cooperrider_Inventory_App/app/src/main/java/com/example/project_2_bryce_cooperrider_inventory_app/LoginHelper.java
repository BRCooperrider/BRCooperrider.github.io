package com.example.project_2_bryce_cooperrider_inventory_app;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

public class LoginHelper extends SQLiteOpenHelper {
    //Initializes variables
    private static final String DATABASE_NAME = "User.db";
    private static final String TABLE_NAME = "user_table";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "Username";
    private static final String COL_3 = "Password";

    //Constructor
    public LoginHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //Creates the login database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT)");
    }

    //Called when the database needs to be upgraded, dropping the old and recreating it
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Inserts a new user into the database
    public boolean insertData(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, username);
        contentValues.put(COL_3, password);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    //Checks if a user exists in the database
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_2 + " = ? AND " + COL_3 + " = ?", new String[] {username, password});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        return false;
    }
}
