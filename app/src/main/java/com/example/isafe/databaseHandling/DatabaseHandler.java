package com.example.isafe.databaseHandling;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyList.db";
    public static final String TABLE_NAME = "MyList.db";
    public static final String COL1 = "ID";
    public static final String COL2 = "ITEM";

    public DatabaseHandler(Context context) { super(context,DATABASE_NAME,null,1);}
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String creatTable = "CREATE TABLE"+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,";

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
