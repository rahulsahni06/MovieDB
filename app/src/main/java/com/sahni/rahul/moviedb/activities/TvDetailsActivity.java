package com.sahni.rahul.moviedb.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.sahni.rahul.moviedb.Networking.Person;
import com.sahni.rahul.moviedb.R;
import com.sahni.rahul.moviedb.fragments.SeasonDetailsBottomSheet;
import com.sahni.rahul.moviedb.fragments.TvShowDetailsFragment;
import com.sahni.rahul.moviedb.helpers.IntentConstants;
import com.sahni.rahul.moviedb.interfaces.OnCastClickListener;
import com.sahni.rahul.moviedb.interfaces.OnCloseBottomSheetClickListener;
import com.sahni.rahul.moviedb.interfaces.OnMovieVideoClickListener;
import com.sahni.rahul.moviedb.interfaces.OnTvClickListener;
import com.sahni.rahul.moviedb.interfaces.OnTvSeasonClickListener;
import com.sahni.rahul.moviedb.models.TvShows;

import static com.sahni.rahul.moviedb.Networking.ApiClient.YOUTUBE_LINK;
import static com.sahni.rahul.moviedb.helpers.IntentConstants.TV_KEY;

public class TvDetailsActivity extends AppCompatActivity implements OnTvSeasonClickListener, OnTvClickListener,
        OnCastClickListener, OnMovieVideoClickListener, OnCloseBottomSheetClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Intent intent = getIntent();
        TvShows tvShows = (TvShows) intent.getParcelableExtra(TV_KEY);

//        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.test_frame_layout);

        TvShowDetailsFragment fragment = TvShowDetailsFragment.newInstance(tvShows);
        fragment.setTvFragmentListener(this, this, this, this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.test_frame_layout,fragment ).commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onTvSeasonClicked(View view, int tvShowId, int seasonNumber) {
        SeasonDetailsBottomSheet seasonDetailsBottomSheet = SeasonDetailsBottomSheet.newInstance(tvShowId, seasonNumber);
        seasonDetailsBottomSheet.setCloseSheetListener(this);
        seasonDetailsBottomSheet.show(getSupportFragmentManager(), seasonDetailsBottomSheet.getTag());
    }

    @Override
    public void onMovieVideoClicked(View view, String videoKey) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(YOUTUBE_LINK + videoKey));

        PackageManager packageManager = getPackageManager();
        if(intent.resolveActivity(packageManager) != null){
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Install browser to watch videos", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onCastClicked(View view, Person person) {
        Intent intent = new Intent(this, PersonDetailsActivity.class);
        intent.putExtra(IntentConstants.PERSON_KEY, person);
        startActivity(intent);

    }

    @Override
    public void onTvClicked(View view, TvShows tvShows) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        TvShowDetailsFragment fragment = TvShowDetailsFragment.newInstance(tvShows);
        fragment.setTvFragmentListener(this, this, this, this);
        fragmentTransaction.replace(R.id.test_frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void onCloseBottomSheetClicked(SeasonDetailsBottomSheet bottomSheet) {
        bottomSheet.dismiss();
    }
}
