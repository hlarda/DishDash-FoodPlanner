package com.example.dishdash_foodplanner;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.dishdash_foodplanner.network.APIs.NetworkChangeReceiver;
import com.example.dishdash_foodplanner.tabs.details.view.DetailsFragment;
import com.example.dishdash_foodplanner.tabs.home.view.HomeFragment;
import com.example.dishdash_foodplanner.tabs.itemlist.view.ItemListFragment;
import com.example.dishdash_foodplanner.tabs.plan.view.PlanFragment;
import com.example.dishdash_foodplanner.tabs.saved.view.SavedMealsFragment;
import com.example.dishdash_foodplanner.tabs.search.view.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.os.Handler;
import android.view.View;
import com.google.android.material.snackbar.Snackbar;

public class NavigationActivity extends AppCompatActivity {
    private Handler handler;
    private Snackbar snackbar;
    private int lastSelectedFragmentId = R.id.nav_home;
    private boolean wasConnected = false;
    private boolean isActivityResumed = false; 
    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                lastSelectedFragmentId = item.getItemId();
                loadFragment(lastSelectedFragmentId);
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        networkChangeReceiver = new NetworkChangeReceiver(isConnected -> {
            wasConnected = isConnected;
            if (isConnected) {
                dismissSnackbar();
                reloadCurrentFragment();
            } else {
                if (lastSelectedFragmentId == R.id.nav_home || lastSelectedFragmentId == R.id.nav_search) {
                    showNetworkSettingsSnackbar();
                }
            }
        });

        handler = new Handler(Looper.getMainLooper());

        setStatusBarTextColor();
    }

    private void setStatusBarTextColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isDarkTheme()) {
                getWindow().getDecorView().setSystemUiVisibility(0); // Clear the flag for dark mode
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR); // Set the flag for light mode
            }
        }
    }

    private boolean isDarkTheme() {
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActivityResumed = true; 
        networkChangeReceiver.register(this);
        if (!wasConnected) {
            if (lastSelectedFragmentId == R.id.nav_home || lastSelectedFragmentId == R.id.nav_search) {
                showNetworkSettingsSnackbar();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActivityResumed = false;  
        networkChangeReceiver.unregister(this);
    }

    private void loadFragment(int itemId) {
        Fragment selectedFragment = null;

        if (itemId == R.id.nav_home) {
            selectedFragment = new HomeFragment();
        } else if (itemId == R.id.nav_search) {
            selectedFragment = new SearchFragment();
        }  else if (itemId == R.id.nav_save) {
            selectedFragment = new SavedMealsFragment();
        } else if (itemId == R.id.nav_plan) {
            selectedFragment = new PlanFragment();
        }

        if (selectedFragment != null && !isFinishing()) {
            try {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentFrame, selectedFragment)
                        //Solved the issue of overlapping fragments
                        .addToBackStack(null)
                        .commit();
            } catch (IllegalStateException e) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentFrame, selectedFragment)
                        .commitAllowingStateLoss();
            }
        }

        // After loading a new fragment, check if we need to show the Snackbar
        if (itemId == R.id.nav_home || itemId == R.id.nav_search) {
            if (!wasConnected) {
                showNetworkSettingsSnackbar();
            } else {
                dismissSnackbar();
            }
        } else {
            dismissSnackbar();
        }
    }

    private void showNetworkSettingsSnackbar() {
        if (snackbar == null || !snackbar.isShown()) {
            View snackbarView = findViewById(R.id.contentFrame);
            snackbar = Snackbar.make(snackbarView, "No network connection available", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Settings", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        }
                    });

            snackbar.setAnchorView(findViewById(R.id.bottomNav));
            snackbar.show();
        }
    }

    private void dismissSnackbar() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
    }
    private void reloadCurrentFragment() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (currentFragment instanceof HomeFragment) {
            ((HomeFragment) currentFragment).reloadData();
        } else if (currentFragment instanceof DetailsFragment) {
            ((DetailsFragment) currentFragment).reloadData();
        } else if (currentFragment instanceof ItemListFragment) {
            ((ItemListFragment) currentFragment).reloadData();
        }
    }
    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        Log.i(TAG, "Back");
        // If the current fragment is a "root" fragment, finish the activity
        if (currentFragment instanceof HomeFragment ||
                currentFragment instanceof SearchFragment ||
                currentFragment instanceof SavedMealsFragment ||
                currentFragment instanceof PlanFragment) {
            finish();  // Exit the app when on root fragments
        } else {
            Log.i(TAG, "onBackPressed: Other");
            // Otherwise, pop the back stack (i.e., go back to the previous fragment)
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }
}
