package com.uncookthebook.app;

import android.app.Activity;
import android.content.Intent;
import android.media.DeniedByServerException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.uncookthebook.app.models.TokenizedObject;
import com.uncookthebook.app.models.User;
import com.uncookthebook.app.network.APIService;
import com.uncookthebook.app.network.APIServiceUtils;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragment representing the login screen.
 */
public class LoginFragment extends Fragment {
    //an int required by google sign in. Can be anything
    private static final int RC_SIGN_IN = 2;
    private View view;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        MaterialButton nextButton = view.findViewById(R.id.login_button);

        nextButton.setOnClickListener(view1 -> signIn());

        return view;
    }

    private void signIn() {
        GoogleActivity mainActivity = ((GoogleActivity) Objects.requireNonNull(getActivity()));
        Intent signInIntent = mainActivity.getGoogleClient().getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            ProgressBar progressBar = view.findViewById(R.id.loading);
            progressBar.setVisibility(View.VISIBLE);
            GoogleSignInAccount account = Objects.requireNonNull(completedTask.getResult(ApiException.class));
            Activity mainActivity = Objects.requireNonNull(getActivity());
            ((GoogleActivity) mainActivity).setGoogleAccount(account);
            sendUserToServer(account, ((NavigationHost) mainActivity));
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("GoogleSignIn", "signInResult:failed code=" + e.getStatusCode());
            showFailedLogin();
        }
    }

    private void sendUserToServer(GoogleSignInAccount account, NavigationHost activity)  {
        APIService apiServiceClient = APIServiceUtils.getAPIServiceClient();
        apiServiceClient.addUser(new TokenizedObject<>(account.getIdToken(), new User(account.getId(), account.getGivenName(), account.getFamilyName(), account.getEmail())))
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code() == 201) {
                            activity.navigateTo(
                                    new PasteArticleFragment(), false, getString(R.string.paste_article_tag)
                            );
                        }else{
                            showFailedLogin();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        showFailedLogin();
                    }
                });
    }

    private void showFailedLogin() {
        ProgressBar progressBar = view.findViewById(R.id.loading);
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(
                getContext(), getString(R.string.login_failed), Toast.LENGTH_SHORT
        ).show();
    }
}
