package com.sahni.rahul.moviedb.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.sahni.rahul.moviedb.Networking.ApiClient;
import com.sahni.rahul.moviedb.Networking.TvResponse;
import com.sahni.rahul.moviedb.R;
import com.sahni.rahul.moviedb.activities.TvDetailsActivity;
import com.sahni.rahul.moviedb.adapter.TvRecyclerAdapter;
import com.sahni.rahul.moviedb.helpers.IntentConstants;
import com.sahni.rahul.moviedb.interfaces.OnTvClickListener;
import com.sahni.rahul.moviedb.models.TvShows;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowsFragment extends Fragment {


    private RecyclerView airingTodayRecyclerView;
    private ArrayList<TvShows> airingTodayArrayList;
    private TvRecyclerAdapter airingTodayAdapter;

    private String TAG = "tv_show_fragment";

    RelativeLayout tvRelativeLayout;
    ProgressBar progressBar;


    private RecyclerView onTheAirRecyclerView;
    private ArrayList<TvShows> onTheAirArrayList;
    private TvRecyclerAdapter onTheAirAdapter;


    private RecyclerView popularRecyclerView;
    private ArrayList<TvShows> popularArrayList;
    private TvRecyclerAdapter popularAdapter;



    private RecyclerView topRatedRecyclerView;
    private ArrayList<TvShows> topRatedArrayList;
    private TvRecyclerAdapter topRatedAdapter;




    public TvShowsFragment() {
        // Required empty public constructor
    }

    public static TvShowsFragment newInstance() {

        return new TvShowsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_tv_shows, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        progressBar = (ProgressBar) view.findViewById(R.id.tv_progress_bar);
        tvRelativeLayout = (RelativeLayout) view.findViewById(R.id.tv_relative_layout);
        tvRelativeLayout.setVisibility(View.INVISIBLE);

//        SnapHelper helper = new LinearSnapHelper();


        airingTodayRecyclerView = (RecyclerView) view.findViewById(R.id.airing_today_recycler_view);
        airingTodayArrayList = new ArrayList<>();
        airingTodayAdapter = new TvRecyclerAdapter(getContext(), airingTodayArrayList, new OnTvClickListener() {
            @Override
            public void onTvClicked(View view, TvShows nullTvShow) {
                Intent intent = new Intent(getActivity(), TvDetailsActivity.class);
                TvShows tvShows = airingTodayArrayList.get(airingTodayRecyclerView.getChildLayoutPosition(view));
                intent.putExtra(IntentConstants.TV_KEY, tvShows);
                startActivity(intent);

            }
        });
        airingTodayRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        new GravitySnapHelper(Gravity.START).attachToRecyclerView(airingTodayRecyclerView);
        airingTodayRecyclerView.setAdapter(airingTodayAdapter);



        onTheAirRecyclerView = (RecyclerView) view.findViewById(R.id.on_the_air_recycler_view);
        onTheAirArrayList = new ArrayList<>();
        onTheAirAdapter = new TvRecyclerAdapter(getContext(), onTheAirArrayList, new OnTvClickListener() {
            @Override
            public void onTvClicked(View view, TvShows nullTvShow) {
                Intent intent = new Intent(getActivity(), TvDetailsActivity.class);
                TvShows tvShows = onTheAirArrayList.get(onTheAirRecyclerView.getChildLayoutPosition(view));
                intent.putExtra(IntentConstants.TV_KEY, tvShows);
                startActivity(intent);

            }
        });
        onTheAirRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        new GravitySnapHelper(Gravity.START).attachToRecyclerView(onTheAirRecyclerView);
        onTheAirRecyclerView.setAdapter(onTheAirAdapter);



        popularRecyclerView = (RecyclerView) view.findViewById(R.id.popular_recycler_view);
        popularArrayList = new ArrayList<>();
        popularAdapter = new TvRecyclerAdapter(getContext(), popularArrayList, new OnTvClickListener() {
            @Override
            public void onTvClicked(View view, TvShows nullTvShow) {
                Intent intent = new Intent(getActivity(), TvDetailsActivity.class);
                TvShows tvShows = popularArrayList.get(popularRecyclerView.getChildLayoutPosition(view));
                intent.putExtra(IntentConstants.TV_KEY, tvShows);
                startActivity(intent);
            }
        });
        popularRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        new GravitySnapHelper(Gravity.START).attachToRecyclerView(popularRecyclerView);
        popularRecyclerView.setAdapter(popularAdapter);



        topRatedRecyclerView = (RecyclerView) view.findViewById(R.id.top_rated_tv_show_recycler_view);
        topRatedArrayList = new ArrayList<>();
        topRatedAdapter = new TvRecyclerAdapter(getContext(), topRatedArrayList, new OnTvClickListener() {
            @Override
            public void onTvClicked(View view, TvShows nullTvShow) {
                Intent intent = new Intent(getActivity(), TvDetailsActivity.class);
                TvShows tvShows = topRatedArrayList.get(topRatedRecyclerView.getChildLayoutPosition(view));
                intent.putExtra(IntentConstants.TV_KEY, tvShows);
                startActivity(intent);
            }
        });
        topRatedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        new GravitySnapHelper(Gravity.START).attachToRecyclerView(topRatedRecyclerView);
        topRatedRecyclerView.setAdapter(topRatedAdapter);

        fetchTvShowDetails();

    }

    private void fetchTvShowDetails() {

//        Retrofit retrofit = ApiClient.getRetrofitClient();
        ApiClient.getRetrofitClient().getOnAiringToday(ApiClient.API_KEY, "en-US", 1)
                .enqueue(new Callback<TvResponse>() {
                    @Override
                    public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                        Log.i(TAG,"Response");
                        if(response.isSuccessful()){
                            displayDetails(response.body());
                        }
                        else{
                            try {
                                Log.i(TAG, response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.i(TAG,"IOException ");

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<TvResponse> call, Throwable t) {
                        t.printStackTrace();
                        Log.i(TAG,"Fail");


                    }
                });


        ApiClient.getRetrofitClient().getOnTheAir(ApiClient.API_KEY, "en-US", 1)
                .enqueue(new Callback<TvResponse>() {
                    @Override
                    public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                        if(response.isSuccessful()){
                            displayOnTheAir(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<TvResponse> call, Throwable t) {
                        t.printStackTrace();
                        Log.i(TAG,"Fail");
                    }
                });


        ApiClient.getRetrofitClient()
                .getPopularTvShow(ApiClient.API_KEY, "en-US", 1)
                .enqueue(new Callback<TvResponse>() {
                    @Override
                    public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                        if(response.isSuccessful()){
                            displayPopularTvShows(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<TvResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });



        ApiClient.getRetrofitClient()
                .getTopRatedTvShow(ApiClient.API_KEY, "en-US", 1)
                .enqueue(new Callback<TvResponse>() {
                    @Override
                    public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                        if(response.isSuccessful()){
                            displayTopRatedTvShows(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<TvResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

    }

    private void displayTopRatedTvShows(TvResponse tvResponse) {
        progressBar.setVisibility(View.GONE);
        tvRelativeLayout.setVisibility(View.VISIBLE);
        ArrayList<TvShows> tvShows = tvResponse.getTvShowsArrayList();
        topRatedArrayList.addAll(tvShows);
        topRatedAdapter.notifyDataSetChanged();
    }

    private void displayPopularTvShows(TvResponse tvResponse) {
        progressBar.setVisibility(View.GONE);
        tvRelativeLayout.setVisibility(View.VISIBLE);
        ArrayList<TvShows> tvShows = tvResponse.getTvShowsArrayList();
        Log.i(TAG,"size = "+tvShows.size());
        popularArrayList.addAll(tvShows);
        popularAdapter.notifyDataSetChanged();
    }

    private void displayOnTheAir(TvResponse tvResponse) {
        progressBar.setVisibility(View.GONE);
        tvRelativeLayout.setVisibility(View.VISIBLE);
        ArrayList<TvShows> tvShows = tvResponse.getTvShowsArrayList();
        Log.i(TAG,"size = "+tvShows.size());
        onTheAirArrayList.addAll(tvShows);
        onTheAirAdapter.notifyDataSetChanged();


    }

    private void displayDetails(TvResponse tvResponse) {
        progressBar.setVisibility(View.GONE);
        tvRelativeLayout.setVisibility(View.VISIBLE);

        ArrayList<TvShows> tvShows = tvResponse.getTvShowsArrayList();
        Log.i(TAG,"size = "+tvShows.size());
        airingTodayArrayList.addAll(tvResponse.getTvShowsArrayList());
        airingTodayAdapter.notifyDataSetChanged();
    }


}
