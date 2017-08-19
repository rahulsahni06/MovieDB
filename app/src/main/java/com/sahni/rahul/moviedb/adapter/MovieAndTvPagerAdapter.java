package com.sahni.rahul.moviedb.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sahni.rahul.moviedb.fragments.MoviesFragment;
import com.sahni.rahul.moviedb.fragments.TvShowsFragment;

/**
 * Created by sahni on 23-Jul-17.
 */

public class MovieAndTvPagerAdapter extends FragmentPagerAdapter {
    public MovieAndTvPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0){
            return MoviesFragment.newInstance();
        }
        else{
            return TvShowsFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "Movies";
        }
        else{
            return "Tv shows";
        }
    }
}
