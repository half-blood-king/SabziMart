package com.example.sabzimart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sabzimart.Model.Cart;
import com.example.sabzimart.Model.Products;
import com.example.sabzimart.ViewHolder.CartViewHolder;
import com.example.sabzimart.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT;


public class cart extends AppCompatActivity {
    TextView cart_total_price;
    String PHONE;
    Button cart_next_btn;
    RecyclerView cart_recycler_view;
    double Total_price=0;
    ArrayList<String> ProductList;
    ArrayList<String> quantityList;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        init();
        PHONE=getIntent().getStringExtra("phone");
        cart_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              cart_total_price.setText("Total Price ="+Total_price);
                Intent conifirm=new Intent(cart.this,conifirmorder.class);
                conifirm.putExtra("total",String.valueOf(Total_price));
                conifirm.putExtra("phone",PHONE);
                conifirm.putExtra("Product List",ProductList);
                conifirm.putExtra("quantity list",quantityList);
                startActivity(conifirm);

            }
        });
    }
    @Override
    protected void onStart() {

        super.onStart();
        final DatabaseReference cartListReference= FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart> options=new FirebaseRecyclerOptions.Builder<Cart>().setQuery(cartListReference.child("User View").child(PHONE),Cart.class).build();
        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter= new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items, parent, false);
                 CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
            @Override
            protected void onBindViewHolder(@NonNull final CartViewHolder cartViewHolder, int i, @NonNull final Cart cart) {
                cartViewHolder.txtProduct_name.setText("Product Name:"+cart.getP_name());
                cartViewHolder.txtProduct_price.setText("Rs: "+cart.getP_price());
                cartViewHolder.txtProduct_quantity.setText("Quantity= "+cart.getP_quantity()+" kg");
                ProductList.add(cart.getP_name());
                quantityList.add(cart.getP_quantity());
                cart_next_btn.setClickable(true);
                double singlevalue=Double.valueOf(cart.getP_price())*Double.valueOf(cart.getP_quantity());
                Total_price=Total_price+singlevalue;
                cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(cart.this);
                        CharSequence options[]=new CharSequence[]{"Edit","discart"};
                        builder.setTitle("CART ITEMS");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which==0) {
                                    Intent edit_activity=new Intent(cart.this,productdetail.class);
                                    edit_activity.putExtra("product",cart.getP_name());
                                    edit_activity.putExtra("phone",PHONE);
                                    startActivity(edit_activity);
                                }
                                else if(which==1){
                                    cartListReference.child("User View").child(PHONE).child(cart.getP_name()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(cart.this,"item remove from the cart successfully",Toast.LENGTH_SHORT).show();
                                                Intent home_activity=new Intent(cart.this,HomeActivity.class);
                                                home_activity.putExtra("phone",PHONE);
                                                startActivity(home_activity);
                                            } }}); } }});
                        builder.show(); }

                });

            }
        };
        cart_recycler_view.setAdapter(adapter);
        adapter.startListening();
    }

    void init(){
        cart_total_price=findViewById(R.id.cart_total_price);
        cart_next_btn=findViewById(R.id.cart_next_bt);
        cart_recycler_view=findViewById(R.id.cart_recyclerview);
        layoutManager=new LinearLayoutManager(this);
        cart_recycler_view.setLayoutManager(layoutManager);
        ProductList=new ArrayList<>();
        quantityList=new ArrayList<>();


    }
}