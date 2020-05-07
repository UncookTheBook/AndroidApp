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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.button.MaterialButton;
import com.uncookthebook.app.models.Article;
import com.uncookthebook.app.models.GetArticleRequest;
import com.uncookthebook.app.models.GetArticleResponse;
import com.uncookthebook.app.models.TokenizedObject;
import com.uncookthebook.app.network.APIService;
import com.uncookthebook.app.network.APIServiceUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.uncookthebook.app.Utils.*;


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
        retrieveArticleInformation();
        setSiteLogo(url.getProtocol() + "://" + url.getHost() + "/favicon.ico");
        setSiteTitle();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        APIServiceUtils.getOkHttpClient().dispatcher().cancelAll();
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
            this.url = new URL(urlString);
            clearSharedPrefs(getContext());
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
                    new PasteArticleFragment(), false, getString(R.string.paste_article_tag)
            );
        });
    }

    private void setSiteLogo(String src) {
        Log.d("SRC", src);
        final String TAG = "SITE_LOGO_RETRIEVAL";
        OkHttpClient okHttpClient = APIServiceUtils.getOkHttpClient();
        final Request request = new okhttp3.Request.Builder().url(src).build();
        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            //okhttp3. prefix necessary to avoid conflicts with retrofit
            public void onResponse(okhttp3.Call call, okhttp3.Response response) {
                // TODO check also response.code
                if (response.isSuccessful() && response.body() != null){
                    final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                    new Handler(Looper.getMainLooper()).post(() -> {
                        ImageView imageView = view.findViewById(R.id.siteLogo);
                        imageView.setImageBitmap(bitmap);
                    });
                }else {
                    Log.e(TAG, response.toString());
                }
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.e(TAG, e.toString());
            }
        });
    }

    private void setSiteTitle(){
        TextView textView = view.findViewById(R.id.siteName);
        textView.setText(url.getHost());
    }

    private void retrieveArticleInformation(){
        view.findViewById(R.id.loading).setVisibility(View.VISIBLE);
        GoogleSignInAccount account = ((GoogleActivity) Objects.requireNonNull(getActivity())).getGoogleAccount();
        APIService apiServiceClient = APIServiceUtils.getAPIServiceClient();
        apiServiceClient.getArticle(new TokenizedObject<>(account.getIdToken(), new GetArticleRequest(url.toString(), url.getHost())))
                .enqueue(new Callback<GetArticleResponse>() {
                    @Override
                    public void onResponse(Call<GetArticleResponse> call, Response<GetArticleResponse> response) {
                        //need to check for body to avoid IDE warning
                        if(response.code() != 200 || response.body() == null){
                            showFailedArticleDataRetrieval();
                        }else{
                            view.findViewById(R.id.loading).setVisibility(View.INVISIBLE);
                            Article article = response.body().getArticle();
                            setupUI(article);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetArticleResponse> call, Throwable t) {
                        Log.d(TAG, t.toString());
                        showFailedArticleDataRetrieval();
                    }
                });
    }

    private void setupUI(Article article){
        setTextViewTo(view, R.id.articleTitle, article.getName());
        setTextViewTo(view, R.id.textReportLegitNumber, Integer.toString(article.getLegitReports()));
        setTextViewTo(view, R.id.textReportFakeNumber, Integer.toString(article.getFakeReports()));
    }

    private void showFailedArticleDataRetrieval() {
        ProgressBar progressBar = view.findViewById(R.id.loading);
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(
                getContext(), getString(R.string.article_data_retrieval_failed), Toast.LENGTH_SHORT
        ).show();
    }
}