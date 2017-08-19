package com.sahni.rahul.moviedb.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;
import static com.sahni.rahul.moviedb.helpers.SharedPrefConstants.LOGGED_IN;

/**
 * Created by sahni on 31-Jul-17.
 */

public class AuthenticationUtils {


    public static boolean checkIfUserIsLoggedIn(Context context) {


        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefConstants.SHARED_PREF_LOGIN_NAME, MODE_PRIVATE);
        int status = sharedPreferences.getInt(SharedPrefConstants.LOGGED_IN_STATUS_KEY, SharedPrefConstants.LOGGED_OUT);
        return status == LOGGED_IN;



    }
}
