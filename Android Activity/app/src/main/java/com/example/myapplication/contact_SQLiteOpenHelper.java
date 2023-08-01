package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class contact_SQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME ="mySQLite.db";

    private SQLiteDatabase sqLiteDatabase;

    public contact_SQLiteOpenHelper(Context context){
        super(context,DB_NAME,null,1);
    }


    public contact_SQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        sqLiteDatabase= getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table contact(id INTEGER PRIMARY KEY AUTOINCREMENT,contactName text, Num text)");
            sqLiteDatabase.execSQL("create table user(account text primary key, password text)");
            sqLiteDatabase.execSQL("create table msg(id INTEGER PRIMARY KEY AUTOINCREMENT,type INTEGER, content text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean add(String account,String password){
        boolean judge= true;
        ArrayList<User> userData=this.getData();
        for(int i=0; i<userData.size(); i++){
            User user = userData.get(i);
            if(account.equals(user.getAccount())){
                judge=false;
                break;
            }
        }
        if (judge){
            sqLiteDatabase.execSQL("insert into user(account,password) values(?,?)", new Object[]{account,password});
        }
        return judge;
    }

    public ArrayList<User> getData(){
        ArrayList<User> userList = new ArrayList<User>();
        sqLiteDatabase=getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("user",null,null,null,null,null,"account desc");
        while(cursor.moveToNext()){
            @SuppressLint("Range") String account =cursor.getString(cursor.getColumnIndex("account"));  //禁用代码检查
            @SuppressLint("Range") String password =cursor.getString(cursor.getColumnIndex("password"));
            userList.add(new User(account,password));
        }
        cursor.close();
        return userList;
    }

    public String addContent(UserMessage userMessage){

        ContentValues cv=new ContentValues();
        cv.put("content",userMessage.getContent());
        cv.put("type",userMessage.getType());

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        long insert=sqLiteDatabase.insert("msg","id",cv);
        if(insert == -1){
            return "fail";
        }
        sqLiteDatabase.close();
        return "success";
    }

    public String addOne(ContactInfo contactInfo){

        ContentValues cv=new ContentValues();
        cv.put("contactName",contactInfo.getContactName());
        cv.put("Num",contactInfo.getNum());

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        long insert=sqLiteDatabase.insert("contact","id",cv);
        if(insert == -1){
            return "fail";
        }
        sqLiteDatabase.close();
        return "success";
    }

    public String deleteOne(ContactInfo contactInfo){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        int delete=sqLiteDatabase.delete("contact","contactName=?",new String[]{contactInfo.getContactName()});
        sqLiteDatabase.close();
        if(delete==0){
            return "fail";
        }
        return "success";
    }


    public String updateOne(ContactInfo contactInfo){

        ContentValues cv=new ContentValues();
        cv.put("contactName",contactInfo.getContactName());
        cv.put("Num",contactInfo.getNum());

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        int update=sqLiteDatabase.update("contact",cv,"id=?",new String[]{String.valueOf(contactInfo.getId())});
        if(update == 0){
            return "fail";
        }
        sqLiteDatabase.close();
        return "success";
    }


    public List<ContactInfo> getAll(){
        ContactInfo contactInfo;
        List<ContactInfo> list =new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        String sql="SELECT * FROM contact";
        Cursor cursor= sqLiteDatabase.rawQuery(sql,null);

        int idIndex =cursor.getColumnIndex("id");
        int nameIndex=cursor.getColumnIndex("contactName");
        int phoneIndex=cursor.getColumnIndex("Num");

        while ((cursor.moveToNext())){
            contactInfo=new ContactInfo(cursor.getInt(idIndex),cursor.getString(nameIndex),cursor.getString(phoneIndex));
            list.add(contactInfo);
        }
        sqLiteDatabase.close();
        return list;
    }

}
