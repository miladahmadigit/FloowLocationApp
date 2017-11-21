package com.floow.maplocation.LocationManagers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.floow.maplocation.DatabaseManagers.DataBaseHelper;
import com.floow.maplocation.Model.Journey;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Milad on 11/20/2017.
 */

public class RecordLocations
{
    private IRecordLocation iRecordLocation = null;
    private int maxPrimaryKeyOfJourney = 0;

    private String DATABASE_NAME = "FloowJourney.db";
    private int DATABASE_VERSION = 1;
    private SQLiteDatabase db;
    private Context context;
    private DataBaseHelper dbHelper;

    //Journey table
    public static final String JourneyTableName = "Journey";
    public static final String column_PK_Journey = "PK_Journey";
    private static final String column_nameOfJourney = "nameOfJourney";
    private static final String column_startTimeOfJourney = "startTimeOfJourney";
    private static final String column_endTimeOfJourney = "endTimeOfJourney";
    private static final String column_zoom = "zoom";

    //LocationItem table
    private static final String LocationItemTableName = "LocationItem";
    private static final String column_PK_LocationItem = "PK_LocationItem";
    private static final String column_FK_Journey = "FK_Journey";
    private static final String column_lat = "lat";
    private static final String column_lng = "lng";


    public RecordLocations(IRecordLocation iRecordLocation_, Context context)
    {
        this.iRecordLocation = iRecordLocation_;
        this.context = context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

    //create table Journey
    public static final String JourneyTable = "create table " + JourneyTableName + " ( "
            + column_PK_Journey + " integer primary key autoincrement,"
            + column_nameOfJourney + " text,"
            + column_startTimeOfJourney + " text,"
            + column_endTimeOfJourney + " text,"
            + column_zoom + " integer); ";


    //create table LocationItem
    public static final String LocationItemTable = "create table " + LocationItemTableName + "( "
            + column_PK_LocationItem + " integer primary key autoincrement,"
            + column_FK_Journey + " integer,"
            + column_lat + " real,"
            + column_lng + " real); ";


    public void insertLocationItem(double lat, double lng)
    {
        ContentValues newValues = new ContentValues();
        newValues.put(column_FK_Journey, maxPrimaryKeyOfJourney);
        newValues.put(column_lat, lat);
        newValues.put(column_lng, lng);
        db.insert(LocationItemTableName, null, newValues);
    }

    public void insertJourney(int zoom)
    {
        String startTimeOfJourney = DateFormat.getTimeInstance().format(new Date());
        maxPrimaryKeyOfJourney = Integer.parseInt(maxNumber(column_PK_Journey, JourneyTableName));
        ContentValues newValues = new ContentValues();
        newValues.put(column_nameOfJourney, "Journey_" + maxPrimaryKeyOfJourney);
        newValues.put(column_startTimeOfJourney, startTimeOfJourney);
        newValues.put(column_endTimeOfJourney, "0");
        newValues.put(column_zoom, zoom);
        db.insert(JourneyTableName, null, newValues);
    }


    /**
     * get max number of journey table
     *
     * @return
     */
    public String maxNumber(String columnName, String tableName)
    {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select max(" + columnName + ") from " + tableName + " ", null);

        cursor.moveToFirst();
        String s1 = String.valueOf(cursor.getInt(0)+1);
        cursor.close();
        return s1;
    }


    /**
     * update endTimeOfJourneys on last record on journey's table
     *
     * @return
     */
    public void updateJourney()
    {
        String endTimeOfJourney = DateFormat.getTimeInstance().format(new Date());
        db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(column_endTimeOfJourney, endTimeOfJourney);
        db.update(JourneyTableName, contentValues, column_PK_Journey + "= ? ", new String[]{Integer.toString(maxPrimaryKeyOfJourney)});
    }

    /**
     * get all journeys from journey table
     */
    public void getListOfjourneys()
    {
        db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + JourneyTableName, null);
        List<Journey> listOfJourneys = new ArrayList<>();
        if (c.moveToFirst())
        {
            do
            {
                Journey journey = new Journey();
                journey.setPK_Journey(c.getInt(c.getColumnIndex(column_PK_Journey)));
                journey.setNameOfJourney(c.getString(c.getColumnIndex(column_nameOfJourney)));
                journey.setStartTimeOfJourney(c.getString(c.getColumnIndex(column_startTimeOfJourney)));
                journey.setEndTimeOfJourney(c.getString(c.getColumnIndex(column_endTimeOfJourney)));
                journey.setZoom(c.getInt(c.getColumnIndex(column_zoom)));
                listOfJourneys.add(journey);

            }
            while (c.moveToNext());
        }
        c.close();
        db.close();
        iRecordLocation.journeyListBackFromRecordLocation(listOfJourneys);

    }


    /**
     * open database
     *
     * @return
     * @throws SQLException
     */
    public RecordLocations open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    /**
     * close database
     */
    public void close()
    {
        db.close();
    }

    /**
     * delete table content
     *
     * @param tableName
     */
    public void deleteTableContent(String tableName)
    {
        db = dbHelper.getWritableDatabase();
        db.delete(tableName, null, null);
    }


    /**
     * delete database
     *
     * @param context
     */
    public void deletDatabase(Context context)
    {
        context.deleteDatabase(DATABASE_NAME);
    }


}
