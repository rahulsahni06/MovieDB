package com.sahni.rahul.moviedb.Networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sahni on 19-Jul-17.
 */

public class ApiClient {

    public final static String API_KEY ="e85fb281987ea0a887b79a828963222b";
    public final static String BASE_URL = "https://api.themoviedb.org/";
    public final static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w780";
    public final static String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w300";
    public final static String YOUTUBE_LINK = "https://www.youtube.com/watch?v=";
    public final static String GRAVATAR_LINK ="https://www.gravatar.com/avatar/";

    public final static int AUTHENTICATION_REQUEST_CODE = 100;
    public final static int AUTHENTICATION_SUCCESS_REQUEST_CODE = 101;
    public final static int AUTHENTICATION_FAILED_REQUEST_CODE = 102;

    public static ApiInterface apiInterface;


    public static ApiInterface getRetrofitClient(){
        if(apiInterface == null){
            Retrofit retrofit =new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiInterface = retrofit.create(ApiInterface.class);
        }
        return  apiInterface;
    }


//    public static Retrofit getRetrofitImageClient(String imageBaseUrl){
//
//        Retrofit retrofit =new Retrofit.Builder()
//                .baseUrl(imageBaseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        return  retrofit;
//    }
}
