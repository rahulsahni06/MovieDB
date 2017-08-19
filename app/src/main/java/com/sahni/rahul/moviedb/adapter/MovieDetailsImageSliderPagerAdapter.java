package com.sahni.rahul.moviedb.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sahni.rahul.moviedb.fragments.ImageSliderFragment;
import com.sahni.rahul.moviedb.models.Images;

import java.util.ArrayList;

/**
 * Created by sahni on 19-Jul-17.
 */

public class MovieDetailsImageSliderPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Images.Backdrops> arrayList;

    public MovieDetailsImageSliderPagerAdapter(FragmentManager fm, ArrayList<Images.Backdrops> arrayList) {
        super(fm);
        this.arrayList = arrayList;
    }

    @Override
    public Fragment getItem(int position) {


        return ImageSliderFragment.newInstance((arrayList.get(position)).getFilePath());

    }

    @Override
    public int getCount() {

        if (arrayList.size() < 8) {
            return arrayList.size();
        } else {
            return 8;
        }
    }
}
