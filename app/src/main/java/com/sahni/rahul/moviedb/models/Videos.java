package com.sahni.rahul.moviedb.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sahni on 28-Jul-17.
 */

public class Videos implements Parcelable{


    @SerializedName("results")
    private ArrayList<VideoResult> videoResultList;




    //Getters and setters

    public ArrayList<VideoResult> getVideoResultList() {
        return videoResultList;
    }

    public void setVideoResultList(ArrayList<VideoResult> videoResultList) {
        this.videoResultList = videoResultList;
    }





    //Parcelable code

    protected Videos(Parcel source){
        videoResultList = source.createTypedArrayList(VideoResult.CREATOR);
    }

    public static final Creator<Videos> CREATOR = new Creator<Videos>() {
        @Override
        public Videos createFromParcel(Parcel source) {
            return new Videos(source);
        }

        @Override
        public Videos[] newArray(int size) {
            return new Videos[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(videoResultList);
    }

    public static class VideoResult implements Parcelable{

        private String name;

        @SerializedName("key")
        private String videoKey;




        //Getter and setter

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVideoKey() {
            return videoKey;
        }

        public void setVideoKey(String videoKey) {
            this.videoKey = videoKey;
        }




        //Parcelable code

        protected VideoResult(Parcel source){
            name = source.readString();
            videoKey = source.readString();
        }

        public static final Creator<VideoResult> CREATOR = new Creator<VideoResult>() {
            @Override
            public VideoResult createFromParcel(Parcel source) {
                return new VideoResult(source);
            }

            @Override
            public VideoResult[] newArray(int size) {
                return new VideoResult[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(videoKey);
        }
    }
}
