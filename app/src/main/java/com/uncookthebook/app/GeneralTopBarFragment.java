package com.uncookthebook.app;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;


public abstract class GeneralTopBarFragment extends Fragment implements View.OnClickListener {
    private NavigationIconClickListener nav;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    protected void layoutSetup(View view) {
        this.view = view;
        ConstraintLayout content = view.findViewById(R.id.content);
        Context context = Objects.requireNonNull(getContext());
        nav = new NavigationIconClickListener(
                context,
                content,
                new AccelerateDecelerateInterpolator(),
                context.getResources().getDrawable(R.drawable.ic_menu), // Menu open icon
                context.getResources().getDrawable(R.drawable.ic_close_menu));

        setUpToolbar();
        showUserName();
        setCutCorner();
        content.setOnClickListener(v -> closeMenuOnElementClick());
    }

    private void setCutCorner() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.findViewById(R.id.content).setBackgroundResource(R.drawable.background_shape);
        }
    }

    protected void closeMenuOnElementClick() {
        if(nav.isBackdropShown()) {
            nav.closeMenu(view);
        }
    }

    private void setUpToolbar() {
        Toolbar toolbar = view.findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }
        Context context = getContext();
        if (context != null) {
            toolbar.setNavigationOnClickListener(nav);
        }
        setupToolbarButtons();
    }

    private void showUserName() {
        Toolbar topBar = view.findViewById(R.id.app_bar);
        topBar.setTitle(String.format(getString(R.string.welcome), getUserName()));
    }

    private String getUserName(){
        return ((GoogleActivity) Objects.requireNonNull(getActivity())).getGoogleAccount().getGivenName();
    }

    private void setupToolbarButtons(){
        FragmentManager fragmentManager = Objects.requireNonNull(getFragmentManager());
        Activity activity = Objects.requireNonNull(getActivity());

        MaterialButton homeButton = view.findViewById(R.id.button_home);
        MaterialButton leaderboardButton = view.findViewById(R.id.button_leadearboard);
        MaterialButton logout = view.findViewById(R.id.button_logout);

        homeButton.setOnClickListener(v -> {
            PasteArticleFragment pasteArticleFragment = (PasteArticleFragment) fragmentManager.findFragmentByTag(getString(R.string.paste_article_tag));
            //check if paste article fragment is not the current visible one
            if (pasteArticleFragment == null || !pasteArticleFragment.isVisible()) {
                //then we load it
                ((NavigationHost) activity).navigateTo(new PasteArticleFragment(), true, getString(R.string.paste_article_tag));
            }
        });

        leaderboardButton.setOnClickListener(v -> {
            LeaderboardFragment leaderboardFragment = (LeaderboardFragment) fragmentManager.findFragmentByTag(getString(R.string.leaderboard_tag));
            if (leaderboardFragment == null || !leaderboardFragment.isVisible()) {
                ((NavigationHost) activity).navigateTo(new LeaderboardFragment(), true, getString(R.string.leaderboard_tag));
            }
        });

        logout.setOnClickListener(v -> {
            ((GoogleActivity) activity).getGoogleClient().signOut()
                    .addOnCompleteListener(activity, task -> {
                        //we clear all the previous fragments
                        for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                            fragmentManager.popBackStack();
                        }
                        LoginFragment loginFragment = (LoginFragment) fragmentManager.findFragmentByTag(getString(R.string.login_tag));
                        if (loginFragment == null || !loginFragment.isVisible()) {
                            ((NavigationHost) activity).navigateTo(new LoginFragment(), false, getString(R.string.login_tag));
                        }
                    });
        });
    }

    @Override
    public abstract void onClick(View v);
}