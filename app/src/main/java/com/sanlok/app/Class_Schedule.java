package com.sanlok.app;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Anip Mehta on 10/1/2015.
 */
public class Class_Schedule extends AppCompatActivity {
    private ListView mainListView;
    private ArrayAdapter<String> listAdapter;
    private Toolbar mainToolbar;
    ArrayAdapter<String> adapter;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView navView;

    private String TAG;
    private Button button;
    private PendingIntent pendingIntent;
    int j=0;
    TextView error;
    int m;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_schedule);
        mainListView = (ListView) findViewById(R.id.listView);
       error=(TextView)findViewById(R.id.error);

        SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String course = myPrefs.getString("course", "");
        String sem = myPrefs.getString("sem", "");
        m=myPrefs.getInt("sflag",-1);
        //int fl=myPrefs.getInt("cflag",-1);
        Log.i(TAG, "fs" + course);
        Log.i(TAG, "fs" + sem);








        new LoginTask().execute("");
        configureToolbar();

        configureDrawer();

    }


    private void scheduleNotification(Notification notification, long delay,int id) {

        Intent notificationIntent = new Intent(this, MyReceiver.class);
        notificationIntent.putExtra(MyReceiver.NOTIFICATION_ID, id);
        notificationIntent.putExtra(MyReceiver.NOTIFICATION, notification);

        System.out.println("index"+id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,id, notificationIntent,PendingIntent.FLAG_CANCEL_CURRENT);


        long futureInMillis = System.currentTimeMillis()+delay;
        System.out.println("final time"+futureInMillis);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
    }
    private Notification getNotification(String content,String title,long delay) {
        Log.i(TAG, "get notifcation");
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Class Reminder");
        Intent myIntent=new Intent(Class_Schedule.this,Class_Schedule.class);
        PendingIntent intent2 = PendingIntent.getActivity(this, 1,
                myIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(intent2);
        builder.setContentText("Subject:" + content.substring(0,1).toUpperCase()+content.substring(1)+" tomorrow");
        //builder.setVisibility(Visibility.MODE_IN.);

        builder.setWhen(delay);
        builder.setSmallIcon(R.drawable.ic_stat_sanlok);

        return builder.build();
    }

    ProgressDialog progressDialog;
    private InputStream is = null;
    private String page_output = "";

    private class LoginTask extends AsyncTask<String, Integer, String> {


        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Class_Schedule.this, R.style.AppTheme_Dark_Dialog);

            progressDialog.setIndeterminate(true);

            progressDialog.setMessage("Please Wait!Fetching Data.....");
            progressDialog.show();
        }


        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String course = myPrefs.getString("course", "");
            String sem = myPrefs.getString("sem", "");
            int flaag = myPrefs.getInt("cflag", -1);
            Log.i(TAG, "fs" + course);
            Log.i(TAG, "fs" + sem);
            Log.i(TAG, "Entered do in back");


                Log.i(TAG, "Entered if");


                try {
                    Log.i(TAG, "entered try()");

                    //Toast.makeText(getApplicationContext(), "Please wait,connecting to server",Toast.LENGTH_LONG).show();

                    String URL = "http://anip.xyz/class.php";

                    HttpClient Client = new DefaultHttpClient();
                    Log.i(TAG, "created client");
//
                    HttpGet httpget = new HttpGet(URL);
                    Log.i(TAG, "hhtp get");
                    Log.i(TAG, "in response handler");
                    List<NameValuePair> data = new ArrayList<NameValuePair>();

                    Log.i(TAG, course);

                    data.add(new BasicNameValuePair("course", course));
                    data.add(new BasicNameValuePair("sem", sem));
                    //data.add(new BasicNameValuePair("spec", spec));

                    Log.i(TAG, "creating name value");

                    DefaultHttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(URL);
                    httpPost.setEntity(new UrlEncodedFormEntity(data));
                    Log.i(TAG, "Encodding");
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    Log.i(TAG, "Executing");
                    HttpEntity httpEntity = httpResponse.getEntity();
                    is = httpEntity.getContent();
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    //SharedPreferences.Editor editor = pref.edit();
                    Log.i(TAG, "in strict mode");

                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                        StringBuilder sb = new StringBuilder();

                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        is.close();
                        page_output = sb.toString();

                        Log.i("LOG", "page_output --> " + page_output);
                    } catch (Exception e) {
                        Log.e("Buffer Error", "Error converting result " + e.toString());
                    }

                    Log.i(TAG, "request executed");

