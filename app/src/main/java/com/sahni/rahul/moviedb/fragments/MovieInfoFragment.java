package com.sahni.rahul.moviedb.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sahni.rahul.moviedb.Networking.ApiInterface;
import com.sahni.rahul.moviedb.Networking.MovieDetailsResponse;
import com.sahni.rahul.moviedb.Networking.Person;
import com.sahni.rahul.moviedb.R;
import com.sahni.rahul.moviedb.activities.MovieDetailsActivity;
import com.sahni.rahul.moviedb.adapter.CastRecyclerAdapter;
import com.sahni.rahul.moviedb.adapter.MovieVideosRecyclerAdapter;
import com.sahni.rahul.moviedb.adapter.MoviesRecyclerAdapter;
import com.sahni.rahul.moviedb.helpers.ContentUtils;
import com.sahni.rahul.moviedb.helpers.IntentConstants;
import com.sahni.rahul.moviedb.interfaces.OnCastClickListener;
import com.sahni.rahul.moviedb.interfaces.OnMovieClickListener;
import com.sahni.rahul.moviedb.interfaces.OnMovieVideoClickListener;
import com.sahni.rahul.moviedb.models.Credits;
import com.sahni.rahul.moviedb.models.Movie;
import com.sahni.rahul.moviedb.models.Videos;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.sahni.rahul.moviedb.Networking.ApiClient.API_KEY;
import static com.sahni.rahul.moviedb.Networking.ApiClient.BASE_URL;
import static com.sahni.rahul.moviedb.helpers.IntentConstants.MOVIE_ID;
import static com.sahni.rahul.moviedb.helpers.IntentConstants.MOVIE_RELEASE_DATE;
import static com.sahni.rahul.moviedb.helpers.IntentConstants.RELEASED_MOVIE;

public class MovieInfoFragment extends Fragment implements OnMovieClickListener, OnMovieVideoClickListener, OnCastClickListener {

    private int movieId;
    private String releaseDate;

    private TextView overviewTextView;
    private TextView directorInfoTextView;
    private TextView directorTextView;
    private TextView dateTextView;

    private ProgressBar progressBar;

    private RecyclerView movieVideosRecyclerView;
    private MovieVideosRecyclerAdapter movieVideosRecyclerAdapter;
    private ArrayList<Videos.VideoResult> videoDetailsArrayList;
    private TextView noVideosTextView;


    private RecyclerView castRecyclerView;
    private CastRecyclerAdapter castRecyclerAdapter;
    private ArrayList<Person> castArrayList;
    private TextView noCastTextView;


    private RecyclerView similarMovieRecyclerView;
    private MoviesRecyclerAdapter similarMovieAdapter;
    private ArrayList<Movie> similarMovieArrayList;
    private TextView noSimilarMovieTextView;

    private RelativeLayout movieInfoRelativeLayout;

    private OnMovieClickListener movieClickListener;
    private OnMovieVideoClickListener movieVideoClickListener;
    private OnCastClickListener castClickListener;

    private MovieDetailsResponse movieResponse;
    private Bundle saveStateBundle;

    void setFragmentListener(){
//        this.listener = listener;
        this.movieClickListener = ((MovieDetailsActivity)getActivity());
        this.movieVideoClickListener = ((MovieDetailsActivity)getActivity());
        this.castClickListener = (MovieDetailsActivity)getActivity();
    }



    public MovieInfoFragment() {
        // Required empty public constructor
    }


    public static MovieInfoFragment newInstance(int movieId, String unformattedReleaseDate) {
        MovieInfoFragment fragment = new MovieInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(MOVIE_ID, movieId);
        bundle.putString(MOVIE_RELEASE_DATE, unformattedReleaseDate);
        fragment.setArguments(bundle);
//        fragment.setFragmentListener();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        movieId = bundle.getInt(MOVIE_ID);
        releaseDate = ContentUtils.getDate(bundle.getString(MOVIE_RELEASE_DATE));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_info, container, false);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        movieInfoRelativeLayout = (RelativeLayout) view.findViewById(R.id.movie_info_relative_layout);
        movieInfoRelativeLayout.setVisibility(View.INVISIBLE);

