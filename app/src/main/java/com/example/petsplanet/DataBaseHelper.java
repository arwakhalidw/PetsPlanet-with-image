package com.example.petsplanet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String Post_TABLE = "Post_TABLE";
    public static final String COLUMN_POST= "COLUMN_POST";
    public static final String COLUMN_ID = "ID";
    public static final String COL_IMAGE = "image";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "post.db", null, 1);
    }
    // when creating the database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "Create TABLE " + Post_TABLE + "  ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_IMAGE + " BLOB, " +
                COLUMN_POST + " TEXT )";
        sqLiteDatabase.execSQL(createTable);
    }
    // when upgrading
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Post_TABLE );
        onCreate(db);
    }
    public boolean addOne(Post post){
        Log.e("my error", "ghfh gh hgfhgfhfhfghgfh gfh fh gfhgf ghgfhgfhgfh ");

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_POST, post.getPost());
        Bitmap img=post.getImage();
        Log.e("my error", String.valueOf(img.getWidth()));

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 0, stream);
        cv.put(COL_IMAGE, stream.toByteArray());


        long insert = db.insert(Post_TABLE, null, cv);
        if(insert == -1){
            return false;
        }
        else {
            return true;
        }
    }
    public boolean DeleteOne(Post post){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString= "Delete From " + Post_TABLE + " WHERE " +
                COLUMN_ID + " = " + post.getId() ;
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            return true;
        } else{
            // nothing happens. no one is added.
            return false;
        }
        //close
    }


    public void editOne(Post clickedStudent, String st2) {
        clickedStudent.setPost(st2);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_POST, clickedStudent.getPost());
        String whereClause = COLUMN_ID + " = ?";
        String[] whereArgs = {String.valueOf(clickedStudent.getId())};

        int rowCount = db.update(Post_TABLE, cv, whereClause, whereArgs);
    }


    public List<Post> getEveryone(){
        List<Post> returnList = new ArrayList<>();
        // get data from database
        String queryString = "Select * from "+ Post_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            // loop through cursor results
            do{
                int SID = cursor.getInt(0); // student ID
                String SName = cursor.getString(1);
                byte[] imageBytes = cursor.getBlob(2);
                Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                Post newPost = new Post(image, SName, SID);
                returnList.add(newPost);
            }while (cursor.moveToNext());
        } else{
            // nothing happens. no one is added.
        }
        //close
        cursor.close();
        db.close();
        return returnList;
    }
}