package com.sanlok.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
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
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EncodingUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anip Mehta on 10/15/2015.
 */
public class Webview extends AppCompatActivity {
    private static final String TAG =null ;
    WebView webView;
    ProgressDialog progressDialog;
    SharedPreferences.Editor editor;
    String email;
    String password;
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
        setContentView(R.layout.web_view);
        m=pref.getInt("sflag",-1);
        webView = (WebView) findViewById(R.id.web);
        email=pref.getString("semail","");
        password=pref.getString("spass","");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        new LoginTask().execute("");
        configureToolbar();

        configureDrawer();
    }
    private InputStream is = null;
    private String page_output = "";
    private class LoginTask extends AsyncTask<String, Integer, String> {


            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(Webview.this,
                        R.style.Widget_AppCompat_ProgressBar_Horizontal);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Redirecting to Sanlok's website........");
                progressDialog.show();
            }


            @Override
            protected String doInBackground(String... params) {
                // TODO Auto-generated method stub
                try {
                    Log.i(TAG, "entered try()");
                    Log.i(TAG, "entered toast()");
                    String URL = "http://www.sanlokinstitute.com/studentlogin.asp";
                    HttpClient Client = new DefaultHttpClient();
                    Log.i(TAG, "created client");
                    HttpGet httpget = new HttpGet(URL);
                    Log.i(TAG, "hhtp get");
                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    Log.i(TAG, "in response handler");
                    List<NameValuePair> data = new ArrayList<NameValuePair>();
                    data.add(new BasicNameValuePair("username", email));
                    data.add(new BasicNameValuePair("password", password));
                    data.add(new BasicNameValuePair("university", "guru"));
                    DefaultHttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(URL);
                    httpPost.setEntity(new UrlEncodedFormEntity(data));

                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    Log.i(TAG, "executed");
                    is = httpEntity.getContent();
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

                } catch (UnsupportedEncodingException e) {
                } catch (IOException e) {
                }
                Log.i(TAG, "returning response");
                return page_output;
            }

            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }

            protected void onPostExecute(String result) {
                Log.i(TAG, "Entered on post execute");
                System.out.println(result);
                progressDialog.dismiss();

                    Log.i(TAG, "Entered if");
                    String postData = "username=" + email + "&password=" + password + "&university=guru";
                    WebSettings webSettings = webView.getSettings();

                    webSettings.setJavaScriptEnabled(true);
                    webView.getSettings().setPluginState(WebSettings.PluginState.ON);
                    //webSettings.setPluginsEnabled(true);
                webView.setWebViewClient(new WebViewClient());
                    webView.setDownloadListener(new DownloadListener() {
                        @Override
                        public void onDownloadStart(String url, String userAgent,
                                                    String contentDisposition, String mimetype,
                                                    long contentLength) {
                            Uri uri = Uri.parse(url);
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }
                    });
                    webSettings.setBuiltInZoomControls(true);
                    //webSettings.setSupportZoom(true);


                webView.postUrl("http://www.sanlokinstitute.com/studentlogin.asp", EncodingUtils.getBytes(postData, "BASE64"));



            }

                    }

    @Override
    protected void onDestroy() {
        Intent inte=new Intent(Webview.this,Home.class);
        startActivity(inte);
        super.onDestroy();
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
                    intent  = new Intent(Webview.this, Notes.class);

                } else {
                    intent = new Intent(Webview.this, Webview.class);
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
//    }
}




