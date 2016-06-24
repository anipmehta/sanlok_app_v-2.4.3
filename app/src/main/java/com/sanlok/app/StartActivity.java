package com.sanlok.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

/**
 * Created by Anip Mehta on 9/30/2015.
 */
public class StartActivity extends AppCompatActivity {
    private static final String TAG = null;
    CountDownTimer counterTimer;
    Runnable runnable;
    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences prefer = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                //String name=pref.getString("name", "");
                int m=prefer.getInt("flag",-1 );
                Log.i(TAG,"dfbdfgb segseg"+String.valueOf(m));
                if(m!=1) {
                    Intent mainIntent = new Intent(StartActivity.this, LoginActivity.class);
                    StartActivity.this.startActivity(mainIntent);
                    StartActivity.this.finish();
                }
                else{
                    Intent mainIntent = new Intent(StartActivity.this, Home.class);
                    StartActivity.this.startActivity(mainIntent);
                    StartActivity.this.finish();
                }

            }
        }, 3000);


    }
}


