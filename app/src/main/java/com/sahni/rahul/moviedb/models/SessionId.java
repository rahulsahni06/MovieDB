package com.sahni.rahul.moviedb.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sahni on 31-Jul-17.
 */

public class SessionId {

    @SerializedName("session_id")
    private String sessionId;


    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
