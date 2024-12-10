package com.example.asansrcoo;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private ListView productListView;
    private List<Product> products;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button showAll,manfaction,paces,employes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the fragment layout
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        showAll = view.findViewById(R.id.SHOW_ALL);
        manfaction = view.findViewById(R.id.manfaction);
        paces = view.findViewById(R.id.Paces);
        employes = view.findViewById(R.id.employes);

        showAll.setOnClickListener(v-> {
            // Redirect to Login Screen
            Intent intent = new Intent(getActivity(), category.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear activity stack
            startActivity(intent);

            // Optionally, finish the parent activity
            if (getActivity() != null) {
                getActivity().finish();
            }
        });

        manfaction.setOnClickListener(v-> {
            // Redirect to Login Screen
            Intent intent = new Intent(getActivity(), categoryDetials.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear activity stack
            startActivity(intent);

            // Optionally, finish the parent activity
            if (getActivity() != null) {
                getActivity().finish();
            }
        });

        paces.setOnClickListener(v-> {
            // Redirect to Login Screen
            Intent intent = new Intent(getActivity(), categoryDetials.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear activity stack
            startActivity(intent);

            // Optionally, finish the parent activity
            if (getActivity() != null) {
                getActivity().finish();
            }
        });

        employes.setOnClickListener(v-> {
            // Redirect to Login Screen
            Intent intent = new Intent(getActivity(), categoryDetials.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear activity stack
            startActivity(intent);

            // Optionally, finish the parent activity
            if (getActivity() != null) {
                getActivity().finish();
            }
        });

        // Initialize FirebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance();
        productListView = view.findViewById(R.id.product_list_view);
        products = new ArrayList<>();




        readData("Products");

        // Set OnItemClickListener
        productListView.setOnItemClickListener((parent, view1, position, id) -> {
            // Get the selected product
            Product selectedProduct = products.get(position);

            // Create an Intent to navigate to itemDetails
            Intent intent = new Intent(getActivity(), itemDetials.class);
            intent.putExtra("item", selectedProduct); // Pass the product
            startActivity(intent);
        });


        return view;
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

                        ProductAdapter adapter = new ProductAdapter(getActivity(), products);
                        productListView.setAdapter(adapter);
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }


}
