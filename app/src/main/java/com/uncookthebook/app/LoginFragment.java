package com.uncookthebook.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

/**
 * Fragment representing the login screen.
 */
public class LoginFragment extends Fragment {
    //an int required by google sign in. Can be anything
    private static final int RC_SIGN_IN = 2;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login_fragment, container, false);
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
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Activity mainActivity = Objects.requireNonNull(getActivity());
            ((GoogleActivity) mainActivity).setGoogleAccount(account);
            ((NavigationHost) mainActivity).navigateTo(new PasteArticleFragment(), false);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("GoogleSignIn", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(
                    getContext(), getString(R.string.login_failed), Toast.LENGTH_SHORT
            ).show();
        }
    }
}
