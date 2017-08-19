package com.sahni.rahul.moviedb.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sahni.rahul.moviedb.Networking.ApiClient;
import com.sahni.rahul.moviedb.Networking.Person;
import com.sahni.rahul.moviedb.R;
import com.sahni.rahul.moviedb.adapter.CastRecyclerAdapter;
import com.sahni.rahul.moviedb.adapter.MovieDetailsImageSliderPagerAdapter;
import com.sahni.rahul.moviedb.adapter.MovieVideosRecyclerAdapter;
import com.sahni.rahul.moviedb.adapter.SeasonAdapter;
import com.sahni.rahul.moviedb.adapter.TvRecyclerAdapter;
import com.sahni.rahul.moviedb.helpers.ContentUtils;
import com.sahni.rahul.moviedb.helpers.IntentConstants;
import com.sahni.rahul.moviedb.interfaces.OnCastClickListener;
import com.sahni.rahul.moviedb.interfaces.OnMovieVideoClickListener;
import com.sahni.rahul.moviedb.interfaces.OnTvClickListener;
import com.sahni.rahul.moviedb.interfaces.OnTvSeasonClickListener;
import com.sahni.rahul.moviedb.models.Genre;
import com.sahni.rahul.moviedb.models.Images;
import com.sahni.rahul.moviedb.models.Season;
import com.sahni.rahul.moviedb.models.TvShows;
import com.sahni.rahul.moviedb.models.Videos;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sahni.rahul.moviedb.helpers.IntentConstants.TV_KEY;


public class TvShowDetailsFragment extends Fragment implements OnCastClickListener, OnTvClickListener, OnMovieVideoClickListener, OnTvSeasonClickListener{

    private String TAG = "TvShowDetailsFragment";
    private String country_code = "US";
    private final static String BUNDLE_KEY = "bundleKey";

    private Bundle savedStateBundle;

//    int x = 10;



    private ViewPager imageSliderViewPager;
    private MovieDetailsImageSliderPagerAdapter pagerAdapter;
    private ArrayList<Images.Backdrops> backdropsArrayList;
    private CircleIndicator circleIndicator;


    //    ArrayList<MovieDetailsResponse.images.backdrops> backdropsArrayList;
    private ImageView posterImageView;
    private TextView titleTextView;
    private TextView languageTextView;
    private TextView runtimeTextView;
    private TextView genreTextView;
    private TextView certificationTextView;
    private CollapsingToolbarLayout toolbarLayout;
    private ImageView separatorImageView;



    private TvShows tvShow;

    private TextView overviewTextView;
    private TextView createdByTextView;
//    TextView directorTextView;
    private TextView dateTextView;

    private ProgressBar progressBar;
    private RelativeLayout tvShowInfoRelativeLayout;

    private RecyclerView tvVideosRecyclerView;
    private MovieVideosRecyclerAdapter tvVideosRecyclerAdapter;
    private ArrayList<Videos.VideoResult> videoArrayList;
    private TextView noVideosTextView;


    private RecyclerView castRecyclerView;
    private CastRecyclerAdapter castRecyclerAdapter;
    private ArrayList<Person> castArrayList;
    private TextView noCastTextView;


    private RecyclerView similarTvShowRecyclerView;
    private TvRecyclerAdapter similarTvShowAdapter;
    private ArrayList<TvShows> similarTvShowArrayList;
    private TextView noSimilarTvShowTextView;


    private RecyclerView seasonRecyclerView;
    private SeasonAdapter seasonAdapter;
    private ArrayList<Season> seasonArrayList;
    private TextView noSeasonTextView;

    private Drawable border;


    private Toolbar toolbar;




//    OnTvFragmentClickListener listener;
    private OnTvSeasonClickListener tvSeasonClickListener;
    private OnCastClickListener castClickListener;
    private OnTvClickListener tvClickListener;
    private OnMovieVideoClickListener movieVideoClickListener;


    public TvShowDetailsFragment() {
        // Required empty public constructor
    }

    public void setTvFragmentListener(OnTvClickListener tvClickListener, OnTvSeasonClickListener tvSeasonClickListener,
                                      OnCastClickListener castClickListener, OnMovieVideoClickListener movieVideoClickListener){
        this.tvClickListener = tvClickListener;
        this.tvSeasonClickListener = tvSeasonClickListener;
        this.castClickListener = castClickListener;
        this.movieVideoClickListener = movieVideoClickListener;
    }

