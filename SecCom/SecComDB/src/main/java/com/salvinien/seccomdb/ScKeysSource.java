package com.salvinien.seccomdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by laurent.salvinien on 2014-09-21.
 */
public class ScKeysSource extends MotherSource
{


    //Ctor
    //context is the activity that is instanciating ScKeysSource
    public ScKeysSource(Context context)
    { super(context); }


    //Methods

    public byte[] getPublicKeyById(int aContactId)
    {
        String query = "SELECT  publicKey FROM sckeys where contactId = " + aContactId;
        SQLiteDatabase db = getHelper().getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        if(cursor == null) { return null; }

        byte[] aKey = cursor.getBlob(0);

        cursor.close();

        return aKey;
    }


    public void savePublicKey(int aContactId, byte[] aPublicKey)
    {
        //let's check if this contact has already a public key registered
        SQLiteDatabase db = getHelper().getWritableDatabase();

        ContentValues v = new ContentValues();
        v.put("contactId", aContactId);
        v.put("publicKey", aPublicKey);

        db.insertWithOnConflict("sckeys", "contactId", v, SQLiteDatabase.CONFLICT_REPLACE);

    }

    public void deleteKey(int aContactId)
    {
        getDB().delete("sckeys", " contactId= " + aContactId, null);
    }


}
