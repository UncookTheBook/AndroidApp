package com.uncookthebook.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;

import java.util.Objects;


public class PasteArticleFragment extends GeneralTopBarFragment {
    private EditText editText;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment with the ProductGrid theme
        View view = inflater.inflate(R.layout.fragment_paste_article, container, false);
        layoutSetup(view);
        editTextSetup(view);
        view.findViewById(R.id.search_button).setOnClickListener(this);
        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void editTextSetup(View view) {
        editText = view.findViewById(R.id.textEdit);
        //Since edit text does override onClick and does not call it, I have to specify it manually
        editText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP && !v.hasFocus()) {
                v.performClick();
            }
            return false;
        });
        editText.setOnClickListener(this);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (! Utils.isURL(editText.getText().toString())) {
                    editText.setError("Invalid URL");
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // we do it here so that we are sure the google account has been set
        ((ReportSender) Objects.requireNonNull(getActivity())).submitReport();
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
                    new ReportArticleFragment(), false, getString(R.string.report_article_tag)
            );
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.textEdit){
            closeMenuOnElementClick();
        }
        if(v.getId() == R.id.search_button && Utils.isURL(editText.getText().toString())){
            //I could have used bundles but since I already use SharedPreferences I am gonna reuse them
            Activity activity = Objects.requireNonNull(getActivity());
            Utils.setURL(activity, editText.getText().toString());
            ((NavigationHost) activity).navigateTo(
                    new ReportArticleFragment(),
                    true,
                    getString(R.string.report_article_tag)
            );
        }
    }

}