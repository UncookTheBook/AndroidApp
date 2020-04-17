package com.uncookthebook.app;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public interface GoogleActivity {
    GoogleSignInClient getGoogleClient();
}
