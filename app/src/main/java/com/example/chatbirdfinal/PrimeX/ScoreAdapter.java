package com.example.chatbirdfinal.PrimeX;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatbirdfinal.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ScoreAdapter extends RecyclerView.Adapter <ScoreAdapter.ScoreViewAdapter> {

    List<ScoreData> list;
    Context context;
    int i=1;


    public ScoreAdapter(List<ScoreData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ScoreViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.score_list_item,parent,false);
        return new ScoreViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewAdapter holder, int position) {

        ScoreData currentitem = list.get(position);
        holder.name.setText(currentitem.getName());
        holder.score.setText(String.valueOf(currentitem.getScore()));
        holder.rank.setText(String.valueOf(i));
        Glide.with(context).load(currentitem.getImage()).into(holder.circleImageView);
        i++;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ScoreViewAdapter extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;
        TextView name,score,rank;

        public ScoreViewAdapter(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.score_user_img);
            name = itemView.findViewById(R.id.score_user_name);
            score = itemView.findViewById(R.id.score_user_result);
            rank = itemView.findViewById(R.id.score_user_rank);
        }
    }
}
