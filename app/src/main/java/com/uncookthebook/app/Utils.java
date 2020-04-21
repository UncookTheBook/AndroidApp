package com.uncookthebook.app;


import androidx.core.util.PatternsCompat;

public class Utils {

    public static boolean isURL(String url){
        return  PatternsCompat.WEB_URL.matcher(url).matches();
    }
}
