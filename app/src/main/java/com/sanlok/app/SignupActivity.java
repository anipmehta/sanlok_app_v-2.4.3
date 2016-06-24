package com.sanlok.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "SignupActivity";

    @InjectView(R.id.input_name) EditText _nameText;
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_signup) Button _signupButton;
    @InjectView(R.id.link_login) TextView _loginLink;
    //@InjectView(R.id.Course) EditText _course;
    Spinner _course;
    @InjectView(R.id.Semester) EditText _sem;
    @InjectView(R.id.Spec) EditText _spec;
    @InjectView(R.id.cn) EditText _phone;
    SharedPreferences.Editor editor;
    String course;
    String name;
    String email;
    String password;
    String sem;
    String spec;
    String phone;


    String[] categories = {"MBA","MCA","PGDCA","Mcom","BBA","BCA","Msc","PGDISM","PGDT","PGD&PR","PGDEM","BMC","MMC"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);
        _spec.setEnabled(false);
        _course=(Spinner)findViewById(R.id.Course);
        _course.setOnItemSelectedListener(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
        _course.setAdapter(new MyAdapter(SignupActivity.this, R.layout.custom, categories));

        SharedPreferences pref =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = pref.edit();
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = _nameText.getText().toString();
                email = _emailText.getText().toString();
                password = _passwordText.getText().toString();

                sem= _sem.getText().toString();
                spec=_spec.getText().toString();
                phone=_phone.getText().toString();
                validate();
                if(!validate()){
                    Log.i(TAG, "Error in validating");
                    Toast.makeText(getApplicationContext(),"Please enter valid details",Toast.LENGTH_LONG).show();
                }
                else {
                    new LoginTask().execute("");
                }

            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }
    ProgressDialog progressDialog;
    private InputStream is = null;
    private String page_output = "";

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        course=item.toLowerCase();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class LoginTask extends AsyncTask<String, Integer, String> {


            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(SignupActivity.this,R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();



            }


            @Override
            protected String doInBackground(String... params) {
                // TODO Auto-generated method stub


//
                try{
                    Log.i(TAG,"entered try()");
                    String URL="http://anip.xyz/register.php";

                    HttpClient Client=new DefaultHttpClient();
                    Log.i(TAG,"created client");
//
                    HttpGet httpget=new HttpGet(URL);
                    Log.i(TAG,"hhtp get");
                    ResponseHandler<String> responseHandler=new BasicResponseHandler();
                    Log.i(TAG,"in response handler");

                    List<NameValuePair> data = new ArrayList<NameValuePair>();
                    Log.i(TAG,email);
                    Log.i(TAG,password);
                    Log.i(TAG,name);
                    Log.i(TAG,course);
                    data.add(new BasicNameValuePair("email", email.toLowerCase()));
                    data.add(new BasicNameValuePair("password", password.toLowerCase()));
                    data.add(new BasicNameValuePair("name", name.toLowerCase()));
                    data.add(new BasicNameValuePair("course", course.toLowerCase()));
                    data.add(new BasicNameValuePair("sem", sem.toLowerCase()));
                    data.add(new BasicNameValuePair("spec", spec.toLowerCase()));
                    data.add(new BasicNameValuePair("phone", phone));
                    Log.i(TAG, "creating name value");

                    DefaultHttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(URL);
                    httpPost.setEntity(new UrlEncodedFormEntity(data));
                    Log.i(TAG, "Encodding");
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    Log.i(TAG, "Executing");
                    HttpEntity httpEntity = httpResponse.getEntity();
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

                }
                catch(UnsupportedEncodingException ex){
                    Toast.makeText(SignupActivity.this, "Incorrect Password or username", Toast.LENGTH_LONG).show();
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


            protected void onPostExecute(String result){
                Log.i(TAG, "Entered on post execute");
                progressDialog.dismiss();
                // Log.i(TAG,result);

                String ref= result.toString();
                Toast.makeText(SignupActivity.this,"length="+result.length()+result, Toast.LENGTH_LONG).show();
                Pattern pattern=Pattern.compile(".*Valid.*");

                Matcher matcher=pattern.matcher(ref);
                if(result.contains("Successfully")) {
                    Log.i(TAG, "Entered if");
                   // successlog();
                    Toast.makeText(SignupActivity.this,"Registered Succesfully", Toast.LENGTH_LONG).show();
                    local_data();
                }
                else if(result.contains("Unsuccessfully")) {
                    Toast.makeText(SignupActivity.this, "failure", Toast.LENGTH_LONG).show();
                    editor.putInt("flag", 0);
                    editor.putInt("cflag",0);
                    editor.commit();
                }
                else
                    Toast.makeText(SignupActivity.this,"Error connecting internet", Toast.LENGTH_LONG).show();

            }
    }

    public void local_data(){
        Log.i(TAG,"entered local data");
        editor.putString("name", name.toLowerCase());
        editor.putString("email",email.toLowerCase());
        editor.putString("course",course.toLowerCase());
        editor.putString("sem",sem.toLowerCase());
        editor.putString("spec",spec.toLowerCase());
        editor.putString("phone", phone);
        editor.putInt("flag", 1);
        editor.commit();
        Intent intent=new Intent(SignupActivity.this,LoginActivity.class);
        intent.putExtra("email",email);
        startActivity(intent);
        finish();
    }
    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        Log.i(TAG,"invalidate");

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            Log.i(TAG, "checking 1");
            valid = false;
        } else {
            _nameText.setError(null);
            Log.i(TAG, "checking 1");
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
            Log.i(TAG, "checking 2");
        } else {
            _emailText.setError(null);
            Log.i(TAG, "checking 2");
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 15) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
            Log.i(TAG, "checking 3");
        } else {
            _passwordText.setError(null);
            Log.i(TAG, "checking 3");
        }
        if(course.contains("mba") &&  (sem.contains("3") || sem.contains("4")) ) {
            _spec.setEnabled(true);

            Log.i(TAG, "checking 4");
        }
        else{
            _spec.setError(null);
            Log.i(TAG, "checking 4");
        }
        return valid;
    }
    public class MyAdapter extends ArrayAdapter {

        public MyAdapter(Context context, int textViewResourceId,
                         String[] objects) {
            super(context, textViewResourceId, objects);
        }

        public View getCustomView(int position, View convertView,
                                  ViewGroup parent) {

// Inflating the layout for the custom Spinner
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom, parent, false);
            View line = (View) layout.findViewById(R.id.line);
// Declaring and Typecasting the textview in the inflated layout
            TextView tvLanguage = (TextView) layout
                    .findViewById(R.id.cat);

// Setting the text using the array
            tvLanguage.setText(categories[position]);
            if (position ==12) {
                line.setEnabled(false);
                line.setBackgroundResource(R.color.black);
            }
// Setting the color of the text

// Declaring and Typecasting the imageView in the inflated layout

// Setting an image using the id's in the array

// Setting Special atrributes for 1st element

            return layout;
        }

        // It gets a View that displays in the drop down popup the data at the specified position
        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        // It gets a View that displays the data at the specified position
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }
    }
}