        overviewTextView = (TextView) view.findViewById(R.id.movie_details_overview_text_view);
        directorInfoTextView = (TextView) view.findViewById(R.id.movie_details_director_info_text_view);
        directorTextView = (TextView) view.findViewById(R.id.movie_details_director_text_view);
        dateTextView = (TextView) view.findViewById(R.id.movie_details_date_info_text_view);
        progressBar = (ProgressBar) view.findViewById(R.id.movie_info_progress_bar);


        castRecyclerView = (RecyclerView) view.findViewById(R.id.movie_details_cast_recycler_view);
        castArrayList = new ArrayList<>();
        castRecyclerAdapter = new CastRecyclerAdapter(castArrayList, getContext(), this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        castRecyclerView.setLayoutManager(layoutManager);
        castRecyclerView.setAdapter(castRecyclerAdapter);
        noCastTextView = (TextView) view.findViewById(R.id.no_cast_text_view);


        movieVideosRecyclerView = (RecyclerView) view.findViewById(R.id.movie_details_videos_recycler_view);
        videoDetailsArrayList = new ArrayList<>();
        movieVideosRecyclerAdapter = new MovieVideosRecyclerAdapter(getContext(), videoDetailsArrayList, this);
        RecyclerView.LayoutManager trailerLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        movieVideosRecyclerView.setLayoutManager(trailerLayoutManager);
        movieVideosRecyclerView.setAdapter(movieVideosRecyclerAdapter);
        noVideosTextView = (TextView) view.findViewById(R.id.no_videos_text_view);


        similarMovieRecyclerView = (RecyclerView) view.findViewById(R.id.movie_details_similar_recycler_view);
        similarMovieArrayList = new ArrayList<>();
        similarMovieAdapter = new MoviesRecyclerAdapter(getContext(), similarMovieArrayList, MovieInfoFragment.this, RELEASED_MOVIE);
        RecyclerView.LayoutManager similarMovieLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        similarMovieRecyclerView.setLayoutManager(similarMovieLayoutManager);
        similarMovieRecyclerView.setAdapter(similarMovieAdapter);
        noSimilarMovieTextView = (TextView) view.findViewById(R.id.no_similar_movie_text_view);



//        fetchMovieDetails(movieId);

    }

