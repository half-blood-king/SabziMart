package com.example.sabzimart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button Signup=findViewById(R.id.joinnow);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SignUp=new Intent(MainActivity.this,Signup.class);
                Toast.makeText(MainActivity.this,"New Activity starting",Toast.LENGTH_SHORT).show();
                startActivity(SignUp);
            }
        });
        Button login=findViewById(R.id.ma_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Logins=new Intent(MainActivity.this, Login.class);
                startActivity(Logins);
            }
        });
    }
}