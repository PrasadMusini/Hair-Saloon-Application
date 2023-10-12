package com.example.practice.crud.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PRACTICE.db";
    private static final String ID = "Id";
    private static final String TABLE_NAME = "Student";
    public static final String USER_NAME = "Name";
    private static final String USER_EMAIL = "Email";
    private static final String USER_LOCATION = "Location";
    private static final int VERSION = 1;

    private RecyclerView.Adapter<Recycler_Adopter.ViewChild> adapter; // Add this adapter reference

    SQLiteDatabase dbWrite = getWritableDatabase();
    SQLiteDatabase dbRead = getReadableDatabase();

    public DbHelper(@Nullable Context context, RecyclerView.Adapter<Recycler_Adopter.ViewChild> adapter) {
        super(context, DATABASE_NAME, null, VERSION);
        this.adapter = adapter;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_NAME + " TEXT, " + USER_EMAIL + " TEXT, " + USER_LOCATION + " TEXT" + ");";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String email, String location) {

        ContentValues values = new ContentValues();
        values.put(USER_NAME, name);
        values.put(USER_EMAIL, email);
        values.put(USER_LOCATION, location);

        long result = dbWrite.insert(TABLE_NAME, null, values);

        return result == -1 ? false : true;
    }

    public Cursor getUsers() {
        String[] columns = {ID, USER_NAME, USER_EMAIL, USER_LOCATION};
        String sortByName = USER_NAME+" ASC"; // sorting users by names

        return dbRead.query(TABLE_NAME, columns, null, null, null, null, sortByName);
    }

    public boolean updateUser(User user) {
        ContentValues values = new ContentValues();
        values.put(USER_NAME, user.getName());
        values.put(USER_EMAIL, user.getEmail());
        values.put(USER_LOCATION, user.getLocation());

        String whereClause = ID + " = ?";
        String[] whereArgs = {String.valueOf(user.getId())};

        int rowsUpdated = dbWrite.update(TABLE_NAME, values, whereClause, whereArgs);

        if (rowsUpdated > 0) {
            // The update was successful
            return true;
        } else {
            // No records were updated
            return false;
        }
    }

    public boolean deleteUser(int id) {
        String whereClause = ID + " = ?";
        String user_id = String.valueOf(id);
        String[] whereArgs = new String[]{user_id};

        int result = dbWrite.delete(TABLE_NAME, whereClause, whereArgs);
//        adapter.notifyItemRemoved(Integer.parseInt(id));
        if (result > 0) {
            // Notify the adapter that an item has been removed
            int position = Integer.parseInt(user_id);
            if (position >= 0) {

            }
            return true;
        } else {
            // No records were deleted
            return false;
        }
    }

    public boolean getUserByEmail(String email){

        String [] columns = {ID, USER_NAME, USER_EMAIL, USER_LOCATION};
        String selection = USER_EMAIL+" = ?";
        String [] selectionArgs = {email};

        Cursor user = dbRead.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        boolean userExists = (user != null && user.getCount() > 0);

        // Close the cursor to release resources
        if (user != null) {
            user.close();
        }

        return userExists;
    }


    public void setAdapter(RecyclerView.Adapter<Recycler_Adopter.ViewChild> adapter) {
        this.adapter = adapter;
    }

}
