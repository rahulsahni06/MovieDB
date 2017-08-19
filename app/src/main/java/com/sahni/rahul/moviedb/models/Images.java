package com.sahni.rahul.moviedb.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sahni on 27-Jul-17.
 */

public class Images implements Parcelable {

    @SerializedName("backdrops")
    private ArrayList<Backdrops> backdropsList;

    @SerializedName("profiles")
    private ArrayList<Profiles> profilesArrayList;


    protected Images(Parcel in) {
        backdropsList = in.createTypedArrayList(Backdrops.CREATOR);
        profilesArrayList = in.createTypedArrayList(Profiles.CREATOR);
    }

    public static final Creator<Images> CREATOR = new Creator<Images>() {
        @Override
        public Images createFromParcel(Parcel in) {
            return new Images(in);
        }

        @Override
        public Images[] newArray(int size) {
            return new Images[size];
        }
    };

    public ArrayList<Backdrops> getBackdropsList() {
        return backdropsList;
    }

    public void setBackdropsList(ArrayList<Backdrops> backdropsList) {
        this.backdropsList = backdropsList;
    }


    public ArrayList<Profiles> getProfilesArrayList() {
        return profilesArrayList;
    }

    public void setProfilesArrayList(ArrayList<Profiles> profilesArrayList) {
        this.profilesArrayList = profilesArrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(backdropsList);
        dest.writeTypedList(profilesArrayList);
    }

    public static class Backdrops implements Parcelable{

        @SerializedName("file_path")
        private String filePath;


        protected Backdrops(Parcel in) {
            filePath = in.readString();
        }

        public static final Creator<Backdrops> CREATOR = new Creator<Backdrops>() {
            @Override
            public Backdrops createFromParcel(Parcel in) {
                return new Backdrops(in);
            }

            @Override
            public Backdrops[] newArray(int size) {
                return new Backdrops[size];
            }
        };

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(filePath);
        }
    }

    public static class Profiles implements Parcelable{

        @SerializedName("file_path")
        private String filePath;


        protected Profiles(Parcel in) {
            filePath = in.readString();
        }

        public static final Creator<Profiles> CREATOR = new Creator<Profiles>() {
            @Override
            public Profiles createFromParcel(Parcel in) {
                return new Profiles(in);
            }

            @Override
            public Profiles[] newArray(int size) {
                return new Profiles[size];
            }
        };

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(filePath);
        }
    }
}
