package com.project.android.finanzm;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class ManagerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_activity);
        // Force landscape orientation
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();

        if(savedInstanceState == null) {
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ArticlesManagerFragment()).commit();

            navigationView.setCheckedItem(R.id.report_manager);
        }
    }


    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.articles_manager:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ArticlesManagerFragment()).commit();

                break;
            case R.id.report_manager:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ReportFragment()).commit();

                break;
            case R.id.setting_manager:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ManageArticlesFragment()).commit();

                break;
            case R.id.user_manager:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new UsersManagerFragment()).commit();

                break;
            case R.id.account:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new CategoriesFragment()).commit();

                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
