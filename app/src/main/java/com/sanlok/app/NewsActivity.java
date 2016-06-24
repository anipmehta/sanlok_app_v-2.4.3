package com.sanlok.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Anip Mehta on 10/4/2015.
 */
public class NewsActivity extends AppCompatActivity{
    private static final String TAG =null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);
        Bundle extras=getIntent().getExtras();
        String news=extras.getString("news");
        Log.i(TAG, news);
        Toast.makeText(getBaseContext(),news, Toast.LENGTH_LONG).show();


    }
}
