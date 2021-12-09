package com.example.loftcoin;

import android.app.Application;
import android.os.StrictMode;

public class LoftApp extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults();
        }

        component = DaggerAppComponent.builder()
                .application(this)
                .build();
    }
}
