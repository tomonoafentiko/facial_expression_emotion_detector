package com.example.emotiondetector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_UPLOAD = 2;
    private static final int IMAGE_SIZE = 224;

    private ImageView imageView, btnLogout, btnProfile;
    private TextView detectedEmotion;
    private Button btnOpenCamera, btnUploadImage, btnNoImOkay, btnProfessionalHelp, btnYesPlease;
    private LinearLayout consultationLayout;
    private FirebaseAuth mAuth;
    private Interpreter tflite;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            navigateToLogin();
            return;
        }

        dbHelper = new DatabaseHelper();  // Initialize the DatabaseHelper
        initializeViews();
        setupListeners();
        loadModel();
    }

    private void initializeViews() {
        imageView = findViewById(R.id.imageView);
        imageView.setVisibility(View.GONE);
        detectedEmotion = findViewById(R.id.detectedEmotion);
        btnOpenCamera = findViewById(R.id.btnOpenCamera);
        btnUploadImage = findViewById(R.id.btnUploadPicture);
        btnLogout = findViewById(R.id.btnLogout);
        btnProfile = findViewById(R.id.btnProfile);
        consultationLayout = findViewById(R.id.consultationLayout);
        btnNoImOkay = findViewById(R.id.btnNoImOkay);
        btnYesPlease = findViewById(R.id.btnYesPlease);
        btnProfessionalHelp = findViewById(R.id.btnProfessionalHelp);
    }

    private void setupListeners() {
        btnOpenCamera.setOnClickListener(v -> dispatchTakePictureIntent());
        btnUploadImage.setOnClickListener(v -> dispatchUploadPictureIntent());
        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            navigateToLogin();
        });

        btnNoImOkay.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "Glad to hear you're okay!", Toast.LENGTH_SHORT).show();
            recreate();
        });

        btnYesPlease.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfessionalCounselingPlatformActivity.class);
            startActivity(intent);
        });

        btnProfessionalHelp.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfessionalCounselingPlatformActivity.class);
            startActivity(intent);
        });

        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfileManagementActivity.class);
            startActivity(intent);
        });

    }

    private void loadModel() {
        try {
            tflite = new Interpreter(loadModelFile());
            Log.d(TAG, "Model loaded successfully.");
        } catch (Exception e) {
            Log.e(TAG, "Error loading model", e);
        }
    }

    private ByteBuffer loadModelFile() throws IOException {
        FileInputStream fis = new FileInputStream(getAssets().openFd("model.tflite").getFileDescriptor());
        FileChannel fileChannel = fis.getChannel();
        long startOffset = getAssets().openFd("model.tflite").getStartOffset();
        long declaredLength = getAssets().openFd("model.tflite").getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength).order(ByteOrder.nativeOrder());
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(this, "Camera not available", Toast.LENGTH_SHORT).show();
        }
    }

    private void dispatchUploadPictureIntent() {
        Intent uploadPictureIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (uploadPictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(uploadPictureIntent, REQUEST_IMAGE_UPLOAD);
        } else {
            Toast.makeText(this, "No app available to upload images", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                processImage(imageBitmap);
            } else if (requestCode == REQUEST_IMAGE_UPLOAD && data != null) {
                Uri imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    processImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Toast.makeText(this, "Image capture/upload failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void processImage(Bitmap imageBitmap) {
        imageView.setImageBitmap(imageBitmap);
        imageView.setVisibility(View.VISIBLE);

        FaceDetectorOptions options =
                new FaceDetectorOptions.Builder()
                        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_NONE)
                        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_NONE)
                        .build();

        FaceDetector faceDetector = FaceDetection.getClient(options);
        InputImage inputImage = InputImage.fromBitmap(imageBitmap, 0);

        faceDetector.process(inputImage)
                .addOnSuccessListener(faces -> {
                    if (faces.size() > 0) {
                        processEmotionDetection(imageBitmap);
                    } else {
                        showRetryDialog("No face detected. Please try again.");
                        detectedEmotion.setText("No face detected.");
                        imageView.setVisibility(View.GONE);
                        consultationLayout.setVisibility(View.GONE);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Face detection failed", e);
                    Toast.makeText(this, "Face detection failed. Please try again.", Toast.LENGTH_SHORT).show();
                });
    }

    private void processEmotionDetection(Bitmap imageBitmap) {
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, IMAGE_SIZE, IMAGE_SIZE, false);
        ByteBuffer inputBuffer = convertBitmapToByteBuffer(resizedBitmap);

        float[][] output = new float[1][2];
        tflite.run(inputBuffer, output);

        int detectedEmotionIndex = getEmotionIndex(output[0]);
        String[] emotions = {"Happy", "Sad"};
        String insight;
        

        if (detectedEmotionIndex == 1) { // Sad
            insight = dbHelper.getSadInsight();
            consultationLayout.setVisibility(View.VISIBLE);
        } else { // Happy
            insight = dbHelper.getHappyInsight();
            consultationLayout.setVisibility(View.GONE);
        }

        detectedEmotion.setText("Detected Emotion: " + emotions[detectedEmotionIndex] + "\nInsight: " + insight);
    }

    private ByteBuffer convertBitmapToByteBuffer(Bitmap bitmap) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(4 * IMAGE_SIZE * IMAGE_SIZE * 3);
        buffer.order(ByteOrder.nativeOrder());

        int[] pixels = new int[IMAGE_SIZE * IMAGE_SIZE];
        bitmap.getPixels(pixels, 0, IMAGE_SIZE, 0, 0, IMAGE_SIZE, IMAGE_SIZE);

        for (int pixel : pixels) {
            buffer.putFloat(((pixel >> 16) & 0xFF) / 255.0f);
            buffer.putFloat(((pixel >> 8) & 0xFF) / 255.0f);
            buffer.putFloat((pixel & 0xFF) / 255.0f);
        }
        return buffer;
    }

    private int getEmotionIndex(float[] output) {
        return output[0] > output[1] ? 0 : 1;
    }

    private void showRetryDialog(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void navigateToLogin() {
        Intent loginIntent = new Intent(HomeActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tflite != null) {
            tflite.close();
        }
    }
}
