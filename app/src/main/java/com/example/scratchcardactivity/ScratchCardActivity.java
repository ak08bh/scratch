package com.example.scratchcardactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.anupkumarpanwar.scratchview.ScratchView;

public class ScratchCardActivity extends AppCompatActivity
{
ScratchView scratchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch_card);
        scratchView = findViewById(R.id.scratch_view);


    }



}