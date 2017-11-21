package com.floow.maplocation.DatabaseManagers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.floow.maplocation.LocationManagers.RecordLocations;

public class DataBaseHelper extends SQLiteOpenHelper
{

    public DataBaseHelper(Context context, String name, CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase _db)
    {
        _db.execSQL(RecordLocations.LocationItemTable);
        _db.execSQL(RecordLocations.JourneyTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion)
    {
        _db.execSQL("DROP TABLE IF EXISTS " + "TEMPLATE");
        onCreate(_db);
    }

}