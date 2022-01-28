package com.example.sabzimart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sabzimart.Model.Order;
import com.example.sabzimart.R;
import com.example.sabzimart.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class deliveredorder extends AppCompatActivity {
    RecyclerView recyclerView_del;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveredorder);
        init();
    }
    @Override
    protected void onStart() {
        super.onStart();
        final DatabaseReference orderListReference= FirebaseDatabase.getInstance().getReference().child("DELIVERED Order");
        FirebaseRecyclerOptions<Order> option=new FirebaseRecyclerOptions.Builder<Order>().setQuery(orderListReference,Order.class).build();
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
            }
        };
        recyclerView_del.setAdapter(adapter);
        adapter.startListening();

    }
    void init(){
       recyclerView_del= findViewById(R.id.del_order_recyclerview);
        layoutManager=new LinearLayoutManager(this);
        recyclerView_del.setLayoutManager(layoutManager);
    }
    }