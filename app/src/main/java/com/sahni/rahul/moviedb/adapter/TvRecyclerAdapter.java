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
import com.sahni.rahul.moviedb.interfaces.OnTvClickListener;
import com.sahni.rahul.moviedb.models.TvShows;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sahni on 26-Jul-17.
 */

public class TvRecyclerAdapter extends RecyclerView.Adapter<TvRecyclerAdapter.TvViewHolder> {

    private Context context;
    private ArrayList<TvShows> arrayList;
    private OnTvClickListener listener;

    public TvRecyclerAdapter(Context context, ArrayList<TvShows> arrayList, OnTvClickListener listener) {
        this.context = context;
        this.arrayList = arrayList;
        this.listener = listener;
    }

    @Override
    public TvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.tv_recycler_item_layout, parent, false);
        return new TvViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(TvViewHolder holder, int position) {
        TvShows tvShows = arrayList.get(position);
        holder.tvNameTextView.setText(tvShows.getName());
        holder.tvRatingTextView.setText(""+ tvShows.getRating());
        Picasso.with(context)
                .load(ApiClient.POSTER_BASE_URL + tvShows.getPosterPath())
                .placeholder(R.drawable.picasso_placeholder)
                .into(holder.tvPosterImageView);
        if(tvShows.getFirstAirDate()!= null){
            holder.tvReleaseYearTextView.setText(ContentUtils.getReleaseYear(tvShows.getFirstAirDate()));
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class TvViewHolder extends RecyclerView.ViewHolder{

        ImageView tvPosterImageView;
        TextView tvNameTextView;
        TextView tvRatingTextView;
        TextView tvReleaseYearTextView;
        OnTvClickListener listener;

        public TvViewHolder(View itemView , final OnTvClickListener listener) {
            super(itemView);
            tvNameTextView = (TextView) itemView.findViewById(R.id.tv_title_text_view);
            tvPosterImageView = (ImageView) itemView.findViewById(R.id.tv_poster_image_view);
            tvRatingTextView = (TextView) itemView.findViewById(R.id.tv_rating_text_view);
            tvReleaseYearTextView = (TextView) itemView.findViewById(R.id.tv_release_year_text_view);
            this.listener = listener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onTvClicked(v, null);
                    }
                }
            });
        }
    }
}
