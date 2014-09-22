package com.salvinien.seccomdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by laurent.salvinien on 2014-09-22.
 */
public class MeSource extends MotherSource
{


    //Ctor
    //context is the activity that is instanciating ScKeysSource
    public MeSource(Context context)
    { super(context); }


    //Methods
    public byte[] getPrivateKey()
    {
        String query = "SELECT  privateKey FROM me";
        SQLiteDatabase db = getHelper().getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        if(cursor == null) { return null; }

        byte[] aKey = cursor.getBlob(0);

        cursor.close();

        return aKey;
    }

    public byte[] getPassword()
    {
        String query = "SELECT  password FROM me";
        SQLiteDatabase db = getHelper().getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        if(cursor == null) { return null; }

        byte[] aPassword = cursor.getBlob(0);

        cursor.close();

        return aPassword;
    }


    public void savePrivateKey(byte[] aPrivateKey)
    {
        //let's check if this contact has already a public key registered
        SQLiteDatabase db = getHelper().getWritableDatabase();

        ContentValues v = new ContentValues();
        v.put("privateKey", aPrivateKey);

        db.update("me", v, null, null);
    }

    public void savePassword(byte[] aPassword)
    {
        //let's check if this contact has already a public key registered
        SQLiteDatabase db = getHelper().getWritableDatabase();

        ContentValues v = new ContentValues();
        v.put("password", aPassword);

        db.update("me", v, null, null);
    }

}
