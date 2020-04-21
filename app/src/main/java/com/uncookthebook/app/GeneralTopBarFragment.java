package com.uncookthebook.app;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.util.Objects;


public class GeneralTopBarFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    protected void layoutSetup(View view) {
        ConstraintLayout content = view.findViewById(R.id.content);
        Context context = Objects.requireNonNull(getContext());
        NavigationIconClickListener nav = new NavigationIconClickListener(
                context,
                content,
                new AccelerateDecelerateInterpolator(),
                context.getResources().getDrawable(R.drawable.ic_menu), // Menu open icon
                context.getResources().getDrawable(R.drawable.ic_close_menu));

        setUpToolbar(view, nav);
        showUserName(view);
        closeMenuOnContentClick(view, content, nav);
        setCutCorner(view);
    }

    private void setCutCorner(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.findViewById(R.id.content).setBackgroundResource(R.drawable.background_shape);
        }
    }

    private void closeMenuOnContentClick(View view, ConstraintLayout content, NavigationIconClickListener nav) {
        content.setOnClickListener(v -> {
            if(nav.isBackdropShown()) {
                nav.closeMenu(view);
            }
        });
    }

    private void setUpToolbar(View view, NavigationIconClickListener nav) {
        Toolbar toolbar = view.findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }
        Context context = getContext();
        if (context != null) {
            toolbar.setNavigationOnClickListener(nav);
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