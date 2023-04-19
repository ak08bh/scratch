package com.example.scratchcardactivity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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
    String remark="FIRST_TIME_INSTALLATION";
    int  statusCode;
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

        call = apiInterface.getScratchCards("eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyIjp7Im5hbWUiOm51bGwsIm1vYmlsZSI6IjcwMDA3OTIyNjQiLCJlbWFpbElkIjpudWxsLCJpZCI6MTMxfSwianRpIjoiMTMxIiwiaWF0IjoxNjgxODkxMjA1fQ.3KfwBQJe9Ue4o8mlv0AXE7oZnNfsg-bG7ga0MNXljcKE9aGDpyTlG79RH89zgPnPKl2bLh_oGurSF_-QcNCHdA", "FIRST_TIME_INSTALLATION");

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


    public  void showDailogBox(ScratchCardAdapter.ViewHolder holder,Context context,String num,int pos,int id,int rewardId,Boolean scratchCard)
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_scratch_card);
        TextView winCoins = dialog.findViewById(R.id.winCoins);
        ScratchView scratch_view=dialog.findViewById(R.id.scratch_view);
        dialog.show();
        scratch_view.setRevealListener(new ScratchView.IRevealListener()
        {
            @Override
            public void onRevealed(ScratchView scratchView)
            {

            }
            @Override
            public void onRevealPercentChangedListener(ScratchView scratchView, float percent)
            {
               if(percent>=0.1)
               {
                   winCoins.setText(num);
                   final Timer timer2 = new Timer();
                   timer2.schedule(new TimerTask() {
                       public void run() {
                           dialog.dismiss();

                           holder.winAmount.setText(num);


                           //this will cancel the timer of the system

                       }
                   }, 2000);
               }
            }

        });

        System.out.println("the position of the view is "+pos);
        cardViewApi(remark,scratchCard,num,id,rewardId,holder);
    }




    public void cardViewApi(String remark,Boolean scratchCard,String sa,int id,int rewardId,ScratchCardAdapter.ViewHolder holder) {


            int scratchAmount = Integer.parseInt(sa);
            JSONObject userObj = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            JSONObject arraydata = new JSONObject();

            JsonObject gsonObject = null;
            try {
                userObj.put("remark", remark);
                arraydata.put("id", id);
                arraydata.put("rewardId", rewardId);
                arraydata.put("scratchCardAmount", scratchAmount);
                jsonArray.put(arraydata);
                userObj.put("scratchCardAmount", jsonArray);

                JsonParser jsonParser = new JsonParser();
                gsonObject = (JsonObject) jsonParser.parse(userObj.toString());
            } catch (Exception e) {

            }

            Call<JsonObject> call = apiInterface.addRewards("eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyIjp7Im5hbWUiOm51bGwsIm1vYmlsZSI6IjcwMDA3OTIyNjQiLCJlbWFpbElkIjpudWxsLCJpZCI6MTMxfSwianRpIjoiMTMxIiwiaWF0IjoxNjgxODkxMjA1fQ.3KfwBQJe9Ue4o8mlv0AXE7oZnNfsg-bG7ga0MNXljcKE9aGDpyTlG79RH89zgPnPKl2bLh_oGurSF_-QcNCHdA", gsonObject);

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    try {

                        JsonObject jsonObject1 = response.body();
                        Log.d("json_data_obj:", String.valueOf(jsonObject1));
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


}