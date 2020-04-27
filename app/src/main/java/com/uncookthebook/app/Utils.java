package com.uncookthebook.app;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.core.util.PatternsCompat;

public class Utils {

    public static boolean isURL(String url){
        return  PatternsCompat.WEB_URL.matcher(url).matches();
    }

    @SuppressLint("ApplySharedPref")
    public static void setURL(Activity activity, String url){
        SharedPreferences sharedPref = activity.getApplicationContext().getSharedPreferences(
                activity.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(activity.getString(R.string.skip_key), true);
        editor.putString(activity.getString(R.string.url_key), url);
        editor.commit();
    }

    public static void clearSharedPrefs(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE
        );
        sharedPref.edit().clear().apply();
    }
}
