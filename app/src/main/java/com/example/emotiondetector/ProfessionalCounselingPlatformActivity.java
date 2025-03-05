package com.example.emotiondetector;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;

public class ProfessionalCounselingPlatformActivity extends AppCompatActivity {
    private LinearLayout platform1Layout, platform2Layout, platform3Layout, platform4Layout;
    private Button btnHomePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_counseling_platform);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        // Get references to the views
        platform1Layout = findViewById(R.id.platform1Layout);
        platform2Layout = findViewById(R.id.platform2Layout);
        platform3Layout = findViewById(R.id.platform3Layout);
        platform4Layout = findViewById(R.id.platform4Layout);  // Button for WhatsApp contact
        btnHomePage = findViewById(R.id.btnHomePage);

        // Set onClickListeners to navigate to each platform
        platform1Layout.setOnClickListener(v -> openLink("https://www.facebook.com/profile.php?id=100078079504744"));
        platform2Layout.setOnClickListener(v -> openLink("https://www.facebook.com/groups/850933051618478"));
        platform3Layout.setOnClickListener(v -> openLink("https://www.facebook.com/marielasilveramindfuljourneycoaching/"));
        platform4Layout.setOnClickListener(v -> openWhatsApp("+260967167916"));

        btnHomePage.setOnClickListener(v -> {
            // Navigate back to Home Page
            Intent intent = new Intent(ProfessionalCounselingPlatformActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });
    }

    // Helper method to open a web link
    private void openLink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    // Helper method to open WhatsApp
    private void openWhatsApp(String number) {
        Uri uri = Uri.parse("https://wa.me/" + number);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
