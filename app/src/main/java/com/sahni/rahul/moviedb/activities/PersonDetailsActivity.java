package com.sahni.rahul.moviedb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sahni.rahul.moviedb.Networking.ApiClient;
import com.sahni.rahul.moviedb.Networking.Person;
import com.sahni.rahul.moviedb.R;
import com.sahni.rahul.moviedb.adapter.MoviesRecyclerAdapter;
import com.sahni.rahul.moviedb.adapter.PersonImageAdapter;
import com.sahni.rahul.moviedb.adapter.TvRecyclerAdapter;
import com.sahni.rahul.moviedb.helpers.ContentUtils;
import com.sahni.rahul.moviedb.helpers.IntentConstants;
import com.sahni.rahul.moviedb.interfaces.OnMovieClickListener;
import com.sahni.rahul.moviedb.interfaces.OnTvClickListener;
import com.sahni.rahul.moviedb.models.Images;
import com.sahni.rahul.moviedb.models.Movie;
import com.sahni.rahul.moviedb.models.TvShows;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import at.blogc.android.views.ExpandableTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sahni.rahul.moviedb.Networking.ApiClient.API_KEY;

public class PersonDetailsActivity extends AppCompatActivity implements OnMovieClickListener, OnTvClickListener {


    private ConstraintLayout constraintLayout;
    private ProgressBar progressBar;


    private TextView dobTextView;
    private ExpandableTextView biographyTextView;
    private TextView nameTextView;
    private ImageView personImageView;
    private TextView ageTextView;
    private TextView birthPlaceTextView;


    String TAG = "PersonDetailsActivity";


    RecyclerView personMovieRecyclerView;
    ArrayList<Movie> personMovieArrayList;
    MoviesRecyclerAdapter personMovieAdapter;

    RecyclerView personTvRecyclerView;
    ArrayList<TvShows> personTvArrayList;
    TvRecyclerAdapter personTvRecyclerAdapter;

    RecyclerView imageRecyclerView;
    PersonImageAdapter imageAdapter;
    ArrayList<Images.Profiles> imageArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);

        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final Person person = getIntent().getParcelableExtra(IntentConstants.PERSON_KEY);


        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.person_app_bar_layout);
        final CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.person_collapsing_toolbar_layout);

//        toolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);


        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                int scrollRange = appBarLayout.getTotalScrollRange();
                Log.i(TAG, "Scroll range = "+scrollRange);
                Log.i(TAG, "vertical offset ="+ verticalOffset);

                if(verticalOffset + scrollRange  == 0){
                    toolbarLayout.setTitle(person.getName());
                }
                else{
                    toolbarLayout.setTitle(" ");
                }
            }
        });





        constraintLayout = (ConstraintLayout) findViewById(R.id.people_constraint_layout);
        constraintLayout.setVisibility(View.INVISIBLE);
        progressBar = (ProgressBar) findViewById(R.id.people_progress_bar);

        personImageView = (ImageView) findViewById(R.id.person_image_view);
        nameTextView = (TextView) findViewById(R.id.person_name_text_view);
        dobTextView = (TextView) findViewById(R.id.dob_text_view);
        ageTextView = (TextView) findViewById(R.id.age_text_view);
        biographyTextView = (ExpandableTextView) findViewById(R.id.biography_text_view);
        biographyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biographyTextView.toggle();
            }
        });

        birthPlaceTextView = (TextView) findViewById(R.id.birth_place_text_View);


        personMovieRecyclerView = (RecyclerView) findViewById(R.id.people_movie_recycler_view);
        personMovieArrayList = new ArrayList<>();
        personMovieAdapter = new MoviesRecyclerAdapter(this, personMovieArrayList, this, IntentConstants.RELEASED_MOVIE);
        personMovieRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        personMovieRecyclerView.setAdapter(personMovieAdapter);


        personTvRecyclerView = (RecyclerView) findViewById(R.id.people_tv_recycler_view);
        personTvArrayList = new ArrayList<>();
        personTvRecyclerAdapter = new TvRecyclerAdapter(this, personTvArrayList, this);
        personTvRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        personTvRecyclerView.setAdapter(personTvRecyclerAdapter);


        imageArrayList = new ArrayList<>();
        imageRecyclerView = (RecyclerView) findViewById(R.id.people_image_recycler_view);
        imageAdapter = new PersonImageAdapter(this, imageArrayList);
        imageRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        imageRecyclerView.setAdapter(imageAdapter);




        Picasso.with(this)
                .load(ApiClient.POSTER_BASE_URL + person.getProfilePath())
                .into(personImageView);

        nameTextView.setText(""+person.getName());

        fetchDetails(person.getId());
    }

    private void fetchDetails(int personId) {

        Log.i(TAG, "Id =" +personId);

//        Retrofit retrofit = ApiClient.getRetrofitClient();
        Call<Person> peopleDetailsCall = ApiClient.getRetrofitClient().getPeopleDetails(personId, API_KEY, "movie_credits,images,tv_credits");
        peopleDetailsCall.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {

                if(response.isSuccessful()){
                    displayDetails(response.body());
                    Log.i(TAG, "Success");
                }
                else{
                    Toast.makeText(PersonDetailsActivity.this , response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "No success "+response.code());
                }
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                Log.i(TAG, "Failure");

            }
        });

    }

    private void displayDetails(Person peopleDetails) {

        progressBar.setVisibility(View.GONE);
        constraintLayout.setVisibility(View.VISIBLE);

        String formattedDob = ContentUtils.getDate(peopleDetails.getBirthday());


        String dob[] = formattedDob.split(";");
        dobTextView.setText(dob[0]);
        if(dob.length > 1){
            ageTextView.setText(dob[1]);
        }


        if(peopleDetails.getPlaceOfBirth() != null){
            if(!peopleDetails.getPlaceOfBirth().equals("")){
                birthPlaceTextView.setText(peopleDetails.getPlaceOfBirth());
            }

        }



        if(!peopleDetails.getBiography().equals("")){
            biographyTextView.setText(peopleDetails.getBiography());
        }

        ArrayList<Movie> movieArrayList = peopleDetails.movieCreditsClass.getMovieArrayList();
        if(!movieArrayList.isEmpty()){
            personMovieArrayList.addAll(movieArrayList);
            personMovieAdapter.notifyDataSetChanged();
        }

        ArrayList<TvShows> tvShowsArrayList = peopleDetails.tvShowCreditsClass.getTvShowsArrayList();
        if(!tvShowsArrayList.isEmpty()){
            personTvArrayList.addAll(tvShowsArrayList);
            personTvRecyclerAdapter.notifyDataSetChanged();
        }

        Images profileImages = peopleDetails.getImages();
        ArrayList<Images.Profiles> profilesArrayList = profileImages.getProfilesArrayList();
        if(!profilesArrayList.isEmpty()){
            imageArrayList.addAll(profilesArrayList);
            imageAdapter.notifyDataSetChanged();
        }

    }


    @Override
    public void onMovieClicked(View view, Movie movie) {

        int position = personMovieRecyclerView.getChildAdapterPosition(view);
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(IntentConstants.MOVIE_KEY, personMovieArrayList.get(position));
        startActivity(intent);

    }

    @Override
    public void onTvClicked(View view, TvShows nullTvShow) {
        int position = personTvRecyclerView.getChildAdapterPosition(view);
        Intent intent = new Intent(this, TvDetailsActivity.class);
        intent.putExtra(IntentConstants.TV_KEY, personTvArrayList.get(position));
        startActivity(intent);
    }
}
