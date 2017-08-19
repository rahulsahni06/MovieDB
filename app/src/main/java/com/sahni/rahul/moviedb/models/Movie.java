package com.sahni.rahul.moviedb.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sahni on 19-Jul-17.
 */

public class Movie implements Parcelable{

    private int id;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("title")
    private String title;

    @SerializedName("vote_average")
    float vote;

    @SerializedName("release_date")
    String date;


    public Movie(int id, String posterPath, String title, float vote, String date) {
        this.id = id;
        this.posterPath = posterPath;
        this.title = title;
        this.vote = vote;
        this.date = date;
    }

    protected Movie(Parcel in) {
        id = in.readInt();
        posterPath = in.readString();
        title = in.readString();
        vote = in.readFloat();
        date = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public float getVote() {
        return vote;
    }

    public String getDate() {
        return date;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        dest.st

        dest.writeInt(id);
        dest.writeString(posterPath);
        dest.writeString(title);
        dest.writeFloat(vote);
        dest.writeString(date);
    }
}
