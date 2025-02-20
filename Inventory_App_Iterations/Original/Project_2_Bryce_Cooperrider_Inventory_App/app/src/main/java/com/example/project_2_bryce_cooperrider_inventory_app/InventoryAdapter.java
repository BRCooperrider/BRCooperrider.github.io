package com.example.project_2_bryce_cooperrider_inventory_app; // Adjust package name if needed

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryItemViewHolder> {

    //Declare variables
    private Cursor cursor;
    private Context context;
    private InventoryHelper db;

    //Constructor for the InventoryAdapter
    public InventoryAdapter(Context context, Cursor cursor, InventoryHelper db) {
        this.context = context;
        this.cursor = cursor;
        this.db = db;
    }

    //Creates a new ViewHolder instance
    @NonNull
    @Override
    public InventoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inventory, parent, false);
        return new InventoryItemViewHolder(itemView);
    }

    //Binds the data to the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull InventoryItemViewHolder holder, int position) {
        if (cursor != null && cursor.moveToPosition(position)) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(InventoryHelper.COLUMN_NAME));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryHelper.COLUMN_QUANTITY));

            holder.itemNameTextView.setText(name);
            holder.itemQuantityTextView.setText("Quantity: " + quantity);

            holder.increaseButton.setOnClickListener(v -> {
                handleIncreaseQuantity(holder.getAdapterPosition());
            });

            holder.decreaseButton.setOnClickListener(v -> {
                handleDecreaseQuantity(holder.getAdapterPosition());
            });
        }
    }

    //Returns the number of items in the data set
    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount() : 0;
    }

    //Swaps the cursor with a new one and updates the RecyclerView
    public void swapCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        notifyDataSetChanged();
    }

    //Refreshes the cursor and updates the RecyclerView
    public void refreshCursor() {
        Cursor newCursor = db.getAllItems();
        swapCursor(newCursor);
    }

    //Handles the increase button for an inventory item
    private void handleIncreaseQuantity(int position) {
        if (position != RecyclerView.NO_POSITION) {
            if (cursor.moveToPosition(position)) {
                int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryHelper.COLUMN_ID));
                String itemName = cursor.getString(cursor.getColumnIndexOrThrow(InventoryHelper.COLUMN_NAME));
                int currentQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryHelper.COLUMN_QUANTITY));

                int newQuantity = currentQuantity + 1;
                if (db.updateItem(itemId, itemName, newQuantity)) {
                    refreshCursor();
                } else {
                    Toast.makeText(context, "Error updating quantity", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //Handles the decrease button for an inventory item
    private void handleDecreaseQuantity(int position) {
        if (position != RecyclerView.NO_POSITION) {
            if (cursor.moveToPosition(position)) {
                int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryHelper.COLUMN_ID));
                String itemName = cursor.getString(cursor.getColumnIndexOrThrow(InventoryHelper.COLUMN_NAME));
                int currentQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryHelper.COLUMN_QUANTITY));

                if (currentQuantity > 0) {
                    int newQuantity = currentQuantity - 1;
                    if (db.updateItem(itemId, itemName, newQuantity)) {
                        refreshCursor();
                    } else {
                        Toast.makeText(context, "Error updating quantity", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Quantity cannot be negative", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}