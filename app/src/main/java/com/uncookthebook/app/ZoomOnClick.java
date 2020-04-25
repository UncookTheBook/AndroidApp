package com.uncookthebook.app;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.view.View;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class handles the increase in size of views
 */
public class ZoomOnClick {
    private Map<View, AtomicBoolean> buttonToStatus = new HashMap<>();

    public void addView(View view){
        buttonToStatus.put(view, new AtomicBoolean(false));
    }

    public void handleSize(View target, Context context){
        AtomicBoolean clicked = buttonToStatus.get(target);
        if(clicked == null) {
            throw new IllegalArgumentException("You must first add the View with addView");
        }
        if(clicked.get()){
            //if it was clicked, we just reduce it's size
            reduceSize(target, context);
        }else {
            //if it hasn't been clicked, we increase it's size and set to standard size every other button
            increaseSize(target, context);
            for (View v: buttonToStatus.keySet()) {
                if (!v.equals(target)){
                    reduceSize(v, context);
                    Objects.requireNonNull(buttonToStatus.get(v)).set(false);
                }
            }
        }
        clicked.set(!clicked.get());
    }

    private void reduceSize(View v1, Context context){
        AnimatorSet decreaser = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.reduce_size);
        decreaser.setTarget(v1);
        decreaser.start();
    }

    private void increaseSize(View v1, Context context){
        AnimatorSet increaser = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.increase_size);
        increaser.setTarget(v1);
        increaser.start();
    }
}
