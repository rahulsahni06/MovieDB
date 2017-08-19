package com.sahni.rahul.moviedb.helpers;

/**
 * Created by sahni on 23-Jul-17.
 */

public class DateConverter {

//    public static String getDate(String stringDate){
//
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//        SimpleDateFormat newFormatter = new SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault());
//        try {
//            if(stringDate != null){
//                if(stringDate.equals("")){
//                    return "N/A";
//                }
//                Date date = formatter.parse(stringDate);
//                return newFormatter.format(date);
//            }
//            else{
//                return "N/A";
//            }
//
//        }
//        catch (ParseException e) {
//            e.printStackTrace();
//            return stringDate;
//        }
//    }

//    public static String getDate(String stringDate){
//
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//        SimpleDateFormat newFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
//        try {
//            if(stringDate != null){
//                if(stringDate.equals("")){
//                    return "N/A";
//                }
//                Date date = formatter.parse(stringDate);
//                Calendar calendar = Calendar.getInstance();
//                int currentYear = calendar.get(Calendar.YEAR);
//                calendar.setTime(date);
//                int oldYear = calendar.get(Calendar.YEAR);
//                String ageInNumber = ""+ (currentYear-oldYear);
//
//
//                return newFormatter.format(date)+";"+ageInNumber;
//            }
//            else{
//                return "N/A";
//            }
//
//        }
//        catch (ParseException e) {
//            e.printStackTrace();
//            return stringDate;
//        }
//    }
//
//    public static String getReleaseYear(String stringDate){
//
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//        SimpleDateFormat newFormatter = new SimpleDateFormat("yyyy", Locale.getDefault());
//        try {
//            if(stringDate != null){
//                if(stringDate.equals("")){
//                    return "N/A";
//                }
//                Date date = formatter.parse(stringDate);
//                return newFormatter.format(date);
//            }
//            else{
//                return "N/A";
//            }
//
//        }
//        catch (ParseException e) {
//            e.printStackTrace();
//            return stringDate;
//        }
//    }


}
