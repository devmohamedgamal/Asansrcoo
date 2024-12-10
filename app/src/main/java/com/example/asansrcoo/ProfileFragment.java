package com.example.asansrcoo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {
    private Button logoutButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();


        logoutButton = view.findViewById(R.id.logoutButton);

        // Set onClickListener for Logout Button
        logoutButton.setOnClickListener(v -> {
            firebaseAuth.signOut(); // Logs out the user
            Toast.makeText(getActivity(), "Logged out successfully!", Toast.LENGTH_SHORT).show();

            // Redirect to Login Screen
            Intent intent = new Intent(getActivity(), LoginScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear activity stack
            startActivity(intent);

            // Optionally, finish the parent activity
            if (getActivity() != null) {
                getActivity().finish();
            }
        });




        if (currentUser != null) {
            // Get user details
            String uid = currentUser.getUid();  // Unique ID for the user
            String email = currentUser.getEmail();  // User's email address
            String displayName = currentUser.getDisplayName();  // User's display name
            Uri photoUrl = currentUser.getPhotoUrl();  // User's profile picture URL

            // Example: Display details in log
            Log.d("UserDetails", "UID: " + uid);
            Log.d("UserDetails", "Email: " + email);
            Log.d("UserDetails", "Display Name: " + displayName);
            Log.d("UserDetails", "Photo URL: " + photoUrl);
        } else {
            // No user is signed in
            Log.d("UserDetails", "No user is signed in.");
        }
        return view;
    }
}
