package com.example.cropfit.activities;

import android.os.Bundle;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.cropfit.R;
import com.example.cropfit.databinding.ActivityMainBinding;
import com.example.cropfit.fragments.HistoryFragment;
import com.example.cropfit.fragments.HomeFragment;
import com.example.cropfit.fragments.UserFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Initialize View Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 2. Set Default Fragment (Home) on startup
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }

        // 3. Bottom Navigation Listener
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (id == R.id.nav_history) {
                selectedFragment = new HistoryFragment();
            } else if (id == R.id.nav_profile) {
                // Ensure your fragment class is named UserFragment
                selectedFragment = new UserFragment();
            }

            return loadFragment(selectedFragment);
        });

        // 4. Back Button Behavior
        // If on History/Profile, back goes to Home. If on Home, back exits.
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (binding.bottomNavigation.getSelectedItemId() != R.id.nav_home) {
                    binding.bottomNavigation.setSelectedItemId(R.id.nav_home);
                } else {
                    finish();
                }
            }
        });
    }

    /**
     * Replaces the content of the fragment_container with the selected fragment
     */
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}