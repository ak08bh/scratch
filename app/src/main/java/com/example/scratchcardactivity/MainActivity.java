package com.example.scratchcardactivity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView scratchCardRecyclerView;
    RelativeLayout click, inviteScreen;
    Button button;
    TextView totalAmount;
    APIInterface apiInterface;
    ScratchCardModel scratchCardModel;
    Context context;
    private ScratchCardAdapter scratchCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        scratchCardRecyclerView = findViewById(R.id.scratchCardRecyclerView);
        click = findViewById(R.id.click);
        inviteScreen = findViewById(R.id.inviteScreen);
        totalAmount = findViewById(R.id.totalAmount);
        button = findViewById(R.id.button);


        scratchCardMethod();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObject jsonObject = new JsonObject();

                    try
                    {
                        JsonArray scratchCardAmount = new JsonArray();
                        jsonObject.addProperty("id", "10");
                        jsonObject.addProperty("rewardId","4");
                        jsonObject.addProperty("scratchCard", false);
                        jsonObject.addProperty("scratchCardAmount", 8);
                        scratchCardAmount.add(jsonObject);

                        JsonObject req = new JsonObject();
                        req.addProperty("remark", "FIRST_TIME_INSTALLATION");
                        req.addProperty("scratchCardAmount", new Gson().toJson(scratchCardAmount));
                    }

                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    JsonParser jsonParser = new JsonParser();
                    JsonObject gsonObject = (JsonObject) jsonParser.parse(jsonObject.toString());
                    Call<JsonObject> call= apiInterface.addRewards("eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyIjp7Im5hbWUiOm51bGwsIm1vYmlsZSI6Ijc0MTI1ODk2MzUiLCJlbWFpbElkIjpudWxsLCJpZCI6MTE5fSwianRpIjoiMTE5IiwiaWF0IjoxNjgwODUzNjMxfQ.jQKw4bWz_zFKv2J__qwY4h8MNC_SPmqr7tkqmwWZj1i_-d7VfqJ3RZvoQb1jtzbfzPGDU3mIefrbMxuv1AfwHA",jsonObject);

                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                            try {

                                JsonObject jsonObject1=response.body();
                                Log.d("json_data_obj:", String.valueOf(gsonObject));
                                Log.e("Response ", jsonObject1 + "");
                            } catch (Exception e) {
                                e.getMessage();
                            }

                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Log.e("message ", t.getMessage() + "");

                        }
                    });




            }
        });

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, UpcomingRewardActivity.class);
                startActivity(intent1);
            }
        });

        inviteScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InviteYourFriend.class);
                startActivity(intent);
            }
        });
    }



    private  void  scratchCardMethod() {
        Call<JsonObject> call;
        apiInterface = APIClient.getClient().create(APIInterface.class);

        call = apiInterface.getScratchCards("eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyIjp7Im5hbWUiOm51bGwsIm1vYmlsZSI6Ijc0MTI1ODk2MzUiLCJlbWFpbElkIjpudWxsLCJpZCI6MTE5fSwianRpIjoiMTE5IiwiaWF0IjoxNjgwODUzNjMxfQ.jQKw4bWz_zFKv2J__qwY4h8MNC_SPmqr7tkqmwWZj1i_-d7VfqJ3RZvoQb1jtzbfzPGDU3mIefrbMxuv1AfwHA", "FIRST_TIME_INSTALLATION");

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {

                    JSONObject api = new JSONObject(String.valueOf(response.body()));
                    JSONObject json = api.getJSONObject("data");

                    ArrayList<ScratchCardModel> reviewlist = new ArrayList();
                    JSONArray scratchCardReview = json.getJSONArray("scratchCardAmount");

                    for (int i = 0; i < scratchCardReview.length(); i++)
                    {
                        ScratchCardModel scratchCardModel= new ScratchCardModel();

                        JSONObject jsonObject = scratchCardReview.getJSONObject(i);
                        scratchCardModel.setScratchCard(jsonObject.getBoolean("scratchCard"));
                        scratchCardModel.setScratchCardAmount(jsonObject.getInt("scratchCardAmount"));
                        scratchCardModel.setId(jsonObject.getInt("id"));
                        scratchCardModel.setRewardId(jsonObject.getInt("rewardId"));

                        reviewlist.add(scratchCardModel);
                    }

                    if(reviewlist.size()>0) {
                        scratchCardRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                        scratchCardAdapter = new ScratchCardAdapter(MainActivity.this, reviewlist);
                        scratchCardRecyclerView.setAdapter(scratchCardAdapter);
                    }

                }
                catch (JSONException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error Creating Comment: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }




}