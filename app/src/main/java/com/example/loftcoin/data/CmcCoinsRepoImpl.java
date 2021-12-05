package com.example.loftcoin.data;

import androidx.annotation.NonNull;

import com.example.loftcoin.BuildConfig;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class CmcCoinsRepoImpl implements CoinsRepo {

    private final CoinApi api;

    public CmcCoinsRepoImpl() {
        api = createRetrofit(createOkHttp()).create(CoinApi.class);
    }

    @NonNull
    @Override
    public List<? extends Coin> getListings(@NonNull String currency) throws IOException {
        final Response<Listing> response = api.getListing(currency).execute();
        if (response.isSuccessful()) {
            final Listing listing = response.body();
            if (listing != null && listing.data() != null) {
                return listing.data();
            } else {
                final ResponseBody responseBody = response.errorBody();
                if (responseBody != null) {
                    throw new IOException(responseBody.string());
                }
            }
        }
        return Collections.emptyList();
    }

    private OkHttpClient createOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor( chain -> {
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

    private Retrofit createRetrofit(OkHttpClient okHttpClient) {
        final Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BuildConfig.API_END_POINT);
        final Moshi moshi = new Moshi.Builder().build();
        builder.addConverterFactory(MoshiConverterFactory.create(
                moshi.newBuilder()
                .add(Coin.class, moshi.adapter(AutoValue_Coin.class))
                .add(Listing.class, moshi.adapter(AutoValue_Listing.class))
                .build()
        ));
        builder.client(okHttpClient);
        return builder.build();
    }
}
