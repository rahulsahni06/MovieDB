package com.sahni.rahul.moviedb.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.sahni.rahul.moviedb.Networking.ApiClient;
import com.sahni.rahul.moviedb.Networking.MovieResponse;
import com.sahni.rahul.moviedb.R;
import com.sahni.rahul.moviedb.activities.MovieDetailsActivity;
import com.sahni.rahul.moviedb.adapter.MoviesRecyclerAdapter;
import com.sahni.rahul.moviedb.helpers.IntentConstants;
import com.sahni.rahul.moviedb.interfaces.OnMovieClickListener;
import com.sahni.rahul.moviedb.models.Movie;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sahni.rahul.moviedb.Networking.ApiClient.API_KEY;
import static com.sahni.rahul.moviedb.helpers.ContentUtils.country_code;
import static com.sahni.rahul.moviedb.helpers.IntentConstants.RELEASED_MOVIE;
import static com.sahni.rahul.moviedb.helpers.IntentConstants.UPCOMING_MOVIE;

//import com.sahni.rahul.moviedb.activities.MovieDetailsActivity;


public class MoviesFragment extends Fragment {


    private MoviesRecyclerAdapter nowPlayingRecyclerAdapter;
    private RecyclerView nowPlayingRecyclerView;
    private ArrayList<Movie> nowPlayingArrayList;
    private ProgressBar progressBar;
//    CardView cardView;

    private MoviesRecyclerAdapter upcomingMovieAdapter;
    private RecyclerView upcomingRecyclerView;
    private ArrayList<Movie> upcomingArrayList;

    private final String TAG = "Movies_Fragment";


    private MoviesRecyclerAdapter popularMovieAdapter;
    private RecyclerView popularRecyclerView;
    private ArrayList<Movie> popularArrayList;


    private MoviesRecyclerAdapter topRatedMovieAdapter;
    private RecyclerView topRatedRecyclerView;
    private ArrayList<Movie> topRatedArrayList;


    private RelativeLayout movieRelativeLayout;

//    private String country_code = "US";

    public MoviesFragment() {
        // Required empty public constructor
    }


    public static MoviesFragment newInstance(){
        return new MoviesFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);


        progressBar = (ProgressBar) view.findViewById(R.id.movie_progress_bar);
        movieRelativeLayout = (RelativeLayout) view.findViewById(R.id.movie_relative_layout);
        movieRelativeLayout.setVisibility(View.INVISIBLE);

        nowPlayingRecyclerView = (RecyclerView) view.findViewById(R.id.movie_now_playing_recycler_view);
        nowPlayingArrayList = new ArrayList<>();

        nowPlayingRecyclerView.setLayoutManager( new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        nowPlayingRecyclerAdapter = new MoviesRecyclerAdapter(getContext(), nowPlayingArrayList, new OnMovieClickListener() {
            @Override
            public void onMovieClicked(View view, Movie nullMovie) {
                int position = nowPlayingRecyclerView.getChildLayoutPosition(view);
                Movie movie = nowPlayingArrayList.get(position);

                Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
//        intent.putExtra(MOVIE_ID, movie.getId());
//        intent.putExtra(MOVIE_TITLE, movie.getTitle());
//        intent.putExtra(MOVIE_RELEASE_DATE, movie.getDate());
                intent.putExtra(IntentConstants.MOVIE_KEY, movie);

                startActivity(intent);
            }
        }, RELEASED_MOVIE);
        new GravitySnapHelper(Gravity.START).attachToRecyclerView(nowPlayingRecyclerView);
        nowPlayingRecyclerView.setAdapter(nowPlayingRecyclerAdapter);


        upcomingArrayList = new ArrayList<>();
        upcomingRecyclerView = (RecyclerView) view.findViewById(R.id.upcoming_movie_recycler_view);
        upcomingMovieAdapter = new MoviesRecyclerAdapter(getContext(), upcomingArrayList, new OnMovieClickListener() {
            @Override
            public void onMovieClicked(View view, Movie nullMovie) {
                int position = upcomingRecyclerView.getChildLayoutPosition(view);
                Movie movie = upcomingArrayList.get(position);

                Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
//        intent.putExtra(MOVIE_ID, movie.getId());
//        intent.putExtra(MOVIE_TITLE, movie.getTitle());
//        intent.putExtra(MOVIE_RELEASE_DATE, movie.getDate());
                intent.putExtra(IntentConstants.MOVIE_KEY, movie);

                startActivity(intent);
            }
        }, UPCOMING_MOVIE);

        upcomingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        new GravitySnapHelper(Gravity.START).attachToRecyclerView(upcomingRecyclerView);
        upcomingRecyclerView.setAdapter(upcomingMovieAdapter);







        popularArrayList = new ArrayList<>();
        popularRecyclerView = (RecyclerView) view.findViewById(R.id.popular_movie_recycler_view);
        popularMovieAdapter = new MoviesRecyclerAdapter(getContext(), popularArrayList, new OnMovieClickListener() {
            @Override
            public void onMovieClicked(View view, Movie nullMovie) {

                int position = popularRecyclerView.getChildLayoutPosition(view);
                Movie movie = popularArrayList.get(position);

                Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
                intent.putExtra(IntentConstants.MOVIE_KEY, movie);

                startActivity(intent);

            }
        }, RELEASED_MOVIE);
        popularRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        new GravitySnapHelper(Gravity.START).attachToRecyclerView(popularRecyclerView);
        popularRecyclerView.setAdapter(popularMovieAdapter);




        topRatedArrayList = new ArrayList<>();
        topRatedRecyclerView = (RecyclerView) view.findViewById(R.id.top_rated_movie_recycler_view);
        topRatedMovieAdapter = new MoviesRecyclerAdapter(getContext(), topRatedArrayList, new OnMovieClickListener() {
            @Override
            public void onMovieClicked(View view, Movie nullMovie) {
                int position = topRatedRecyclerView.getChildLayoutPosition(view);
                Movie movie = topRatedArrayList.get(position);

                Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
                intent.putExtra(IntentConstants.MOVIE_KEY, movie);

                startActivity(intent);

            }
        }, RELEASED_MOVIE);
        topRatedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        new GravitySnapHelper(Gravity.START).attachToRecyclerView(topRatedRecyclerView);
        topRatedRecyclerView.setAdapter(topRatedMovieAdapter);



        fetchMovies();




        return view;
    }

