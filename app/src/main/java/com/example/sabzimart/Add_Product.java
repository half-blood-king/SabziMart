package com.example.sabzimart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class Add_Product extends AppCompatActivity {
    ImageView imageView[]=new ImageView[12];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__product);
        init();

    }
    void init(){

        imageView[0]=(ImageView)findViewById(R.id.potato);
        imageView[1]=(ImageView)findViewById(R.id.onion);
        imageView[2]=(ImageView)findViewById(R.id.carrot);
        imageView[3]=(ImageView)findViewById(R.id.garlic);
        imageView[4]=(ImageView)findViewById(R.id.peas);
        imageView[5]=(ImageView)findViewById(R.id.Brussel);
        imageView[6]=(ImageView)findViewById(R.id.green);
        imageView[7]=(ImageView)findViewById(R.id.tomato);
        imageView[8]=(ImageView)findViewById(R.id.Gobli);
        imageView[9]=(ImageView)findViewById(R.id.chilli);
        imageView[10]=(ImageView)findViewById(R.id.eggplant);
        imageView[11]=(ImageView)findViewById(R.id.cumber);
    }
    public void ProductSelect(View v){

        v.setDrawingCacheEnabled(true);
        Bitmap b=v.getDrawingCache();
        Intent Product_Detail=new Intent(Add_Product.this,Product_Set.class);
        //send the Vegatable name
        Product_Detail.putExtra("name",v.getTag().toString());
        //Vegtable Image
        Product_Detail.putExtra("image",b);
        startActivity(Product_Detail);
    }
}