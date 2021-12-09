package com.example.loftcoin.ui.widget;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class AppModule {

    @Singleton
    @Provides
    static Context context (@NonNull Application app) {
        return app.getApplicationContext();
    }
}
