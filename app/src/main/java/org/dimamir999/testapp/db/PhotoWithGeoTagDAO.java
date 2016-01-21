package org.dimamir999.testapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Pair;

import org.dimamir999.testapp.model.PhotoWithGeoTag;

import java.util.ArrayList;
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
    public ArrayList<PhotoWithGeoTag> getBetweenDates(Date startDate, Date endDate){
        Cursor c = database.query("photos", null, null, null, null, null, null);

        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("path");
            int dateColIndex = c.getColumnIndex("date");

            do {
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d("dimamir999",
                        "ID = " + c.getInt(idColIndex) +
                                ", path = " + c.getString(nameColIndex)
                                +
                                ", date = " + new Date(c.getLong(dateColIndex)));
            } while (c.moveToNext());
        } else
            Log.d("dimamir999", "0 rows");
        c.close();

        ArrayList<PhotoWithGeoTag> result = new ArrayList<PhotoWithGeoTag>();
        String selectionString = "date > ? AND date < ?";
        String[] selectionArgs = new String[]{String.valueOf(startDate.getTime()), String.valueOf(endDate.getTime())};
        Cursor cursor = database.query("photos", null,selectionString , selectionArgs, null, null, null);

        Log.d("dimamir999", "my query " + startDate + " " + startDate);
        if (cursor.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = cursor.getColumnIndex("id");
            int pathColIndex = cursor.getColumnIndex("path");
            int dateColIndex = cursor.getColumnIndex("date");
            int latitudeColIndex = cursor.getColumnIndex("latitude");
            int longitudeColIndex = cursor.getColumnIndex("longitude");

            do {
                PhotoWithGeoTag photoObject = new PhotoWithGeoTag(cursor.getLong(idColIndex), cursor.getLong(dateColIndex),
                        cursor.getString(pathColIndex), cursor.getDouble(longitudeColIndex), cursor.getDouble(latitudeColIndex));
                result.add(photoObject);
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d("dimamir999",
                        "ID = " + cursor.getInt(idColIndex) +
                                ", path = " + cursor.getString(pathColIndex)
                                +
                                ", date = " + cursor.getLong(dateColIndex));
            } while (cursor.moveToNext());
        } else
            Log.d("dimamir999", "0 rows");

        return  result;
    }

}
