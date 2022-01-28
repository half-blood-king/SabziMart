package com.example.sabzimart.ViewHolder;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sabzimart.Interface.ItemClickedListener;
import com.example.sabzimart.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView productname,productprice,productquantity;
     public ImageView productimage;
     public ItemClickedListener product_listener;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        productname=(TextView)itemView.findViewById(R.id.product_name);
        productprice=(TextView)itemView.findViewById(R.id.product_price);
        productimage=(ImageView) itemView.findViewById(R.id.product_image);
        productquantity=(TextView)itemView.findViewById(R.id.product_quantity);
    }

    public void setProduct_listener(ItemClickedListener product_listener) {
        this.product_listener = product_listener;
    }

    @Override
    public void onClick(View v) {
        product_listener.onClick(v,getAdapterPosition(),false);
    }
}
