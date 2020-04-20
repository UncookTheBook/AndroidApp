package com.uncookthebook.app;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.util.Objects;


public class ReportArticleFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment with the ProductGrid theme
        View view = inflater.inflate(R.layout.report_article_fragment, container, false);

        // Set up the toolbar
        setUpToolbar(view);
        showUserName(view);
        // Set cut corner background for API 23+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.findViewById(R.id.product_grid).setBackgroundResource(R.drawable.background_shape);
        }
        return view;
    }

    private void setUpToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }
        Context context = getContext();
        if (context != null) {
            toolbar.setNavigationOnClickListener(new NavigationIconClickListener(
                    context,
                    view.findViewById(R.id.product_grid),
                    new AccelerateDecelerateInterpolator(),
                    context.getResources().getDrawable(R.drawable.ic_menu), // Menu open icon
                    context.getResources().getDrawable(R.drawable.ic_close_menu)));
        }
    }

    private void showUserName(View view) {
        Toolbar topBar = view.findViewById(R.id.app_bar);
        topBar.setTitle(String.format(getString(R.string.welcome), getUserName()));
    }

    private String getUserName(){
        return ((GoogleActivity) Objects.requireNonNull(getActivity())).getGoogleAccount().getGivenName();
    }
}