package com.example.scratchcardactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.anupkumarpanwar.scratchview.ScratchView;

public class ScratchCardActivity extends AppCompatActivity
{
    private  boolean scratchRevealed=false;
    TextView winAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch_card);

        ScratchView scratchView = findViewById(R.id.scratch_view);
         winAmount = findViewById(R.id.winAmount);


    }
}