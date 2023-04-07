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
import java.util.ArrayList;

public class ScratchCardAdapter extends RecyclerView.Adapter<ScratchCardAdapter.ViewHolder>
{

    ArrayList<ScratchCardItemList> scratchCardItemListArrayList;
    Context context;

    public ScratchCardAdapter(ArrayList<ScratchCardItemList> scratchCardItemListArrayList,Context context)
    {
        this.scratchCardItemListArrayList = scratchCardItemListArrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public ScratchCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.scratch_adapter_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ScratchCardAdapter.ViewHolder holder, int position)
    {
        ScratchCardItemList list=scratchCardItemListArrayList.get(position);
        if(list.getScratchedStatus())
        {
            holder.cardViewImage.setImageResource(R.drawable.scratched_card);
            holder.wonLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.cardViewImage.setImageResource(R.drawable.scratch_card);
            holder.wonLayout.setVisibility(View.GONE);
            holder.cardViewImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.activity_scratch_card);
                    dialog.show();
                }
            });
        }

    }

    @Override
    public int getItemCount()
    {
        return scratchCardItemListArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        LinearLayout wonLayout;
        TextView winAmount;
        ImageView  cardViewImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            winAmount=itemView.findViewById(R.id.winAmount);
            wonLayout=itemView.findViewById(R.id.wonLayout);
            cardViewImage=itemView.findViewById(R.id.cardViewImage);

        }
    }
}
