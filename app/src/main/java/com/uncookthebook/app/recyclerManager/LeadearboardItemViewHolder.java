package com.uncookthebook.app.recyclerManager;


import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.google.android.material.card.MaterialCardView;
import com.uncookthebook.app.R;


public class LeadearboardItemViewHolder extends RecyclerView.ViewHolder {

    public TextView personName;
    public TextView personPoints;
    public TextView personPosition;
    public MaterialCardView materialCardView;

    public LeadearboardItemViewHolder(@NonNull View itemView) {
        super(itemView);
        personName = itemView.findViewById(R.id.person_name);
        personPoints = itemView.findViewById(R.id.person_points);
        personPosition = itemView.findViewById(R.id.person_position);
        materialCardView = itemView.findViewById(R.id.person_card);
    }
}
