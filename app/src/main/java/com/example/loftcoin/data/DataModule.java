package com.example.loftcoin.data;

import com.example.loftcoin.BuildConfig;
import com.squareup.moshi.Moshi;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public abstract class DataModule {

    @Provides
    @Singleton
    static OkHttpClient httpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor(chain -> {
            final Request request = chain.request();
            return chain.proceed(request.newBuilder()
                    .addHeader(CoinApi.API_KEY, BuildConfig.API_KEY)
                    .build());
        });

        if (BuildConfig.DEBUG) {
            final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpLoggingInterceptor.redactHeader(CoinApi.API_KEY);
            builder.addInterceptor(httpLoggingInterceptor);
        }
        return builder.build();
    }

    @Provides
    static Moshi moshi() {
        final Moshi moshi = new Moshi.Builder().build();
        return moshi.newBuilder()
                .add(Coin.class, moshi.adapter(AutoValue_Coin.class))
                .add(Listing.class, moshi.adapter(AutoValue_Listing.class))
                .build();

    }

    @Provides
    static Retrofit retrofit(OkHttpClient okHttpClient) {
        final Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BuildConfig.API_END_POINT);
        final Moshi moshi = new Moshi.Builder().build();
        builder.addConverterFactory(MoshiConverterFactory.create(moshi));
        builder.client(okHttpClient);
        return builder.build();
    }

    @Provides
    static CoinApi coinApi (Retrofit retrofit) {
        return retrofit.create(CoinApi.class);
    }

    @Binds
    abstract CoinsRepo coinsRepo(CmcCoinsRepoImpl impl);
}
