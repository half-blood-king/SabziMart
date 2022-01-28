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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class updateproduct extends AppCompatActivity {
    EditText produtq_Set,product_p_Set;
    TextView produtn_Set;
    Button Set_product;
    String P_name;
    String prouct_name,prouct_quantity,prouct_price;
    DatabaseReference ProductReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateproduct);
        init();
        produtn_Set.setText(P_name);
        Set_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateInfo();
            }
        });
    }
    void   UpdateInfo() {
        prouct_name = produtn_Set.getText().toString();
        prouct_price = product_p_Set.getText().toString();
        prouct_quantity =produtq_Set.getText().toString();
        if (prouct_price.isEmpty()) {
            Toast.makeText(updateproduct.this, "Please fill Price field", Toast.LENGTH_SHORT).show();
        } else if (prouct_quantity.isEmpty()) {  Toast.makeText(updateproduct.this, "Please fill Quantity", Toast.LENGTH_SHORT).show();}
        else{
            Updatedata(prouct_name,prouct_price,prouct_quantity);
        }
    }

    private void Updatedata(String pro_name, String pro_price, String pro_quantity) {
        HashMap<String,Object> product=new HashMap<>();
        product.put("product_name",pro_name);
        product.put("product_price",pro_price);
        product.put("product_quantity",pro_quantity);
        ProductReference.child(pro_name).updateChildren(product).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(updateproduct.this, "data Uploaded", Toast.LENGTH_SHORT).show();
                    Intent viewor=new Intent(updateproduct.this,HomeActivity.class);
                    viewor.putExtra("category","admin");
                    startActivity(viewor);
                }
                else{
                    Toast.makeText(updateproduct.this, task.getException().toString(), Toast.LENGTH_SHORT).show();}
            }
        });
    }

    void init(){
        product_p_Set=findViewById(R.id.product_set_price);
        produtq_Set=findViewById(R.id.product_set_quantity);
        produtn_Set=findViewById(R.id.product_set_name);
        P_name=getIntent().getStringExtra("product");
        Set_product=findViewById(R.id.set_productset_bt);
        ProductReference = FirebaseDatabase.getInstance().getReference().child("Products");
    }
}