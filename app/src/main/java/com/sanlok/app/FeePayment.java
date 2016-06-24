package com.sanlok.app;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Anip Mehta on 10/15/2015.
 */
public class FeePayment extends AppCompatActivity {
    private static final String TAG =null ;
    WebView webView;
    ProgressDialog progressDialog;
    SharedPreferences.Editor editor;
    String email;
    String password;
    TextView error;
    private Toolbar mainToolbar;
    ArrayAdapter<String> adapter;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView navView;
    int m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = pref.edit();
        setContentView(R.layout.feepayment);
        m=pref.getInt("sflag",-1);
        webView = (WebView) findViewById(R.id.web);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        WebSettings webSettings = webView.getSettings();
        error=(TextView)findViewById(R.id.error);
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        //webSettings.setPluginsEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        error.setVisibility(View.VISIBLE);
        error.setText("OOPS! Last Date for online payment is over");
        webSettings.setBuiltInZoomControls(true);
        //webSettings.setSupportZoom(true);


        //webView.loadUrl("https://easypay.axisbank.co.in/easyPay/makePayment?mid=MjcxMDM%3D");

//        new LoginTask().execute("");



    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    private InputStream is = null;
    private String page_output = "";
    private class LoginTask extends AsyncTask<String, Integer, String> {


        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(FeePayment.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Processing Please wait......");
            progressDialog.show();
        }


        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            Log.i(TAG, "returning response");
HttpGet httpGet=new HttpGet("https://easypay.axisbank.co.in/easyPay/makePayment?mid=MjcxMDM%3D");
            HttpClient httpClient = new DefaultHttpClient();
            try {
                HttpResponse httpResponse=httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "Executing");

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

            return page_output;
        }

        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        protected void onPostExecute(String result) {
            Log.i(TAG, "Entered on post execute");
            if(result.contains("Axis Easy Pay")) {
                WebSettings webSettings = webView.getSettings();
                progressDialog.dismiss();
                webSettings.setJavaScriptEnabled(true);
                webView.getSettings().setPluginState(WebSettings.PluginState.ON);
                //webSettings.setPluginsEnabled(true);
                webView.setWebViewClient(new WebViewClient());

                webSettings.setBuiltInZoomControls(true);
                webSettings.setSupportZoom(true);


                webView.loadUrl("https://easypay.axisbank.co.in/easyPay/makePayment?mid=MjcxMDM%3D");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences prefer = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        //String name=pref.getString("name", "");
                        int m = prefer.getInt("flag", -1);
                        Log.i(TAG, "dfbdfgb segseg" + String.valueOf(m));
                            Toast.makeText(FeePayment.this, "Please use your Roll No as Id", Toast.LENGTH_LONG).show();
                        Toast.makeText(FeePayment.this, "Please use your Roll No as Id", Toast.LENGTH_LONG).show();
                        Toast.makeText(FeePayment.this, "Please use your Roll No as Id", Toast.LENGTH_LONG).show();
                    }
                }, 6000);


            }
            else{
                progressDialog.dismiss();
                error.setVisibility(View.VISIBLE);
              //  Toast.makeText(FeePayment.this,"Please check your internet connection",Toast.LENGTH_LONG).show();

            }


        }

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

/*
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
                    intent  = new Intent(FeePayment.this, Notes.class);

                } else {
                    intent = new Intent(FeePayment.this, Webview.class);
                }
                break;
            case R.id.nav_fourth_fragment:
                intent = new Intent(this, Profile.class);
                break;
            default :
                intent = new Intent(this, Home.class);
                break;
        }

        startActivity(intent);
        menuItem.setChecked(false);
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
//    }*/
}





