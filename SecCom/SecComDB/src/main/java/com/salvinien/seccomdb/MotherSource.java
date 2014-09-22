package com.salvinien.seccomdb;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by laurent.salvinien on 2014-09-22.
 */
public class MotherSource
{

    // Members
    private SQLiteDatabase database;
    private SecComDBHelper dbHelper;


    //Ctor
    //context is the activity that is instanciating ScKeysSource
    public MotherSource(Context context)
    { dbHelper = new SecComDBHelper(context); }


    //Methods
    protected SecComDBHelper getHelper()
    { return dbHelper;}

    protected SQLiteDatabase getDB() { return database;}

    public void open() throws SQLException { database = dbHelper.getWritableDatabase(); }

    public void close() { dbHelper.close(); }

}
