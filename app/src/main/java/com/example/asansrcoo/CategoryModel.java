package com.example.asansrcoo;

public class CategoryModel {

    private String name;
    private String imageUrl;

    public CategoryModel() {
        // Default constructor required for Firestore
    }

    public CategoryModel(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
