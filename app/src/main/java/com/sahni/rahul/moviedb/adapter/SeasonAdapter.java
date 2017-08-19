package com.sahni.rahul.moviedb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sahni.rahul.moviedb.Networking.ApiClient;
import com.sahni.rahul.moviedb.R;
import com.sahni.rahul.moviedb.interfaces.OnTvSeasonClickListener;
import com.sahni.rahul.moviedb.models.Season;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sahni on 29-Jul-17.
 */

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.SeasonViewHolder> {


    private Context context;
    private ArrayList<Season> arrayList;
    OnTvSeasonClickListener listener;


    public SeasonAdapter(Context context, ArrayList<Season> arrayList, OnTvSeasonClickListener listener) {
        this.context = context;
        this.arrayList = arrayList;
        this.listener = listener;
    }

    @Override
    public SeasonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.season_item_layout, parent, false);

        return new SeasonViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(SeasonViewHolder holder, int position) {

        Season season = arrayList.get(position);
        holder.seasonNameTextView.setText("Season " + season.getSeasonNumber());
        Picasso.with(context)
                .load(ApiClient.POSTER_BASE_URL + season.getPosterPath())
                .placeholder(R.drawable.picasso_placeholder)
                .into(holder.seasonPosterImageView);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class SeasonViewHolder extends RecyclerView.ViewHolder {

        ImageView seasonPosterImageView;
        TextView seasonNameTextView;
        OnTvSeasonClickListener listener;

        SeasonViewHolder(View itemView, final OnTvSeasonClickListener listener) {
            super(itemView);
            seasonPosterImageView = (ImageView) itemView.findViewById(R.id.season_item_image_view);
            seasonNameTextView = (TextView) itemView.findViewById(R.id.season_item_name_text_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onTvSeasonClicked(v, 0, 0);
                    }
                }
            });

        }
    }
}
