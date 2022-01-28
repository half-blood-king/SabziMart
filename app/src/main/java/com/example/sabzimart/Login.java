package com.example.sabzimart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sabzimart.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Login extends AppCompatActivity {
    EditText input_pw,input_phone;
    Button Logins;
    String Parentdb;
    TextView admin,notadmin;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        //Admin Flag
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Parentdb="Admins";
                admin.setVisibility(View.INVISIBLE);
                notadmin.setVisibility(View.VISIBLE);
                Logins.setText("Login as Admin");
            }
        });
        //Not Admin text View
        notadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Parentdb="Users";
                notadmin.setVisibility(View.INVISIBLE);
                admin.setVisibility(View.VISIBLE);
                Logins.setText("Login");
            }
        });
        //User Login Button
        Logins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setTitle("Log into Account");
                loading.setMessage("Please wait while we check crediablity of account");
                loading.show();
                loading.setCancelable(false);
                Userlogin();
            }
        });
    }
    void Userlogin(){
        //password field text
        String pw=input_pw.getText().toString();
        //Phone Number Field Text
        String ph=input_phone.getText().toString();
        if(pw.isEmpty()){
            Toast.makeText(Login.this,"Please Enter the Password",Toast.LENGTH_SHORT).show();
            loading.dismiss();
        }
        else if(ph.isEmpty()){
            Toast.makeText(Login.this,"Please Enter the Phone Number",Toast.LENGTH_SHORT).show();
            loading.dismiss();
        }
        //else check the user is user exit
        else{
           CheckUser(ph,pw);
        }

    }
    void CheckUser(final String phon,final String Pw){
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //User exit or not
                if (snapshot.child(Parentdb).child(phon).exists()) {
                    //Data into user Model Class
                    Users data=snapshot.child(Parentdb).child(phon).getValue(Users.class);
                    if(data.getPhone().equals(phon)){
                        {
                            if(data.getPassword().equals(Pw)){
                               Toast.makeText(Login.this,"Logined Successfully",Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                            if(Parentdb.equals("Admins")){
                                Intent addProduct=new Intent(Login.this,admin_opion.class);
                                startActivity(addProduct);
                            }
                            else { Intent home=new Intent(Login.this,HomeActivity.class);
                            home.putExtra("phone",phon);
                                home.putExtra("category","user");startActivity(home);}
                            }
                            //Check Password
                            else{ Toast.makeText(Login.this,"Password Incorrect",Toast.LENGTH_SHORT).show();
                            loading.dismiss();}
                        } } }
                else{
                    Toast.makeText(Login.this,"No Such UsserExit ",Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }

        }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    void init(){
        //Password
        input_pw=findViewById(R.id.pw);
        //Phone Number
        input_phone=(findViewById(R.id.Phone_no));
        //Login Button
        Logins=findViewById(R.id.login);
        loading=new ProgressDialog(this);
        Parentdb="Users";
        admin=findViewById(R.id.adminflag);
        //not admin text view
        notadmin=findViewById(R.id.notAdmin);

    }
}