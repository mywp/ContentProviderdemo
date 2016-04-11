package com.example.scorpio.sqlitedemo1;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.scorpio.sqlitedemo.R;

public class MainActivity extends AppCompatActivity {

    protected static final String TAG ="MainActivity";
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Uri uri = Uri.parse("content://PersonContentProvider");
        getContentResolver().registerContentObserver(uri, true, new ContentObserver(new Handler()) {

            @Override
            public void onChange(boolean selfChange) {
                Log.i(TAG,"内容改变了");
            }
        });
    }
}
