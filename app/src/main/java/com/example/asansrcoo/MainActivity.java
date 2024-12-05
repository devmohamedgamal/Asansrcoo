package com.example.asansrcoo;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//    private Button logoutButton;
    private FirebaseAuth firebaseAuth;
    private ListView productListView;
    private List<Product> Cities;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @SuppressLint({"MissingInflatedId", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Initialize FirebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance();
        productListView = findViewById(R.id.product_list_view);
//         Find Logout Button
//        logoutButton = findViewById(R.id.logoutBtn);
        Cities = new ArrayList<>();
//         Set OnClickListener for Logout Button
//        logoutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                firebaseAuth.signOut(); // Logs out the user
//                Toast.makeText(MainActivity.this, "Logged out successfully!", Toast.LENGTH_SHORT).show();
//
//                // Redirect to Login Screen
//                Intent intent = new Intent(MainActivity.this, LoginScreen.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear activity stack
//                startActivity(intent);
//                finish();
//            }
//        });

//        listProducts();
        readData("Products");



    }
    void readData(String collectionName) {
        db.collection(collectionName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Cities.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            String imageUrl = document.getString("imageUrl");
                            String name = document.getString("name");
                            String price = document.getString("price");

                            Product product = new Product(name,imageUrl,price);
                            Cities.add(product);
                        }

                        ProductAdapter adapter = new ProductAdapter(this, Cities);
                        productListView.setAdapter(adapter);
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }


}