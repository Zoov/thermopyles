package com.salvinien.testspongycastle;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.security.Key;
import java.security.KeyPair;


public class CreateKey extends Activity
{

    //http://stackoverflow.com/questions/20112338/use-of-spongy-castle-in-android

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_key);
        //for test
        try
        {
            KeyPair kp = MauiCrypterManager.generateRSAKey();
            Key priv = kp.getPrivate();
            Key pub = kp.getPublic();

            // globally
            TextView myAwesomeTextView = (TextView) findViewById(R.id.editText2);

            //in your OnCreate() method
            String s = SecMod.bytesToHex(pub.getEncoded());
            myAwesomeTextView.setText(s);

            //SimpleProviderTest.test();
            //SimplePolicyTest.test();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_key, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
