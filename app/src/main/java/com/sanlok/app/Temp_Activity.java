package com.sanlok.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;

/**
 * Created by Anip Mehta on 10/1/2015.
 */
public class Temp_Activity extends AppCompatActivity {
    private static final String TAG =null ;
    Button button;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_activity);
        ButterKnife.inject(this);
        final SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String name = myPrefs.getString("name", "");

        button = (Button) findViewById(R.id.button);
        button2=(Button)findViewById((R.id.notes));
       // button3=(Button)findViewById(R.id.test);
        button4=(Button)findViewById(R.id.profile);
        button5=(Button)findViewById(R.id.drawer);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick");
                Intent inte=new Intent(Temp_Activity.this,Home.class);
                startActivity(inte);


            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myPrefs.getInt("sflag",-1)!=1) {
                    Intent intent2 = new Intent(Temp_Activity.this, Notes.class);
                    startActivity(intent2);
                }
                else{
                    Intent intent2 = new Intent(Temp_Activity.this, Webview.class);
                    startActivity(intent2);

                }
            }
        });

        /*button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte=new Intent(Temp_Activity.this,MyActivity.class);
                startActivity(inte);
            }
        });*/
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Temp_Activity.this, Profile.class);
                startActivity(intent2);

            }
        });

        Log.i(TAG, "sfwef" + name);
        Log.i(TAG, myPrefs.getString("email", ""));
        Log.i(TAG, myPrefs.getString("course", ""));
        Log.i(TAG, myPrefs.getString("phone", ""));
        Log.i(TAG, myPrefs.getString("sem", ""));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Temp_Activity.this, Class_Schedule.class);
                startActivity(intent);
            }

        });

    }
}
