package com.example.asansrcoo;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Calendar;


public class SignUpScreen extends AppCompatActivity {

    private EditText emailEditText, passwordEditText, confirmPasswordEditText,nameEditText,nummberEditText,etDate;
    private Button signupButton;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;

    private static final int RC_SIGN_IN = 123; // Request code for Google Sign-In
    private GoogleSignInClient googleSignInClient;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tvClickable = findViewById(R.id.textView);
        tvClickable.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Navigate to the target activity
                Intent intent = new Intent(SignUpScreen.this, LoginScreen.class);
                startActivity(intent);
            }
        });

        etDate = findViewById(R.id.et_date);

        etDate.setOnClickListener(v -> {
            // Get the current date
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Show the DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    SignUpScreen.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        // Format and set the selected date in EditText
                        String selectedDate = selectedDay + "/" + (selectedMonth) + "/" + selectedYear;
                        etDate.setText(selectedDate);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });

        // Initialize UI components
        nameEditText = findViewById(R.id.NameField);
        nummberEditText = findViewById(R.id.NumberField);
        emailEditText = findViewById(R.id.EmailField);
        passwordEditText = findViewById(R.id.PasswordField);
        confirmPasswordEditText = findViewById(R.id.ConfermPasswordField);
        signupButton = findViewById(R.id.SignUpBtn);
        progressBar = findViewById(R.id.progressBar);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Handle Sign-Up Button Click
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        // Configure Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);



        // Set up Google Sign-In button
        findViewById(R.id.googleAuthBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogle();
            }
        });


    }
    private void registerUser() {
        String name = nameEditText.getText().toString().trim();
        String number = nummberEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        // Validate input

        if (TextUtils.isEmpty(name)) {
            nameEditText.setError("Name is required.");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required.");
            return;
        }

        if (TextUtils.isEmpty(number)) {
            nummberEditText.setError("Number is required.");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required.");
            return;
        }

        if (password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match.");
            return;
        }

        // Show progress bar
        progressBar.setVisibility(View.VISIBLE);

        // Create user with Firebase Authentication
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);

                    if (task.isSuccessful()) {
                        // Sign-up successful
                        Toast.makeText(SignUpScreen.this, "User registered successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpScreen.this, MainActivity.class));
                        finish();
                    } else {
                        // Sign-up failed
                        Toast.makeText(SignUpScreen.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign-In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("GoogleSignIn", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign-In failed
                Log.w("GoogleSignIn", "Google sign-in failed", e);
                Toast.makeText(this, "Google sign-in failed.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign-in success
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        Log.d("FirebaseAuth", "Sign-in successful. User: " + user.getEmail());
                        Toast.makeText(SignUpScreen.this, "Welcome " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpScreen.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Ensure the login screen is removed from the back stack
                    } else {
                        // Sign-in failed
                        Log.w("FirebaseAuth", "Sign-in failed", task.getException());
                        Toast.makeText(SignUpScreen.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void signInWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

}