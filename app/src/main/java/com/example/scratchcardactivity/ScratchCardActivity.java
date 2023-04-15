package com.example.scratchcardactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anupkumarpanwar.scratchview.ScratchView;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScratchCardActivity extends AppCompatActivity implements  ScratchCardInterface
{
ScratchCardAdapter scratchCardAdapter;
ScratchCardModel scratchCardModel;
ScratchView scratchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch_card);
        scratchView =findViewById(R.id.scratch_view);

    }


    @Override
    public void scratchMethod()
    {
        scratchView.setRevealListener(new ScratchView.IRevealListener() {
            @Override
            public void onRevealed(ScratchView scratchView)
            {

            }

            @Override
            public void onRevealPercentChangedListener(ScratchView scratchView, float percent)
            {

                if(percent>=0.5)
                {
                    scratchView.setVisibility(View.GONE);
                }

            }
        });

    }
}