package com.example.scratchcardactivity;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface APIInterface
{
    @Headers("Content-Type: application/json")
    @GET("users/getScratchCards")
    Call<JsonObject> getScratchCards(
            @Header("accessToken") String accessToken,
            @Query("remark") String remark);
}
