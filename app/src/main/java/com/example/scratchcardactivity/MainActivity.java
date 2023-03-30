package com.example.scratchcardactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

     RecyclerView scratchCardRecyclerView;
     RecyclerView.Adapter adapter;
     RelativeLayout click;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        scratchCardRecyclerView= findViewById(R.id.scratchCardRecyclerView);
        click= findViewById(R.id.click);

        scratchCardRecyclerView.setHasFixedSize(true);

        scratchCardRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));

        ArrayList<ScratchCardItemList> scratchCardItemListArrayList= new ArrayList<>();
        scratchCardItemListArrayList.add(new ScratchCardItemList("15rs",false));
        scratchCardItemListArrayList.add(new ScratchCardItemList("25rs",true));
        scratchCardItemListArrayList.add(new ScratchCardItemList("10rs",true));
        scratchCardItemListArrayList.add(new ScratchCardItemList("20rs",false));
        scratchCardItemListArrayList.add(new ScratchCardItemList("30rs",false));
        scratchCardItemListArrayList.add(new ScratchCardItemList("15rs",false));
        scratchCardItemListArrayList.add(new ScratchCardItemList("20rs",false));
        scratchCardItemListArrayList.add(new ScratchCardItemList("15rs",false));
        scratchCardItemListArrayList.add(new ScratchCardItemList("20rs",false));
        scratchCardItemListArrayList.add(new ScratchCardItemList("5rs",false));

        adapter=new ScratchCardAdapter(scratchCardItemListArrayList);

        scratchCardRecyclerView.setAdapter(adapter);


        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UpcomingRewardActivity.class));
            }
        });

    }
}