package com.example.asansrcoo;

import android.widget.ImageView;

public class Product {
    private final String name;
    private final String imageUrl;
    private final String price;

    public Product(String name, String imageUrl, String price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPrice() {
        return price;
    }
}
