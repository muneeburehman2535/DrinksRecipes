package com.example.drinksrecipes.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkRequest {

    @GET("/api/json/v1/1/search.php?")
    Call<ResponseBody> getDrinksByName(@Query("s") String s);

    @GET("/api/json/v1/1/search.php?")
    Call<ResponseBody> getDrinksByAlphabet(@Query("f") String f);


}
