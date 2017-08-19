package com.sahni.rahul.moviedb.Networking;

import com.google.gson.annotations.SerializedName;
import com.sahni.rahul.moviedb.models.TvShows;

import java.util.ArrayList;

/**
 * Created by sahni on 26-Jul-17.
 */

public class TvResponse {

    @SerializedName("results")
    private ArrayList<TvShows> tvShowsArrayList;

    public ArrayList<TvShows> getTvShowsArrayList() {
        return tvShowsArrayList;
    }

    public void setTvShowsArrayList(ArrayList<TvShows> tvShowsArrayList) {
        this.tvShowsArrayList = tvShowsArrayList;
    }
}
