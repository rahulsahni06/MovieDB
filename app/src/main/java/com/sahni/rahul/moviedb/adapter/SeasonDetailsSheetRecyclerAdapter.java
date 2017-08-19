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
import com.sahni.rahul.moviedb.helpers.ContentUtils;
import com.sahni.rahul.moviedb.models.Episodes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sahni on 29-Jul-17.
 */

public class SeasonDetailsSheetRecyclerAdapter extends RecyclerView.Adapter<SeasonDetailsSheetRecyclerAdapter.SeasonDetailsViewHolder> {


    Context context;
    ArrayList<Episodes.SeasonEpisode> seasonEpisodesArrayList;


    public SeasonDetailsSheetRecyclerAdapter(Context context, ArrayList<Episodes.SeasonEpisode> seasonEpisodesArrayList) {
        this.context = context;
        this.seasonEpisodesArrayList = seasonEpisodesArrayList;
    }

    @Override
    public SeasonDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.season_episode_item_layout, parent, false);
        return new SeasonDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SeasonDetailsViewHolder holder, int position) {

        Episodes.SeasonEpisode episode = seasonEpisodesArrayList.get(position);

        Picasso.with(context)
                .load(ApiClient.IMAGE_BASE_URL + episode.getImagePath())
                .into(holder.episodeImageView);

        holder.episodeNameTextView.setText(episode.getName());
        holder.episodeNumberTextView.setText(""+episode.getEpisodeNumber());
        String date[] = ContentUtils.getDate(episode.getAirDate()).split(";");
        holder.episodeAirDateTextView.setText(date[0]);
        holder.episodeOverviewTextView.setText(episode.getOverview());

    }

    @Override
    public int getItemCount() {
        return seasonEpisodesArrayList.size();
    }

    static class SeasonDetailsViewHolder extends RecyclerView.ViewHolder{

        ImageView episodeImageView;
        TextView episodeNumberTextView;
        TextView episodeNameTextView;
        TextView episodeOverviewTextView;
        TextView episodeAirDateTextView;

        SeasonDetailsViewHolder(View itemView) {
            super(itemView);
            episodeImageView = (ImageView) itemView.findViewById(R.id.episode_image_view);
            episodeNameTextView = (TextView) itemView.findViewById(R.id.episode_name_text_view);
            episodeNumberTextView = (TextView) itemView.findViewById(R.id.episode_number_text_view);
            episodeOverviewTextView = (TextView) itemView.findViewById(R.id.episode_overview_text_view);
            episodeAirDateTextView = (TextView) itemView.findViewById(R.id.episode_air_date_text_view);
        }
    }
}
