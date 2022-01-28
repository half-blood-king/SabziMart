package com.example.sabzimart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sabzimart.Model.Products;
import com.example.sabzimart.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import static android.widget.Toast.LENGTH_SHORT;

public class HomeActivity extends AppCompatActivity {
    DatabaseReference ProductsReference;
    FloatingActionButton go_to_cart;
     RecyclerView recyclerView;
     String PHONE;
    String category;
    FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        PHONE= getIntent().getStringExtra("phone");
        category=getIntent().getStringExtra("category");
        if(category.equals("admin")){
            go_to_cart.setVisibility(View.INVISIBLE);
        }
        else{
            go_to_cart.setVisibility(View.VISIBLE);
        }
        go_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cart_activity=new Intent(HomeActivity.this,cart.class);
                cart_activity.putExtra("phone",PHONE);
                startActivity(cart_activity);
            }
        });
        Picasso.get().setLoggingEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Products> options=new FirebaseRecyclerOptions.Builder<Products>().setQuery(ProductsReference,Products.class).build();
       /* FirebaseRecyclerAdapter<Products, ProductViewHolder> */ adapter=new FirebaseRecyclerAdapter<Products,ProductViewHolder>(options){
            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
                ProductViewHolder holder = new ProductViewHolder(view);
                return holder;
            }

            @Override
            protected void onBindViewHolder(@NonNull final ProductViewHolder productViewHolder, int i, @NonNull final Products products) {
                productViewHolder.productname.setText(products.getProduct_name());
                productViewHolder.productprice.setText("Price is :"+products.getProduct_price()+" rs");
                productViewHolder.productquantity.setText("Available Quantity is :"+products.getProduct_quantity());
                Picasso.get().load(products.getImage()).into(productViewHolder.productimage);
                productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(category.equals("admin")){
                            Intent product_detail=new Intent(HomeActivity.this,updateproduct.class);
                            product_detail.putExtra("product",products.getProduct_name());
                            startActivity(product_detail);
                        }
                        else {
                            Intent product_detail = new Intent(HomeActivity.this, productdetail.class);
                            product_detail.putExtra("product", products.getProduct_name());
                            product_detail.putExtra("phone", PHONE);
                            startActivity(product_detail);
                        }
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
        if(adapter == null){
            Toast.makeText(HomeActivity.this,"No data found",LENGTH_SHORT).show();
        }
        else
        adapter.startListening();

    }
    @Override
    protected void onStop() {
        super.onStop();
        if(adapter != null) {
            adapter.stopListening();
        }
    }

    void init(){

        ProductsReference = FirebaseDatabase.getInstance().getReference().child("Products");
        recyclerView = findViewById(R.id.recyclerview);
        go_to_cart=findViewById(R.id.go_to_cart);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);}
}