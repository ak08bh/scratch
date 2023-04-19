package com.example.scratchcardactivity;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface
{
    @Headers("Content-Type: application/json")
    @GET("users/getScratchCards")
    Call<JsonObject> getScratchCards(
            @Header("accessToken") String accessToken,
            @Query("remark") String remark);

    @Headers("Content-Type: application/json")
    @POST("users/addRewards")
    Call<JsonObject> addRewards(
            @Header("accessToken") String accessToken,
            @Body JsonObject rewardDtos );

    @Headers("Content-Type: application/json")
    @GET("users/getRewards")
    Call<JsonObject> getRewards(
            @Header("accessToken") String accessToken);

}
