package com.example.sabzimart.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sabzimart.Interface.ItemClickedListener;
import com.example.sabzimart.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView txtProduct_name,txtProduct_price,txtProduct_quantity;
    private ItemClickedListener itemClickedListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txtProduct_name=(TextView) itemView.findViewById(R.id.cart_product_name);
        txtProduct_price=(TextView)itemView.findViewById(R.id.cart_product_price);
        txtProduct_quantity=(TextView)itemView.findViewById(R.id.cart_product_quantity);
    }

    @Override
    public void onClick(View v) {
        itemClickedListener.onClick(v,getAdapterPosition(),false);

    }
    public void setItemClickedListener(ItemClickedListener itemClickedListener) {
        this.itemClickedListener = itemClickedListener;
    }
}
