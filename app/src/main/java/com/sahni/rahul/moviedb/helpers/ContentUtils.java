package com.sahni.rahul.moviedb.helpers;

import com.sahni.rahul.moviedb.Networking.Person;
import com.sahni.rahul.moviedb.models.Genre;
import com.sahni.rahul.moviedb.models.TvShows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by sahni on 27-Jul-17.
 */

public class ContentUtils {

    public static final String SEARCH_MOVIE = "search_movie";
    public static final String SEARCH_TV_SHOW = "search_tv_show";
    public static final String SEARCH_PERSON ="search_person";
    public static final String SEARCH_FOR = "search_type";
    public static final String country_code = "US";

    public static String getLanguageFromCode(String code){
        if(code != null){
            if(code.equals("")){
                return "Language N/A";
            }
            Locale locale = new Locale(code);
            String lang = locale.getDisplayName();
            return lang.substring(0,1).toUpperCase()+lang.substring(1);
        }
        else{
            return "Language N/A";
        }

    }

    public static String getProperRuntime(int min){
        if(min != 0){
            int hour = min/60;
            int minutes = min%60;
            if(hour == 0){
                return minutes+" minutes";
            }
            else if(minutes == 0){
                return hour+" hour";
            }
            return hour+" hour "+minutes+" minutes";

        }
        else{
            return "Runtime N/A";
        }

    }

    public static String getGenre(ArrayList<Genre> arrayList){
        if(arrayList != null) {

            if(arrayList.isEmpty()){
                return "Genre N/A";
            }
            String genre = "";
            for (int i = 0; i < arrayList.size() - 1; i++) {
                genre += arrayList.get(i).getName() + ", ";
            }
            if (arrayList.size() >= 1) {
                genre += arrayList.get(arrayList.size() - 1).getName();

            }
            return genre;
        }
        else{
            return "Genre N/A";
        }
    }

    public static String getCreatedBy(ArrayList<Person> createdByList){

        String createdBy = "";
        if (createdByList != null) {

            if (createdByList.isEmpty()) {
                return "N/A";
            }
            else {
                for (int i = 0; i < createdByList.size() - 1; i++) {
                    createdBy += createdByList.get(i).getName() + ", ";
                }
                if (createdByList.size() >= 1) {
                    createdBy += createdByList.get(createdByList.size() - 1).getName();
                }
                return createdBy;
            }
        }
        else{
            return "N/A";
        }


//        if(createdByList.isEmpty()){
//            return "N/A";
//        }
//        else{
//            for(int i = 0; i<createdByList.size()-1; i++){
//                createdBy += createdByList.get(i).getName()+", ";
//            }
//            if(createdByList.size()>=1){
//                createdBy +=createdByList.get(createdByList.size()-1).getName();
//
//            }
//            return createdBy;
//        }
    }


    public static String getTvShowCertification(String country_code, ArrayList<TvShows.CertificationResult> arrayList){
        if(arrayList != null){
            if(arrayList.isEmpty()){
                return "Certificate N/A";
            }
            else{

                for(TvShows.CertificationResult result : arrayList){
                    if(result.getCountryCode().equals(country_code)){
                        return result.getCertification();
                    }
                }

                return "Certificate N/A";
            }
        }
        else{
            return "Certificate N/A";
        }
    }


    public static String getDate(String stringDate){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat newFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        try {
            if(stringDate != null){
                if(stringDate.equals("")){
                    return "N/A";
                }
                Date date = formatter.parse(stringDate);
                Calendar calendar = Calendar.getInstance();
                int currentYear = calendar.get(Calendar.YEAR);
                calendar.setTime(date);
                int oldYear = calendar.get(Calendar.YEAR);
                String ageInNumber = ""+ (currentYear-oldYear);


                return newFormatter.format(date)+";"+ageInNumber;
            }
            else{
                return "N/A";
            }

        }
        catch (ParseException e) {
            e.printStackTrace();
            return stringDate;
        }
    }

    public static String getReleaseYear(String stringDate){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat newFormatter = new SimpleDateFormat("yyyy", Locale.getDefault());
        try {
            if(stringDate != null){
                if(stringDate.equals("")){
                    return "N/A";
                }
                Date date = formatter.parse(stringDate);
                return newFormatter.format(date);
            }
            else{
                return "N/A";
            }

        }
        catch (ParseException e) {
            e.printStackTrace();
            return stringDate;
        }
    }

}
