package nl.ckramer.mynotifications.Activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import nl.ckramer.mynotifications.Fragment.AgendaFragment;
import nl.ckramer.mynotifications.Fragment.HomeFragment;
import nl.ckramer.mynotifications.Fragment.MonthFragment;
import nl.ckramer.mynotifications.Fragment.NoteFragment;
import nl.ckramer.mynotifications.Fragment.NotificationCreateFragment;
import nl.ckramer.mynotifications.Fragment.TodayFragment;
import nl.ckramer.mynotifications.Fragment.WeekFragment;
import nl.ckramer.mynotifications.Model.PushNotification;
import nl.ckramer.mynotifications.R;
import nl.ckramer.mynotifications.Util.NotificationUtil;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    Context mContext;
    FragmentManager mFragmentManager;

    Toolbar mMaterialToolbar;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mFragmentManager = getSupportFragmentManager();

        mMaterialToolbar = findViewById(R.id.toolbar);
        if(mMaterialToolbar != null) {
            setSupportActionBar(mMaterialToolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mMaterialToolbar, R.string.drawer_open,  R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mFragmentManager.addOnBackStackChangedListener(() -> {
            if (mFragmentManager.getBackStackEntryCount() > 1) {
                mDrawerToggle.setDrawerIndicatorEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            } else {
                mDrawerToggle.setDrawerIndicatorEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                mDrawerToggle.syncState();
            }
        });

        mMaterialToolbar.setNavigationOnClickListener(view -> {
            if(mFragmentManager.getBackStackEntryCount() > 1) {
                onBackPressed();
            } else {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        mNavigationView = findViewById(R.id.navigation_view);
        setupDrawerContent(mNavigationView);

        generateNotificationChannels();

        if(getIntent().getParcelableExtra("notification") != null) {
            nl.ckramer.mynotifications.Entity.Notification notification = getIntent().getParcelableExtra("notification");
            if(notification != null) {
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_right, R.animator.slide_in_right, R.animator.slide_out_right);
                transaction.replace(R.id.fragment_content, new NotificationCreateFragment(notification));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();

    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (mFragmentManager.getBackStackEntryCount() > 1) {
            mFragmentManager.popBackStack();
        } else {
            mFragmentManager.popBackStack();
            super.onBackPressed();
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(menuItem -> selectDrawerItem(menuItem, false));

        //On setup select first menu item as checked
        MenuItem selectedMenuItem = mNavigationView.getMenu().getItem(0);
        if(selectedMenuItem != null) {
            selectedMenuItem.setChecked(true);
            selectDrawerItem(selectedMenuItem, true);
        }
    }



    public boolean selectDrawerItem(MenuItem menuItem, boolean needToAddBackstack) {
        switch(menuItem.getItemId()) {
            case R.id.nav_home:
                changeFragment(new HomeFragment(), needToAddBackstack);
                setTitle(R.string.notification);
                break;
            case R.id.nav_today:
                changeFragment(new TodayFragment(), needToAddBackstack);
                setTitle(R.string.screen_today);
                break;
            case R.id.nav_week:
                changeFragment(new WeekFragment(), needToAddBackstack);
                setTitle(R.string.screen_week);
                break;
            case R.id.nav_month:
                changeFragment(new MonthFragment(), needToAddBackstack);
                setTitle(R.string.screen_month);
                break;
            case R.id.nav_agenda:
                changeFragment(new AgendaFragment(), needToAddBackstack);
                setTitle(R.string.screen_agenda);
                break;
            case R.id.nav_note:
                changeFragment(new NoteFragment(), needToAddBackstack);
                setTitle(R.string.screen_note);
                break;
            default:
                changeFragment(new HomeFragment(), needToAddBackstack);
                setTitle(R.string.notification);
        }

        menuItem.setChecked(true);
        mDrawerLayout.closeDrawers();
        return true;
    }

    private void changeFragment(Fragment fragment, boolean needToAddBackstack) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (needToAddBackstack)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void generateNotificationChannels() {
        PushNotification notificationChannel = new PushNotification(getString(R.string.notification), getString(R.string.notification_description), mContext, NotificationManager.IMPORTANCE_MAX, NotificationUtil.NOTIFICATION_CHANNEL);
        NotificationUtil.createNotificationChannel(notificationChannel);

        PushNotification reminderChannel = new PushNotification(getString(R.string.reminder), getString(R.string.reminder_description), mContext, NotificationManager.IMPORTANCE_HIGH, NotificationUtil.REMINDER_CHANNEL);
        NotificationUtil.createNotificationChannel(reminderChannel);
    }

}

