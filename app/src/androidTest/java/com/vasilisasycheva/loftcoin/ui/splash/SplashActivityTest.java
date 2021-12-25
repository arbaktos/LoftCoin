package com.vasilisasycheva.loftcoin.ui.splash;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SplashActivityTest {

    private SharedPreferences prefs;

    @Before
    public void setUp() throws Exception {
        final Context context = ApplicationProvider.getApplicationContext();
        prefs = PreferenceManager.getDefaultSharedPreferences(context);

    }

    @Test
    public void open_welcome_activity_if_first_run() {
        final ActivityScenario<SplashActivity> scenario = ActivityScenario.launch(SplashActivity.class);

    }

}