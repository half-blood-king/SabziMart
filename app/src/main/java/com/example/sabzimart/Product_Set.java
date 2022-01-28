package com.example.sabzimart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;


public class Product_Set extends AppCompatActivity {
    EditText Product_price,Product_quantity;
    TextView Product_name;
    String P_NAME,P_PRICE,P_QUANTITY,ImageUrl;
    Button Add_To_item,Check_order;
    ImageView mainImage;
    Uri imageUri;
    Boolean flag=false;
     StorageReference ProductImagesRef;
     ProgressDialog loading;
     DatabaseReference ProductReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__set);
        init();
        //get the data from previous activity
        Intent data=getIntent();
        Product_name.setText(data.getStringExtra("name"));
        //get images
       Bitmap bitmap=data.getParcelableExtra("image");
       //set image
        mainImage.setImageBitmap(bitmap);
        mainImage.setClickable(true);
        mainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChoseImage(); }});
        Add_To_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreInformation();
            }
        });

    }
    void StoreInformation() {
        P_NAME = Product_name.getText().toString();
        P_PRICE = Product_price.getText().toString();
        P_QUANTITY = Product_quantity.getText().toString();
        if (P_PRICE.isEmpty()) {
            Toast.makeText(Product_Set.this, "Please fill Price field", Toast.LENGTH_SHORT).show();
        } else if (P_QUANTITY.isEmpty()) {  Toast.makeText(Product_Set.this, "Please fill Quantity", Toast.LENGTH_SHORT).show();}
        else{
            Uploadimage();
        }
    }
    void Uploadimage(){
        loading.setTitle("Image Uploading");
        loading.setMessage("please Wait while we upload Product image");
        loading.show();
        //Create name for the branch
        final StorageReference filePath=ProductImagesRef.child(P_NAME+".png");
        //Put the data in the form bytes
        final UploadTask uploadTask;



            mainImage.setDrawingCacheEnabled(true);
            mainImage.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) mainImage.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
        filePath.putBytes(data).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return filePath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    ImageUrl = task.getResult().toString();
                    Toast.makeText(Product_Set.this, "Image Uploading", Toast.LENGTH_SHORT).show();
                    savedata();
                } else {
                    Toast.makeText(Product_Set.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void savedata(){
        HashMap<String,Object> product=new HashMap<>();
        product.put("product_name",P_NAME);
        product.put("image",ImageUrl);
        product.put("product_price",P_PRICE);
        product.put("product_quantity",P_QUANTITY);
        ProductReference.child(P_NAME).updateChildren(product).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    loading.dismiss();Toast.makeText(Product_Set.this, "data Uploaded", Toast.LENGTH_SHORT).show();}
                else{
                    loading.dismiss();Toast.makeText(Product_Set.this, task.getException().toString(), Toast.LENGTH_SHORT).show();}
            }
        });
    }
    void ChoseImage(){
        Intent gallery = new Intent();
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        startActivityForResult(gallery, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            flag=true;
            imageUri = data.getData();
            mainImage.setImageURI(imageUri);
        }
    }

    void init(){
        Product_name=findViewById(R.id.edn_name);
        Product_quantity=findViewById(R.id.ed_quanlity);
        Product_price=findViewById(R.id.ed_price);
        Add_To_item=findViewById(R.id.bt_add);
        mainImage=findViewById(R.id.product_image);
        loading=new ProgressDialog(this);
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductReference = FirebaseDatabase.getInstance().getReference().child("Products");
    }
}