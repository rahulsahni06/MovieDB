package com.sahni.rahul.moviedb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sahni.rahul.moviedb.R;
import com.sahni.rahul.moviedb.helpers.ContentUtils;
import com.sahni.rahul.moviedb.interfaces.OnMovieClickListener;
import com.sahni.rahul.moviedb.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.sahni.rahul.moviedb.Networking.ApiClient.POSTER_BASE_URL;
import static com.sahni.rahul.moviedb.helpers.IntentConstants.UPCOMING_MOVIE;

/**
 * Created by sahni on 19-Jul-17.
 */

public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.MoviesViewHolder> {

    Context mContext;
    ArrayList<Movie> mArrayList;
    OnMovieClickListener listener;
    String movieType;

    public MoviesRecyclerAdapter(Context mContext, ArrayList<Movie> mArrayList, OnMovieClickListener listener, String movieType) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
        this.listener = listener;
        this.movieType = movieType;
    }


    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_recycler_item_layout, parent, false);
        return new MoviesViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        Movie movie = mArrayList.get(position);
        holder.titleTextView.setText(movie.getTitle());
        Picasso.with(mContext)
                .load(POSTER_BASE_URL + movie.getPosterPath())
                .placeholder(R.drawable.picasso_placeholder)
                .error(android.R.color.darker_gray)
                .into(holder.posterImageView);
        if(movieType.equals(UPCOMING_MOVIE)){
            holder.ratingTextView.setVisibility(View.GONE);
            holder.releaseYearTextView.setVisibility(View.GONE);
            String date[] = ContentUtils.getDate(movie.getDate()).split(";");
            holder.upcomingDateTextView.setText(date[0]);

        }
        else{
            holder.upcomingDateTextView.setVisibility(View.GONE);
            holder.ratingTextView.setText(""+movie.getVote());
            holder.releaseYearTextView.setText(ContentUtils.getReleaseYear(movie.getDate()));
        }

    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

     static class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView posterImageView;
        TextView titleTextView;
        TextView ratingTextView;
         TextView releaseYearTextView;
         TextView upcomingDateTextView;
        OnMovieClickListener listener;

        public MoviesViewHolder(View itemView, OnMovieClickListener listener) {
            super(itemView);
            this.listener = listener;
            posterImageView = (ImageView) itemView.findViewById(R.id.tv_poster_image_view);
            titleTextView = (TextView) itemView.findViewById(R.id.tv_title_text_view);
            ratingTextView = (TextView) itemView.findViewById(R.id.tv_rating_text_view);
            releaseYearTextView = (TextView) itemView.findViewById(R.id.release_year_text_view);
            upcomingDateTextView = (TextView) itemView.findViewById(R.id.upcoming_release_text_View);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if(listener != null){
                listener.onMovieClicked(view, null);
            }
        }
    }
}
