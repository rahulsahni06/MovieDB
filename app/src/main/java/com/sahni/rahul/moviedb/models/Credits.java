package com.sahni.rahul.moviedb.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.sahni.rahul.moviedb.Networking.Person;

import java.util.ArrayList;

/**
 * Created by sahni on 28-Jul-17.
 */

public class Credits implements Parcelable {


    @SerializedName("cast")
    private ArrayList<Person> castArrayList;

    @SerializedName("crew")
    private ArrayList<Person> crewArrayList;





   //Getter and setters
    public ArrayList<Person> getCastArrayList() {
        return castArrayList;
    }

    public void setCastArrayList(ArrayList<Person> castArrayList) {
        this.castArrayList = castArrayList;
    }

    public ArrayList<Person> getCrewArrayList() {
        return crewArrayList;
    }

    public void setCrewArrayList(ArrayList<Person> crewArrayList) {
        this.crewArrayList = crewArrayList;
    }






    //Parcelable code

    protected Credits(Parcel source){
        castArrayList = source.createTypedArrayList(Person.CREATOR);
        crewArrayList = source.createTypedArrayList(Person.CREATOR);
    }


    public static final Creator<Credits> CREATOR = new Creator<Credits>() {
        @Override
        public Credits createFromParcel(Parcel source) {
            return new Credits(source);
        }

        @Override
        public Credits[] newArray(int size) {
            return new Credits[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(castArrayList);
        dest.writeTypedList(crewArrayList);
    }
}
