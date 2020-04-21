package com.uncookthebook.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import java.util.Objects;


public class PasteArticleFragment extends GeneralTopBarFragment {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment with the ProductGrid theme
        View view = inflater.inflate(R.layout.fragment_paste_article, container, false);
        EditText editText = view.findViewById(R.id.textEdit);
        //Since edit text does override onClick and does not call it, I have to specify it manually
        editText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP && !v.hasFocus()) {
                v.performClick();
            }
            return false;
        });
        layoutSetup(view, new ArrayList<>(Collections.singletonList(editText)));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        skipIfNeeded();
    }

    private void skipIfNeeded() {
        final SharedPreferences sharedPref = Objects.requireNonNull(getContext()).getSharedPreferences(
                getString(R.string.preference_file_key),
                Context.MODE_PRIVATE
        );
        final boolean skip = sharedPref.getBoolean(getString(R.string.skip_key), false);
        if(skip) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.remove(getString(R.string.skip_key));
            editor.apply();
            ((NavigationHost) Objects.requireNonNull(getActivity())).navigateTo(
                    new ReportArticleFragment(), false
            );
        }
    }

}