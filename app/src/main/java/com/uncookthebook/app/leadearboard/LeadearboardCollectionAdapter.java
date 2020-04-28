package com.uncookthebook.app.leadearboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.uncookthebook.app.LeaderboardFragment;

public class LeadearboardCollectionAdapter extends FragmentStateAdapter {
    public LeadearboardCollectionAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int)
        Fragment fragment = new LeaderboardFragment();
        Bundle args = new Bundle();
        args.putBoolean(LeaderboardFragment.FRIENDS_LEADERBOARD, position != 0);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
