package com.sahni.rahul.moviedb.Networking;

import com.google.gson.annotations.SerializedName;
import com.sahni.rahul.moviedb.models.Movie;

import java.util.ArrayList;

/**
 * Created by sahni on 19-Jul-17.
 */

public class MovieResponse {

    @SerializedName("results")
    private ArrayList<Movie> movieArrayList;

    public ArrayList<Movie> getMovieArrayList() {
        return movieArrayList;
    }

    public void setMovieArrayList(ArrayList<Movie> movieArrayList) {
        this.movieArrayList = movieArrayList;
    }
}