    public static TvShowDetailsFragment newInstance(TvShows tvShow) {
        TvShowDetailsFragment fragment = new TvShowDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(TV_KEY, tvShow);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        tvShow = (TvShows) bundle.getParcelable(TV_KEY);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        Log.i(TAG,"onCreateView: created by =" + tvShow.getName());


//
        return inflater.inflate(R.layout.fragment_tv_show_details, container, false);
    }







    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        toolbar = (Toolbar) view.findViewById(R.id.tv_details_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeButtonEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.app_bar_layout);
        final CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);


        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                int scrollRange = appBarLayout.getTotalScrollRange();
                if(scrollRange + verticalOffset == 0){
//                    Log.i(TAG, "Top Margin ,"+toolbar.getTitle());

                    toolbarLayout.setTitle(""+tvShow.getName());

                }
                else{
                    toolbarLayout.setTitle(" ");

                }
            }
        });

        progressBar = (ProgressBar) view.findViewById(R.id.tv_show_details_progress_bar);
        tvShowInfoRelativeLayout = (RelativeLayout) view.findViewById(R.id.tv_show_details_relative_layout);
        tvShowInfoRelativeLayout.setVisibility(View.INVISIBLE);


        imageSliderViewPager = (ViewPager) view.findViewById(R.id.tv_details_image_viewpager);
        backdropsArrayList = new ArrayList<>();
        pagerAdapter = new MovieDetailsImageSliderPagerAdapter(getChildFragmentManager(), backdropsArrayList);
        imageSliderViewPager.setAdapter(pagerAdapter);
        circleIndicator = (CircleIndicator) view.findViewById(R.id.indicator);
        circleIndicator.setViewPager(imageSliderViewPager);
        pagerAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());



        posterImageView = (ImageView) view.findViewById(R.id.tv_details_poster_image_view);
        titleTextView = (TextView) view.findViewById(R.id.tv_details_title_text_view);
        languageTextView = (TextView) view.findViewById(R.id.tv_details_language_text_view);
        runtimeTextView = (TextView) view.findViewById(R.id.tv_details_runtime_text_view);
        genreTextView = (TextView) view.findViewById(R.id.tv_details_genre_text_view);
        certificationTextView = (TextView) view.findViewById(R.id.tv_details_certification);
        separatorImageView = (ImageView) view.findViewById(R.id.dot_separator_image_view);



        overviewTextView = (TextView) view.findViewById(R.id.tv_show_details_overview_text_view);
        createdByTextView = (TextView) view.findViewById(R.id.tv_show_details_director_info_text_view);
        dateTextView = (TextView) view.findViewById(R.id.tv_show_info_date_text_view);

        castArrayList = new ArrayList<>();
        castRecyclerView = (RecyclerView) view.findViewById(R.id.tv_show_info_cast_recycler_view);
        castRecyclerAdapter = new CastRecyclerAdapter(castArrayList, getContext(), this);
        castRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        castRecyclerView.setAdapter(castRecyclerAdapter);

        similarTvShowArrayList = new ArrayList<>();
        similarTvShowRecyclerView = (RecyclerView) view.findViewById(R.id.tv_show_info_similar_recycler_view);
        similarTvShowAdapter = new TvRecyclerAdapter(getContext(), similarTvShowArrayList, this);
        similarTvShowRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        similarTvShowRecyclerView.setAdapter(similarTvShowAdapter);

        videoArrayList = new ArrayList<>();
        tvVideosRecyclerView = (RecyclerView) view.findViewById(R.id.tv_show_info_videos_recycler_view);
        tvVideosRecyclerAdapter = new MovieVideosRecyclerAdapter(getContext(), videoArrayList, this);
        tvVideosRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        tvVideosRecyclerView.setAdapter(tvVideosRecyclerAdapter);


        seasonRecyclerView = (RecyclerView) view.findViewById(R.id.tv_show_season_recycler_view);
        seasonArrayList = new ArrayList<>();
        seasonAdapter = new SeasonAdapter(getContext(), seasonArrayList, this);
        seasonRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        seasonRecyclerView.setAdapter(seasonAdapter);

        Picasso.with(getActivity())
                .load(ApiClient.POSTER_BASE_URL+tvShow.getPosterPath())
                .placeholder(R.drawable.picasso_placeholder)
                .into(posterImageView);

        border = ContextCompat.getDrawable(getActivity(), R.drawable.border_text);

    }





    private void fetchTvShowDetails(int id) {

        ApiClient.getRetrofitClient().getTvShowDetails(id, ApiClient.API_KEY, "images,credits,videos,content_ratings,similar")
                .enqueue(new Callback<TvShows>() {
                    @Override
                    public void onResponse(Call<TvShows> call, Response<TvShows> response) {
                        if(response.isSuccessful()){
                            displayTvShowDetails(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<TvShows> call, Throwable t) {

                    }
                });
    }

    private void displayTvShowDetails(TvShows tvShow) {

        this.tvShow = tvShow;

        progressBar.setVisibility(View.GONE);
        tvShowInfoRelativeLayout.setVisibility(View.VISIBLE);



        separatorImageView.setVisibility(View.VISIBLE);
//        pagerAdapter = new MovieDetailsImageSliderPagerAdapter(getFragmentManager(), tvShow.getImages().getBackdropsList());
        backdropsArrayList.addAll(tvShow.getImages().getBackdropsList());
        pagerAdapter.notifyDataSetChanged();
//        imageSliderViewPager.setAdapter(pagerAdapter);

        titleTextView.setText(tvShow.getName());

        languageTextView.setText(ContentUtils.getLanguageFromCode(tvShow.getOriginalLanguage()));

        ArrayList<Integer> arrayList = tvShow.getRuntime();
        if(!arrayList.isEmpty()) {
            runtimeTextView.setText(ContentUtils.getProperRuntime(arrayList.get(0)));
        }


        ArrayList<Genre> genreList = tvShow.getGenresList();
        genreTextView.setText(ContentUtils.getGenre(genreList));

        ArrayList<TvShows.CertificationResult> certificationList = tvShow.getContentCertification().getCertificationResultList();

        String certificate = ContentUtils.getTvShowCertification(country_code, certificationList);
        if(certificate.equals("Certificate N/A")){
            certificationTextView.setBackground(ContextCompat.getDrawable((AppCompatActivity)getActivity(), android.R.color.transparent));
            certificationTextView.setPadding(0,0,0,0);
        }
        else{
            certificationTextView.setBackground(border);
        }

        certificationTextView.setText(certificate);








        overviewTextView.setText(tvShow.getOverview());
        createdByTextView.setText(ContentUtils.getCreatedBy(tvShow.getCreatedByList()));
        dateTextView.setText(tvShow.getFirstAirDate());
        if(!tvShow.getCredits().getCastArrayList().isEmpty()){
            castArrayList.addAll(tvShow.getCredits().getCastArrayList());
            castRecyclerAdapter.notifyDataSetChanged();
        }

        if(!tvShow.getVideos().getVideoResultList().isEmpty()){
            videoArrayList.addAll(tvShow.getVideos().getVideoResultList());
            tvVideosRecyclerAdapter.notifyDataSetChanged();
        }

        if(!tvShow.getSeasons().isEmpty()){
            seasonArrayList.addAll(tvShow.getSeasons());
            seasonAdapter.notifyDataSetChanged();
        }

        TvShows.SimilarTvShows similarTvShows = tvShow.getSimilarTvShows();
        ArrayList<TvShows> similarTvShowsList = similarTvShows.getTvShowsArrayList();
        if(!similarTvShowsList.isEmpty()){
            similarTvShowArrayList.addAll(similarTvShowsList);
            similarTvShowAdapter.notifyDataSetChanged();
        }

    }


    @Override
    public void onCastClicked(View view, Person nullPerson) {
        int position = castRecyclerView.getChildAdapterPosition(view);
        Person cast = castArrayList.get(position);
        if(castClickListener != null){
            castClickListener.onCastClicked(null, cast);
        }
    }

    @Override
    public void onTvSeasonClicked(View view, int tvShowId, int seasonNumber) {
        int position = seasonRecyclerView.getChildAdapterPosition(view);

        if(tvSeasonClickListener != null){
            tvSeasonClickListener.onTvSeasonClicked(null, tvShow.getId(), seasonArrayList.get(position).getSeasonNumber());
        }


    }

    @Override
    public void onMovieVideoClicked(View view, String nullVideoKey) {

        int position = tvVideosRecyclerView.getChildAdapterPosition(view);
        Videos.VideoResult videoDetails = videoArrayList.get(position);

        if(movieVideoClickListener != null){
            movieVideoClickListener.onMovieVideoClicked(null, videoDetails.getVideoKey());
        }

    }

    @Override
    public void onTvClicked(View view, TvShows nullTvShow) {
        int position = similarTvShowRecyclerView.getChildAdapterPosition(view);
        TvShows tvShows = similarTvShowArrayList.get(position);
        if(tvClickListener != null){
            tvClickListener.onTvClicked(null, tvShows);
        }


    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putParcelable(IntentConstants.TV_KEY, tvShow);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated");

        if (savedInstanceState != null) {
            Log.i(TAG, "onActivityCreated: savedInstanceState bundle is not null");
            displayTvShowDetails((TvShows) savedInstanceState.getParcelable(IntentConstants.TV_KEY));
        }
        else if(savedStateBundle != null ){
            if(!savedStateBundle.isEmpty()) {
                Log.i(TAG, "onActivityCreated: savedStateBundle bundle is not empty");
//                getActivity().supportInvalidateOptionsMenu();
                displayTvShowDetails((TvShows) savedStateBundle.getParcelable(TV_KEY));
//                toolbar.setTitleMargin(0,0,0,140);
//                toolbar.getT
            }
        }
        else{
            Log.i(TAG, "onActivityCreated: fetching");
            fetchTvShowDetails(tvShow.getId());

        }

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: "+tvShow.getName());
        savedStateBundle = new Bundle();
        savedStateBundle.putParcelable(IntentConstants.TV_KEY, tvShow);
    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        Log.i(TAG, "onStop: "+tvShow.getName());
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
////        setTvFragmentListener();
//        Log.i(TAG, "onResume: "+tvShow.getName());
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        Log.i(TAG, "onStart: "+tvShow.getName());
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        Log.i(TAG, "onAttach: ");
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        Log.i(TAG, "onDetach: "+tvShow.getName());
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.i(TAG, "onDestroy: "+tvShow.getName());
//    }
//
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        Log.i(TAG, "onDestroyView: "+tvShow.getName());
//    }
}
