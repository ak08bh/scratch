package com.example.scratchcardactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

     RecyclerView scratchCardRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scratchCardRecyclerView= findViewById(R.id.scratchCardRecyclerView);

        scratchCardRecyclerView.setHasFixedSize(true);

        scratchCardRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));

    }
}