//
                } catch (UnsupportedEncodingException ex) {
                    Toast.makeText(Class_Schedule.this, "Incorrect Password or username", Toast.LENGTH_LONG).show();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {

                    e.printStackTrace();
                }
                Log.i(TAG, "returning response");


            return page_output;

        }

        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        protected void onPostExecute(String result) {

            Log.i(TAG, "Entered on post execute");
            progressDialog.dismiss();

            Log.i(TAG, result);
            if (result.contains("None")) {
                error.setText("OOPS! No classes found for you. For further queries contact institute.");
                error.setVisibility(View.VISIBLE);
                mainListView.setVisibility(View.GONE);

                SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = mypref.edit();

                editor.putString("class", "null");


                editor.commit();
            }

            //String jsonStr;
            else if(result.contains("S.no")){

                SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = mypref.edit();
                int flaaag = mypref.getInt("cflag", -1);

                    Log.i(TAG, "Entered if");

                    editor.putString("class", result);
                    editor.putInt("fuckyou", 1);


                    editor.commit();




            }
            else{
                SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = mypref.edit();

                editor.putString("class", "error");
              //  editor.commit();
            }
            list();

        }


        public void list() {

            Log.i(TAG, "entered list fun");
            SharedPreferences mpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String sc = mpref.getString("class", "");
            if (sc == "null") {
                error.setText("OOPS! No classes found for you. For further queries contact institute.");
                error.setVisibility(View.VISIBLE);
                mainListView.setVisibility(View.GONE);
            }

            else if(sc.contains("S.no")){
                SharedPreferences.Editor edit = mpref.edit();
                int nflaag = mpref.getInt("nflag", -1);
                int cflag = mpref.getInt("cflag", -1);

                Log.i(TAG, "result in list" + sc);
                Log.i(TAG, sc);
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(sc);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "in json try");
                Log.i(TAG, "sffef" + jsonArray.length());

                String[] name = new String[jsonArray.length()];
                String[] date = new String[jsonArray.length()];
                String[] ddate = new String[jsonArray.length()];
                String[] time = new String[jsonArray.length()];
                String[] fdate = new String[jsonArray.length()];
                String[] stime = new String[jsonArray.length()];
                String[] teacher = new String[jsonArray.length()];
                int[] id = new int[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    // get value with the NODE key
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                    SimpleDateFormat df5 = new SimpleDateFormat("yyyyMMdd");


                    JSONObject obj = null;
                    try {
                        obj = jsonArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        name[i] = obj.getString("name");
                        id[i] = obj.getInt("S.no");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        date[i] = obj.getString("date");
                        //ddate[i]=obj.getString("date");
                        //Log.i(TAG, df5.format(ddate[i]));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        time[i] = obj.getString("time");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        teacher[i] = obj.getString("teacher");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    stime[i] = time[i].replaceAll(":", "");
                    fdate[i] = date[i].replaceAll("-", "");

                    Log.i(TAG, fdate[i]);
                    Log.i(TAG, "Combined date and time" + fdate[i] + stime[i]);
                    //Log.i(TAG, "generating notification");
                    Calendar c1 = Calendar.getInstance();
                    try {
                        c1.setTime(new SimpleDateFormat("yyyyMMddHHmmss").parse(fdate[i] + stime[i]));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    long tiime = c1.getTimeInMillis();
                    //  Log.i(TAG, "time in milis" + String.valueOf(tiime));
                    long tim = System.currentTimeMillis() + 86400000;
                    edit.putInt("cflag", 1);
                    edit.commit();
                    System.out.println("real device time " + tim);
                    long delay = tiime - tim;
                    //long del=delay-43200000;


                    if (delay > 0) {
                        Log.i(TAG, "notification generated");
                        Log.i(TAG, "name is" + name[i]);
                        scheduleNotification(getNotification(name[i], "Class Reminder", tiime - 86400000), delay, id[i]);

                    }//SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MMM-dd").parse(date).rep;

                    Date pdate = null;
                    try {
                        pdate = formatter.parse(fdate[i]);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    System.out.println(formatter.format(pdate));
                    try {
                        time[i] = obj.getString("time");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.i(TAG, "fetching values");

                }

                mainListView.setAdapter(new Listadapter(name, date, time, teacher, getApplicationContext()));
            }
            else {
                error.setText("OOPS! Please Check Your Internet Connection");
                error.setVisibility(View.VISIBLE);
                mainListView.setVisibility(View.GONE);
            }



        }

    }

    private void configureToolbar() {
        mainToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("Sanlok Institute");

//        mainToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
//                    mDrawerLayout.closeDrawer(Gravity.START);
//
//                } else {
//                    mDrawerLayout.openDrawer(Gravity.START);
//                }
//            }
//        });
    }

    private void configureDrawer() {
        // Configure drawer
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        navView = (NavigationView) findViewById(R.id.navView);

        setupDrawerContent(navView);


//        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
//                R.string.drawer_open,
//                R.string.drawer_closed) {
//
//            public void onDrawerClosed(View view) {
//                supportInvalidateOptionsMenu();
//            }
//
//            public void onDrawerOpened(View drawerView) {
//                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//        };
//
//        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle = setupDrawerToggle();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.setDrawerListener(mDrawerToggle);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, mainToolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
//  In case of Fragments
//        Fragment fragment = null;
//
//        Class fragmentClass;
//        switch(menuItem.getItemId()) {
//            case R.id.nav_first_fragment:
//                fragmentClass = MainActivity.class;
//                break;
//            case R.id.nav_second_fragment:
//                fragmentClass = TransitionFirstActivity.class;
//                break;
//            case R.id.nav_third_fragment:
//                fragmentClass = TransitionSecondActivity.class;
//                break;
//            default:
//                fragmentClass = MainActivity.class;
//        }
//
//        try {
//            fragment = (Fragment) fragmentClass.newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.sample_content_fragment, fragment).commit();

        Intent intent = null;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                intent = new Intent(this, Home.class);
                break;
            case R.id.nav_second_fragment:
                intent = new Intent(this, Class_Schedule.class);
                break;
            case R.id.nav_third_fragment:
                if (m != 1) {
                    intent = new Intent(this, Notes.class);

                   // startActivity(inte);
                } else {
                    intent = new Intent(this, Webview.class);
                    //startActivity(inten);

                }
                break;
            case R.id.nav_fourth_fragment:
                intent = new Intent(this, Profile.class);
                break;
            case R.id.nav_fifth_fragment:
                intent=new Intent(this,FeePayment.class);
                break;
            default :
                intent = new Intent(this, Home.class);
                break;

        }
        finish();
        startActivity(intent);
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

//    private class DrawerItemClickListener implements ListView.OnItemClickListener {
//        @Override
//        public void onItemClick(AdapterView parent, View view, int position, long id) {
//            selectItem(position);
//            drawerLayout.closeDrawer(drawerListView);
//
//        }
//    }
}


