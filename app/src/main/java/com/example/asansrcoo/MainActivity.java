package com.example.asansrcoo;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button logoutButton;
    private FirebaseAuth firebaseAuth;
    private ListView productListView;
    private List<Product> products;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
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
        logoutButton = findViewById(R.id.logoutBtn);
        products = new ArrayList<>();
//         Set OnClickListener for Logout Button
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut(); // Logs out the user
                Toast.makeText(MainActivity.this, "Logged out successfully!", Toast.LENGTH_SHORT).show();

                // Redirect to Login Screen
                Intent intent = new Intent(MainActivity.this, LoginScreen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear activity stack
                startActivity(intent);
                finish();
            }
        });

        readData("Products");

        productListView.setOnItemClickListener((parent, view, position, id) -> {
            // Get the selected product
            Product selectedProduct = products.get(position);

            // Create an Intent to navigate to itemDetials
            Intent intent = new Intent(MainActivity.this, itemDetials.class);
            intent.putExtra("item", selectedProduct); // Pass the product
            startActivity(intent); // Start the details activity
        });



    }
    void readData(String collectionName) {
        db.collection(collectionName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        products.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            String imageUrl = document.getString("imageUrl");
                            String name = document.getString("name");
                            String price = document.getString("price");
                            String description = document.getString("description");

                            Product product = new Product(name,price,imageUrl,description);
                            products.add(product);
                        }

                        ProductAdapter adapter = new ProductAdapter(this, products);
                        productListView.setAdapter(adapter);
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }


}