    private void fetchMovieDetails(int movieId) {

//        int cacheSize = 5 * 1024 * 1024; // 10 MB
//        Cache cache = new Cache(getContext().getCacheDir(), cacheSize);
//
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .cache(cache)
//                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
//                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Call<MovieDetailsResponse> call = retrofit.create(ApiInterface.class).getMovieDetails(movieId, API_KEY, "credits,videos,similar");
        call.enqueue(new Callback<MovieDetailsResponse>() {
            @Override
            public void onResponse(Call<MovieDetailsResponse> call, Response<MovieDetailsResponse> response) {
                if(response.isSuccessful()){
                    MovieDetailsResponse movieDetails = response.body();
//                    response.errorBody();

                    if(movieDetails != null){
                        displayDetails(movieDetails);
                    }
                    else {
                        Toast.makeText(getContext(),"ArrayList is null", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getContext(),"ArrayList is null", Toast.LENGTH_SHORT).show();
                    Log.i("Response unsuccessful",""+response.errorBody());
                }

            }

            @Override
            public void onFailure(Call<MovieDetailsResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        setFragmentListener();

    }

    void displayDetails(MovieDetailsResponse response){

        this.movieResponse = response;

        progressBar.setVisibility(View.GONE);
        movieInfoRelativeLayout.setVisibility(View.VISIBLE);
        directorTextView.setText("Director");

        String overview = response.getOverview();
        overviewTextView.setText(overview);
        String date[] = releaseDate.split(";");
        dateTextView.setText(date[0]);


        Credits credits = response.getCredits();
//        ArrayList<MovieDetailsResponse.CreditResponse.Crew> crewArrayList = creditResponse.getCrewArrayList();

        for(Person crewMember: credits.getCrewArrayList()){
            if(crewMember.getJob().equalsIgnoreCase("Director")){
                directorInfoTextView.setText(crewMember.getName());
                break;
            }
        }

        if(!credits.getCastArrayList().isEmpty()){
            castArrayList.addAll(credits.getCastArrayList());
            castRecyclerAdapter.notifyDataSetChanged();
        }
        else{
            noCastTextView.setVisibility(View.VISIBLE);
        }

        if(!response.getVideos().getVideoResultList().isEmpty()){
            videoDetailsArrayList.addAll(response.getVideos().getVideoResultList());
            movieVideosRecyclerAdapter.notifyDataSetChanged();
        }
        else{
            noVideosTextView.setVisibility(View.VISIBLE);
        }


        if(!response.similarMovieClass.getMovieArrayList().isEmpty()){
            similarMovieArrayList.addAll(response.similarMovieClass.getMovieArrayList());
            similarMovieAdapter.notifyDataSetChanged();
        }
        else{
            noSimilarMovieTextView.setVisibility(View.VISIBLE);
        }
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
        if(movieResponse != null){
            saveStateBundle = new Bundle();
            saveStateBundle.putParcelable(IntentConstants.MOVIE_KEY, movieResponse);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null){
            displayDetails((MovieDetailsResponse) savedInstanceState.getParcelable(IntentConstants.MOVIE_KEY));
        }
        else if(saveStateBundle != null){
            displayDetails((MovieDetailsResponse) saveStateBundle.getParcelable(IntentConstants.MOVIE_KEY));
        }
        else{
            fetchMovieDetails(movieId);
        }
    }

    @Override
    public void onMovieClicked(View view, Movie movie) {
        int position = similarMovieRecyclerView.getChildAdapterPosition(view);
        Movie newMovie = similarMovieArrayList.get(position);
//        Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
////        intent.putExtra(IntentConstants.MOVIE_ID, movie.getId());
////        intent.putExtra(IntentConstants.MOVIE_RELEASE_DATE, movie.getDate());
////        intent.putExtra(MOVIE_TITLE, movie.getTitle());
//        intent.putExtra(IntentConstants.MOVIE_KEY, movie);
//        getActivity().startActivity(intent);

//        this.setFragmentListener((MovieDetailsActivity) getActivity());
        if(movieClickListener != null){
            movieClickListener.onMovieClicked(null, newMovie);
        }
    }

    @Override
    public void onMovieVideoClicked(View view, String nullVideoKey) {
        int position = movieVideosRecyclerView.getChildAdapterPosition(view);
        Videos.VideoResult videoDetails = videoDetailsArrayList.get(position);

        if(movieVideoClickListener != null){
            movieVideoClickListener.onMovieVideoClicked(null, videoDetails.getVideoKey());
        }


//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse(YOUTUBE_LINK + videoDetails.getVideoKey()));
//
//        PackageManager packageManager = getContext().getPackageManager();
//        if(intent.resolveActivity(packageManager) != null){
//            startActivity(intent);
//        }
//        else{
//            Toast.makeText(getContext(), "Install browser to watch videos", Toast.LENGTH_SHORT).show();
//        }

    }

    @Override
    public void onCastClicked(View view, Person nullPerson) {
        int position = castRecyclerView.getChildAdapterPosition(view);

//        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.movie_details_toolbar);
//        int height = toolbar.getHeight();

        Person cast = castArrayList.get(position);
        if(castClickListener != null){
            castClickListener.onCastClicked(null, cast);
        }
//        Intent intent = new Intent(getActivity(), PersonDetailsActivity.class);
//        intent.putExtra(IntentConstants.PERSON_KEY, cast);
//        getActivity().startActivity(intent);
//        CastDetailsBottomSheet castDetailsBottomSheet = CastDetailsBottomSheet.newInstance(cast.getId(), cast.getName());
////        castDetailsBottomSheet.
////        castDetailsBottomSheet.getW;
//        castDetailsBottomSheet.show(getFragmentManager(), castDetailsBottomSheet.getTag());


//        View sheetView = LayoutInflater.from(getContext()).inflate(R.layout.cast_bottom_sheet_layout, null, false);
//        bottomSheetLayout = (BottomSheetLayout) sheetView.findViewById(R.id.flipboard_bottom_sheet);
//        TextView nameTextView = (TextView) view.findViewById(R.id.person_name_text_view);
//        nameTextView.setText(cast.getName());
//        bottomSheetLayout.showWithSheetView(LayoutInflater.from(getContext()).inflate(R.layout.cast_bottom_sheet_layout, bottomSheetLayout, false));




    }
}
