package com.sahni.rahul.moviedb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sahni.rahul.moviedb.R;
import com.sahni.rahul.moviedb.interfaces.OnMovieVideoClickListener;
import com.sahni.rahul.moviedb.models.Videos;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sahni on 23-Jul-17.
 */

public class MovieVideosRecyclerAdapter extends RecyclerView.Adapter<MovieVideosRecyclerAdapter.MovieVideosViewHolder>{


    Context context;
    ArrayList<Videos.VideoResult> arrayList;
    String url = "https://img.youtube.com/vi/";
    OnMovieVideoClickListener listener;


    public MovieVideosRecyclerAdapter(Context context, ArrayList<Videos.VideoResult> arrayList, OnMovieVideoClickListener listener) {
        this.context = context;
        this.arrayList = arrayList;
        this.listener = listener;
    }

    @Override
    public MovieVideosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_videos_recycler_item_layout, parent, false);
        return new MovieVideosViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(MovieVideosViewHolder holder, int position) {

        Videos.VideoResult videoDetails = arrayList.get(position);
        holder.videoTitleTextView.setText(videoDetails.getName());
        Picasso.with(context)
                .load(url + videoDetails.getVideoKey() + "/hqdefault.jpg")
                .placeholder(R.drawable.picasso_placeholder)
                .into(holder.videoImageView);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class MovieVideosViewHolder extends RecyclerView.ViewHolder{

        ImageView videoImageView;
        TextView videoTitleTextView;
        OnMovieVideoClickListener listener;

        public MovieVideosViewHolder(final View itemView, final OnMovieVideoClickListener listener) {
            super(itemView);
            videoImageView = (ImageView) itemView.findViewById(R.id.movie_videos_image_view);
            videoTitleTextView = (TextView) itemView.findViewById(R.id.movie_videos_title_view);
            this.listener = listener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onMovieVideoClicked(itemView, null);
                    }
                }
            });
        }
    }
}
