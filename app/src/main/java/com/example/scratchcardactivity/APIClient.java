package com.example.scratchcardactivity;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient
{
    private static Retrofit retrofit = null;
    //Production
    //private  static String BaseUrl="http://cooldbprod-env.eba-bznxkqwk.ap-northeast-1.elasticbeanstalk.com/chef/v1/";

    //Testing
    private  static String BaseUrl="http://cooldbstaging-env.eba-7xfzavz8.ap-northeast-1.elasticbeanstalk.com/chef/v1/";

    public static Retrofit getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(320, TimeUnit.SECONDS)
                .writeTimeout(320, TimeUnit.SECONDS)
                .connectTimeout(320, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }
}
