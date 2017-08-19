package com.sahni.rahul.moviedb.interfaces;

import android.view.View;

import com.sahni.rahul.moviedb.models.Movie;

/**
 * Created by sahni on 19-Jul-17.
 */

public interface OnMovieClickListener {

    void onMovieClicked(View view, Movie movie);
}
