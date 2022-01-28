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
import android.widget.Toast;

import com.example.sabzimart.Model.Cart;
import com.example.sabzimart.Model.Order;
import com.example.sabzimart.ViewHolder.CartViewHolder;
import com.example.sabzimart.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static android.widget.Toast.LENGTH_LONG;

public class orders extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    String PHONE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        init();
    }
    @Override
    protected void onStart() {
        super.onStart();
        final DatabaseReference orderListReference= FirebaseDatabase.getInstance().getReference().child("Order");
        FirebaseRecyclerOptions<Order>option=new FirebaseRecyclerOptions.Builder<Order>().setQuery(orderListReference,Order.class).build();
        FirebaseRecyclerAdapter<Order, OrderViewHolder> adapter= new FirebaseRecyclerAdapter<Order, OrderViewHolder>(option) {
            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_order, parent, false);
                OrderViewHolder holder = new OrderViewHolder(view);
                return holder;
            }

            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder orderViewHolder, int i, @NonNull final Order order) {
                orderViewHolder.admin_address.setText("Address :"+order.getAddress());
                orderViewHolder.admin_pquantity.setText("Quantity :"+order.getProduct_Quantity());
                orderViewHolder.admin_date.setText("date :"+order.getDate());
                orderViewHolder.admin_pname.setText("Products:"+order.getProduct_NAMES());
                orderViewHolder.admin_time.setText("time"+order.getTime());
               orderViewHolder.admin_price.setText("Price:"+order.getPrice()+"rs");
                orderViewHolder.admin_phone.setText("Phone:"+order.getPhone());
                orderViewHolder.admin_Name.setText("Name:"+order.getName());
                orderViewHolder.state.setText(order.getState());
                PHONE=getRef(i).getKey();
                orderViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(orders.this);
                        CharSequence options[]=new CharSequence[]{"Delivered","pending"};
                        builder.setTitle("Order ITEMS");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which==1) {
                                }
                                else if(which==0){
                                    Delivery(order.getName(),order.getAddress(),order.getProduct_NAMES(),order.getProduct_Quantity(),order.getPhone(),order.getHouse(),order.getPrice(),order.getDate(),order.getTime());
                                    orderListReference.child(order.getPhone()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                                Toast.makeText(orders.this,"Order delivered Sucessfully",Toast.LENGTH_SHORT).show();
                                                finish();
                                                startActivity(getIntent());
                                            } }}); } }});
                        builder.show(); }

                });


            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
    private void Delivery(final String customer_name, final String customer_address, final String customer_pname,final String pq,String customer_phone,final String customer_houseno,final String price,final String cust_Date,final String cust_time) {
        final DatabaseReference orderlistRef= FirebaseDatabase.getInstance().getReference().child("DELIVERED Order").child(customer_phone);
        final HashMap<String,Object> orderdMap=new HashMap<>();
        orderdMap.put("Date",cust_Date);
        orderdMap.put("Time",cust_time);
        orderdMap.put("Product_NAMES",customer_pname);
        orderdMap.put("Product_Quantity",pq);
        orderdMap.put("Address",customer_address);
        orderdMap.put("Phone",customer_phone);
        orderdMap.put("House",customer_houseno);
        orderdMap.put("Price",price);
        orderdMap.put("Name",customer_name);
        orderdMap.put("State","Delivered");
        orderlistRef.updateChildren(orderdMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    FirebaseDatabase.getInstance().getReference("Cart List").child("User View").removeValue().
                            addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if(task.isSuccessful()) {

                                        } }

                            });
                }
            }
        });

    }
    void init(){
        recyclerView=findViewById(R.id.order_recyclerview);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
}