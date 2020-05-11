package com.uncookthebook.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.uncookthebook.app.models.ReportValue;
import com.uncookthebook.app.models.SubmitReportRequest;
import com.uncookthebook.app.models.TokenizedRequest;
import com.uncookthebook.app.network.APIService;
import com.uncookthebook.app.network.APIServiceUtils;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.uncookthebook.app.Utils.isURL;

public class MainActivity extends AppCompatActivity implements NavigationHost, GoogleActivity, ReportSender {

    private static final String TAG = "MainActivity";

    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount userAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new LoadingFragment())
                    .commit();
        }
        setupLogin();
        setupShareIntent();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utils.clearSharedPrefs(getApplicationContext());
    }

    /**
     * Navigate to the given fragment.
     *
     * @param fragment       Fragment to navigate to.
     * @param addToBackstack Whether or not the current fragment should be added to the backstack.
     */
    @Override
    public void navigateTo(Fragment fragment, boolean addToBackstack, String fragmentTag) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.container, fragment, fragmentTag);

        if (addToBackstack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    private void setupLogin(){
        final GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // See https://developers.google.com/identity/sign-in/android/backend-auth
        mGoogleSignInClient.silentSignIn().addOnCompleteListener(this, this::handleSilentSignInResult);
    }

    private void handleSilentSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            //User already signed in
            if(account != null) {
                Log.d(TAG,"User already signed in");
                //Only for debugging
                String idToken = account.getIdToken();
                Log.d(TAG, idToken);
                //
                setGoogleAccount(account);
                this.navigateTo(new PasteArticleFragment(), false, getString(R.string.paste_article_tag));
            }
        } catch (ApiException e) {
            this.navigateTo(new LoginFragment(), false, getString(R.string.login_tag));
        }
    }

    private void setupShareIntent(){
        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleTextReceived(intent); // Handle text being sent
            }
        }
    }

    void handleTextReceived(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            if(isURL(sharedText)){
                Utils.setURL(this, sharedText);
            }
        }
    }

    @Override
    public GoogleSignInClient getGoogleClient() {
        return mGoogleSignInClient;
    }

    @Override
    public GoogleSignInAccount getGoogleAccount() {
        return userAccount;
    }

    //after the login page userAccount must not be null
    @Override
    public void setGoogleAccount(GoogleSignInAccount account) {
        userAccount = account;
    }


    public void submitReport(){
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.send_report), MODE_PRIVATE);
        String url = sharedPreferences.getString(getString(R.string.url_key), null);
        if(url != null) {
            final boolean legit = sharedPreferences.getBoolean(getString(R.string.legit_key), false);
            final ReportValue reportValue = legit ? ReportValue.LEGIT : ReportValue.FAKE;
            final APIService apiService = APIServiceUtils.getAPIServiceClient();
            //it's on a thread so that it works even if the app is closed
            new Thread(() -> apiService.submitReport(
                    new TokenizedRequest<>(
                            userAccount.getIdToken(),
                            new SubmitReportRequest(url, reportValue)
                    )
            ).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.code() == 201){
                                Log.d(TAG, "Submit report successful");
                                sharedPreferences.edit().clear().apply();
                            }else{
                                Log.w(TAG, "Submit report failed");
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.w(TAG, "Submit report failed");
                        }
                    }
                )
            ).start();

        }

    }
}
