package com.salvinien.seccomdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by laurent.salvinien on 2014-09-21.
 */
public class SecComDBHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME    = "seccom.db";
    private static final int    DATABASE_VERSION = 1;

    //CTOR
    public SecComDBHelper(Context context)
    { super(context, DATABASE_NAME, null, DATABASE_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        // Database creation sql statement
        String TABLE_KEY = "create table sckeys ( contactId integer primary key, publicKey BLOB )";
        String TABLE_ME = "create table me ( id integer primary key, password BLOB, privateKey BLOB )";

        database.execSQL(TABLE_KEY);
        database.execSQL(TABLE_ME);

        database.execSQL("INSERT INTO me (id ) VALUES (1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.w(SecComDBHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS sckeys");
        db.execSQL("DROP TABLE IF EXISTS me");

        onCreate(db);
    }
}
