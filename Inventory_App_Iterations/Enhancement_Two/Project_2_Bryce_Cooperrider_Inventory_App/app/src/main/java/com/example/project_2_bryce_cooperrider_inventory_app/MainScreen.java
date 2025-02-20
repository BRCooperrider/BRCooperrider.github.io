package com.example.project_2_bryce_cooperrider_inventory_app;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.MessageFormat;

public class MainScreen extends AppCompatActivity implements InventoryAdapter.OnItemClickListener {

    //Declare variables
    InventoryHelper db;
    RecyclerView inventoryRecyclerView;
    InventoryAdapter inventoryAdapter;
    FloatingActionButton addButton;

    private EditText itemNameInput;

    //Sets up the layout, views, and loads inventory items
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializes the inventory database helper and view references
        db = new InventoryHelper(this);
        inventoryRecyclerView = findViewById(R.id.inventoryRecyclerView);
        addButton = findViewById(R.id.floatingActionButton);

        //Sets up the number of columns in the RecyclerView
        int numberOfColumns = 1;
        inventoryRecyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        inventoryAdapter = new InventoryAdapter(this, null, db, this);
        inventoryRecyclerView.setAdapter(inventoryAdapter);

        //Loads inventory items from the database
        loadInventoryItems();

        addButton.setOnClickListener(this::showOptionsMenu);

        //Find sort button
        Button sortButton = findViewById(R.id.sortButton);
        sortButton.setOnClickListener(v -> showSortPopup());

