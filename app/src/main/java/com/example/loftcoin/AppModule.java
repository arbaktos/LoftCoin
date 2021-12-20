package com.example.loftcoin;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.loftcoin.data.CoinApi;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public abstract class AppModule {

    @Provides
    @Singleton
    static OkHttpClient httpClient(ExecutorService executor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.dispatcher(new Dispatcher(executor));

        if (BuildConfig.DEBUG) {
            final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpLoggingInterceptor.redactHeader(CoinApi.API_KEY);
            builder.addInterceptor(httpLoggingInterceptor);
        }
        return builder.build();
    }

    @Singleton
    @Provides
    public static Context context (@NonNull Application app) {
        return app.getApplicationContext();
    }


    @Provides
    @Singleton
    static ExecutorService ioExecutor() {
        int poolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
        return Executors.newFixedThreadPool(poolSize);
    }

    @Provides
    @Singleton
    static Picasso picasso(Context context, OkHttpClient httpClient, ExecutorService executor) {
        return new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(httpClient))
                .executor(executor)
                .build();
    }
}
