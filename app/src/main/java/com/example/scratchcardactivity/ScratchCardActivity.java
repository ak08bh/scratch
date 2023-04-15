package com.example.scratchcardactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.anupkumarpanwar.scratchview.ScratchView;

public class ScratchCardActivity extends AppCompatActivity implements  ScratchCardInterface
{
ScratchCardAdapter scratchCardAdapter;
ScratchCardModel scratchCardModel;
ScratchView scratchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch_card);
        scratchView = findViewById(R.id.scratch_view);

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

    @Override
    public void scratchMethod() {

    }

    @Override
    public void scratchMethod(int position) {

    }
}