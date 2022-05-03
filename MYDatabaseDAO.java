package com.example.mydemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MYDatabaseDAO extends SQLiteOpenHelper{


    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String NAME = "NAME";
    public static final String AGE = "AGE";
    public static final String STATUS = "STATUS";
    public static final String ID = "ID";

    public MYDatabaseDAO(@Nullable Context context) {
        super(context, "Customer.db", null, 1);

    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {

        String statement = "CREATE TABLE " + CUSTOMER_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + NAME + " TEXT , " + AGE + " INT , " + STATUS + " BOOL)";
        String state = "CREATE TABLE EMPOO (ID INTEGER PRIMARY KEY AUTOINCREMENT , NAME TEXT )";
        db.execSQL(statement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean Addthis(@NonNull CustomerModel customer){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NAME, customer.getName());
        cv.put(AGE, customer.getAge());
        cv.put(STATUS , customer.isActive());

        long insert = db.insert(CUSTOMER_TABLE, null, cv);

        if(insert ==-1){
            return  false;
        }else{
            return  true;
        }

    }
    public List<CustomerModel> getALLs(){
        List<CustomerModel> returnList = new ArrayList<>();

        String alldata = "SELECT * FROM " +CUSTOMER_TABLE;

        SQLiteDatabase DB = this.getReadableDatabase();

        Cursor cursor = DB.rawQuery(alldata,null);

        if(cursor.moveToFirst()){
            do{
                int ID = cursor.getInt(0);
                String name = cursor.getString(1);
                int age = cursor.getInt(2);
                boolean status = cursor.getInt(3) ==1 ? true : false;

                CustomerModel newone = new CustomerModel(ID,name,age,status);
                returnList.add(newone);

            }while (cursor.moveToNext());


        }else{

        }


        DB.close();
        cursor.close();
        return  returnList;

    }


    public boolean deleteme(CustomerModel c){
        SQLiteDatabase db = this.getWritableDatabase();

        String deleter = "DELETE FROM " + CUSTOMER_TABLE + " WHERE " + ID + " = " + c.getId();

        Cursor cursor = db.rawQuery(deleter, null);

        if(cursor.moveToFirst()){
            return true;
        }else {
            return  false;
        }

    }

}
