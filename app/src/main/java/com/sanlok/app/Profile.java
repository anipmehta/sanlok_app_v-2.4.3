package com.sanlok.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Anip Mehta on 10/17/2015.
 */
public class Profile extends AppCompatActivity {

    String[] menu;
    DrawerLayout dLayout;
    ListView dList;
    private Toolbar mainToolbar;
    ArrayAdapter<String> adapter;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView navView;
    EditText email;
    EditText name, phone, course, sem, spec;
    int m;
    ListView listView;
    SharedPreferences.Editor editor;
    Button edit;
    FloatingActionButton but;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = pref.edit();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        edit = (Button) findViewById(R.id.edit);
        //but=(FloatingActionButton)findViewById(R.id.view);
        System.out.println("Created Activity");
        m=pref.getInt("sflag",-1);

        email = (EditText) findViewById(R.id.email);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        course = (EditText) findViewById(R.id.course);
        //listView=(ListView)findViewById(R.id.listview);
        sem = (EditText) findViewById(R.id.sem);
        spec = (EditText) findViewById(R.id.spec);
        System.out.println("Setting Text");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        name.setText(pref.getString("name", ""));
        email.setText(pref.getString("email", ""));
        course.setText(pref.getString("course", ""));
        sem.setText(pref.getString("sem", ""));
        //pref.getString("spec","");
        spec.setText(pref.getString("spec", ""));
        phone.setText(pref.getString("phone", ""));

        email.setEnabled(false);
        name.setEnabled(false);
        phone.setEnabled(false);
        course.setEnabled(false);
        sem.setEnabled(false);
        spec.setEnabled(false);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setEnabled(true);
                name.setEnabled(true);
                phone.setEnabled(true);
                course.setEnabled(true);
                sem.setEnabled(true);
                spec.setEnabled(true);
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editor.putInt("cflag", 0);
                        editor.putInt("nflag", 0);
                        editor.putInt("sflag", 0);
                        editor.putString("name", name.getText().toString());
                        editor.putString("email", email.getText().toString());
                        editor.putString("phone", phone.getText().toString());
                        editor.putString("course", course.getText().toString());
                        editor.putString("sem", sem.getText().toString());
                        editor.putString("spec", spec.getText().toString());
                        editor.commit();
                        Toast.makeText(getApplicationContext(), "Data Updated Successfully", Toast.LENGTH_LONG).show();
                        Intent inte = new Intent(Profile.this, Home.class);
                        startActivity(inte);
                    }
                });
                edit.setText("Update");


                //editor.putInt("cflag",0);
            }
        });

        editor.commit();

        configureToolbar();

        configureDrawer();
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
                    intent = new Intent(Profile.this, Notes.class);

                } else {
                    intent = new Intent(Profile.this, Webview.class);
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


