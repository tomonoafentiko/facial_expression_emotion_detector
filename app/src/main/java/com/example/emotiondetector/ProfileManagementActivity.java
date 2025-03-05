package com.example.emotiondetector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileManagementActivity extends AppCompatActivity {

    private Button btnChangePassword, btnSendFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_management);

        // Initialize buttons
        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnSendFeedback = findViewById(R.id.btnSendFeedback);
        // Set button click listeners to navigate to respective activities
        btnChangePassword.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileManagementActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        });

        btnSendFeedback.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileManagementActivity.this, ContactUsActivity.class);
            startActivity(intent);
        });
    }
}
