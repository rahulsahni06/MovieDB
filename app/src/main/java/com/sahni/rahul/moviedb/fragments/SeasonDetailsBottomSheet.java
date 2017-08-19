package com.sahni.rahul.moviedb.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sahni.rahul.moviedb.Networking.ApiClient;
import com.sahni.rahul.moviedb.R;
import com.sahni.rahul.moviedb.adapter.SeasonDetailsSheetRecyclerAdapter;
import com.sahni.rahul.moviedb.helpers.IntentConstants;
import com.sahni.rahul.moviedb.interfaces.OnCloseBottomSheetClickListener;
import com.sahni.rahul.moviedb.models.Episodes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sahni on 29-Jul-17.
 */

public class SeasonDetailsBottomSheet extends BottomSheetDialogFragment {

    private int tvId;
    private int seasonNumber;

    private final String TAG ="SeasonDetailsSheet";

    private RecyclerView seasonDetailsRecyclerView;
    private SeasonDetailsSheetRecyclerAdapter adapter;
    private ArrayList<Episodes.SeasonEpisode> episodeArrayList;

    private RelativeLayout relativeLayout;
    private ProgressBar progressBar;
    private OnCloseBottomSheetClickListener closeBottomSheetClickListener;

    public static SeasonDetailsBottomSheet newInstance(int tvId, int seasonNumber) {
        
        Bundle args = new Bundle();
        args.putInt(IntentConstants.TV_ID, tvId);
        args.putInt(IntentConstants.TV_SEASON_NO, seasonNumber);
        SeasonDetailsBottomSheet fragment = new SeasonDetailsBottomSheet();
        fragment.setArguments(args);
        return fragment;
    }

    public void setCloseSheetListener(OnCloseBottomSheetClickListener listener){
        this.closeBottomSheetClickListener = listener;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        tvId = bundle.getInt(IntentConstants.TV_ID);
        seasonNumber = bundle.getInt(IntentConstants.TV_SEASON_NO);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.season_details_bottom_sheet_layout, container, false);

        ImageView cancelImageView = (ImageView) view.findViewById(R.id.episode_cancel_image_view);
        cancelImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SeasonDetailsBottomSheet.this.dismiss();
                if(closeBottomSheetClickListener != null){
                    closeBottomSheetClickListener.onCloseBottomSheetClicked(SeasonDetailsBottomSheet.this);
                }
            }
        });
        relativeLayout = (RelativeLayout) view.findViewById(R.id.episode_relative_layout);
        relativeLayout.setVisibility(View.INVISIBLE);

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        TextView seasonNumberTextView = (TextView) view.findViewById(R.id.episode_season_no_text_view);
        seasonNumberTextView.setText("Season "+seasonNumber);

        episodeArrayList = new ArrayList<>();
        seasonDetailsRecyclerView = (RecyclerView) view.findViewById(R.id.season_details_recycler_view);
        adapter = new SeasonDetailsSheetRecyclerAdapter(getContext(), episodeArrayList);
        seasonDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//        seasonDetailsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        seasonDetailsRecyclerView.setAdapter(adapter);

        fetchDetails(tvId, seasonNumber);


        return view;
    }

    private void fetchDetails(int tvId, int seasonNumber) {

        Log.i(TAG, "fetchDetails(): fetching");

        ApiClient.getRetrofitClient()
                .getSeasonDetails(tvId, seasonNumber, ApiClient.API_KEY)
                .enqueue(new Callback<Episodes>() {
                    @Override
                    public void onResponse(Call<Episodes> call, Response<Episodes> response) {
                        Log.i(TAG, "fetchDetails(): response Received");
                        if(response.isSuccessful()){
                            Log.i(TAG, "fetchDetails(): response Received successful");
                            displayDetails(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Episodes> call, Throwable t) {
                        Log.i(TAG, "fetchDetails(): response failed");
                        t.printStackTrace();

                    }
                });
    }

    private void displayDetails(Episodes episodes) {

        Log.i(TAG, "displayDetails(): displaying");

        progressBar.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.VISIBLE);


        if(!episodes.getEpisodeArrayList().isEmpty()){
            episodeArrayList.addAll(episodes.getEpisodeArrayList());
            adapter.notifyDataSetChanged();
        }
        else{
            Log.i(TAG, "displayDetails(): arrayList empty");
        }
    }
}
