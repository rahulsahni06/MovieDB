package com.sahni.rahul.moviedb.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sahni.rahul.moviedb.R;
import com.sahni.rahul.moviedb.adapter.SearchPagerAdapter;
import com.sahni.rahul.moviedb.fragments.SearchMovieFragment;
import com.sahni.rahul.moviedb.fragments.SearchPersonFragment;
import com.sahni.rahul.moviedb.fragments.SearchTvShowFragment;

public class SearchActivity extends AppCompatActivity {


    static final String TAG = "SearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(" ");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final SearchView searchView = (SearchView) findViewById(R.id.search_view);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.search_tab_layout);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.search_view_pager);
        final SearchPagerAdapter searchPagerAdapter = new SearchPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(searchPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                for(int i = 0; i<searchPagerAdapter.getCount(); i++){
                    Fragment fragment = searchPagerAdapter.fragments[i];
                    if(fragment instanceof SearchMovieFragment){
                        ((SearchMovieFragment)fragment).update(query);
                    }
                    else if(fragment instanceof SearchTvShowFragment){
                        ((SearchTvShowFragment) fragment).update(query);
                    }
                    else if(fragment instanceof SearchPersonFragment){
                        ((SearchPersonFragment)fragment).update(query);
                    }
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }

}
