package com.sanlok.app;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Anip Mehta on 10/2/2015.
 */
public class Broadcast extends AppCompatActivity {
    private static final String TAG =null ;
    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.broadcast);
        //SharedPreferences.Editor editor;
        //SharedPreferences pref =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //editor = pref.edit();
        button=(Button)findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoginTask().execute("");
            }
        });
        //editor.putString("time", String.valueOf(System.currentTimeMillis()));
        //editor.commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor;
        SharedPreferences pref =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //editor = pref.edit();
        //editor.putString("time", String.valueOf(System.currentTimeMillis()));
        //editor.commit();

    }

    private InputStream is = null;
    private String page_output = "";

    private class LoginTask extends AsyncTask<String, Integer, String> {


        protected void onPreExecute() {
            super.onPreExecute();
           // progressDialog = new ProgressDialog(LoginActivity.this,
                    //R.style.AppTheme_Dark_Dialog);
           // progressDialog.setIndeterminate(true);
           // progressDialog.setMessage("Authenticating...");
            //progressDialog.show();
            // progressDialog.dismiss();


        }


        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub


//            Log.i(TAG,uname.getText().toString()+"ksjdvnslkdvxnwadlk");
//			Log.i(TAG,pass.getText().toString()+"fsdxcjvnskjdn");
            try{
               // Log.i(TAG, "entered try()");

                //Toast.makeText(getApplicationContext(), "Please wait,connecting to server",Toast.LENGTH_LONG).show();
               // Log.i(TAG,"entered toast()");
               // Log.i(TAG,email);
               // Log.i(TAG,password);
                String URL="http://anip.xyz/news.php";
                HttpClient Client=new DefaultHttpClient();
                Log.i(TAG,"created client");
//			try{
//				String Response="";
               // HttpGet httpget=new HttpGet(URL);
               // Log.i(TAG,"hhtp get");
                ResponseHandler<String> responseHandler=new BasicResponseHandler();
                Log.i(TAG,"in response handler");
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                //String name=pref.getString("name", "");
                String sc=pref.getString("course","");
                String tim=pref.getString("time", "");
                List<NameValuePair> data = new ArrayList<NameValuePair>();
                data.add(new BasicNameValuePair("course",sc));
                data.add(new BasicNameValuePair("time",tim));
                //data.add(new BasicNameValuePair("password", password));
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(URL);
                httpPost.setEntity(new UrlEncodedFormEntity(data));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                Log.i(TAG,"executed");
                is = httpEntity.getContent();
                Log.i(TAG, "in strict mode");

                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    is.close();
                    page_output = sb.toString();

                    Log.i("LOG", "page_output --> " + page_output);
                } catch (Exception e) {
                    Log.e("Buffer Error", "Error converting result " + e.toString());
                }

                Log.i(TAG,"request executed");

            } catch(UnsupportedEncodingException e){
            } catch (IOException e) {
            }
            Log.i(TAG, "returning response");
            return page_output;
        }

        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        protected void onPostExecute(String result){
            try {

                String jsonStr;
                SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor=myPrefs.edit();
                Log.i(TAG,result);
                editor.putString("news", result);

                //editor.putInt("cflag",1);

                editor.commit();
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                //String name=pref.getString("name", "");
                String sc=pref.getString("news","");
                Log.i(TAG,sc);


                JSONArray jsonArray=new JSONArray(sc);
                Log.i(TAG, "in json try");
                // If you have array
                //JSONArray resultArray = jsonObj.getJSONArray("result"); // Here you will get the Array

                // Iterate the loop
                int n=jsonArray.length();
                for (int i = 0; i < jsonArray.length(); i++) {
                    // get value with the NODE key
                    //SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");;
                    JSONObject obj =jsonArray.getJSONObject(i);

                    String  title =  obj.getString("title");
                    String desc=    obj.getString("description");
                    String course= obj.getString("course");
                   // String time=obj.getLong("date");
                    //String fdate=date.replaceAll("-","");
                    //Log.i(TAG,fdate);
                    //SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MMM-dd").parse(date).rep;
                    //Date pdate = formatter.parse(fdate);
                    //System.out.println(formatter.format(pdate));
                    //String time =    obj.getString("time");


                    //java.sql.Time timeValue = new java.sql.Time(formatter.parse(time).getTime());

                    notifi(title, desc,i);
                    Log.i(TAG, "fetching values");
                    if(i==n-1){
                        SharedPreferences prefer = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor edit=prefer.edit();
                        //editor = pref.edit();
                        long l = Long.parseLong(obj.getString("date"));
                        Date beginupd = new Date(l);
                        long millisecond=beginupd.getTime();
                        edit.putString("time", String.valueOf(millisecond));
                        edit.commit();

                    }
                    //Toast.makeText(Class_Schedule.this,"Class:"+name+ "   "+"Date:"+pdate+"   "+"Time:"+time, Toast.LENGTH_LONG).show();

                }

                // If you have object
                // String result = json.getString("result");

            } catch (Exception e) {
                e.printStackTrace();
            }

            }

        }
    protected void notifi(String notificationTitle,String notificationMessage,int notificationId){
        Log.i(TAG, "entered notify");
        NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Log.i(TAG, "created not manager");
        Notification notification=new Notification(R.drawable.mlogo,notificationMessage,System.currentTimeMillis());
        Log.i(TAG, "created notification");
        Intent notIntent=new Intent(this,Temp_Activity.class);
        PendingIntent pen=PendingIntent.getActivity(this,0,notIntent,0);
        Log.i(TAG, "created pending intent");
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        Log.i(TAG, "generating notification");
        notification.defaults = Notification.DEFAULT_ALL;
        notification.setLatestEventInfo(Broadcast.this, notificationTitle,notificationMessage, pen);
        notificationManager.notify(9999, notification);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor=pref.edit();
        //editor = pref.edit();
        editor.putString("time", String.valueOf(System.currentTimeMillis()));
        editor.commit();

    }

    }