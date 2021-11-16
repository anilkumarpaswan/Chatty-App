package com.chatty;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;



public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;
    private TabLayout tablayouts;
    private FrameLayout frameLayout;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbars = (Toolbar) findViewById(R.id.toolbars);
        setSupportActionBar(toolbars);
        tablayouts = (TabLayout) findViewById(R.id.tablayouts);
        tablayouts.addTab(tablayouts.newTab().setText("Registered Users"));
        tablayouts.addTab(tablayouts.newTab().setText("Friend Requests"));
        tablayouts.addTab(tablayouts.newTab().setText("My Friends"));


        viewPager = (ViewPager) findViewById(R.id.viewpager_nav);
        frameLayout = (FrameLayout) findViewById(R.id.fragment_containor);
        //Adapter
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), tablayouts.getTabCount());
        viewPager.setAdapter(myPagerAdapter);

        sharedPreferences = getApplicationContext().getSharedPreferences(SharedPreference.My_Pref, Context.MODE_PRIVATE);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayouts));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbars, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        tablayouts.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);
        frameLayout.setVisibility(View.GONE);

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {
            startActivity(new Intent(NavigationDrawer.this, ProfileActivity.class));
            // Handle the camera action


        }/* else if (id == R.id.settings) {

        } */else if (id == R.id.aboutus) {
            startActivity(new Intent(NavigationDrawer.this, AboutusActivity.class));
        } else if (id == R.id.logout) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(SharedPreference.get_password);
            editor.remove(SharedPreference.get_username);
            editor.remove(SharedPreference.get_user_logged_in);

            //Setting boolean get_user_logged_in as false as the user is logged out
            editor.putBoolean(SharedPreference.get_user_logged_in, false);
            editor.commit();

            //Redirecting to Login Screen
            startActivity(new Intent(NavigationDrawer.this, Login_Activity.class));
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void ChatRoom(View view) {

        Intent intent = new Intent(getApplication(), Chatroom.class);
        startActivity(intent);


    }

    public void chatRoom(View view) {
        Toast.makeText(getApplicationContext(), "move to the chatroom", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplication(), Chatroom.class);
        startActivity(intent);
    }
}
