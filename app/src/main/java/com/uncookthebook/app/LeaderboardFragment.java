package com.uncookthebook.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uncookthebook.app.recyclerManager.LeadearboardItemRecyclerViewAdapter;
import com.uncookthebook.app.recyclerManager.PersonEntry;

import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class LeaderboardFragment extends GeneralTopBarFragment {
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment with the ProductGrid theme
        View view = inflater.inflate(R.layout.fragment_leadearboard, container, false);
        layoutSetup(view);
        int playerPosition = 2;
        recyclerViewSetup(view, playerPosition);
        TextView currentPositionText = view.findViewById(R.id.currentPosition);
        currentPositionText.setText(String.format(Locale.US, "%d", playerPosition));
        return view;
    }

    private void recyclerViewSetup(View view, int playerPosition) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        LeadearboardItemRecyclerViewAdapter adapter = new LeadearboardItemRecyclerViewAdapter(
                PersonEntry.initProductEntryList(),
                playerPosition,
                ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorAccent)
        );
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }
}
