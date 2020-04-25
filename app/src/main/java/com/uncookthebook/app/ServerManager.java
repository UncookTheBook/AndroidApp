package com.uncookthebook.app;

import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.uncookthebook.app.models.TokenizedObject;
import com.uncookthebook.app.models.User;
import com.uncookthebook.app.network.APIService;
import com.uncookthebook.app.network.APIServiceUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


class ServerManager {

    static void sendUserToServer(GoogleSignInAccount account, String TAG) {
        // Use case of APIServiceClient
        APIService apiServiceClient = APIServiceUtils.getAPIServiceClient();
        User user = new User(account.getId(), account.getGivenName(),
                account.getFamilyName(), account.getEmail());
        apiServiceClient.addUser(new TokenizedObject<>(account.getIdToken() + "casda", user)).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //do something
                Log.d(TAG, "Is request successful? " + response.isSuccessful());
                Log.d(TAG, "Response body: " + response.body());
                Log.d(TAG, "Response code and message: " + response.code() + ", " + response.message());
                Log.d(TAG, "Raw response: " + response.raw());
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //do something
                Log.d(TAG, t.getMessage());
            }
        });
    }

}
