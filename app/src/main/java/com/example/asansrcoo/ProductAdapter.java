package com.example.asansrcoo;
import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

     Context context;
     List<Product> Products;

    public ProductAdapter(Context context, List<Product> Products) {
        this.context = context;
        this.Products = Products;
    }

    @Override
    public int getCount() {
        return Products.size();
    }

    @Override
    public Object getItem(int position) {
        return Products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        }

        Product product = Products.get(position);

        ImageView  productImageUrl = convertView.findViewById(R.id.product_image);
        TextView productName = convertView.findViewById(R.id.product_name);
        TextView productPrice = convertView.findViewById(R.id.product_price);

        productName.setText(product.getName());

        Glide.with(context)
                .load(product.getImageUrl())
                .apply(new RequestOptions().placeholder(R.drawable.placeholder).error(R.drawable.placeholder))
                .into(productImageUrl);

        productPrice.setText("price : " + product.getPrice() + "$");

        return convertView;
    }
}