        //Find Filter button
        Button filterButton = findViewById(R.id.filterButton);
        filterButton.setOnClickListener(v -> showFilterPopup());
    }

    //Loads inventory items from the database and updates the recycler view
    private void loadInventoryItems() {
        Cursor cursor = db.getAllItems();
        inventoryAdapter.changeCursor(cursor);
    }

    //Shows a popup menu with options to add or remove items
    private void showOptionsMenu(View v) {
        PopupMenu popup = new PopupMenu(MainScreen.this, v);
        popup.getMenuInflater().inflate(R.menu.menu_options, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.add_item) {
                showAddItemDialog();
                return true;
            } else if (item.getItemId() == R.id.remove_item) {
                showRemoveItemDialog();
                return true;
            }
            return false;
        });
        popup.show();
    }

    //Displays a dialog to add a new item
    private void showAddItemDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_add_item, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        final EditText itemName = dialogView.findViewById(R.id.itemName);
        final EditText itemQuantity = dialogView.findViewById(R.id.itemQuantity);
        final EditText itemDescription = dialogView.findViewById(R.id.itemDescription);

        dialogBuilder.setTitle("Add Item");
        dialogBuilder.setPositiveButton("Add", (dialog, which) -> {
            String name = itemName.getText().toString().trim();
            String quantityStr = itemQuantity.getText().toString().trim();
            String descriptionStr = itemDescription.getText().toString().trim();
            if (!name.isEmpty() && !quantityStr.isEmpty()) {
                int quantity = Integer.parseInt(quantityStr);

                if (db.addItem(name, quantity, descriptionStr)) {
                    Toast.makeText(MainScreen.this, "Item added", Toast.LENGTH_SHORT).show();
                    loadInventoryItems();
                } else {
                    Toast.makeText(MainScreen.this, "Error adding item", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainScreen.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", null);
        dialogBuilder.show();
    }

    //Displays a dialog to remove an item by name
    private void showRemoveItemDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_remove_item, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        itemNameInput = dialogView.findViewById(R.id.itemNameInput);

        dialogBuilder.setTitle("Remove Item");
        dialogBuilder.setPositiveButton("Remove", (dialog, which) -> {
            String itemName = itemNameInput.getText().toString().trim();
            if (!itemName.isEmpty()) {
                if (db.deleteItemByName(itemName)) {
                    Toast.makeText(MainScreen.this, "Item removed", Toast.LENGTH_SHORT).show();
                    loadInventoryItems();
                } else {
                    Toast.makeText(MainScreen.this, "Error removing item", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainScreen.this, "Please enter an item name", Toast.LENGTH_SHORT).show();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", null);
        dialogBuilder.show();
    }

    @Override
    public void onItemClick(int position) {
        showItemDetailsPopup(position);
    }

    private void showItemDetailsPopup(int position) {
        if (inventoryAdapter != null) {
            Cursor cursor = inventoryAdapter.getCursor(); // Get the cursor from the adapter
            if (cursor != null && cursor.moveToPosition(position)) {
                int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(InventoryHelper.COLUMN_NAME));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryHelper.COLUMN_QUANTITY));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(InventoryHelper.COLUMN_DESCRIPTION));

                // Inflate the layout for the popup
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_item_details, inventoryRecyclerView, false);

                // Find the TextViews in the popup layout
                TextView popupName = popupView.findViewById(R.id.popupItemName);
                TextView popupQuantity = popupView.findViewById(R.id.popupItemQuantity);
                TextView popupDescription = popupView.findViewById(R.id.popupItemDescription);

                // Set the text for the TextViews
                popupName.setText(name);
                popupQuantity.setText(MessageFormat.format("Quantity: {0}", quantity));
                popupDescription.setText(MessageFormat.format("Description: {0}", description));

                // Create the PopupWindow
                int width = ViewGroup.LayoutParams.WRAP_CONTENT;
                int height = ViewGroup.LayoutParams.WRAP_CONTENT;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

                // Show the popup in the center of the screen
                popupWindow.showAtLocation(inventoryRecyclerView, Gravity.CENTER, 0, 0);

                // Find and handle the delete button
                Button deleteButton = popupView.findViewById(R.id.deleteButton);
                deleteButton.setOnClickListener(v -> {
                    // Remove the item from the database
                    if (db.deleteItem(itemId)) {
                        Toast.makeText(MainScreen.this, "Item removed", Toast.LENGTH_SHORT).show();
                        Cursor newCursor = db.getAllItems();
                        inventoryAdapter.changeCursor(newCursor);
                    }
                    else {
                        Toast.makeText(MainScreen.this, "Error deleting item", Toast.LENGTH_SHORT).show();
                    }
                    popupWindow.dismiss();
                });

                // Add a dismiss listener to clean up when the popup is closed
                popupView.setOnClickListener(v -> popupWindow.dismiss());

                popupWindow.setOnDismissListener(() -> {

                });
            } else {
                Toast.makeText(this, "Error, Could not display the item details", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Error, Adapter is null", Toast.LENGTH_SHORT).show();
        }
    }

    //Method to show the sort popup
    private void showSortPopup() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View popupView = inflater.inflate(R.layout.popup_sort_options, null, false);

        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        //Find the buttons inside the popup
        Button sortNameAscButton = popupView.findViewById(R.id.sortNameAscButton);
        Button sortNameDescButton = popupView.findViewById(R.id.sortNameDescButton);
        Button sortQuantityAscButton = popupView.findViewById(R.id.sortQuantityAscButton);
        Button sortQuantityDescButton = popupView.findViewById(R.id.sortQuantityDescButton);

        //Set the OnClickListener for each button
        sortNameAscButton.setOnClickListener(v -> {
            sortItems(InventoryHelper.SORT_BY_NAME_ASC);
            popupWindow.dismiss();
        });

        sortNameDescButton.setOnClickListener(v -> {
            sortItems(InventoryHelper.SORT_BY_NAME_DESC);
            popupWindow.dismiss();
        });

        sortQuantityAscButton.setOnClickListener(v -> {
            sortItems(InventoryHelper.SORT_BY_QUANTITY_ASC);
            popupWindow.dismiss();
        });

        sortQuantityDescButton.setOnClickListener(v -> {
            sortItems(InventoryHelper.SORT_BY_QUANTITY_DESC);
            popupWindow.dismiss();
        });
        
        popupWindow.showAsDropDown(findViewById(R.id.sortButton));

    }

    private void showFilterPopup() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_filter_options, null, false);

        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        //Find the views inside the popup
        Button filterButton = popupView.findViewById(R.id.filterButton);
        EditText filterEditText = popupView.findViewById(R.id.filterEditText);

        //Set the click listener for the apply button
        filterButton.setOnClickListener(v -> {
            String filterText = filterEditText.getText().toString();
            filterItems(filterText);
            popupWindow.dismiss();
        });

        popupWindow.showAsDropDown(findViewById(R.id.filterButton));
    }

    //Method to sort the items in the database
    private void sortItems(String sortOrder) {
        Cursor newCursor = db.getSortedItems(sortOrder);
        inventoryAdapter.changeCursor(newCursor);
    }

    //Method to Filter items in the database
    public void filterItems(String filterText) {
        Cursor newCursor = db.getFilteredItems(filterText);
        inventoryAdapter.changeCursor(newCursor);
    }
}