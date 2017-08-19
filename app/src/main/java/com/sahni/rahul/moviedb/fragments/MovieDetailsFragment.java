package com.sahni.rahul.moviedb.fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sahni.rahul.moviedb.Networking.ApiClient;
import com.sahni.rahul.moviedb.Networking.ApiInterface;
import com.sahni.rahul.moviedb.Networking.MovieDetailsResponse;
import com.sahni.rahul.moviedb.R;
import com.sahni.rahul.moviedb.adapter.MovieDetailsImageSliderPagerAdapter;
import com.sahni.rahul.moviedb.adapter.MovieDetailsViewPagerAdapter;
import com.sahni.rahul.moviedb.helpers.ContentUtils;
import com.sahni.rahul.moviedb.helpers.IntentConstants;
import com.sahni.rahul.moviedb.models.Genre;
import com.sahni.rahul.moviedb.models.Images;
import com.sahni.rahul.moviedb.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.sahni.rahul.moviedb.Networking.ApiClient.API_KEY;
import static com.sahni.rahul.moviedb.Networking.ApiClient.BASE_URL;
import static com.sahni.rahul.moviedb.helpers.ContentUtils.country_code;
import static com.sahni.rahul.moviedb.helpers.ContentUtils.getGenre;
import static com.sahni.rahul.moviedb.helpers.IntentConstants.MOVIE_KEY;


public class MovieDetailsFragment extends Fragment {


    private ViewPager imageViewPager;
    private MovieDetailsImageSliderPagerAdapter pagerAdapter;
    private ArrayList<Images.Backdrops> backdropsArrayList;
    private ImageView PosterImageView;
    private TextView titleTextView;
    private TextView languageTextView;
    private TextView runtimeTextView;
    private TextView genreTextView;
    private TextView certificationTextView;
    private CollapsingToolbarLayout toolbarLayout;
    private ImageView separatorImageView;

    private ViewPager detailsViewPager;

    private CircleIndicator circleIndicator;

    private Movie movie;

    private static final String TAG = "MovieShowFragment";
    private MovieDetailsResponse movieResponse;

    private Bundle saveStateBundle;

    private Drawable textBorder;


    public MovieDetailsFragment() {
        // Required empty public constructor
    }


    public static MovieDetailsFragment newInstance(Movie movie) {

        Bundle args = new Bundle();
        args.putParcelable(MOVIE_KEY, movie);

        MovieDetailsFragment fragment = new MovieDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movie = getArguments().getParcelable(IntentConstants.MOVIE_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_movie_show_details, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Toolbar toolbar = (Toolbar) view.findViewById(R.id.movie_details_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().onBackPressed();
            }
        });




        final int movieId = movie.getId();
        final String movieTitle = movie.getTitle();
        String unformattedReleaseDate = movie.getDate();

        toolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);

        AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.app_bar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                int scrollRange = appBarLayout.getTotalScrollRange();

                if (scrollRange + verticalOffset == 0) {
                    toolbarLayout.setTitle(movieTitle);
//                    isShow = true;
                } else  {
                    toolbarLayout.setTitle(" ");
//                    isShow = false;
                }

            }
        });

        backdropsArrayList = new ArrayList<>();
        imageViewPager = (ViewPager) view.findViewById(R.id.movie_details_image_viewpager);
        pagerAdapter = new MovieDetailsImageSliderPagerAdapter(getChildFragmentManager(),  backdropsArrayList);
        imageViewPager.setAdapter(pagerAdapter);
        circleIndicator = (CircleIndicator) view.findViewById(R.id.circle_indicator);
        circleIndicator.setViewPager(imageViewPager);
        pagerAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        PosterImageView = (ImageView) view.findViewById(R.id.movie_details_poster_image_view);
        titleTextView = (TextView) view.findViewById(R.id.movie_details_title_text_view);
        runtimeTextView = (TextView) view.findViewById(R.id.movie_details_runtime_text_view);
        languageTextView = (TextView) view.findViewById(R.id.movie_details_language_text_view);
        genreTextView = (TextView) view.findViewById(R.id.movie_details_genre_text_view);
        certificationTextView = (TextView) view.findViewById(R.id.movie_details_certification);
        separatorImageView = (ImageView) view.findViewById(R.id.dot_separator_image_view);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.movie_details_tab_layout);
        detailsViewPager = (ViewPager) view.findViewById(R.id.movie_details_view_pager);
        MovieDetailsViewPagerAdapter viewPagerAdapter = new MovieDetailsViewPagerAdapter(getChildFragmentManager(), movieId, unformattedReleaseDate);
        detailsViewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(detailsViewPager);


        Picasso.with(getContext())
                .load(ApiClient.IMAGE_BASE_URL +movie.getPosterPath())
                .placeholder(R.drawable.picasso_placeholder)
                .into(PosterImageView);

        textBorder = ContextCompat.getDrawable(getActivity(), R.drawable.border_text);


