package com.example.asansrcoo;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class category extends AppCompatActivity {
    private GridView categoryGridView;
    private FirebaseFirestore db;
    private CategoryAdapter adapter;
    private List<CategoryModel> categoryList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.fragment_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        categoryGridView = findViewById(R.id.category_grid_view);

        db = FirebaseFirestore.getInstance();
        categoryList = new ArrayList<>();
        adapter = new CategoryAdapter(this, categoryList);

        categoryGridView.setAdapter(adapter);

        fetchCategories();
    }


    private void fetchCategories() {
        db.collection("categories")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        categoryList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String name = document.getString("name");
                            String imageUrl = document.getString("imageUrl");
                            categoryList.add(new CategoryModel(name, imageUrl));
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Error fetching categories", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}