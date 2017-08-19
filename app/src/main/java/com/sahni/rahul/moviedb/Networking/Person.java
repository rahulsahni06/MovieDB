package com.sahni.rahul.moviedb.Networking;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.sahni.rahul.moviedb.models.Images;
import com.sahni.rahul.moviedb.models.Movie;
import com.sahni.rahul.moviedb.models.TvShows;

import java.util.ArrayList;

/**
 * Created by sahni on 24-Jul-17.
 */

public class Person implements Parcelable {

    private int id;

    private String birthday;

    private String biography;

    private String name;

    @SerializedName("character")
    private String characterName;

    private String job;

//    @SerializedName("also_known_as")
//    ArrayList<String> alsoKnownAs;

    @SerializedName("place_of_birth")
    private String placeOfBirth;

    @SerializedName("profile_path")
    private String profilePath;

    @SerializedName("movie_credits")
    public MovieCreditsClass movieCreditsClass;

    @SerializedName("tv_credits")
    public TvShowCreditsClass tvShowCreditsClass;

    @SerializedName("images")
    public Images images;






    //Parcelable code
    protected Person(Parcel in) {
        id = in.readInt();
        birthday = in.readString();
        biography = in.readString();
        name = in.readString();
//        alsoKnownAs = in.createStringArrayList();
        placeOfBirth = in.readString();
        profilePath = in.readString();
        movieCreditsClass = (MovieCreditsClass) in.readValue(MovieCreditsClass.class.getClassLoader());
        tvShowCreditsClass = (TvShowCreditsClass) in.readValue(TvShowCreditsClass.class.getClassLoader());
        images = (Images) in.readValue(Images.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(birthday);
        dest.writeString(biography);
        dest.writeString(name);
//        dest.writeStringList(alsoKnownAs);
        dest.writeString(placeOfBirth);
        dest.writeString(profilePath);
        dest.writeValue(movieCreditsClass);
        dest.writeValue(tvShowCreditsClass);
        dest.writeValue(images);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };







    //Getters and setters

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public ArrayList<String> getAlsoKnownAs() {
//        return alsoKnownAs;
//    }

//    public void setAlsoKnownAs(ArrayList<String> alsoKnownAs) {
//        this.alsoKnownAs = alsoKnownAs;
//    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }


    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }







    public static class MovieCreditsClass implements Parcelable{

        @SerializedName("cast")
        ArrayList<Movie> movieArrayList;





        //Parcelable code

        protected MovieCreditsClass(Parcel in) {
            movieArrayList = in.createTypedArrayList(Movie.CREATOR);
        }

        public static final Creator<MovieCreditsClass> CREATOR = new Creator<MovieCreditsClass>() {
            @Override
            public MovieCreditsClass createFromParcel(Parcel in) {
                return new MovieCreditsClass(in);
            }

            @Override
            public MovieCreditsClass[] newArray(int size) {
                return new MovieCreditsClass[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(movieArrayList);
        }





        //Getters and setters

        public ArrayList<Movie> getMovieArrayList() {
            return movieArrayList;
        }

        public void setMovieArrayList(ArrayList<Movie> movieArrayList) {
            this.movieArrayList = movieArrayList;
        }


    }




    public static class TvShowCreditsClass implements Parcelable{

        @SerializedName("cast")
        ArrayList<TvShows> tvShowsArrayList;




        //Getters and setters
        public ArrayList<TvShows> getTvShowsArrayList() {
            return tvShowsArrayList;
        }

        public void setTvShowsArrayList(ArrayList<TvShows> tvShowsArrayList) {
            this.tvShowsArrayList = tvShowsArrayList;
        }







        //Parcelable code

        protected TvShowCreditsClass(Parcel source) {
            tvShowsArrayList = source.createTypedArrayList(TvShows.CREATOR);
        }

        public static final Creator<TvShowCreditsClass> CREATOR = new Creator<TvShowCreditsClass>() {
            @Override
            public TvShowCreditsClass createFromParcel(Parcel source) {
                return new TvShowCreditsClass(source);
            }

            @Override
            public TvShowCreditsClass[] newArray(int size) {
                return new TvShowCreditsClass[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(tvShowsArrayList);

        }
    }
}
