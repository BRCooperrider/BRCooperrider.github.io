package com.example.project_2_bryce_cooperrider_inventory_app;

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
    private final Context context;
    private final InventoryHelper db;
    private final OnItemClickListener itemClickListener;

    //Constructor for the InventoryAdapter
    public InventoryAdapter(Context context, Cursor cursor, InventoryHelper db, OnItemClickListener listener) {
        this.context = context;
        this.cursor = cursor;
        this.db = db;
        this.itemClickListener = listener;
    }

    //Interface for the click listener
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    //Creates a new ViewHolder instance
    @NonNull
    @Override
    public InventoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inventory, parent, false);
        return new InventoryItemViewHolder(itemView, itemClickListener);
    }

    //Binds the data to the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull InventoryItemViewHolder holder, int position) {
        if (cursor != null && cursor.moveToPosition(position)) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(InventoryHelper.COLUMN_NAME));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryHelper.COLUMN_QUANTITY));

            holder.itemNameTextView.setText(name);

            String quantityLabel = context.getString(R.string.quantity_label);
            String quantityText = String.format(quantityLabel, quantity);
            holder.itemQuantityTextView.setText(quantityText);

            holder.increaseButton.setOnClickListener(v -> handleIncreaseQuantity(holder.getAdapterPosition()));

            holder.decreaseButton.setOnClickListener(v -> handleDecreaseQuantity(holder.getAdapterPosition()));
        }
    }

    //Returns the number of items in the data set
    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount() : 0;
    }

    //Functionality for changing the cursor. Added to get DeleteItem to work within popup_item_details
    public void changeCursor(Cursor newCursor) {
        Cursor oldCursor = swapCursor(newCursor);
        if (oldCursor != null) {
            oldCursor.close();
        }
    }

    //Swaps the cursor with a new one and updates the RecyclerView
    private Cursor swapCursor(Cursor newCursor) {
        if (newCursor == cursor) {
            return null;
        }
        final Cursor oldCursor = cursor;
        cursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
        else {
            this.notifyItemRangeRemoved(0, getItemCount());
        }
        return oldCursor;
    }

    //Returns the current cursor
    public Cursor getCursor() {
        return cursor;
    }

    //Handles the increase button for an inventory item
    private void handleIncreaseQuantity(int position) {
        if (position != RecyclerView.NO_POSITION) {
            if (cursor.moveToPosition(position)) {
                int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryHelper.COLUMN_ID));
                String itemName = cursor.getString(cursor.getColumnIndexOrThrow(InventoryHelper.COLUMN_NAME));
                int currentQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryHelper.COLUMN_QUANTITY));
                String itemDescription = cursor.getString(cursor.getColumnIndexOrThrow(InventoryHelper.COLUMN_DESCRIPTION));

                int newQuantity = currentQuantity + 1;
                if (db.updateItem(itemId,
                        itemName,
                        newQuantity,
                        itemDescription)) {

                    cursor = db.getAllItems();
                    notifyItemChanged(position);

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
                String itemDescription = cursor.getString(cursor.getColumnIndexOrThrow(InventoryHelper.COLUMN_DESCRIPTION));

                if (currentQuantity > 0) {
                    int newQuantity = currentQuantity - 1;
                    if (db.updateItem(itemId,
                            itemName,
                            newQuantity,
                            itemDescription)) {
                        cursor = db.getAllItems();
                        notifyItemChanged(position);

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