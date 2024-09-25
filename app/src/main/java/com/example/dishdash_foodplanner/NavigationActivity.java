package com.example.dishdash_foodplanner;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.dishdash_foodplanner.tabs.cart.view.CartFragment;
import com.example.dishdash_foodplanner.tabs.favourite.view.FavoritesFragment;
import com.example.dishdash_foodplanner.tabs.home.view.HomeFragment;
import com.example.dishdash_foodplanner.tabs.plan.view.PlanFragment;
import com.example.dishdash_foodplanner.tabs.search.view.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    selectedFragment = new HomeFragment();
                } else if (itemId == R.id.nav_search) {
                     selectedFragment = new SearchFragment();
                } else if (itemId == R.id.nav_cart) {
                     selectedFragment = new CartFragment();
                } else if (itemId == R.id.nav_fav) {
                     selectedFragment = new FavoritesFragment();
                } else if (itemId == R.id.nav_plan) {
                     selectedFragment = new PlanFragment();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.contentFrame, selectedFragment).commit();
                }
                return true;
            }
        });

        // Set the default selected item to the home item
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }
}