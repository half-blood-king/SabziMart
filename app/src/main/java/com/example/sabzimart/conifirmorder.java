package com.example.sabzimart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static android.widget.Toast.LENGTH_LONG;

public class conifirmorder extends AppCompatActivity {
     TextView conf_total_price;
     EditText conf_cname,conf_cphone,conf_caddress,conf_houseno;
    Button Conifirm_bt;
    String PHONE,cust_Name,cust_phone,cust_address,cust_houseno;
    ArrayList<String> P_list,P_quantity;
    Boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conifirmorder);
        init();
        get_intent_data();
        Conifirm_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatedata();
            }
        });

    }
    void validatedata() {
        cust_Name=conf_cname.getText().toString();
        cust_address=conf_caddress.getText().toString();
        cust_phone=conf_cphone.getText().toString();
        cust_houseno=conf_houseno.getText().toString();
        if(cust_Name.isEmpty()){
            Toast.makeText(conifirmorder.this,"PLEASE FILL ALL THE FIELDS",Toast.LENGTH_SHORT).show();
        }
        else if(cust_address.isEmpty()){
            Toast.makeText(conifirmorder.this,"PLEASE FILL ALL THE FIELDS",Toast.LENGTH_SHORT).show();
        }
        else if(cust_phone.isEmpty()){
            Toast.makeText(conifirmorder.this,"PLEASE FILL ALL THE FIELDS",Toast.LENGTH_SHORT).show();
        }
        else if(cust_houseno.isEmpty()){
            Toast.makeText(conifirmorder.this,"PLEASE FILL ALL THE FIELDS",Toast.LENGTH_SHORT).show();
        }
        else{
            ConifirmOrder(cust_Name,cust_address,cust_phone,cust_houseno);
        }
    }
    void get_intent_data(){
        P_list = (ArrayList<String>) getIntent().getSerializableExtra("Product List");
        P_quantity = (ArrayList<String>) getIntent().getSerializableExtra("quantity list");
        conf_total_price.setText(getIntent().getStringExtra("total"));
        PHONE=getIntent().getStringExtra("phone");
    }

    private void ConifirmOrder(final String customer_name, final String customer_address, final String customer_phone,final String customer_houseno) {

        String current_date,current_Time;
        Calendar calfordate=Calendar.getInstance();
        SimpleDateFormat currentdate= new SimpleDateFormat("MMM,dd,yyy");
        current_date=currentdate.format(calfordate.getTime());

        SimpleDateFormat currenttime= new SimpleDateFormat("HH,mm,ss a");
        current_Time=currenttime.format(calfordate.getTime());
        final DatabaseReference orderlistRef= FirebaseDatabase.getInstance().getReference().child("Order").child(customer_phone);
        final HashMap<String,Object> orderdMap=new HashMap<>();
        orderdMap.put("Date",current_date);
        orderdMap.put("Time",current_Time);
        orderdMap.put("Product_NAMES",get_List_String(P_list));
        orderdMap.put("Product_Quantity",get_List_String(P_quantity));
        orderdMap.put("Address",customer_address);
        orderdMap.put("Phone",customer_phone);
        orderdMap.put("House",customer_houseno);
        orderdMap.put("Price",conf_total_price.getText().toString());
        orderdMap.put("Name",customer_name);
        orderdMap.put("State","Pending");
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
                                        Toast.makeText(conifirmorder.this, "Order Placed ", LENGTH_LONG).show();
                                        int i=0;
                                        for (String a:P_list) {

                                            updatedb(a,i);
                                            i++;
                                        } }

                                    }
                            });
                }
            }
        });

    }
    void updatedb(final String a, final int i)
    {
        final DatabaseReference updateproduct = FirebaseDatabase.getInstance().getReference("Products").child(a);
        final  Double quantity_sub = Double.valueOf(P_quantity.get(i));
        updateproduct.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(!flag){
                Double quantity_total = Double.valueOf(snapshot.child("product_quantity").getValue().toString());
                Double quantity_real = quantity_total - quantity_sub;
                updateproduct.child("product_quantity").setValue(quantity_real.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if(i==(P_list.size()-1)) {
                                Toast.makeText(conifirmorder.this, "Product Data Updated ", LENGTH_LONG).show();
                                Intent home = new Intent(conifirmorder.this, HomeActivity.class);
                                home.putExtra("phone", PHONE);
                                home.putExtra("category", "user");
                                home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(home);
                            }
                            flag = true;

                        } else {
                            Toast.makeText(conifirmorder.this, task.getException().toString(), LENGTH_LONG).show();
                        }
                    }
                });
                }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(conifirmorder.this, error.toString(), LENGTH_LONG).show();
            }
        });
}

    String get_List_String(ArrayList<String>list){
        String List_String="";
        int i=0;
        while(i!=list.size()) {
            List_String = List_String + list.get(i).toString()+",";
            i++;
        }
        return List_String;
    }
    void init(){
        conf_total_price=findViewById(R.id.conifirm_total_price);
        conf_cname=findViewById(R.id.conifirm_Cname);
        conf_caddress=findViewById(R.id.conifirm_Caddress);
        conf_cphone=findViewById(R.id.conifirm_Cphone);
        conf_houseno=findViewById(R.id.conifirm_Chouseno);
        Conifirm_bt=findViewById(R.id.bt_confirm);
        P_list=new ArrayList<>();
        P_quantity=new ArrayList<>();
    }
}