package org.beginningandroid.futbolappdos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Match_RecyclerViewAdapter extends RecyclerView.Adapter<Match_RecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<MatchModel> matchModels;

    public Match_RecyclerViewAdapter(Context context, ArrayList<MatchModel> matchModels){
        this.context = context;
        this.matchModels = matchModels;

    }

    @NonNull
    @Override
    public Match_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // This is where you inflate the layout (Giving a look to our rows)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new Match_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Match_RecyclerViewAdapter.MyViewHolder holder, int position) {
        // Assigning values to the view we created in the recycler_view_row layout file
        // based on the position of the recycler view

        holder.match.setText(matchModels.get(position).getMatch());
        holder.cup.setText(matchModels.get(position).getCup());
        holder.hour.setText(matchModels.get(position).getHour());
        holder.channel.setText(matchModels.get(position).getChannel());

    }

    @Override
    public int getItemCount() {
        // the recycler view just want to know the number of items you want displayed
        return matchModels.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // grabbing all the views from our recycler_view_row layout
        // kinda like in the Oncreate method

        TextView match, hour, cup, channel;
        //ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            match = itemView.findViewById(R.id.match);
            hour = itemView.findViewById(R.id.hour);
            cup = itemView.findViewById(R.id.cup);
            channel = itemView.findViewById(R.id.channel);
        }
    }
}
