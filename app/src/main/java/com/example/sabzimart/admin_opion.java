package com.example.sabzimart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class admin_opion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_opion);
        Button Add_Product=findViewById(R.id.bt_Add_Product);
        Button view_order=findViewById(R.id.newOrder_bt);
        Button view_product=findViewById(R.id.set_product_bt);
        Button delivery_order=findViewById(R.id.delivered_button);
        Add_Product.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addproduct=new Intent(admin_opion.this,Add_Product.class);
                startActivity(addproduct);
            }
        });
        view_order.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewor=new Intent(admin_opion.this,orders.class);
                startActivity(viewor);
            }
        });
        view_product.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewor=new Intent(admin_opion.this,HomeActivity.class);
                viewor.putExtra("category","admin");
                startActivity(viewor);

            }
        });
        delivery_order.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewor=new Intent(admin_opion.this,deliveredorder.class);
                startActivity(viewor);
            }
        });

    }
}