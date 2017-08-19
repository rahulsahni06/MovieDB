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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sahni.rahul.moviedb.Networking.ApiClient;
import com.sahni.rahul.moviedb.Networking.TvResponse;
import com.sahni.rahul.moviedb.R;
import com.sahni.rahul.moviedb.activities.TvDetailsActivity;
import com.sahni.rahul.moviedb.adapter.SearchAdapter;
import com.sahni.rahul.moviedb.helpers.IntentConstants;
import com.sahni.rahul.moviedb.interfaces.OnTvClickListener;
import com.sahni.rahul.moviedb.interfaces.UpdateSearchFragmentDetails;
import com.sahni.rahul.moviedb.models.TvShows;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sahni.rahul.moviedb.Networking.ApiClient.API_KEY;


public class SearchTvShowFragment extends Fragment implements UpdateSearchFragmentDetails, OnTvClickListener {


    private RecyclerView recyclerView;
    private SearchAdapter tvAdapter;

    private ArrayList<TvShows> tvArrayList;
    private ArrayList<TvShows> savedTvArrayList;


    private TextView noTvTextView;
    private String searchFor;
    private ProgressBar progressBar;
    private ViewPager viewPager;
    private SearchView searchView;

    private CardView cardView;

    static final String TAG = "SearchTvShowFragment";
    static final String TV_ARRAY_LIST = "tv_array_list";


    public SearchTvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search, container, false);
    }


    public static SearchTvShowFragment newInstance() {

        return new SearchTvShowFragment();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(TV_ARRAY_LIST, tvArrayList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            savedTvArrayList = savedInstanceState.getParcelableArrayList(TV_ARRAY_LIST);
            if (savedTvArrayList != null) {
                fetchTvDetails(savedTvArrayList);
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        searchView = (SearchView) getActivity().findViewById(R.id.search_view);

        recyclerView = (RecyclerView) view.findViewById(R.id.search_recycler_view);
        noTvTextView = (TextView) view.findViewById(R.id.search_status_text_view);
        noTvTextView.setText("Search for tv shows");
        progressBar = (ProgressBar) view.findViewById(R.id.search_progress_bar);
        cardView = (CardView) view.findViewById(R.id.search_card_view);
        cardView.setVisibility(View.INVISIBLE);

        tvAdapter = new SearchAdapter(getContext(), this);

        tvArrayList = new ArrayList<>();

        tvAdapter.setTvShowsArrayList(tvArrayList);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(tvAdapter);


    }


    private void fetchDetails(String tvName) {


        ApiClient.getRetrofitClient()
                .getTvSearchResult(API_KEY, "en-US", tvName)
                .enqueue(new Callback<TvResponse>() {
                    @Override
                    public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                        if (response.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            noTvTextView.setVisibility(View.GONE);
                            fetchTvDetails(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<TvResponse> call, Throwable t) {

                    }
                });


    }

    private void fetchTvDetails(TvResponse tvResponse) {
        if (tvResponse.getTvShowsArrayList().isEmpty()) {
            noTvTextView.setText("Tv show not found");
            cardView.setVisibility(View.INVISIBLE);
            noTvTextView.setVisibility(View.VISIBLE);
        } else {
            cardView.setVisibility(View.VISIBLE);
            tvArrayList.addAll(tvResponse.getTvShowsArrayList());
            tvAdapter.notifyDataSetChanged();
        }

    }


    private void fetchTvDetails(ArrayList<TvShows> arrayList)
    {
        if (arrayList.isEmpty() && !searchView.getQuery().toString().isEmpty())
        {
            noTvTextView.setText("Tv show not found");
            cardView.setVisibility(View.INVISIBLE);
            noTvTextView.setVisibility(View.VISIBLE);

        }
        else if (!arrayList.isEmpty())
        {
            noTvTextView.setVisibility(View.INVISIBLE);
            cardView.setVisibility(View.VISIBLE);
            tvArrayList.addAll(arrayList);
            tvAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void update(String query) {

        tvArrayList.clear();
        tvAdapter.notifyDataSetChanged();
        cardView.setVisibility(View.INVISIBLE);

        progressBar.setVisibility(View.VISIBLE);
        noTvTextView.setVisibility(View.INVISIBLE);
        fetchDetails(query);

    }

    @Override
    public void onTvClicked(View view, TvShows nullTvShow) {
        int position = recyclerView.getChildAdapterPosition(view);
        TvShows tvShows = tvArrayList.get(position);
        Intent intent = new Intent(getActivity(), TvDetailsActivity.class);
        intent.putExtra(IntentConstants.TV_KEY, tvShows);
        startActivity(intent);
    }
}
