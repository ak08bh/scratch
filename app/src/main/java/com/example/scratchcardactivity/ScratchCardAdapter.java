package com.example.scratchcardactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anupkumarpanwar.scratchview.ScratchView;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Callback;

public class ScratchCardAdapter extends RecyclerView.Adapter<ScratchCardAdapter.ViewHolder> {

    ArrayList<ScratchCardModel> status;
    Context context;
    private final MainActivity mMainActivity;


    public ScratchCardAdapter(Context context, ArrayList<ScratchCardModel> status, MainActivity mMainActivity) {
        this.status = status;
        this.context = context;
        this.mMainActivity = mMainActivity;
    }

    @NonNull
    @Override
    public ScratchCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.scratch_adapter_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScratchCardAdapter.ViewHolder holder, int position) {
        holder.setReviewOBJ(status.get(position));
        holder.renderCell(holder.getAdapterPosition(),holder);

    }

    @Override
    public int getItemCount() {
        return status.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout wonLayout;
        TextView winAmount;
        ImageView cardViewImage;
        ScratchCardModel scratchCardModel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            winAmount = itemView.findViewById(R.id.winAmount);
            wonLayout = itemView.findViewById(R.id.wonLayout);
            cardViewImage = itemView.findViewById(R.id.cardViewImage);
        }

        public void setReviewOBJ(ScratchCardModel scratchCardModel) {

            this.scratchCardModel = scratchCardModel;
        }

        public void renderCell(int position,ScratchCardAdapter.ViewHolder holder)
        {

            if (status.get(position).getScratchCard())
            {
                cardViewImage.setImageResource(R.drawable.scratched_card);
                wonLayout.setVisibility(View.VISIBLE);
                winAmount.setText(String.valueOf(scratchCardModel.getScratchCardAmount()));
            }

            else
            {
                cardViewImage.setImageResource(R.drawable.scratch_card);
                wonLayout.setVisibility(View.GONE);
                String num = String.valueOf(scratchCardModel.getScratchCardAmount());
                winAmount.setText(String.valueOf(scratchCardModel.getScratchCardAmount()));

                int id=scratchCardModel.getId();
                int rewardId=scratchCardModel.getRewardId();
                Boolean scratchCard=scratchCardModel.getScratchCard();

                cardViewImage.setOnClickListener(v -> mMainActivity.showDailogBox(cardViewImage,wonLayout,holder,context, num,position,id,rewardId,scratchCard));
            }
        }
    }
}

