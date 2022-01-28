package com.example.sabzimart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Signup extends AppCompatActivity {
    EditText user,password,phone;
    Button signup;
    ProgressDialog loading;
    String parentDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        user=(EditText)findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        password=findViewById(R.id.password);
        signup=findViewById(R.id.signup);
        loading=new ProgressDialog(this);
        //parentDb="Admins";
       signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               CreateAccount();
            }
        });
    }
    private void CreateAccount(){
        String name=user.getText().toString();
        String pass=password.getText().toString();
        String phone1=phone.getText().toString();
        if(name.isEmpty())
        {
            Toast.makeText(this,"Please Enter the YOUR name",Toast.LENGTH_SHORT).show();
        }
        else if(phone1.isEmpty())
        {
            Toast.makeText(this,"Please Enter your phone number",Toast.LENGTH_SHORT).show();
        }
        else if(pass.isEmpty())
        {
            Toast.makeText(this,"Please enter your password",Toast.LENGTH_SHORT).show();
        }
        else{
            loading.setTitle("Create Account");
            loading.setMessage("Please wait while we create your account");
            loading.show();
            AddAccount(name,phone1,pass);
        }
    }
void AddAccount(final String name,final String Phone,final String Password){

        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child("Users").child(Phone).exists())){
                    HashMap<String,Object> userhash=new HashMap<>();
                    userhash.put("phone",Phone);
                    userhash.put("name",name);
                    userhash.put("password",Password);
                    RootRef.child("Users").child(Phone).updateChildren(userhash).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                         if(task.isSuccessful()){
                             loading.dismiss();
                             Toast.makeText(Signup.this,"Congrats account is created",Toast.LENGTH_SHORT).show();
                             Intent activity=new Intent(Signup.this,MainActivity.class);
                             startActivity(activity);
                         }
                         else{
                             loading.dismiss();
                             Toast.makeText(Signup.this,"Unable to Create Try again",Toast.LENGTH_SHORT).show();
                         }
                        }
                    });
                }
                else{
                    Toast.makeText(Signup.this,"Account already exit on this phone number",Toast.LENGTH_LONG).show();
                    Intent activity=new Intent(Signup.this,MainActivity.class);
                    startActivity(activity);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
}

}