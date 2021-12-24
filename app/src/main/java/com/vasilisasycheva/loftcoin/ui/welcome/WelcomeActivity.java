package com.vasilisasycheva.loftcoin.ui.welcome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.vasilisasycheva.loftcoin.databinding.ActivityWelcomeBinding;
import com.vasilisasycheva.loftcoin.ui.main.MainActivity;
import com.vasilisasycheva.loftcoin.ui.widget.CircleIndicator;

public class WelcomeActivity extends AppCompatActivity {

    public static final String KEY_SHOW_WELCOME = "show_welcome";
    private SnapHelper helper;
    private ActivityWelcomeBinding binding;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.recyclerWelcome.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.recyclerWelcome.setAdapter(new WelcomeRecyclerAdapter());
        binding.recyclerWelcome.addItemDecoration(new CircleIndicator(this));
        helper = new PagerSnapHelper();
        helper.attachToRecyclerView(binding.recyclerWelcome);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        binding.textBtnBegin.setOnClickListener(v -> {
            prefs.edit().putBoolean(KEY_SHOW_WELCOME, false).apply();
            startActivity(new Intent(this, MainActivity.class));
            finish();

        });

    }

    @Override
    protected void onDestroy() {
        helper.attachToRecyclerView(null);
        binding.recyclerWelcome.setAdapter(null);
        super.onDestroy();
    }
}
