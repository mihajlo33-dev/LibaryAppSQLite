package com.example.libaryappsqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DBNAME = "users.db";
    private Context context;


    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USERS(ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT)");
        db.execSQL("CREATE TABLE BOOKS(ID INTEGER PRIMARY KEY AUTOINCREMENT, BOOKNAME TEXT, BOOKAUTHOR TEXT, BOOKPRICE TEXT, BOOKDESC TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USERS");
        db.execSQL("DROP TABLE IF EXISTS BOOKS");
    }

    // User FUNCTIONS
    public Boolean addUser(String username,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username",username);
        values.put("password",password);

        long result = db.insert("USERS",null,values);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }


    public Boolean checkUsername(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=?",new String[] {username});
        if(cursor.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }

    public Boolean checkUsernamePassword(String username,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=? AND password=?",new String[] {username,password});
        if(cursor.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }

    //BOOKS FUNCTIONS
    void addBook(String bookName,String bookAuthor,String bookPrice,String bookDesc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("bookname",bookName);
        cv.put("bookauthor",bookAuthor);
        cv.put("bookprice",bookPrice);
        cv.put("bookdesc",bookDesc);
        long result = db.insert("BOOKS",null,cv);

        if(result == -1){
            Toast.makeText(context,"Book isn't inserted!",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Book added!",Toast.LENGTH_SHORT).show();
        }
    }

    Cursor getBooks(){
        String query = "SELECT * FROM BOOKS";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    void updateBook(String bookId,String bookName,String bookAuthor,String bookPrice,String bookDesc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("bookname",bookName);
        cv.put("bookauthor",bookAuthor);
        cv.put("bookprice",bookPrice);
        cv.put("bookdesc",bookDesc);

        long result = db.update("BOOKS",cv,"id=?",new String[]{bookId});

        if(result == -1){
            Toast.makeText(context,"Book isn't updated!",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Book is updated!",Toast.LENGTH_SHORT).show();
        }

    }

    void deleteBook(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("BOOKS","id=?",new String[] {id});

        if(result == -1){
            Toast.makeText(context,"Book isn't deleted!",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Book is deleted!",Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM BOOKS");
    }

}
