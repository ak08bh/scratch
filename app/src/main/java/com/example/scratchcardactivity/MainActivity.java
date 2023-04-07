package com.example.scratchcardactivity;

import static java.lang.String.valueOf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView scratchCardRecyclerView;
    ArrayList<ScratchCardItemList> scratchCardItemListArrayList = new ArrayList<>();
    RelativeLayout click, inviteScreen;
    Button button;
    TextView coins;
    APIInterface apiInterface;

    private ScratchCardAdapter scratchCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        scratchCardRecyclerView = findViewById(R.id.scratchCardRecyclerView);
        click = findViewById(R.id.click);
        inviteScreen = findViewById(R.id.inviteScreen);
        coins = findViewById(R.id.coins);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Call<JsonObject> call;
                    apiInterface = APIClient.getClient().create(APIInterface.class);

                    call = apiInterface.getScratchCards("eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyIjp7Im5hbWUiOm51bGwsIm1vYmlsZSI6Ijc0MTI1ODk2MzUiLCJlbWFpbElkIjpudWxsLCJpZCI6MTE5fSwianRpIjoiMTE5IiwiaWF0IjoxNjgwODUzNjMxfQ.jQKw4bWz_zFKv2J__qwY4h8MNC_SPmqr7tkqmwWZj1i_-d7VfqJ3RZvoQb1jtzbfzPGDU3mIefrbMxuv1AfwHA", "FIRST_TIME_INSTALLATION");
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
                        {
                            JsonObject s = response.body();
                            System.out.print(s);
                            Toast.makeText(getApplicationContext(), "Error Creating Comment: " + s, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Error Creating Comment: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {

                }
            }
        });

        scratchCardRecyclerView.setHasFixedSize(true);

        scratchCardRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

        scratchCardItemListArrayList.add(new ScratchCardItemList("15", false));
        scratchCardItemListArrayList.add(new ScratchCardItemList("25", true));
        scratchCardItemListArrayList.add(new ScratchCardItemList("10", true));
        scratchCardItemListArrayList.add(new ScratchCardItemList("20", false));
        scratchCardItemListArrayList.add(new ScratchCardItemList("30", false));
        scratchCardItemListArrayList.add(new ScratchCardItemList("15", false));
        scratchCardItemListArrayList.add(new ScratchCardItemList("20", false));
        scratchCardItemListArrayList.add(new ScratchCardItemList("15", false));
        scratchCardItemListArrayList.add(new ScratchCardItemList("20", false));
        scratchCardItemListArrayList.add(new ScratchCardItemList("5", false));

        scratchCardAdapter = new ScratchCardAdapter(scratchCardItemListArrayList, MainActivity.this);
        scratchCardRecyclerView.setAdapter(scratchCardAdapter);

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

}