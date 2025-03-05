An Android application that detects emotions (Happy or Sad) from user-uploaded or camera-captured images. The app provides insights based on the detected emotion and offers professional counseling options for users feeling sad. Built with Firebase Authentication, Google ML Kit, and TensorFlow Lite.

FEATURES
-**Emotion Detection**: Detects emotions (Happy or Sad) from images using a TensorFlow Lite model.
-**Face Detection**: Ensures a face is present in the image before processing.
-**Image Capture and Upload**: Users can take a picture using the camera or upload an image from their gallery.

INSIGHTS AND COUNSELING
-Displays insights based on the detected emotion.
-Offers professional counseling options if the user is feeling sad.
-User Authentication: Secure login and logout using Firebase Authentication.
-Profile Management: Navigate to a profile management screen (implementation details not provided).
-Responsive UI: Works seamlessly on both phones and tablets.

TECHNOLOGIES USED
- **Frontend**:
  - Android XML for UI design.
  - Java for app logic.
- **Backend**:
  - Firebase Authentication for user login and logout.
- **Machine Learning**:
  - TensorFlow Lite for emotion detection.
  - Google ML Kit for face detection.
- **Other Libraries**:
  - `DatabaseHelper` for fetching insights based on detected emotions.

SETUP INSTRUCTIONS
**Prerequisites**:
- Android Studio (latest version recommended).
- An Android device or emulator with camera support.
- Firebase project for authentication (optional, if you want to use Firebase).

Steps to Run the Project
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/tomonoafentiko/emotion_detector.git
