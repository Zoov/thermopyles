package com.salvinien.seccomdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by laurent.salvinien on 2014-09-21.
 */
public class ScKeysSource
{


    // Members
    private SQLiteDatabase database;
    private SecComDBHelper dbHelper;


    //Ctor
    public ScKeysSource(Context context)
    { dbHelper = new SecComDBHelper(context); }


    //Methods
    public void open() throws SQLException
    { database = dbHelper.getWritableDatabase(); }

    public void close() { dbHelper.close(); }


    public byte[] getPublicKeyById(int aContactId)
    {
        String query = "SELECT  publicKey FROM sckeys where contactId = " + aContactId;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        if(cursor == null) { return null; }

        byte[] aKey = cursor.getBlob(0);

        cursor.close();

        return aKey;
    }


    public void savePublicKey(int aContactId, byte[] aPublicKey)
    {
        //let's check if this contact has already a public key registered
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues v = new ContentValues();
        v.put("contactId", aContactId);
        v.put("publicKey", aPublicKey);

        db.insertWithOnConflict("sckeys", "contactId", v, SQLiteDatabase.CONFLICT_REPLACE);

    }

    public void deleteKey(int aContactId)
    {
        database.delete("sckeys", " contactId= " + aContactId, null);
    }


}
