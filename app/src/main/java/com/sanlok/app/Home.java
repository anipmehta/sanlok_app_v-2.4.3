package com.sanlok.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * Created by Anip Mehta on 11/1/2015.
 */
public class Home extends AppCompatActivity {
    String[] menu;
    DrawerLayout dLayout;
    ListView dList;
    private Toolbar mainToolbar;
    ArrayAdapter<String> adapter;
    private DrawerLayout mDrawer;
     SharedPreferences.Editor editor;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView navView;
    //SharedPreferences prefer = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    //String name=pref.getString("name", "");
 int m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        SharedPreferences prefer = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
         m=prefer.getInt("sflag",-1 );
       // Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        //setSupportActionBar(myToolbar);

        //menu=new String[]{"Home","Class Details","Study Material","Profile"};
        dLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        //dList=(ListView)findViewById(R.id.left_drawer);
        //adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,menu);
        //dList.setAdapter(adapter);
        //dList.setSelector(android.R.color.holo_blue_dark);
        /*dList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dLayout.closeDrawers();
                System.out.println("closing drawer");
                Bundle args = new Bundle();
                args.putString("Menu", menu[position]);
                if (menu[position].equals("Class Details")) {
                    System.out.println("entered ");
                    Intent intent = new Intent(Home.this, Class_Schedule.class);
                    startActivity(intent);
                }
                if (menu[position].equals("Study Material")) {
                    if (m != 1) {
                        Intent intent = new Intent(Home.this, Notes.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(Home.this, Webview.class);
                        startActivity(intent);
                    }


                }

                if (menu[position].equals("Profile")) {
                    Intent intent = new Intent(Home.this, Profile.class);
                    startActivity(intent);
                }


                //Fragment detail = new DetailFragment();
                //detail.setArguments(args);
                //FragmentManager fragmentManager = getFragmentManager();
                //fragmentManager.beginTransaction().replace(R.id.content_frame,detail).commit();
            }

        });*/

   /* if (savedInstanceState == null) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        SlidingTabsBasicFragment fragment = new SlidingTabsBasicFragment();
        transaction.replace(R.id.sample_content_fragment, fragment);
        transaction.commit();
    }*/

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
                    intent = new Intent(Home.this, Notes.class);
                } else {
                    intent = new Intent(Home.this, Webview.class);
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
