package com.sahni.rahul.moviedb.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.sahni.rahul.moviedb.Networking.Person;

import java.util.ArrayList;

/**
 * Created by sahni on 26-Jul-17.
 */

public class TvShows implements Parcelable {

    private String name;

    private int id;

    @SerializedName("vote_average")
    private float rating;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("first_air_date")
    private String firstAirDate;

    @SerializedName("last_air_date")
    private String lastAirDate;

    @SerializedName("original_language")
    private String originalLanguage;

    private String overview;

    @SerializedName("created_by")
    private ArrayList<Person> createdByList;

    @SerializedName("episode_run_time")
    private ArrayList<Integer> runtime;

    private Images images;

    @SerializedName("genres")
    private ArrayList<Genre> genresList;

    @SerializedName("content_ratings")
    private ContentCertification contentCertification;


    private Credits credits;

    private Videos videos;


    private ArrayList<Season> seasons;

    @SerializedName("similar")
    private SimilarTvShows similarTvShows;


    protected TvShows(Parcel in) {
        name = in.readString();
        id = in.readInt();
        rating = in.readFloat();
        posterPath = in.readString();
        firstAirDate = in.readString();
        lastAirDate = in.readString();
        originalLanguage = in.readString();
        overview = in.readString();
//        createdByList = in.readArrayList(Person.class.getClassLoader());
        createdByList = in.createTypedArrayList(Person.CREATOR);
        runtime = in.readArrayList(Integer.class.getClassLoader());
        images = (Images) in.readValue(Images.class.getClassLoader());
//        genresList = in.readArrayList(Genre.class.getClassLoader());
        genresList = in.createTypedArrayList(Genre.CREATOR);
        contentCertification = (ContentCertification) in.readValue(ContentCertification.class.getClassLoader());
        credits = (Credits) in.readValue(Credits.class.getClassLoader());
        videos = (Videos) in.readValue(Videos.class.getClassLoader());
//        seasons = in.readArrayList(Season.class.getClassLoader());
        seasons = in.createTypedArrayList(Season.CREATOR);
        similarTvShows = (SimilarTvShows) in.readValue(SimilarTvShows.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeFloat(rating);
        dest.writeString(posterPath);
        dest.writeString(firstAirDate);
        dest.writeString(lastAirDate);
        dest.writeString(originalLanguage);
        dest.writeString(overview);
        dest.writeTypedList(createdByList);
        dest.writeList(runtime);
        dest.writeValue(images);
        dest.writeTypedList(genresList);
        dest.writeValue(contentCertification);
        dest.writeValue(credits);
        dest.writeValue(videos);
        dest.writeTypedList(seasons);
        dest.writeValue(similarTvShows);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TvShows> CREATOR = new Creator<TvShows>() {
        @Override
        public TvShows createFromParcel(Parcel in) {
            return new TvShows(in);
        }

        @Override
        public TvShows[] newArray(int size) {
            return new TvShows[size];
        }
    };

    public ArrayList<Genre> getGenresList() {
        return genresList;
    }

    public void setGenresList(ArrayList<Genre> genresList) {
        this.genresList = genresList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public ArrayList<Integer> getRuntime() {
        return runtime;
    }

    public void setRuntime(ArrayList<Integer> runtime) {
        this.runtime = runtime;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public ArrayList<Person> getCreatedByList() {
        return createdByList;
    }

    public void setCreatedByList(ArrayList<Person> createdByList) {
        this.createdByList = createdByList;
    }


    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }


    public ContentCertification getContentCertification() {
        return contentCertification;
    }

    public void setContentCertification(ContentCertification contentCertification) {
        this.contentCertification = contentCertification;
    }

    public Credits getCredits() {
        return credits;
    }

    public void setCredits(Credits credits) {
        this.credits = credits;
    }


    public Videos getVideos() {
        return videos;
    }

    public void setVideos(Videos videos) {
        this.videos = videos;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }


    public SimilarTvShows getSimilarTvShows() {
        return similarTvShows;
    }

    public void setSimilarTvShows(SimilarTvShows similarTvShows) {
        this.similarTvShows = similarTvShows;
    }

    public static class ContentCertification implements Parcelable {

        @SerializedName("results")
        private ArrayList<CertificationResult> certificationResultList;


        public ArrayList<CertificationResult> getCertificationResultList() {
            return certificationResultList;
        }

        public void setCertificationResultList(ArrayList<CertificationResult> certificationResultList) {
            this.certificationResultList = certificationResultList;
        }


        protected ContentCertification(Parcel source){
            certificationResultList = source.createTypedArrayList(CertificationResult.CREATOR);
        }

        public static final Creator<ContentCertification> CREATOR = new Creator<ContentCertification>() {
            @Override
            public ContentCertification createFromParcel(Parcel source) {
                return new ContentCertification(source);
            }

            @Override
            public ContentCertification[] newArray(int size) {
                return new ContentCertification[size];
            }
        };


        @Override
        public int describeContents() {
            return 0;
        }


        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(certificationResultList);
        }
    }


    public static class CertificationResult implements Parcelable {

        @SerializedName("iso_3166_1")
        private String countryCode;

        @SerializedName("rating")
        private String certification;


        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getCertification() {
            return certification;
        }

        public void setCertification(String certification) {
            this.certification = certification;
        }

        protected CertificationResult(Parcel source) {
            countryCode = source.readString();
            certification = source.readString();
        }


        public static final Creator<CertificationResult> CREATOR = new Creator<CertificationResult>() {
            @Override
            public CertificationResult createFromParcel(Parcel source) {
                return new CertificationResult(source);
            }

            @Override
            public CertificationResult[] newArray(int size) {
                return new CertificationResult[size];
            }
        };


        @Override
        public int describeContents() {
            return 0;
        }


        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(countryCode);
            dest.writeString(certification);
        }
    }


    public static class SimilarTvShows implements Parcelable {

        @SerializedName("results")
        ArrayList<TvShows> tvShowsArrayList;


        protected SimilarTvShows(Parcel in) {
            tvShowsArrayList = in.createTypedArrayList(TvShows.CREATOR);
        }

        public static final Creator<SimilarTvShows> CREATOR = new Creator<SimilarTvShows>() {
            @Override
            public SimilarTvShows createFromParcel(Parcel in) {
                return new SimilarTvShows(in);
            }

            @Override
            public SimilarTvShows[] newArray(int size) {
                return new SimilarTvShows[size];
            }
        };

        public ArrayList<TvShows> getTvShowsArrayList() {
            return tvShowsArrayList;
        }

        public void setTvShowsArrayList(ArrayList<TvShows> tvShowsArrayList) {
            this.tvShowsArrayList = tvShowsArrayList;
        }


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
