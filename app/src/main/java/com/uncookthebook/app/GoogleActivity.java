package com.uncookthebook.app;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

/**
 * Models an activity that perform a google login.
 */
public interface GoogleActivity {
    GoogleSignInClient getGoogleClient();

    GoogleSignInAccount getGoogleAccount();
    void setGoogleAccount(GoogleSignInAccount account);
}
