package com.sahni.rahul.moviedb.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sahni on 27-Jul-17.
 */

public class Genre implements Parcelable{

    private int id;

    private String name;


    protected Genre(Parcel source){
        id = source.readInt();
        name = source.readString();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static final Creator<Genre> CREATOR = new Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel source) {
            return new Genre(source);
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }
}
