package com.example.project_2_bryce_cooperrider_inventory_app;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    //Initialize variables
    LoginHelper db;
    EditText username, password;
    Button loginButton, createButton;

    //Sets up the layout, views, and handles the login and create button clicks
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Initializes the login database helper and view references
        db = new LoginHelper(this);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        createButton = findViewById(R.id.createButton);

        //handle the login button clicks
        loginButton.setOnClickListener(view -> {
            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();

            boolean res = db.checkUser(user, pass);
            if (res) {
                Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, MainScreen.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        });

        //Handle the create button clicks
        createButton.setOnClickListener(view -> {
            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                boolean isInserted = db.insertData(user, pass);
                if (isInserted) {
                    Toast.makeText(MainActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, SmsRequest.class);
                    intent.putExtra("username", user);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Account creation failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}