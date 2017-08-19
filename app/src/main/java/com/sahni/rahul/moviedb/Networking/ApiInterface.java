package com.sahni.rahul.moviedb.Networking;

import com.sahni.rahul.moviedb.models.Episodes;
import com.sahni.rahul.moviedb.models.SessionId;
import com.sahni.rahul.moviedb.models.TokenRequest;
import com.sahni.rahul.moviedb.models.TvShows;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sahni on 19-Jul-17.
 */

public interface ApiInterface {

    @GET("3/movie/now_playing")
    Call<MovieResponse> getNowShowingMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page,
            @Query("region") String region
    );


    @GET("3/movie/popular")
    Call<MovieResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page,
            @Query("region") String region
    );



    @GET("3/movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page,
            @Query("region") String region
    );


    @GET("3/movie/{id}")
    Call<MovieDetailsResponse> getMovieDetails(
            @Path("id") int id,
            @Query("api_key") String apiKey,
            @Query("append_to_response") String appendToResponse);


    @GET("3/person/{person_id}")
    Call<Person> getPeopleDetails(
            @Path("person_id") int personId,
            @Query("api_key") String apiKey,
            @Query("append_to_response") String appendToResponse
    );


    @GET("3/movie/upcoming")
    Call<MovieResponse> getUpcomingMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page,
            @Query("region") String region
    );

    @GET("3/tv/airing_today")
    Call<TvResponse> getOnAiringToday(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("3/tv/on_the_air")
    Call<TvResponse> getOnTheAir(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );


    @GET("3/tv/popular")
    Call<TvResponse> getPopularTvShow(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );



    @GET("3/tv/top_rated")
    Call<TvResponse> getTopRatedTvShow(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );




    @GET("3/tv/{id}")
    Call<TvShows> getTvShowDetails(
            @Path("id") int tvShowId,
            @Query("api_key") String apiKey,
            @Query("append_to_response") String appendToResponse
    );


    @GET("3/tv/{tvId}/season/{seasonNumber}")
    Call<Episodes> getSeasonDetails(
            @Path("tvId") int tvId,
            @Path("seasonNumber") int seasonNumber,
            @Query("api_key") String apiKey
    );


    @GET("3/tv/{tv_id}/similar")
    Call<TvResponse> getSimilarTvShows(
            @Path("tv_id") int tvId,
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET("3/authentication/token/new")
    Call<TokenRequest> getToken(
            @Query("api_key") String apiKey
    );

    @GET(".")
    Call<Void> authenticate();

    @GET("3/authentication/session/new")
    Call<SessionId> createSession(
            @Query("api_key") String apiKey,
            @Query("request_token") String requestToken
    );


    @GET("3/search/movie")
    Call<MovieResponse> getMovieSearchResult(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("query") String query
    );


    @GET("3/search/tv")
    Call<TvResponse> getTvSearchResult(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("query") String query
    );


    @GET("3/search/person")
    Call<PersonResponse> getPersonSearchResult(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("query") String query
    );













}
