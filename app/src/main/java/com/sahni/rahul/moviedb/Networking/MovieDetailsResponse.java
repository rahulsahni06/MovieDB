package com.sahni.rahul.moviedb.Networking;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.sahni.rahul.moviedb.models.Credits;
import com.sahni.rahul.moviedb.models.Genre;
import com.sahni.rahul.moviedb.models.Images;
import com.sahni.rahul.moviedb.models.Movie;
import com.sahni.rahul.moviedb.models.Videos;

import java.util.ArrayList;

/**
 * Created by sahni on 19-Jul-17.
 */

public class MovieDetailsResponse implements Parcelable{



    @SerializedName("genres")
    private ArrayList<Genre> genreArrayList;

    private int id;

    @SerializedName("title")
    private String title;

    private String overview;

    @SerializedName("poster_path")
    private String posterPath;

    private int runtime;

    public Images images;

    @SerializedName("original_language")
    private String language;

    @SerializedName("release_dates")
    public ReleaseDatesResult releaseDatesResult;

    @SerializedName("credits")
    public Credits credits;

    @SerializedName("reviews")
    public ReviewsClass reviewsClass;

    @SerializedName("videos")
    public Videos videos;

    @SerializedName("similar")
    public similarMovieClass similarMovieClass;





    //Parcelable code
    protected MovieDetailsResponse(Parcel in) {
        genreArrayList = in.createTypedArrayList(Genre.CREATOR);
        id = in.readInt();
        title = in.readString();
        overview = in.readString();
        posterPath = in.readString();
        runtime = in.readInt();
        images = in.readParcelable(Images.class.getClassLoader());
        language = in.readString();
        releaseDatesResult = in.readParcelable(ReleaseDatesResult.class.getClassLoader());
        credits = in.readParcelable(Credits.class.getClassLoader());
        reviewsClass = in.readParcelable(ReviewsClass.class.getClassLoader());
        videos = in.readParcelable(Videos.class.getClassLoader());
        similarMovieClass = in.readParcelable(MovieDetailsResponse.similarMovieClass.class.getClassLoader());
    }

