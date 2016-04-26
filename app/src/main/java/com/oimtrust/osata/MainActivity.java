package com.oimtrust.osata;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.oimtrust.osata.fragment.FragmentGallery;
import com.oimtrust.osata.fragment.FragmentHome;
import com.oimtrust.osata.fragment.FragmentMaps;
import com.oimtrust.osata.fragment.FragmentService;
import com.oimtrust.osata.fragment.FragmentTour;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolbar    = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);



        /**
         * untuk Fragment didalam NavigationView
         */

        if (savedInstanceState == null){
            Fragment fragment   = null;
            Class fragmentClass = null;
            fragmentClass       = FragmentHome.class;
            try {
                fragment        = (Fragment) fragmentClass.newInstance();
            } catch (Exception e){
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }

        DrawerLayout mDrawerLayout      = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle    = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView mNavigationView  = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout mDrawerLayout      = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return  true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id              = item.getItemId();
        Fragment fragment   = null;
        Class fragmentClass = null;

        switch (id){
            case R.id.beranda:
                setTitle("OSATA");
                fragmentClass   = FragmentHome.class;
                break;

            case R.id.layanan:
                setTitle("Layanan");
                fragmentClass   = FragmentService.class;
                break;

            case R.id.peta:
                setTitle("Peta");
                fragmentClass   = FragmentMaps.class;
                break;

            case R.id.wisata:
                setTitle("Status");
                fragmentClass   = FragmentTour.class;
                break;

            case R.id.gallery:
                setTitle("Galeri");
                fragmentClass   = FragmentGallery.class;
                break;
        }

        try {
            fragment    = (Fragment) fragmentClass.newInstance();
        } catch (Exception e){
            e.printStackTrace();
        }

        /**
         * Insert the fragment by replacing any existing fragment
         */

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item and close the drawer
        item.setChecked(true);
        DrawerLayout mDrawerLayout  = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}
