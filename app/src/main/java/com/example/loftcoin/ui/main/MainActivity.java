package com.example.loftcoin.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentFactory;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.os.Bundle;

import com.example.loftcoin.BaseComponent;
import com.example.loftcoin.LoftApp;
import com.example.loftcoin.R;
import com.example.loftcoin.databinding.ActivityMainBinding;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity {

    private MainComponent component;

    @Inject FragmentFactory fragmentFactory;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        final BaseComponent baseComponent = ((LoftApp) newBase.getApplicationContext()).getComponent();
        component = DaggerMainComponent.builder().baseComponent(baseComponent).build();
        component.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().setFragmentFactory(fragmentFactory);

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