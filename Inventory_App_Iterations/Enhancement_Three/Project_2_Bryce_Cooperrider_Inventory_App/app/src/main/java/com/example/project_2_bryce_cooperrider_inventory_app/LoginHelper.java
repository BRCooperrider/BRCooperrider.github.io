package com.example.project_2_bryce_cooperrider_inventory_app;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import org.apache.commons.validator.routines.EmailValidator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginHelper extends SQLiteOpenHelper {
    //Initializes variables
    private static final String DATABASE_NAME = "User.db";
    private static final String TABLE_NAME = "user_table";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "Username";
    private static final String COL_3 = "Password";
    private static final String TAG = "LoginHelper";

    //Constructor
    public LoginHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //Creates the login database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " TEXT UNIQUE, " +
                COL_3 + " TEXT)");
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

        if (!isValidEmail(username)) {
            Log.e(TAG, "Invalid email address");
            return false;
        }

        if (!isValidPassword(password)) {
            Log.e(TAG, "Password does not meet complexity requirements");
            return false;
        }
        try {
            contentValues.put(COL_3, hashPassword(password));
        }
        catch(NoSuchAlgorithmException e) {
            Log.e(TAG, "Error hashing password", e);
            return false;
        }
        catch(IllegalArgumentException e) {
            Log.e(TAG, "Error: ", e);
            return false;
        }
        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return result != -1;
    }

    //Checks if a user exists in the database
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String hashedPassword;
        try {
            hashedPassword = hashPassword(password);
        }
        catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "Error hashing password", e);
            return false;
        }
        catch(IllegalArgumentException e) {
            Log.e(TAG, "Error: ", e);
            return false;
        }
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_2 + " = ? AND " + COL_3 + " = ?", new String[] {username, hashedPassword});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    //Checks if a user exists in the database by ID
    public boolean checkUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_1 + " = ?", new String[]{String.valueOf(id)});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    //Hashes password using SHA-256
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        if (password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(password.getBytes());
        byte[] hash = digest.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    //Checks to see if password meets complexity requirements
    public boolean isValidPassword(String password) {
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{10,}$";

        return password.matches(pattern);
    }

    //Uses Apache commons validator to verify valid email address
    public boolean isValidEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }
}
