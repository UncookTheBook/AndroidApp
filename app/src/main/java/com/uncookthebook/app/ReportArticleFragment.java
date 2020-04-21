package com.uncookthebook.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import java.util.Objects;


public class ReportArticleFragment extends GeneralTopBarFragment {
    private String url;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment with the ProductGrid theme
        View view = inflater.inflate(R.layout.fragment_report_article, container, false);

        layoutSetup(view, new ArrayList<>());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getURL();
    }

    private void getURL() {
        final SharedPreferences sharedPref = Objects.requireNonNull(getContext()).getSharedPreferences(
                getString(R.string.preference_file_key),
                Context.MODE_PRIVATE
        );
        url = sharedPref.getString(getString(R.string.url_key), null);
        if(url != null) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.remove(getString(R.string.url_key));
            editor.apply();
            Log.d("URL", url);
        }
    }
}