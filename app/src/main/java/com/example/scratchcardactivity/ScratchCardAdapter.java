package com.example.scratchcardactivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ScratchCardAdapter extends RecyclerView.Adapter<ScratchCardAdapter.ViewHolder>
{

    @NonNull
    @Override
    public ScratchCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.scratch_adapter_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ScratchCardAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private  final RelativeLayout wonLayout;
        private  final RelativeLayout scratchedLayout;
        private  final TextView winAmount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wonLayout=itemView.findViewById(R.id.wonLayout);
            scratchedLayout=itemView.findViewById(R.id.scratchedLayout);
            winAmount=itemView.findViewById(R.id.winAmount);
        }
    }
}
