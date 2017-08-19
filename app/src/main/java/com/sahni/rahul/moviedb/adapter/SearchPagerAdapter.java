package com.sahni.rahul.moviedb.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.sahni.rahul.moviedb.fragments.SearchMovieFragment;
import com.sahni.rahul.moviedb.fragments.SearchPersonFragment;
import com.sahni.rahul.moviedb.fragments.SearchTvShowFragment;

/**
 * Created by sahni on 04-Aug-17.
 */

public class SearchPagerAdapter extends FragmentStatePagerAdapter {

    private String tabTitle[] = {"Movie", "Tv shows","Person"};


    /**
     * array to store fragment references
     */
    public Fragment fragments[] = new Fragment[tabTitle.length];

    public SearchPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 2){
            return SearchPersonFragment.newInstance();

        }
        else if(position == 1) {
            return SearchTvShowFragment.newInstance();
        }
        else{
            return SearchMovieFragment.newInstance();
        }

    }

    @Override
    public int getCount() {
        return tabTitle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }


    /**
     *update fragment references in array of fragments
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment currentFragment = (Fragment) super.instantiateItem(container, position);
        fragments[position] = currentFragment;

        return currentFragment;
    }
}
