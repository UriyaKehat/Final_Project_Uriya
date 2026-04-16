package com.example.final_project_uriya;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    List<GameScore> scoresData;
    public Adapter(Context context, List<GameScore> scoresData){
        this.context = context;
        this.scoresData = scoresData;
    }
    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        GameScore gameScoretemp = this.scoresData.get(position);
        holder.textView.setText(String.valueOf(gameScoretemp.getId() + " | " + gameScoretemp.getUserName() +" | " + gameScoretemp.getTime() + " | " + gameScoretemp.getAppleAmount()));
    }

    @Override
    public int getItemCount() {
        return this.scoresData.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.itemTv);
        }
    }
}
