package com.sahni.rahul.moviedb.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sahni.rahul.moviedb.fragments.MovieInfoFragment;
import com.sahni.rahul.moviedb.fragments.MovieReviewsFragment;

/**
 * Created by sahni on 21-Jul-17.
 */

public class MovieDetailsViewPagerAdapter extends FragmentPagerAdapter {

    int movieId;
    int mCurrentPosition = -1;
    String unformattedReleaseDate;

    public MovieDetailsViewPagerAdapter(FragmentManager fm, int movieId, String unformattedReleaseDate) {
        super(fm);
        this.movieId = movieId;
        this.unformattedReleaseDate = unformattedReleaseDate;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return MovieInfoFragment.newInstance(movieId, unformattedReleaseDate);
        }
        else {
            return MovieReviewsFragment.newInstance(movieId);
        }


    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Info";
        }
        else {
            return "Reviews";
        }
    }


//    @Override
//    public void setPrimaryItem(ViewGroup container, int position, Object object) {
//        super.setPrimaryItem(container, position, object);
//
//        if (position != mCurrentPosition) {
//            Fragment fragment = (Fragment) object;
//            MyViewPager pager = (MyViewPager) container;
//            if (fragment != null && fragment.getView() != null) {
//                mCurrentPosition = position;
//                pager.measureCurrentView(fragment.getView());
//            }
//        }
//    }
}
