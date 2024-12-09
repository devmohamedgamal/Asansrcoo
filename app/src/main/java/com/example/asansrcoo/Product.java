package com.example.asansrcoo;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

     String name;
     String price;
     String imageUrl;

     String description;

    // Constructor
    public Product(String name, String price, String imageUrl,String description) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    // Parcelable implementation
    protected Product(Parcel in) {
        name = in.readString();
        price = in.readString();
        imageUrl = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(imageUrl);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
