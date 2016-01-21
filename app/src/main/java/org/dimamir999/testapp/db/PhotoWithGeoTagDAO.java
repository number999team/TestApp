package org.dimamir999.testapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.dimamir999.testapp.model.PhotoWithGeoTag;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by dimamir999 on 18.01.16.
 */
public class PhotoWithGeoTagDAO {

    //should  be one object of database
    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;

    public PhotoWithGeoTagDAO(Context context) {
        this.dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public void add(PhotoWithGeoTag photoObject){
        ContentValues contentValues = new ContentValues();
        contentValues.put("path", photoObject.getPath());
        contentValues.put("date", photoObject.getDate().getTime());
        contentValues.put("latitude", photoObject.getLatitude());
        contentValues.put("longitude", photoObject.getLongitude());
        // вставляем запись и получаем ее ID
        long rowID = database.insert("photos", null, contentValues);
        Log.d("dimamir999", "1 row inserted to photos table");
    }

    public void delete(int id){

    }

    //test this method
    public Cursor getBetweenDates(Date startDate, Date endDate){
        String selectionString = "date < ? & date > ?";
        String[] selectionArgs = new String[]{String.valueOf(startDate.getTime()), String.valueOf(endDate.getTime())};
        Cursor cursor = database.query("photos", null,selectionString , selectionArgs, null, null, null);
        return  cursor;
    }
}
