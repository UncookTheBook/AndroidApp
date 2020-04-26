package com.uncookthebook.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import lombok.SneakyThrows;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ReportArticleFragment extends Fragment {

    private static final String TAG = "ReportArticleFragment";

    private URL url;
    private ZoomOnClick animationHandler = new ZoomOnClick();
    private View view;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment with the ProductGrid theme
        view = inflater.inflate(R.layout.fragment_report_article, container, false);
        voteButtonSetup(R.id.button_legit);
        voteButtonSetup(R.id.button_fake);
        homeButtonSetup();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getURL();
        setSiteLogo(url.getProtocol() + "://" + url.getHost() + "/favicon.ico");
        setSiteTitle();
    }

    @SneakyThrows
    private void getURL() {
        final SharedPreferences sharedPref = Objects.requireNonNull(getContext()).getSharedPreferences(
                getString(R.string.preference_file_key),
                Context.MODE_PRIVATE
        );
        String urlString = sharedPref.getString(getString(R.string.url_key), null);
        if(urlString != null) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.remove(getString(R.string.url_key));
            editor.apply();
            Log.d("URL", urlString);
            this.url = new URL(urlString);
        }
    }

    private void voteButtonSetup(int id){
        MaterialButton button = view.findViewById(id);
        animationHandler.addView(button);
        button.setOnClickListener(v -> {
            animationHandler.handleSize(v, getContext());
        });
    }

    private void homeButtonSetup(){
        MaterialButton button = view.findViewById(R.id.button_home);
        button.setOnClickListener(v -> {
            ((NavigationHost) Objects.requireNonNull(getActivity())).navigateTo(
                    new PasteArticleFragment(), false
            );
        });
    }

    private void setSiteLogo(String src) {
        final String TAG = "SITE_LOGO_RETRIEVAL";
        final OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(src).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, e.toString());
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (response.isSuccessful() && response.body() != null){
                    final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                    new Handler(Looper.getMainLooper()).post(() -> {
                        view.findViewById(R.id.image_progress_loader).setVisibility(View.INVISIBLE);;
                        ImageView imageView = view.findViewById(R.id.siteLogo);
                        imageView.setImageBitmap(bitmap);
                    });
                }else {
                    Log.e(TAG, response.toString());
                }
            }
        });
    }

    private void setSiteTitle(){
        TextView textView = view.findViewById(R.id.siteName);
        textView.setText(url.getHost());
    }
}