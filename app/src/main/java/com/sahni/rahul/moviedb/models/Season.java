package com.sahni.rahul.moviedb.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sahni on 29-Jul-17.
 */

public class Season implements Parcelable {

    @SerializedName("air_date")
    private String airDate;

    @SerializedName("episode_count")
    private String noOfEpisodes;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("season_number")
    private int seasonNumber;

    private  int id;

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public String getNoOfEpisodes() {
        return noOfEpisodes;
    }

    public void setNoOfEpisodes(String noOfEpisodes) {
        this.noOfEpisodes = noOfEpisodes;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    protected Season(Parcel source){
        airDate = source.readString();
        noOfEpisodes = source.readString();
        posterPath = source.readString();
        seasonNumber = source.readInt();
        id = source.readInt();
    }


    public static final Creator<Season> CREATOR = new Creator<Season>() {
        @Override
        public Season createFromParcel(Parcel source) {
            return new Season(source);
        }

        @Override
        public Season[] newArray(int size) {
            return new Season[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(airDate);
        dest.writeString(noOfEpisodes);
        dest.writeString(posterPath);
        dest.writeInt(seasonNumber);
        dest.writeInt(id);
    }
}