    private void fetchMovies() {

//        int cacheSize = 10 * 1024 * 1024; // 10 MB
//        Cache cache = new Cache(getContext().getCacheDir(), cacheSize);
//
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .cache(cache)
//                .build();

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.themoviedb.org/")
////                .client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

//        Retrofit retrofit = ApiClient.getRetrofitClient();

//        final ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<MovieResponse> call = ApiClient.getRetrofitClient().getNowShowingMovies(API_KEY, "en-US", 1, country_code);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    movieRelativeLayout.setVisibility(View.VISIBLE);
                    ArrayList<Movie> arrayList = movieResponse.getMovieArrayList();

                    if(arrayList != null){
                        nowPlayingArrayList.clear();
                        nowPlayingArrayList.addAll(arrayList);
                        nowPlayingRecyclerAdapter.notifyDataSetChanged();
                    }

                    else {
                        Toast.makeText(getContext(), "Error in onResponse", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), "not success in onResponse", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

                Toast.makeText(getActivity(), "Error in onFailure", Toast.LENGTH_SHORT).show();

            }
        });


        Call<MovieResponse> upcomingCall = ApiClient.getRetrofitClient().getUpcomingMovies(API_KEY, "en-US", 1, country_code);
        upcomingCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if(response.isSuccessful()){
                    displayUpcomingMovies(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });


        ApiClient.getRetrofitClient()
                .getPopularMovies(API_KEY, "en-US", 1, country_code)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if(response.isSuccessful()){
                            displayPopularMovies(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });


        ApiClient.getRetrofitClient()
                .getTopRatedMovies(API_KEY, "en-US", 1, country_code)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if(response.isSuccessful()){
                            displayTopRated(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        t.printStackTrace();

                    }
                });
    }

    private void displayTopRated(MovieResponse body) {
        ArrayList<Movie> list = body.getMovieArrayList();
        if(list != null){
            topRatedArrayList.clear();
            topRatedArrayList.addAll(list);
            topRatedMovieAdapter.notifyDataSetChanged();
        }
        else{
            Log.i(TAG, "arrayList is null");
        }

    }

    private void displayPopularMovies(MovieResponse body) {

        ArrayList<Movie> list = body.getMovieArrayList();
        if(list != null){
            popularArrayList.clear();
            popularArrayList.addAll(list);
            popularMovieAdapter.notifyDataSetChanged();
        }
        else{
            Log.i(TAG, "arrayList is null");
        }
    }

    private void displayUpcomingMovies(MovieResponse body) {

        ArrayList<Movie> upcomingMovieList = body.getMovieArrayList();
        if(upcomingMovieList != null){
            upcomingArrayList.clear();
            upcomingArrayList.addAll(upcomingMovieList);
            upcomingMovieAdapter.notifyDataSetChanged();
        }
        else{
            Log.i(TAG, "arrayList is null");
        }
    }


//    @Override
//    public void onMovieClicked(View view, Movie nullMovie) {
////        Toast.makeText(getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
//
//        int position = nowPlayingRecyclerView.getChildLayoutPosition(view);
//        Movie movie = nowPlayingArrayList.get(position);
//
//        Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
////        intent.putExtra(MOVIE_ID, movie.getId());
////        intent.putExtra(MOVIE_TITLE, movie.getTitle());
////        intent.putExtra(MOVIE_RELEASE_DATE, movie.getDate());
//        intent.putExtra(IntentConstants.MOVIE_KEY, movie);
//
//        startActivity(intent);
//    }
}
