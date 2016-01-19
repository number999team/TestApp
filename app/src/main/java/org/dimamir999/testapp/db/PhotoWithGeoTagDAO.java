package org.dimamir999.testapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.dimamir999.testapp.model.PhotoWithGeoTag;

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
        contentValues.put("date", photoObject.getDate().toString());
        contentValues.put("latitude", photoObject.getLatitude());
        contentValues.put("longitude", photoObject.getLongitude());
        // вставляем запись и получаем ее ID
        long rowID = database.insert("mytable", null, contentValues);
        Log.d("dimamir999", "");
    }

    public void delete(int id){

    }

    public PhotoWithGeoTag get(int id){

        return  null;
    }
}
