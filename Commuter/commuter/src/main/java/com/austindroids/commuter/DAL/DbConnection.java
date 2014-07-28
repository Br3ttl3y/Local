package com.austindroids.commuter.DAL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Brett on 7/27/2014.
 */
public class DbConnection extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Commuter";
    private static SQLiteDatabase db;

    public DbConnection(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getWritableDatabase();
    }

    public static SQLiteDatabase GetDb() { return db; }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO Add more tables as they are created.
        db.execSQL(DbQueries.CreateRouteTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DbQueries.DropAllTables();
        onCreate(db);
    }
}
