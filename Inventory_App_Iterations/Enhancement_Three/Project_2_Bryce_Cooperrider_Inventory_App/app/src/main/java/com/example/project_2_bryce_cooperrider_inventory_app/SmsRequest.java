package com.example.project_2_bryce_cooperrider_inventory_app;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SmsRequest extends AppCompatActivity {

    //Initialize SMS permission request code
    private static final int SMS_PERMISSION_REQUEST_CODE = 123;

    //Creates the SMS permission request dialog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.sms_request);

        String username = getIntent().getStringExtra("username");

        showSmsRequestDialog(username);
    }

    //Displays dialog asking the user to grant SMS permission
    private void showSmsRequestDialog(String username) {

        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.sms_request, null);
        Button allowButton = dialogView.findViewById(R.id.allowButton);
        Button skipButton = dialogView.findViewById(R.id.denyButton);

        AlertDialog dialog = new AlertDialog.Builder(this).setView(dialogView).setCancelable(false).create();

        allowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(SmsRequest.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SmsRequest.this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_REQUEST_CODE);
                } else {
                    saveSmsPreference(true, username);
                    dialog.dismiss();
                    proceedToMainScreen();
                }
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSmsPreference(false, username);
                dialog.dismiss();
                proceedToMainScreen();
            }
        });

        dialog.show();
    }

    //Handles the result of the SMS permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            String username = getIntent().getStringExtra("username");
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveSmsPreference(true, username);
                proceedToMainScreen();
            }
            else {
                saveSmsPreference(false, username);
            }
            proceedToMainScreen();
        }
    }

    //Saves the SMS permission preference for the user
    private void saveSmsPreference(boolean isEnabled, String username) {
        SharedPreferences sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("sms_preference_shown" + username, isEnabled);
        editor.apply();
        Log.d("SmsRequest", "Saving preference for user: " + username + ", Value: " + isEnabled);
    }

    //Starts the MainScreen activity and finishes this activity
    private void proceedToMainScreen() {
        Intent intent = new Intent(SmsRequest.this, MainScreen.class);
        startActivity(intent);
        finish();
    }
}
