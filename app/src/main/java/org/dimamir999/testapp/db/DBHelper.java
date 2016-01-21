package org.dimamir999.testapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by dimamir999 on 18.01.16.
 */
public class DBHelper extends SQLiteOpenHelper{

    private final static String DB_NAME = "GeoPhotos";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.d("dimamir999", "Creation of database");
        database.execSQL("create table photos ("
                + "id integer primary key autoincrement,"
                + "path text,"
                + "longitude real,"
                + "latitude real,"
                + "date integer"
                + ");");
        database.execSQL("create table distances ("
                + "id integer primary key autoincrement,"
                + "distance real,"
                + "date integer"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
