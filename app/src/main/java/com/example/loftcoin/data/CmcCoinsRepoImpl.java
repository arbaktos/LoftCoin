package com.example.loftcoin.data;

import androidx.annotation.NonNull;

import com.example.loftcoin.BuildConfig;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Singleton
class CmcCoinsRepoImpl implements CoinsRepo {

    private final CoinApi api;

    @Inject
    public CmcCoinsRepoImpl(CoinApi api) {
        this.api = api;
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

}
