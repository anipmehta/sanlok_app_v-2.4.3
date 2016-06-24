package com.sanlok.app;

import android.app.ProgressDialog;
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
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anip Mehta on 10/11/2015.
 */
/*public class Notes extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes);
        webView=(WebView)findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        final Activity activity = this;
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
                activity.setProgress(progress * 1000);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });
        webView.loadUrl("http://www.sanlokinstitute.com/studentlogin.asp");
    }
}*/
public class Notes extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

   Button login;
   EditText email1;
   EditText pass;
  WebView webView;
    private Toolbar mainToolbar;
    ArrayAdapter<String> adapter;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView navView;

    // String Response;
    String email;
    String password;
    ProgressDialog progressDialog;
    SharedPreferences.Editor editor;
int m;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        SharedPreferences pref =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = pref.edit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes);
        m=pref.getInt("sflag",-1);
        login=(Button)findViewById(R.id.login);
        email1=(EditText)findViewById(R.id.e);
        pass=(EditText)findViewById(R.id.pass);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                email = email1.getText().toString();
                password =pass.getText().toString();
                new LoginTask().execute("");

            }
        });
        configureToolbar();

        configureDrawer();

    }

    /*public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }
    */private InputStream is = null;
    private String page_output = "";

    private class LoginTask extends AsyncTask<String, Integer, String> {


        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Notes.this,
                    R.style.Base_Widget_AppCompat_ProgressBar_Horizontal);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Validating User........");
            progressDialog.show();
            // progressDialog.dismiss();


        }


        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            try {
                Log.i(TAG, "entered try()");
                Log.i(TAG, "entered toast()");
                Log.i(TAG, email);
                Log.i(TAG, password);
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
            if (result.contains("Hello, Student Welcome")) {
                Log.i(TAG, "Entered if");
                editor.putString("semail", email);
                editor.putString("spass", password);
                editor.putInt("sflag", 1);
                editor.commit();
                // onLoginSuccess()
                Toast.makeText(Notes.this, "VALID USER", Toast.LENGTH_LONG).show();
                Intent inteent=new Intent(Notes.this,Webview.class);
                startActivity(inteent);
                finish();
            } else if (result.contains("New Student")) {
                Toast.makeText(Notes.this, "Please enter valid username or password", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(Notes.this, "No Internet Connection", Toast.LENGTH_LONG).show();
            }
            progressDialog.dismiss();


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
                    intent = new Intent(Notes.this, Notes.class);

                } else {
                    intent = new Intent(Notes.this, Webview.class);
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


