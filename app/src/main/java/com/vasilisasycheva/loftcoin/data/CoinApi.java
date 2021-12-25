package com.vasilisasycheva.loftcoin.data;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CoinApi {

    String API_KEY = "X-CMC_PRO_API_KEY";

    @GET("cryptocurrency/listings/latest")
    Observable<Listing> getListing(@Query("convert") String convert);

    @GET("/v1/key/info")
    Call<String> getKeyInfo();

}
