package com.example.project_2_bryce_cooperrider_inventory_app;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;

public class InventoryItemViewHolder extends RecyclerView.ViewHolder {

    //Variable declaration
    public TextView itemNameTextView;
    public TextView itemQuantityTextView;
    public Button increaseButton;
    public Button decreaseButton;

    //Constructor to initialize references to the views in the item layout
    public InventoryItemViewHolder(@NonNull View itemView, InventoryAdapter.OnItemClickListener listener) {
        super(itemView);
        itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
        itemQuantityTextView = itemView.findViewById(R.id.itemQuantityTextView);
        increaseButton = itemView.findViewById(R.id.increaseButton);
        decreaseButton = itemView.findViewById(R.id.decreaseButton);

        //Set the click listener on the item view
        itemView.setOnClickListener(v -> {
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position);
                }
            }
        });
    }
}