//        fetchMovieDetails(movieId);

    }


    private void fetchMovieDetails(int movieId) {

//        int cacheSize = 5 * 1024 * 1024; // 10 MB
//        Cache cache = new Cache(getCacheDir(), cacheSize);
//
//
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .cache(cache)
//                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
//                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Call<MovieDetailsResponse> call = retrofit.create(ApiInterface.class).getMovieDetails(movieId, API_KEY, "images,release_dates,credits");
        call.enqueue(new Callback<MovieDetailsResponse>() {
            @Override
            public void onResponse(Call<MovieDetailsResponse> call, Response<MovieDetailsResponse> response) {
                if(response.isSuccessful()){
                    MovieDetailsResponse movieDetails = response.body();
//                    response.errorBody();
                    Log.i(TAG, "header = "+response.headers().names());

                    if(movieDetails != null){
                        displayDetails(movieDetails);
                    }
                    else {
                        Toast.makeText(getContext(),"ArrayList is null", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getContext(),"ArrayList is null", Toast.LENGTH_SHORT).show();
                    Log.i(TAG,""+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<MovieDetailsResponse> call, Throwable t) {

            }
        });
    }

    void displayDetails(MovieDetailsResponse response){

        this.movieResponse = response;

        separatorImageView.setVisibility(View.VISIBLE);

        backdropsArrayList.addAll(response.images.getBackdropsList());
        pagerAdapter.notifyDataSetChanged();

        titleTextView.setText(response.getTitle());

        runtimeTextView.setText(ContentUtils.getProperRuntime(response.getRuntime()));
        ArrayList<Genre> arrayList = response.getGenreArrayList();
        genreTextView.setText(getGenre(arrayList));


        String certification = "Certification N/A";

        ArrayList<MovieDetailsResponse.ReleaseDatesResult.Results> releaseDatesResults = response.releaseDatesResult.getResults();
        for(MovieDetailsResponse.ReleaseDatesResult.Results results: releaseDatesResults){
//            Log.i(TAG, "country code checking");
            if(results.getCountryCode().equals(country_code)) {

                Log.i(TAG, "country code found");
                ArrayList<MovieDetailsResponse.ReleaseDatesResult.Results.FinalReleaseDate> finalReleaseDates = results.getFinalReleaseDates();
                Log.i("certify", "" + finalReleaseDates.get(0).getCertification());
                String certificate = finalReleaseDates.get(0).getCertification();
                if (!certificate.equals("")) {
                    certification = certificate;
                    Log.i(TAG, "certificate = "+ certification);
                    break;
                }
            }
            Log.i(TAG, "country code NOT found");
        }

        if(certification.equals("Certification N/A")){
            certificationTextView.setBackground(ContextCompat.getDrawable(getContext(), android.R.color.transparent));
            certificationTextView.setPadding(0,0,0,0);
            certificationTextView.setText(certification);
        }
        else{
            certificationTextView.setBackground(textBorder);
            certificationTextView.setText(certification);
        }

        languageTextView.setText(ContentUtils.getLanguageFromCode(response.getLanguage()));


//        if(pagerAdapter.getCount() > 1){
//            imageViewPager.setCurrentItem(1,false);
//            Log.i(TAG, "Current item: "+imageViewPager.getCurrentItem());
//            imageViewPager.setCurrentItem(0,false);
//            Log.i(TAG, "Current item: "+imageViewPager.getCurrentItem());
//            imageViewPager.scrollBy(50, 0);
//        }
//        else{
//            Log.i(TAG, "Current item: child count 0");
//        }



    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(movieResponse != null){
            outState.putParcelable(IntentConstants.MOVIE_KEY, movieResponse);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        saveStateBundle = new Bundle();
        if(movieResponse != null){
            saveStateBundle.putParcelable(IntentConstants.MOVIE_KEY, movieResponse);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Picasso.with(getContext())
                .load(ApiClient.IMAGE_BASE_URL +movie.getPosterPath())
                .placeholder(R.drawable.picasso_placeholder)
                .into(PosterImageView);

        if(savedInstanceState != null){
            displayDetails((MovieDetailsResponse) savedInstanceState.getParcelable(IntentConstants.MOVIE_KEY));
//            imageViewPager

        }
        else if(saveStateBundle != null){
            displayDetails((MovieDetailsResponse) saveStateBundle.getParcelable(IntentConstants.MOVIE_KEY));
        }
        else{
            fetchMovieDetails(movie.getId());
        }
    }
}
