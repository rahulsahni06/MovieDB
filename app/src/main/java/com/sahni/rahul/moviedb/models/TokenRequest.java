package com.sahni.rahul.moviedb.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sahni on 31-Jul-17.
 */

public class TokenRequest {

    @SerializedName("request_token")
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
