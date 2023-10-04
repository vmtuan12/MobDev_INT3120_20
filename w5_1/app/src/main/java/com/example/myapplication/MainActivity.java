package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    UserProvider userProvider;

    Uri uri = Uri.parse("content://com.demo.user.provider/Country");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userProvider = new UserProvider();

        Button contactButton = (Button) findViewById(R.id.button_contact);
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readContacts();
            }
        });
    }

    private void readContacts() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_CONTACTS}, 0);
        }

        ContentResolver contentResolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                System.out.println(name + " ::: " + number);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
    public void onClickAddDetails(View view) {

        // class to add values in the database
        ContentValues values = new ContentValues();

        // fetching text from user
        values.put(UserProvider.name, ((EditText) findViewById(R.id.textName)).getText().toString());

        // inserting into database through content URI
        ContentValues[] contentValues = {values, values, values};
        getContentResolver().insert(UserProvider.CONTENT_URI, values);

        // displaying a toast message
        Toast.makeText(getBaseContext(), "New record inserted", Toast.LENGTH_LONG).show();
    }

    public void onClickShowDetails(View view) {
        // inserting complete table details in this text field
        TextView resultView= (TextView) findViewById(R.id.res);

        // creating a cursor object of the
        // content URI
        Cursor cursor = getContentResolver().query(Uri.parse("content://com.demo.user.provider/users"), null, null, null, null);

        // iteration of the cursor
        // to print whole table
        if(cursor.moveToFirst()) {
            StringBuilder strBuild=new StringBuilder();
            while (!cursor.isAfterLast()) {
                strBuild.append("\n"+cursor.getString(cursor.getColumnIndex("id"))+ "-"+ cursor.getString(cursor.getColumnIndex("name")));
                cursor.moveToNext();
            }
            resultView.setText(strBuild);
        }
        else {
            resultView.setText("No Records Found");
        }
    }
}