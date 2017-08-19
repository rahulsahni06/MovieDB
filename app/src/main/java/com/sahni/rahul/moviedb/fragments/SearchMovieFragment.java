package com.sahni.rahul.moviedb.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sahni.rahul.moviedb.Networking.ApiClient;
import com.sahni.rahul.moviedb.Networking.MovieResponse;
import com.sahni.rahul.moviedb.R;
import com.sahni.rahul.moviedb.activities.MovieDetailsActivity;
import com.sahni.rahul.moviedb.adapter.SearchAdapter;
import com.sahni.rahul.moviedb.helpers.IntentConstants;
import com.sahni.rahul.moviedb.interfaces.OnMovieClickListener;
import com.sahni.rahul.moviedb.interfaces.UpdateSearchFragmentDetails;
import com.sahni.rahul.moviedb.models.Movie;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sahni.rahul.moviedb.Networking.ApiClient.API_KEY;


public class SearchMovieFragment extends Fragment implements OnMovieClickListener, UpdateSearchFragmentDetails {


    private RecyclerView recyclerView;
    private SearchAdapter moviesAdapter;

    private ArrayList<Movie> movieArrayList;
    private ArrayList<Movie> savedMovieArrayList;
//    private ArrayList<TvShows> tvArrayList;


    private TextView noMoviesTextView;
    private String searchFor;
    private ProgressBar progressBar;
    private ViewPager viewPager;
    private SearchView searchView;

    private CardView cardView;

    static final String TAG ="SearchMovieFragment";

//    private boolean isResultAvailable = false;

    private static final String ARRAY_LIST_MOVIE ="array_list_movie";


    public SearchMovieFragment() {
        // Required empty public constructor
    }

    public static SearchMovieFragment newInstance() {

        return new SearchMovieFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * fetch bundle here
         */

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null){
            savedMovieArrayList = savedInstanceState.getParcelableArrayList(ARRAY_LIST_MOVIE);
            if (savedMovieArrayList != null) {
                displayDetails(savedMovieArrayList);
            }
            else{
                Log.i(TAG,"onActivityCreated : saved ArrayList is null");
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, "onSaveInstanceState");
        outState.putParcelableArrayList(ARRAY_LIST_MOVIE,movieArrayList);
        Log.i(TAG, "onSaveInstanceState: ArrayList size = "+ movieArrayList.size());
        super.onSaveInstanceState(outState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i(TAG, "onViewCreated");

        searchView = (SearchView) getActivity().findViewById(R.id.search_view);

        recyclerView = (RecyclerView) view.findViewById(R.id.search_recycler_view);
        noMoviesTextView = (TextView) view.findViewById(R.id.search_status_text_view);
        noMoviesTextView.setText("Search for movies");
        progressBar = (ProgressBar) view.findViewById(R.id.search_progress_bar);
        cardView = (CardView) view.findViewById(R.id.search_card_view);
        cardView.setVisibility(View.INVISIBLE);

        moviesAdapter = new SearchAdapter(getContext(), this);

        movieArrayList = new ArrayList<>();

        moviesAdapter.setMovieArrayList(movieArrayList);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(moviesAdapter);
    }

    private void fetchDetails(String movieName) {


        ApiClient.getRetrofitClient()
                .getMovieSearchResult(API_KEY, "en-US", movieName)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if(response.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            noMoviesTextView.setVisibility(View.GONE);
                            displayDetails(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        t.printStackTrace();
                    }
                });


    }


    private void displayDetails(MovieResponse movieResponse) {

        if(movieResponse.getMovieArrayList().isEmpty()){
//            isResultAvailable = false;
            noMoviesTextView.setText("Movies not found");
            cardView.setVisibility(View.INVISIBLE);
            noMoviesTextView.setVisibility(View.VISIBLE);
        }
        else{
//            isResultAvailable = true;
            cardView.setVisibility(View.VISIBLE);
            movieArrayList.addAll(movieResponse.getMovieArrayList());
            moviesAdapter.notifyDataSetChanged();
        }


    }


    private void displayDetails(ArrayList<Movie> arrayList) {

        if(arrayList.isEmpty() && !searchView.getQuery().toString().isEmpty()){
//            isResultAvailable = false;
            noMoviesTextView.setText("Movies not found");
            cardView.setVisibility(View.INVISIBLE);
            noMoviesTextView.setVisibility(View.VISIBLE);
        }
        else if(!arrayList.isEmpty()){
//            isResultAvailable = true;
            noMoviesTextView.setVisibility(View.INVISIBLE);
            cardView.setVisibility(View.VISIBLE);
            movieArrayList.addAll(arrayList);
            moviesAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public void onMovieClicked(View view, Movie nullMovie) {

        int position = recyclerView.getChildAdapterPosition(view);
        Movie movie = movieArrayList.get(position);
        Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
        intent.putExtra(IntentConstants.MOVIE_KEY, movie);
        startActivity(intent);
    }


    @Override
    public void update(String query) {
//        String query = searchView.getQuery().toString();
        movieArrayList.clear();
        moviesAdapter.notifyDataSetChanged();
        cardView.setVisibility(View.INVISIBLE);
        noMoviesTextView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        fetchDetails(query);
    }

}
