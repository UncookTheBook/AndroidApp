package com.uncookthebook.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class PasteArticleFragment extends GeneralTopBarFragment {

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment with the ProductGrid theme
        View view = inflater.inflate(R.layout.paste_article_fragment, container, false);
        layoutSetup(view);

        MaterialButton infoButton = view.findViewById(R.id.info_button);
        infoButton.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(getContext())
                    .setTitle(getString(R.string.info_title))
                    .setMessage(getString(R.string.info_paste_article))
                    .setPositiveButton(getString(R.string.info_accept), null)
                    .show();
        });
        return view;
    }

}