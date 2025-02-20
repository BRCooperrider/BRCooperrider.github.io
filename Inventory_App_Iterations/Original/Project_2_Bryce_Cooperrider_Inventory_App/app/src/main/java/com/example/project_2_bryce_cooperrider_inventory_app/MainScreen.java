package com.example.project_2_bryce_cooperrider_inventory_app;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainScreen extends AppCompatActivity {

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
        inventoryAdapter = new InventoryAdapter(this, null, db);
        inventoryRecyclerView.setAdapter(inventoryAdapter);

        //Loads inventory items from the database
        loadInventoryItems();

        addButton.setOnClickListener(v -> showOptionsMenu(v));
    }

    //Loads inventory items from the database and updates the recycler view
    private void loadInventoryItems() {
        Cursor cursor = db.getAllItems();
        inventoryAdapter.swapCursor(cursor);
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

        dialogBuilder.setTitle("Add Item");
        dialogBuilder.setPositiveButton("Add", (dialog, which) -> {
            String name = itemName.getText().toString().trim();
            String quantityStr = itemQuantity.getText().toString().trim();
            if (!name.isEmpty() && !quantityStr.isEmpty()) {
                int quantity = Integer.parseInt(quantityStr);
                if (db.addItem(name, quantity)) {
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
}