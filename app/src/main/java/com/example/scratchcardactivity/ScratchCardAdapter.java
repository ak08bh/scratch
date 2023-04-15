package com.example.scratchcardactivity;

import android.app.Dialog;
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

import java.util.ArrayList;


public class ScratchCardAdapter extends RecyclerView.Adapter<ScratchCardAdapter.ViewHolder> {

    ArrayList<ScratchCardModel> status;
    Context context;


    public ScratchCardAdapter(Context context, ArrayList<ScratchCardModel> status) {
        this.status = status;
        this.context = context;
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
        holder.renderCell(position);

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

        public void renderCell(int position) {

            if (scratchCardModel.getScratchCard()) {
                cardViewImage.setImageResource(R.drawable.scratched_card);
                wonLayout.setVisibility(View.VISIBLE);
                winAmount.setText(String.valueOf(scratchCardModel.getScratchCardAmount()));


            } else {
                cardViewImage.setImageResource(R.drawable.scratch_card);
                wonLayout.setVisibility(View.GONE);
                winAmount.setText(String.valueOf(scratchCardModel.getScratchCardAmount()));

                cardViewImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView winCoins;
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.activity_scratch_card);
                        winCoins = dialog.findViewById(R.id.winCoins);
                        dialog.show();
                        winCoins.setText(String.valueOf(scratchCardModel.getScratchCardAmount()));
                    }
                });

            }
        }
    }
}



