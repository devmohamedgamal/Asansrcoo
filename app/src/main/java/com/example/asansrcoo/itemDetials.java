package com.example.asansrcoo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import java.util.Objects;

public class itemDetials extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_detials);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable back navigation
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Set click listener for back button
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        Product item = getIntent().getParcelableExtra("item");
        if (item != null) {
            // Use the model's data
            String name = item.getName();
            double price = Double.parseDouble(item.getPrice());
            String imageUrl = item.getImageUrl();

            double totalPrice = (price - (price * .1)) + (price * .14);

            // Update the UI or perform actions
            TextView productNameTextView = findViewById(R.id.tvProductTitle);
            TextView productPriceTextView = findViewById(R.id.tvCostValue);
            TextView productTotalPriceCost = findViewById(R.id.tvTotalPriceCost);
            TextView productDescription = findViewById(R.id.tvDescriptionValue);
            ImageView imageView = findViewById(R.id.ivProductImage);

            productNameTextView.setText(name);
            productPriceTextView.setText(price + "$");
            productTotalPriceCost.setText("Cost: " + totalPrice + "$");
            productDescription.setText(item.getDescription());
            if (item.getImageUrl() != null && !item.getImageUrl().isEmpty()) {
                Glide.with(this)
                        .load(item.getImageUrl())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(imageView);
            } else {
                Glide.with(this)
                        .load(R.drawable.laptop)  // Default fallback image if the URL is null
                        .into(imageView);
            }
        } else {
            Log.e("ItemDetails", "Product is null!");
        }

    }
}