package nl.ckramer.mynotifications.Activity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import nl.ckramer.mynotifications.Fragment.AgendaFragment;
import nl.ckramer.mynotifications.Fragment.HomeFragment;
import nl.ckramer.mynotifications.Fragment.MonthFragment;
import nl.ckramer.mynotifications.Fragment.NoteFragment;
import nl.ckramer.mynotifications.Fragment.TodayFragment;
import nl.ckramer.mynotifications.Fragment.WeekFragment;
import nl.ckramer.mynotifications.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    Context mContext;

    MaterialToolbar mMaterialToolbar;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    NavigationView mNavigationView;

    /*
    * https://objectbox.io/
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        mMaterialToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mMaterialToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mMaterialToolbar, R.string.drawer_open,  R.string.drawer_close);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mNavigationView = findViewById(R.id.navigation_view);
        setupDrawerContent(mNavigationView);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        MenuItem menuItem = mNavigationView.getMenu().getItem(0);
        if(menuItem != null) {
            menuItem.setChecked(true);
            selectDrawerItem(menuItem);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectDrawerItem(menuItem);
                return true;
            }
        });
    }



    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass;

        switch(menuItem.getItemId()) {
            case R.id.nav_home:
                fragmentClass = HomeFragment.class;
                break;
            case R.id.nav_today:
                fragmentClass = TodayFragment.class;
                break;
            case R.id.nav_week:
                fragmentClass = WeekFragment.class;
                break;
            case R.id.nav_month:
                fragmentClass = MonthFragment.class;
                break;
            case R.id.nav_agenda:
                fragmentClass = AgendaFragment.class;
                break;
            case R.id.nav_note:
                fragmentClass = NoteFragment.class;
                break;
            default:
                fragmentClass = HomeFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            Log.e(TAG, "selectDrawerItem: ", e);
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragment, TAG);
        fragmentTransaction.replace(R.id.fragment_content, fragment).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawerLayout.closeDrawers();

    }
}
