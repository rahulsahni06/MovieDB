package com.sahni.rahul.moviedb.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.sahni.rahul.moviedb.Networking.Person;
import com.sahni.rahul.moviedb.R;
import com.sahni.rahul.moviedb.fragments.MovieDetailsFragment;
import com.sahni.rahul.moviedb.helpers.IntentConstants;
import com.sahni.rahul.moviedb.interfaces.OnCastClickListener;
import com.sahni.rahul.moviedb.interfaces.OnMovieClickListener;
import com.sahni.rahul.moviedb.interfaces.OnMovieVideoClickListener;
import com.sahni.rahul.moviedb.models.Movie;

import static com.sahni.rahul.moviedb.Networking.ApiClient.YOUTUBE_LINK;
import static com.sahni.rahul.moviedb.helpers.IntentConstants.MOVIE_KEY;

public class MovieDetailsActivity extends AppCompatActivity implements OnCastClickListener,
        OnMovieClickListener, OnMovieVideoClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        Movie movie = getIntent().getParcelableExtra(MOVIE_KEY);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        MovieDetailsFragment fragment = MovieDetailsFragment.newInstance(movie);
//        fragment.setFragmentListener(this);
        fragmentTransaction.replace(R.id.movie_info_frame_layout, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onCastClicked(View view, Person person) {
        Intent intent = new Intent(MovieDetailsActivity.this, PersonDetailsActivity.class);
        intent.putExtra(IntentConstants.PERSON_KEY, person);
        startActivity(intent);
    }

    @Override
    public void onMovieClicked(View view, Movie movie) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        MovieDetailsFragment fragment = MovieDetailsFragment.newInstance(movie);
        fragmentTransaction.replace(R.id.movie_info_frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onMovieVideoClicked(View view, String videoKey) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(YOUTUBE_LINK + videoKey));

        PackageManager packageManager =getPackageManager();
        if(intent.resolveActivity(packageManager) != null){
            startActivity(intent);
        }
        else{
            Toast.makeText(MovieDetailsActivity.this, "Install browser to watch videos", Toast.LENGTH_SHORT).show();
        }

    }
}
