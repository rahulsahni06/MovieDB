package com.sahni.rahul.moviedb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sahni.rahul.moviedb.Networking.ApiClient;
import com.sahni.rahul.moviedb.Networking.Person;
import com.sahni.rahul.moviedb.R;
import com.sahni.rahul.moviedb.helpers.ContentUtils;
import com.sahni.rahul.moviedb.interfaces.OnMovieClickListener;
import com.sahni.rahul.moviedb.interfaces.OnPersonClickListener;
import com.sahni.rahul.moviedb.interfaces.OnTvClickListener;
import com.sahni.rahul.moviedb.models.Movie;
import com.sahni.rahul.moviedb.models.TvShows;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sahni on 04-Aug-17.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {


    private Context context;
    private ArrayList<Movie> movieArrayList;
    private ArrayList<TvShows> tvShowsArrayList;
    private ArrayList<Person> personArrayList;


    private OnMovieClickListener movieClickListener;
    private OnTvClickListener tvClickListener;
    private OnPersonClickListener personClickListener;


//    public SearchAdapter(Context context) {
//        this.context = context;
//    }

    public SearchAdapter(Context context, OnMovieClickListener onMovieClickListener){

        this.context = context;
        this.movieClickListener = onMovieClickListener;
    }


    public SearchAdapter(Context context, OnTvClickListener tvClickListener){

        this.context = context;
        this.tvClickListener = tvClickListener;
    }


    public SearchAdapter(Context context, OnPersonClickListener personClickListener){

        this.context = context;
        this.personClickListener = personClickListener;
    }



    public void setMovieArrayList(ArrayList<Movie> movieArrayList) {
        this.movieArrayList = movieArrayList;
    }

    public void setTvShowsArrayList(ArrayList<TvShows> tvShowsArrayList) {
        this.tvShowsArrayList = tvShowsArrayList;
    }

    public void setPersonArrayList(ArrayList<Person> personArrayList) {
        this.personArrayList = personArrayList;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        SearchViewHolder holder;
        holder = new SearchViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_search_item_layout, parent, false));

        if(tvClickListener != null){
            holder.tvClickListener = tvClickListener;
        }
        else if(personClickListener != null){
            holder.personClickListener = personClickListener;
        }
        else{
            holder.movieClickListener = movieClickListener;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
//        Movie movie = movieArrayList.get(position);
//        holder.titleTextView.setText(movie.getTitle());
//        Picasso.with(context)
//                .load(ApiClient.POSTER_BASE_URL + movie.getPosterPath())
//                .placeholder(R.drawable.picasso_placeholder)
//                .into(holder.imageView);
//        holder.dateTextView.setText();
//

        String title;
        String date;
        float ratings;
        String posterPath;


        if(movieArrayList != null){
            Movie movie = movieArrayList.get(position);
            title = movie.getTitle();
            date = ContentUtils.getReleaseYear(movie.getDate());
            ratings = movie.getVote();
            posterPath = movie.getPosterPath();
        }
        else if(tvShowsArrayList != null){
            TvShows tvShow = tvShowsArrayList.get(position);
            title = tvShow.getName();
            date = ContentUtils.getReleaseYear(tvShow.getFirstAirDate());
            ratings = tvShow.getRating();
            posterPath = tvShow.getPosterPath();
        }
        else {
            Person person = personArrayList.get(position);
            title = person.getName();
            posterPath = person.getProfilePath();
            holder.dateTextView.setVisibility(View.GONE);
            holder.ratingTextView.setVisibility(View.GONE);
            holder.starImageView.setVisibility(View.GONE);
            date = "";
            ratings = 0;
        }

        setDetails(holder, title, date, ratings, posterPath);
    }

    @Override
    public int getItemCount() {
        if(movieArrayList != null){
            return movieArrayList.size();
        }
        else if(tvShowsArrayList != null){
            return tvShowsArrayList.size();
        }
        else{
            return personArrayList.size();
        }
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView titleTextView;
        private TextView dateTextView;
        private TextView ratingTextView;
        private ImageView starImageView;

        private OnMovieClickListener movieClickListener;
        private OnTvClickListener tvClickListener;
        private OnPersonClickListener personClickListener;


        SearchViewHolder(final View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.search_item_image_view);
            titleTextView = (TextView) itemView.findViewById(R.id.search_item_title_text_view);
            dateTextView = (TextView) itemView.findViewById(R.id.search_item_date_text_view);
            ratingTextView = (TextView) itemView.findViewById(R.id.search_item_rating_text_view);
            starImageView = (ImageView) itemView.findViewById(R.id.search_item_star_text_view) ;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(movieClickListener != null){
                        movieClickListener.onMovieClicked(itemView, null);
                    }
                    else if(tvClickListener != null){
                        tvClickListener.onTvClicked(itemView, null);
                    }
                    else if(personClickListener != null){
                        personClickListener.onPersonClicked(itemView);
                    }
                }
            });
        }

    }


    private void setDetails(SearchViewHolder holder, String title, String date, float rating, String posterPath){

        Picasso.with(context)
                .load(ApiClient.POSTER_BASE_URL + posterPath)
                .placeholder(R.drawable.picasso_placeholder)
                .into(holder.imageView);

        holder.titleTextView.setText(title);
        holder.dateTextView.setText(date);
        holder.ratingTextView.setText(""+rating);
    }
}
