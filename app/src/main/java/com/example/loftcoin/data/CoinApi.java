package com.example.loftcoin.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface CoinApi {

    String API_KEY = "X-CMC_PRO_API_KEY";

    @GET("./cryptocurrency/listings/latest")
    Call<Listing> getListing(@Query("convert") String convert);
}
