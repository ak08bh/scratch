package com.example.scratchcardactivity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anupkumarpanwar.scratchview.ScratchView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{
    RecyclerView scratchCardRecyclerView;
    RelativeLayout click, inviteScreen;
    Button button;
    APIInterface apiInterface;
    private ScratchCardAdapter scratchCardAdapter;
    TextView total;
    Dialog dialog;
    String remark="FIRST_TIME_INSTALLATION";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        scratchCardRecyclerView = findViewById(R.id.scratchCardRecyclerView);
        click = findViewById(R.id.click);
        inviteScreen = findViewById(R.id.inviteScreen);
        button = findViewById(R.id.button);
        total=findViewById(R.id.total);

        scratchCardMethod();
        addRewardsApi();

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

        call = apiInterface.getScratchCards("eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyIjp7Im5hbWUiOm51bGwsIm1vYmlsZSI6IjcwMDA3OTIyNjQiLCJlbWFpbElkIjpudWxsLCJpZCI6MTMyfSwianRpIjoiMTMyIiwiaWF0IjoxNjgxOTgzNDMwfQ.mvlUTzb3v2tuyEeqmvH0Md42eY20Hq2BXdGfy26Y0tpuQ_HE1LnCPdUwbpJpH_7DOnuaG92YDVDNKKOPaCPsBw", "FIRST_TIME_INSTALLATION");

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JSONObject api = new JSONObject(String.valueOf(response.body()));
                    JSONObject json = api.getJSONObject("data");

                    JSONArray scratchCardReview = json.getJSONArray("scratchCardAmount");
                    ArrayList<ScratchCardModel> reviewlist = new ArrayList();

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

                    if(reviewlist.size()>0)
                    {
                        scratchCardRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                        scratchCardAdapter = new ScratchCardAdapter(MainActivity.this, reviewlist,MainActivity.this);
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

    public  void showDailogBox(LinearLayout wonLayout,ScratchCardAdapter.ViewHolder holder,Context context,String num,int id,int rewardId,Boolean scratchCard)
    {
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.activity_scratch_card);
            TextView winCoins = dialog.findViewById(R.id.winCoins);
            ScratchView scratch_view = dialog.findViewById(R.id.scratch_view);
            dialog.show();

            scratch_view.setRevealListener(new ScratchView.IRevealListener() {
                @Override
                public void onRevealed(ScratchView scratchView)
                {

                }

                @Override
                public void onRevealPercentChangedListener(ScratchView scratchView, float percent) {
                    if (percent >= 0.2) {
                        winCoins.setText(num);
                        scratch_view.setVisibility(View.GONE);
                        wonLayout.setVisibility(View.GONE);
                        holder.winAmount.setText(num);

                        new CountDownTimer(1000, 10) {
                            public void onTick(long millisUntilFinished) {

                            }

                            public void onFinish() {
                                dialog.dismiss();

                            }
                        }.start();


                        //this will cancel the timer of the system
                    }
                }
            });
        cardViewApi(remark, num, id, rewardId);

        scratchCardMethod();
        }

    private void addRewardsApi()
    {
             try {
             Call<JsonObject> call= apiInterface.getRewards("eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyIjp7Im5hbWUiOm51bGwsIm1vYmlsZSI6IjcwMDA3OTIyNjQiLCJlbWFpbElkIjpudWxsLCJpZCI6MTMyfSwianRpIjoiMTMyIiwiaWF0IjoxNjgxOTgzNDMwfQ.mvlUTzb3v2tuyEeqmvH0Md42eY20Hq2BXdGfy26Y0tpuQ_HE1LnCPdUwbpJpH_7DOnuaG92YDVDNKKOPaCPsBw");

             call.enqueue(new Callback<JsonObject>() {
                 @Override
                 public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        try
                        {
                            JSONObject api= new JSONObject(String.valueOf(response.body()));
                            String statusCode = api.getString("statusCode");
                            JSONObject data = api.getJSONObject("data");

                            if(statusCode.equals("200"))
                            {
                                int numberTotal= data.getInt("totalAmount");
                                total.setText(String.valueOf(numberTotal));
                            }

                        }
                        catch (JSONException e)
                        {

                        }
                 }

                 @Override
                 public void onFailure(Call<JsonObject> call, Throwable t) {
                     Log.e("message ", t.getMessage() + "");

                 }
             });
              }
            catch (Exception e)
             {
                    e.printStackTrace();
               }
    }

    public void cardViewApi(String remark,String sa,int id,int rewardId)
    {
        try {
            JSONObject userObj = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            JSONObject arraydata = new JSONObject();

            JsonObject gsonObject = null;

                userObj.put("remark", remark);
                arraydata.put("id", id);
                arraydata.put("rewardId", rewardId);
                arraydata.put("scratchCardAmount", sa);
                jsonArray.put(arraydata);
                userObj.put("scratchCardAmount", jsonArray);

                JsonParser jsonParser = new JsonParser();
                gsonObject = (JsonObject) jsonParser.parse(userObj.toString());

            Call<JsonObject> call = apiInterface.addRewards("eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyIjp7Im5hbWUiOm51bGwsIm1vYmlsZSI6IjcwMDA3OTIyNjQiLCJlbWFpbElkIjpudWxsLCJpZCI6MTMyfSwianRpIjoiMTMyIiwiaWF0IjoxNjgxOTgzNDMwfQ.mvlUTzb3v2tuyEeqmvH0Md42eY20Hq2BXdGfy26Y0tpuQ_HE1LnCPdUwbpJpH_7DOnuaG92YDVDNKKOPaCPsBw", gsonObject);

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.body() != null) {

                        JSONObject api = null;
                        try {
                            api = new JSONObject(String.valueOf(response.body()));
                            String statusCode = api.getString("statusCode");
                            String message = api.getString("message");

                            if (statusCode.equals("200")) {
                                addRewardsApi();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.e("message ", t.getMessage() + "");

                }

            });


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        }

    }

