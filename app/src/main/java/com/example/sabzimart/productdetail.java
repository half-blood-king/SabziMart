package com.example.sabzimart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sabzimart.Model.Products;
import com.example.sabzimart.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class productdetail extends AppCompatActivity {
   TextView product_dname,product_dprice,getProduct_dquantity;;
   ImageView image_d;
   Double Max;
   String PHONE;
   String Product_Name;
   Button plus,negative,add_to_cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetail);
        init();
        Product_Name=getIntent().getStringExtra("product");
        PHONE=getIntent().getStringExtra("phone");
        get_Product_Detail(Product_Name);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double a=Double.parseDouble(getProduct_dquantity.getText().toString());
                a=a+0.25;
                if(a<=Max)
                getProduct_dquantity.setText(String.valueOf(a));
                else
                    Toast.makeText(productdetail.this,"Value cannot get here than avaiable",Toast.LENGTH_SHORT).show();

            }
        });

        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double a=Double.parseDouble(getProduct_dquantity.getText().toString());
                a=a-0.25;
                if(a<0){
                    Toast.makeText(productdetail.this,"Value cannot get lower than zero",Toast.LENGTH_SHORT).show();
                }
                else
                getProduct_dquantity.setText(String.valueOf(a));
            }
        });
        //addtocart
        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_to_cart_list();
            }
        });
    }
    void add_to_cart_list(){
        String current_date,current_Time;
        Calendar calfordate=Calendar.getInstance();
        SimpleDateFormat currentdate= new SimpleDateFormat("MMM,dd,yyy");
        current_date=currentdate.format(calfordate.getTime());

        SimpleDateFormat currenttime= new SimpleDateFormat("HH,mm,ss a");
        current_Time=currenttime.format(calfordate.getTime());
       final  DatabaseReference cartlistRef=FirebaseDatabase.getInstance().getReference().child("Cart List");
        final HashMap<String,Object> cartdMap=new HashMap<>();
        cartdMap.put("date",current_date);
        cartdMap.put("time",current_Time);
        cartdMap.put("P_name",product_dname.getText().toString());
        cartdMap.put("P_price",product_dprice.getText().toString());
        cartdMap.put("P_quantity",getProduct_dquantity.getText().toString());
        cartlistRef.child("User View").child(PHONE).child(product_dname.getText().toString()).updateChildren(cartdMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //for admin purpose
                        if(task.isSuccessful()){
                            cartlistRef.child("Admin View").child(PHONE).child(product_dname.getText().toString()).updateChildren(cartdMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                Toast.makeText(productdetail.this, "Product add to CART LISt", Toast.LENGTH_SHORT).show();
                                                Intent bact_home=new Intent(productdetail.this,HomeActivity.class);
                                                bact_home.putExtra("phone",PHONE);
                                                bact_home.putExtra("category","user");
                                                startActivity(bact_home);
                                            }
                                        }
                                    });
                        }
                    }
                });




    }
    void get_Product_Detail(final String Key){
        DatabaseReference productReference= FirebaseDatabase.getInstance().getReference().child("Products");
        productReference.child(Key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    Products product_d = snapshot.getValue(Products.class);
                    product_dname.setText(Key);
                    product_dprice.setText(product_d.getProduct_price());
                    Max = Double.parseDouble(product_d.getProduct_quantity());
                    Picasso.get().load(product_d.getImage()).into(image_d);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    void init(){
        product_dname=findViewById(R.id.product_detail_name);
        product_dprice=findViewById(R.id.product_detail_price);
        image_d=findViewById(R.id.prouduct_detail_image);
        getProduct_dquantity=findViewById(R.id.avaiable);
        plus=findViewById(R.id.plus_button);
        negative=findViewById(R.id.negative_button);
        add_to_cart=findViewById(R.id.add_to_cart);
    }
}