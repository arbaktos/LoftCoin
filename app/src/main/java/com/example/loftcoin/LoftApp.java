package com.example.loftcoin;

import android.app.Application;
import android.os.StrictMode;

import com.example.loftcoin.ui.main.MainActivity;
import com.example.loftcoin.utils.DebugTree;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.iid.InstanceIdResult;

import timber.log.Timber;

public class LoftApp extends Application {

    private BaseComponent component;

    public BaseComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults();
            Timber.plant(new DebugTree());
        }

        component = DaggerAppComponent.builder()
                .application(this)
                .build();

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(instanceIdResult -> {
            Timber.d("fcm %s", instanceIdResult.getToken());
        });

    }
}
