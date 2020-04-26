package com.uncookthebook.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;


public class ReportArticleFragment extends Fragment {

    private static final String TAG = "ReportArticleFragment";

    private String url;
    private ZoomOnClick animationHandler = new ZoomOnClick();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment with the ProductGrid theme
        View view = inflater.inflate(R.layout.fragment_report_article, container, false);
        voteButtonSetup(view, R.id.button_legit);
        voteButtonSetup(view, R.id.button_fake);
        homeButtonSetup(view);
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

    private void voteButtonSetup(View view, int id){
        MaterialButton button = view.findViewById(id);
        animationHandler.addView(button);
        button.setOnClickListener(v -> {
            animationHandler.handleSize(v, getContext());
        });
    }

    private void homeButtonSetup(View view){
        MaterialButton button = view.findViewById(R.id.button_home);
        button.setOnClickListener(v -> {
            ((NavigationHost) Objects.requireNonNull(getActivity())).navigateTo(
                    new PasteArticleFragment(), false, getString(R.string.paste_article_tag)
            );
        });
    }
}