package com.sahni.rahul.moviedb.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sahni.rahul.moviedb.Networking.ApiClient;
import com.sahni.rahul.moviedb.Networking.MovieDetailsResponse;
import com.sahni.rahul.moviedb.R;
import com.sahni.rahul.moviedb.adapter.ReviewListAdapter;
import com.sahni.rahul.moviedb.interfaces.OnReviewClickListener;

import java.util.ArrayList;

import at.blogc.android.views.ExpandableTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sahni.rahul.moviedb.Networking.ApiClient.API_KEY;
import static com.sahni.rahul.moviedb.helpers.IntentConstants.MOVIE_ID;

public class MovieReviewsFragment extends Fragment implements OnReviewClickListener {

    RecyclerView recyclerView;
    ReviewListAdapter reviewListAdapter;
    ArrayList<MovieDetailsResponse.ReviewsClass.ReviewClassResult> arrayList;

    ProgressBar progressBar;
    TextView noReviewTextView;

    int movieId;


    public MovieReviewsFragment() {
        // Required empty public constructor
    }


    public static MovieReviewsFragment newInstance(int movieId) {

        MovieReviewsFragment fragment = new MovieReviewsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(MOVIE_ID, movieId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        movieId = bundle.getInt(MOVIE_ID);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_reviews, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.user_reviews_recycler_view);
        arrayList = new ArrayList<>();
        reviewListAdapter = new ReviewListAdapter(getContext(), arrayList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(reviewListAdapter);

        progressBar = (ProgressBar) view.findViewById(R.id.user_review_progress_bar);
        noReviewTextView = (TextView) view.findViewById(R.id.no_review_text_view);

        fetchDetails(movieId);

        return view;
    }

    private void fetchDetails(int movieId) {

        ApiClient.getRetrofitClient().getMovieDetails(movieId, API_KEY, "reviews")
                .enqueue(new Callback<MovieDetailsResponse>() {
                    @Override
                    public void onResponse(Call<MovieDetailsResponse> call, Response<MovieDetailsResponse> response) {

                        if (response.isSuccessful()) {
                            displayReviews(response.body());
                        }

                    }

                    @Override
                    public void onFailure(Call<MovieDetailsResponse> call, Throwable t) {

                    }
                });
    }

    private void displayReviews(MovieDetailsResponse body) {
        progressBar.setVisibility(View.GONE);
        ArrayList<MovieDetailsResponse.ReviewsClass.ReviewClassResult> arrayList = body.reviewsClass.getReviewClassResultArrayList();
        if (!arrayList.isEmpty()) {
            recyclerView.setVisibility(View.VISIBLE);
            this.arrayList.addAll(arrayList);
            reviewListAdapter.notifyDataSetChanged();
        } else {
            noReviewTextView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onReviewClicked(ExpandableTextView view) {
//        int position = nowPlayingRecyclerView.getChildAdapterPosition(view);
//        MovieDetailsResponse.ReviewsClass.ReviewClassResult review = arrayList.get(position);
        view.toggle();

    }
}