    public static final Creator<MovieDetailsResponse> CREATOR = new Creator<MovieDetailsResponse>() {
        @Override
        public MovieDetailsResponse createFromParcel(Parcel in) {
            return new MovieDetailsResponse(in);
        }

        @Override
        public MovieDetailsResponse[] newArray(int size) {
            return new MovieDetailsResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(genreArrayList);
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(posterPath);
        dest.writeInt(runtime);
        dest.writeParcelable(images, flags);
        dest.writeString(language);
        dest.writeParcelable(releaseDatesResult, flags);
        dest.writeParcelable(credits, flags);
        dest.writeParcelable(reviewsClass, flags);
        dest.writeParcelable(videos, flags);
        dest.writeParcelable(similarMovieClass, flags);
    }


    //Class
    public static class ReleaseDatesResult implements Parcelable{

        private ArrayList<Results> results;




        //Getters and setters
        public ArrayList<Results> getResults() {
            return results;
        }

        public void setResults(ArrayList<Results> results) {
            this.results = results;
        }





        //Parcelable code
        protected ReleaseDatesResult(Parcel in) {
            results = in.createTypedArrayList(Results.CREATOR);
        }

        public static final Creator<ReleaseDatesResult> CREATOR = new Creator<ReleaseDatesResult>() {
            @Override
            public ReleaseDatesResult createFromParcel(Parcel in) {
                return new ReleaseDatesResult(in);
            }

            @Override
            public ReleaseDatesResult[] newArray(int size) {
                return new ReleaseDatesResult[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(results);
        }





        //Class
        public static class Results implements Parcelable{

            @SerializedName("iso_3166_1")
            String countryCode;

            @SerializedName("release_dates")
            ArrayList<FinalReleaseDate> finalReleaseDates;



            //Getters and setters
            public String getCountryCode() {
                return countryCode;
            }

            public void setCountryCode(String countryCode) {
                this.countryCode = countryCode;
            }

            public ArrayList<FinalReleaseDate> getFinalReleaseDates() {
                return finalReleaseDates;
            }

            public void setFinalReleaseDates(ArrayList<FinalReleaseDate> FinalReleaseDates) {
                this.finalReleaseDates = FinalReleaseDates;
            }





            //Parcelable code
            protected Results(Parcel in) {
                countryCode = in.readString();
                finalReleaseDates = in.createTypedArrayList(FinalReleaseDate.CREATOR);
            }

            public static final Creator<Results> CREATOR = new Creator<Results>() {
                @Override
                public Results createFromParcel(Parcel in) {
                    return new Results(in);
                }

                @Override
                public Results[] newArray(int size) {
                    return new Results[size];
                }
            };


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(countryCode);
                dest.writeTypedList(finalReleaseDates);
            }







            //Class
            public  static class FinalReleaseDate implements Parcelable{

                String certification;

                @SerializedName("release_date")
                String actualReleaseDate;




                //Getters and setters
                public String getCertification() {
                    return certification;
                }

                public void setCertification(String certification) {
                    this.certification = certification;
                }

                public String getActualReleaseDate() {
                    return actualReleaseDate;
                }

                public void setActualReleaseDate(String actualReleaseDate) {
                    this.actualReleaseDate = actualReleaseDate;
                }




                //Parcelable code
                protected FinalReleaseDate(Parcel source){
                    certification = source.readString();
                    actualReleaseDate = source.readString();
                }

                public static final Creator<FinalReleaseDate> CREATOR  = new Creator<FinalReleaseDate>() {
                    @Override
                    public FinalReleaseDate createFromParcel(Parcel source) {
                        return new FinalReleaseDate(source);
                    }

                    @Override
                    public FinalReleaseDate[] newArray(int size) {
                        return new FinalReleaseDate[size];
                    }
                };

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(certification);
                    dest.writeString(actualReleaseDate);
                }
            }

        }


    }



    //Class
    public static class ReviewsClass implements Parcelable{

        @SerializedName("results")
        private ArrayList<ReviewClassResult> reviewClassResultArrayList;





        //Getters and setters
        public ArrayList<ReviewClassResult> getReviewClassResultArrayList() {
            return reviewClassResultArrayList;
        }

        public void setReviewClassResultArrayList(ArrayList<ReviewClassResult> reviewClassResultArrayList) {
            this.reviewClassResultArrayList = reviewClassResultArrayList;
        }





        //Parcelable code
        protected ReviewsClass(Parcel in) {
            reviewClassResultArrayList = in.createTypedArrayList(ReviewClassResult.CREATOR);
        }

        public static final Creator<ReviewsClass> CREATOR = new Creator<ReviewsClass>() {
            @Override
            public ReviewsClass createFromParcel(Parcel in) {
                return new ReviewsClass(in);
            }

            @Override
            public ReviewsClass[] newArray(int size) {
                return new ReviewsClass[size];
            }
        };


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(reviewClassResultArrayList);
        }


        //Class
        public static class ReviewClassResult implements Parcelable{

            private String author;

            private String content;

            @SerializedName("url")
            String reviewUrl;




            //Getters and setters
            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getReviewUrl() {
                return reviewUrl;
            }

            public void setReviewUrl(String reviewUrl) {
                this.reviewUrl = reviewUrl;
            }





            //Parcelable code
            protected ReviewClassResult(Parcel source){
                author = source.readString();
                content = source.readString();
                reviewUrl = source.readString();
            }

            public static final Creator<ReviewClassResult> CREATOR = new Creator<ReviewClassResult>() {
                @Override
                public ReviewClassResult createFromParcel(Parcel source) {
                    return new ReviewClassResult(source);
                }

                @Override
                public ReviewClassResult[] newArray(int size) {
                    return new ReviewClassResult[size];
                }
            };

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(author);
                dest.writeString(content);
                dest.writeString(reviewUrl);
            }
        }

    }



    //Class
    public static class similarMovieClass implements Parcelable{

        @SerializedName("results")
        private ArrayList<Movie> movieArrayList;




        //Getters and setters
        public ArrayList<Movie> getMovieArrayList() {
            return movieArrayList;
        }

        public void setMovieArrayList(ArrayList<Movie> movieArrayList) {
            this.movieArrayList = movieArrayList;
        }





        //Parcelable
        protected similarMovieClass(Parcel in) {
            movieArrayList = in.createTypedArrayList(Movie.CREATOR);
        }

        public static final Creator<similarMovieClass> CREATOR = new Creator<similarMovieClass>() {
            @Override
            public similarMovieClass createFromParcel(Parcel in) {
                return new similarMovieClass(in);
            }

            @Override
            public similarMovieClass[] newArray(int size) {
                return new similarMovieClass[size];
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
    }






    //Getters and setters
    public ArrayList<Genre> getGenreArrayList() {
        return genreArrayList;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }


    public void setGenreArrayList(ArrayList<Genre> genreArrayList) {
        this.genreArrayList = genreArrayList;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
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
}
