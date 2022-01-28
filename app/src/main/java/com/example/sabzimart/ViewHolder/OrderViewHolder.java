package com.example.sabzimart.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sabzimart.Interface.ItemClickedListener;
import com.example.sabzimart.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
   public TextView admin_pname,admin_pquantity,admin_Name,admin_time,admin_date,admin_price,admin_phone,admin_address,state;

    public void setListener(ItemClickedListener listener) {
        this.listener = listener;
    }

    ItemClickedListener listener;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        admin_pname=(TextView)itemView.findViewById(R.id.admin_product_name);
        admin_pquantity=(TextView)itemView.findViewById(R.id.admin_product_quantity);
        admin_time=(TextView)itemView.findViewById(R.id.time);
        admin_date=(TextView)itemView.findViewById(R.id.date);
        admin_price=(TextView)itemView.findViewById(R.id.admin_product_price);
        admin_Name=(TextView)itemView.findViewById(R.id.admin_Name);
        admin_phone=(TextView)itemView.findViewById(R.id.order_phone_no);
        admin_address=(TextView)itemView.findViewById(R.id.admin_address);
        state=itemView.findViewById(R.id.admin_state);

    }

    @Override
    public void onClick(View v) {
        listener.onClick(v,getAdapterPosition(),false);
    }
}
