package com.sahni.rahul.moviedb.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sahni on 29-Jul-17.
 */

public class Episodes {

    @SerializedName("episodes")
    private ArrayList<SeasonEpisode> episodeArrayList;


    public ArrayList<SeasonEpisode> getEpisodeArrayList() {
        return episodeArrayList;
    }

    public void setEpisodeArrayList(ArrayList<SeasonEpisode> episodeArrayList) {
        this.episodeArrayList = episodeArrayList;
    }

    public class SeasonEpisode {

        private String name;

        private String overview;

        @SerializedName("still_path")
        private String imagePath;

        @SerializedName("air_date")
        private String airDate;

        @SerializedName("episode_number")
        private int episodeNumber;






        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public String getAirDate() {
            return airDate;
        }

        public void setAirDate(String airDate) {
            this.airDate = airDate;
        }

        public int getEpisodeNumber() {
            return episodeNumber;
        }

        public void setEpisodeNumber(int episodeNumber) {
            this.episodeNumber = episodeNumber;
        }




    }
}
