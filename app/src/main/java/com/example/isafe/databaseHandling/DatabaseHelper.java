package com.example.isafe.databaseHandling;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String PARTICIPANT_TABLE = "PARTICIPANT_TABLE";
    public static final String COLUMN_PARTICIPANT_NAME = "PARTICIPANT_NAME";
    public static final String COLUMN_PARTICIPANT_NUMBER = "PARTICIPANT_NUMBER";
    public static final String PARTICIPANT_ID = "ID";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "participants.db", null, 1);
    }

    //This is called the first time database is accessed. There should be code in here to create a new Database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatements = "CREATE TABLE " + PARTICIPANT_TABLE + "(" + PARTICIPANT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PARTICIPANT_NAME + " TEXT, " + COLUMN_PARTICIPANT_NUMBER + " TEXT)";
        sqLiteDatabase.execSQL(createTableStatements);
    }

    //This is called when database version number changes. It prevents previous users apps from breaking when you change the database design.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(ParticipantModel participantModel) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase(); //getWritableDatabase() for write actions and getReadableDatabase() for read actions
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PARTICIPANT_NAME, participantModel.getName());
        cv.put(COLUMN_PARTICIPANT_NUMBER, participantModel.getNumber());

        long insert = sqLiteDatabase.insert(PARTICIPANT_TABLE, null, cv);
        if(insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<ParticipantModel> getAll() {
        List<ParticipantModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM  " + PARTICIPANT_TABLE;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            do {
                int participantId = cursor.getInt(0);
                String participantName = cursor.getString(1);
                String participantNumber = cursor.getString(2);

                ParticipantModel participantModel = new ParticipantModel(participantId, participantName, participantNumber);
                returnList.add(participantModel);

            } while (cursor.moveToNext());

        } else {}

        cursor.close();
        sqLiteDatabase.close();

        return returnList;
    }

    public boolean deleteTitle(String name)
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return sqLiteDatabase.delete(PARTICIPANT_TABLE, name + "=?", new String[]{name}) > 0;
    }

    public void deleteOne(List<ParticipantModel> all, final int pos) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.delete(PARTICIPANT_TABLE,"PARTICIPANT_NUMBER=?",new String[]{all.get(pos).getNumber()});
    }
}
