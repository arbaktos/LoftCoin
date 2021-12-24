package com.vasilisasycheva.loftcoin.data;

import android.content.Context;

import androidx.room.Room;

import com.squareup.moshi.Moshi;
import com.vasilisasycheva.loftcoin.BuildConfig;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public abstract class DataModule {

    @Provides
    static Moshi moshi() {
        final Moshi moshi = new Moshi.Builder().build();
        return moshi.newBuilder()
                .add(Coin.class, moshi.adapter(AutoValue_CmcCoin.class))
                .add(Listing.class, moshi.adapter(AutoValue_Listing.class))
                .build();

    }

    @Provides
    @Singleton
    static LoftDatabase loftDatabase(Context context) {
        if (BuildConfig.DEBUG) {
            return Room.inMemoryDatabaseBuilder(context, LoftDatabase.class).build();
        } else {
            return Room.databaseBuilder(context, LoftDatabase.class, "loft.db").build();
        }
    }

    @Provides
    static Retrofit retrofit(OkHttpClient httpClient, Moshi moshi) {
        final Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(httpClient.newBuilder()
                .addInterceptor(chain -> {
                    final Request request = chain.request();
                    return chain.proceed(request.newBuilder()
                            .addHeader(CoinApi.API_KEY, BuildConfig.API_KEY)
                            .build());
                })
                .build());
        builder.baseUrl(BuildConfig.API_END_POINT);
        builder.addConverterFactory(MoshiConverterFactory.create(moshi));
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        return builder.build();
    }

    @Provides
    static CoinApi coinApi (Retrofit retrofit) {
        return retrofit.create(CoinApi.class);
    }

    @Binds
    abstract CoinsRepo coinsRepo(CmcCoinsRepoImpl impl);

    @Binds
    abstract CurrencyRepo currencyRepo(CurrencyRepoImpl impl);

    @Binds
    abstract WalletsRepo walletsRepo(WalletsRepoImpl impl);
}
