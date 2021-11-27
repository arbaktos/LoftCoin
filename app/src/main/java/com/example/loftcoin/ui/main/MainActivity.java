package com.example.loftcoin.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.loftcoin.R;
import com.example.loftcoin.databinding.ActivityMainBinding;
import com.example.loftcoin.databinding.ActivityWelcomeBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final NavController navController = Navigation.findNavController(this, R.id.nav_controller_container);
        NavigationUI.setupWithNavController(binding.bottomNavMenu, navController);
        NavigationUI.setupWithNavController(binding.toolbar, navController,
                new AppBarConfiguration
                        .Builder(binding.bottomNavMenu.getMenu())
                        .build());
    }
}