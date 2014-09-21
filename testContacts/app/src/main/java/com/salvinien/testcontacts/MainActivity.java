package com.salvinien.testcontacts;

import android.app.Activity;
import android.os.Bundle;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.widget.TextView;

public class MainActivity extends Activity {
	    public TextView outputText;
                    @Override
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.main);
                outputText = (TextView) findViewById(R.id.textview1);
                fetchContacts();
            }

        public void fetchContacts() {
                String phoneNumber = null;
                String email = null;
                Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
                String _ID = ContactsContract.Contacts._ID;
                String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
                String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
                Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
                String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

                Uri EmailCONTENT_URI =  ContactsContract.CommonDataKinds.Email.CONTENT_URI;
                String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
                String DATA = ContactsContract.CommonDataKinds.Email.DATA;

                StringBuffer output = new StringBuffer();

                ContentResolver contentResolver = getContentResolver();

                Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);

                // Loop for every contact in the phone
                if (cursor.getCount() > 0) {
            	            while (cursor.moveToNext()) {

                              String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
                                String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));

                                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));
                                if (hasPhoneNumber > 0) {

                                        output.append("\n First Name:" + name);

                                        // Query and loop for every phone number of the contact
                                        Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);

                                        while (phoneCursor.moveToNext()) {
                                                phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                                                output.append("\n Phone number:" + phoneNumber);

                                            }

                                        phoneCursor.close();

                                        // Query and loop for every email of the contact
                                        Cursor emailCursor = contentResolver.query(EmailCONTENT_URI,    null, EmailCONTACT_ID+ " = ?", new String[] { contact_id }, null);

                                        while (emailCursor.moveToNext()) {

                                                email = emailCursor.getString(emailCursor.getColumnIndex(DATA));

                                                output.append("\nEmail:" + email);

                                            }

                                        emailCursor.close();
                                    }

                                output.append("\n");
                            }

                        outputText.setText(output);
                    }
            }

            }
/*
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
*/