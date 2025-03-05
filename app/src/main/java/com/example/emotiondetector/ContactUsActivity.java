package com.example.emotiondetector;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ContactUsActivity extends AppCompatActivity {
    private Button btnEmail, btnWhatsApp, btnFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        btnEmail = findViewById(R.id.btnEmail);
        btnWhatsApp = findViewById(R.id.btnWhatsApp);
        btnFacebook = findViewById(R.id.btnFacebook);

        btnEmail.setOnClickListener(v -> sendEmail());
        btnWhatsApp.setOnClickListener(v -> openWhatsApp());
        btnFacebook.setOnClickListener(v -> openFacebook());
    }

    private void sendEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "angelpanji99@gmail.com", null));
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    private void openWhatsApp() {
        String url = "https://wa.me/260971854854";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    private void openFacebook() {
        String url = "https://www.facebook.com/panji.cashridermwale/";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
