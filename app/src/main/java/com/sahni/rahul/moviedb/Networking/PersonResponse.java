package com.sahni.rahul.moviedb.Networking;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sahni on 13-Aug-17.
 */

public class PersonResponse {

    @SerializedName("results")
    private ArrayList<Person> personArrayList;

    public ArrayList<Person> getPersonArrayList() {
        return personArrayList;
    }

    public void setPersonArrayList(ArrayList<Person> personArrayList) {
        this.personArrayList = personArrayList;
    }
}
