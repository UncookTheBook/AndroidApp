package com.uncookthebook.app.leadearboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.uncookthebook.app.R;

import java.util.List;
import java.util.Locale;

/**
 * Adapter used to show the leadearboard.
 */
public class LeadearboardItemRecyclerViewAdapter extends RecyclerView.Adapter<LeadearboardItemViewHolder> {
    private int playerPositon;
    private int playerColor;
    private List<PersonEntry> itemList;

    public LeadearboardItemRecyclerViewAdapter(List<PersonEntry> itemList, int playerPosition, int playerColor) {
        this.itemList = itemList;
        this.playerPositon = playerPosition - 1;
        this.playerColor = playerColor;
    }

    @NonNull
    @Override
    public LeadearboardItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.include_leadearboard_item, parent, false);
        return new LeadearboardItemViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull LeadearboardItemViewHolder holder, int position) {
        if (itemList != null && position < itemList.size()) {
            PersonEntry person = itemList.get(position);
            holder.personName.setText(person.name);
            holder.personPoints.setText(String.format(Locale.US, "%d", person.points));
            holder.personPosition.setText(String.format(Locale.US, "%d", position + 1));
            if(position == playerPositon){
                holder.materialCardView.setCardBackgroundColor(playerColor);
            }
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}