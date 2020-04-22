package com.uncookthebook.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
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


public class PasteArticleFragment extends GeneralTopBarFragment {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment with the ProductGrid theme
        View view = inflater.inflate(R.layout.fragment_paste_article, container, false);
        EditText searchButton = view.findViewById(R.id.textEdit);
        //Since edit text does override onClick and does not call it, I have to specify it manually
        searchButton.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP && !v.hasFocus()) {
                v.performClick();
            }
            return false;
        });
        layoutSetup(view, new ArrayList<>(Collections.singletonList(searchButton)));
        return view;
    }
}