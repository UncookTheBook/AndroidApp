package com.uncookthebook.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uncookthebook.app.GeneralTopBarFragment;
import com.uncookthebook.app.R;
import com.uncookthebook.app.leadearboard.LeadearboardItemRecyclerViewAdapter;
import com.uncookthebook.app.leadearboard.PersonEntry;

import java.util.Locale;
import java.util.Objects;

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

    @Override
    public void onClick(View v) {

    }
}
