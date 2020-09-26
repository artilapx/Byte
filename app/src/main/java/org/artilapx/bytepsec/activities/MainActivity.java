package org.artilapx.bytepsec.activities;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.artilapx.bytepsec.R;
import org.artilapx.bytepsec.fragments.NewsFragment;
import org.artilapx.bytepsec.fragments.ScheduleFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private boolean mDoubleBackToExitPressedOnce = false;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;
    private Toolbar toolbar;

    private ScheduleFragment scheduleFragment;
    private NewsFragment newsFragment;

    private static final String KEY_NAV_ITEM = "CURRENT_NAV_ITEM";

    private int selectedNavItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> mDrawerLayout.openDrawer(GravityCompat.START));
        setSupportActionBar(toolbar);
        enableTransparentStatusBar(android.R.color.transparent);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        if (mDrawerLayout != null){
            mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close){
                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    invalidateOptionsMenu();
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    invalidateOptionsMenu();
                }
            };
            mToggle.setDrawerIndicatorEnabled(true);
            mDrawerLayout.addDrawerListener(mToggle);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        mNavigationView = findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        // Init the fragments.
        if (savedInstanceState != null) {
            scheduleFragment = (ScheduleFragment) getSupportFragmentManager().getFragment(savedInstanceState, "ScheduleFragment");
            newsFragment = (NewsFragment) getSupportFragmentManager().getFragment(savedInstanceState, "NewsFragment");
            selectedNavItem = savedInstanceState.getInt(KEY_NAV_ITEM);
        } else {
            scheduleFragment = (ScheduleFragment) getSupportFragmentManager().findFragmentById(R.id.content);
            if (scheduleFragment == null) {
                scheduleFragment = ScheduleFragment.newInstance(0);
            }

            newsFragment = (NewsFragment) getSupportFragmentManager().findFragmentById(R.id.content);
            if (newsFragment == null) {
                newsFragment = NewsFragment.newInstance();
            }
        }

        // Add the fragments.
        if (!scheduleFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, scheduleFragment, "ScheduleFragment")
                    .commit();
        }
        if (!newsFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, newsFragment, "NewsFragment")
                    .commit();
        }

        // Show the default fragment.
        if (selectedNavItem == 0) {
            showScheduleFragment();
        } else if (selectedNavItem == 1) {
            showNewsFragment();
        }
    }

    public void enableTransparentStatusBar(@ColorRes int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            if (color != 0) {
                window.setStatusBarColor(ContextCompat.getColor(this, color));
            }
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (mDoubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.mDoubleBackToExitPressedOnce = true;
            Toast.makeText(this, getString(R.string.double_back_to_exit_message), Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(() -> mDoubleBackToExitPressedOnce = false, 2000);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (scheduleFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "ScheduleFragment", scheduleFragment);
        }
        if (newsFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "NewsFragment", newsFragment);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle != null && mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        Intent intent = new Intent();
        int x = item.getItemId();
        switch (x) {
            case R.id.action_about:
                intent.setClass(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent = new Intent();
        int id = menuItem.getItemId();
        if (id == R.id.action_schedule) {
            showScheduleFragment();
        } else if (id == R.id.action_news) {
            showNewsFragment();
        } else if (id == R.id.action_about) {
            intent.setClass(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Show the schedule fragment.
     */
    private void showScheduleFragment() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(scheduleFragment);
        fragmentTransaction.hide(newsFragment);
        fragmentTransaction.commit();

        toolbar.setTitle(getResources().getString(R.string.schedule));
        mNavigationView.setCheckedItem(R.id.action_schedule);
    }

    /**
     * Show the news list fragment.
     */
    private void showNewsFragment() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(newsFragment);
        fragmentTransaction.hide(scheduleFragment);
        fragmentTransaction.commit();

        toolbar.setTitle(getResources().getString(R.string.news));
        mNavigationView.setCheckedItem(R.id.action_news);
    }

}