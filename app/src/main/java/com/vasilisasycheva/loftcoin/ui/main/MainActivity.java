package com.vasilisasycheva.loftcoin.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentFactory;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.os.Bundle;

import com.vasilisasycheva.loftcoin.BaseComponent;
import com.vasilisasycheva.loftcoin.LoftApp;
import com.vasilisasycheva.loftcoin.R;
import com.vasilisasycheva.loftcoin.databinding.ActivityMainBinding;

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

        setSupportActionBar(binding.toolbar);

        final NavController navController = Navigation.findNavController(this, R.id.nav_controller_container);
        NavigationUI.setupWithNavController(binding.bottomNavMenu, navController);
        NavigationUI.setupWithNavController(binding.toolbar, navController,
                new AppBarConfiguration
                        .Builder(binding.bottomNavMenu.getMenu())
                        .build());
    }
}