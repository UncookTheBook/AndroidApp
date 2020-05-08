package com.uncookthebook.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uncookthebook.app.leadearboard.LeadearboardItemRecyclerViewAdapter;
import com.uncookthebook.app.leadearboard.PersonEntry;
import com.uncookthebook.app.models.AddFriendRequest;
import com.uncookthebook.app.models.TokenizedRequest;
import com.uncookthebook.app.network.APIService;
import com.uncookthebook.app.network.APIServiceUtils;

import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaderboardFragment extends GeneralTopBarFragment {
    public static String FRIENDS_LEADERBOARD = "friends_leadearboard";
    private View view;
    private boolean isFriendsLeadearboard = false;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment with the ProductGrid theme
        this.view = inflater.inflate(R.layout.fragment_leadearboard, container, false);
        layoutSetup(view);

        Bundle bundle = Objects.requireNonNull(getArguments());
        isFriendsLeadearboard = bundle.getBoolean(FRIENDS_LEADERBOARD);
        int playerPosition = isFriendsLeadearboard ? 2 : 5;

        recyclerViewSetup(playerPosition);
        setCurrentPositionText(playerPosition);
        addFriendSetup();
        friendEditTextSetup();
        return view;
    }

    private void setCurrentPositionText(int playerPosition) {
        TextView currentPositionText = view.findViewById(R.id.currentPosition);
        currentPositionText.setText(String.format(Locale.US, "%d", playerPosition));
    }

    private void recyclerViewSetup(int playerPosition) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        LeadearboardItemRecyclerViewAdapter adapter = new LeadearboardItemRecyclerViewAdapter(
                PersonEntry.initProductEntryList(isFriendsLeadearboard),
                playerPosition,
                ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorAccent)
        );
        recyclerView.setAdapter(adapter);
    }

    private void addFriendSetup(){
        FloatingActionButton floatingAddButton = view.findViewById(R.id.fab);
        floatingAddButton.setOnClickListener(this);

        MaterialButton cancel = view.findViewById(R.id.cancel_button);
        cancel.setOnClickListener(this);

        MaterialButton add = view.findViewById(R.id.add_button);
        add.setOnClickListener(this);
    }


    @SuppressLint("ClickableViewAccessibility")
    private void friendEditTextSetup() {
        EditText friendEmailInputField = view.findViewById(R.id.emailField);
        //Since edit text does override onClick and does not call it, I have to specify it manually
        friendEmailInputField.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP && !v.hasFocus()) {
                v.performClick();
            }
            return false;
        });
        friendEmailInputField.setOnClickListener(this);
        friendEmailInputField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (! Utils.isValidEmail(friendEmailInputField.getText().toString())) {
                    friendEmailInputField.setError("Invalid email");
                }
            }
        });
    }

    private void sendFriendMail(String mail){
        view.findViewById(R.id.loading).setVisibility(View.VISIBLE);
        APIService apiService = APIServiceUtils.getAPIServiceClient();
        GoogleSignInAccount account = ((GoogleActivity) getActivity()).getGoogleAccount();
        apiService.addFriend(
                new TokenizedRequest<>(
                        account.getIdToken(),
                        new AddFriendRequest(mail)
                )
        ).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 201) {
                    showSuccessfulFriendSend();
                }else{
                    showFailedFriendSend();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showFailedFriendSend();
            }
        });
    }

    private void showFailedFriendSend() {
        view.findViewById(R.id.loading).setVisibility(View.INVISIBLE);
        Toast.makeText(
                getContext(), getString(R.string.friend_send_failed), Toast.LENGTH_SHORT
        ).show();
    }


    private void showSuccessfulFriendSend() {
        view.findViewById(R.id.loading).setVisibility(View.INVISIBLE);
        Toast.makeText(
                getContext(), getString(R.string.friend_send_success), Toast.LENGTH_SHORT
        ).show();
    }

    @Override
    public void onClick(View v) {
        closeMenuOnElementClick();
        if(v.getId() == R.id.add_button){
            EditText friendEmailInputField = view.findViewById(R.id.emailField);
            String friendEmail = friendEmailInputField.getText().toString();
            if(Utils.isValidEmail(friendEmailInputField.getText().toString())) {
                sendFriendMail(friendEmail);
                Log.d("Friend mail", friendEmail);
                view.findViewById(R.id.friend_dialog).setVisibility(View.INVISIBLE);
            }
        }
        if(v.getId() == R.id.cancel_button){
            view.findViewById(R.id.friend_dialog).setVisibility(View.INVISIBLE);
        }
        if(v.getId() == R.id.fab){
            view.findViewById(R.id.friend_dialog).setVisibility(View.VISIBLE);
        }
    }
}
