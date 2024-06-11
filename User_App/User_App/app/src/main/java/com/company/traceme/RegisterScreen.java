package com.company.traceme;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;


public class RegisterScreen extends AppCompatActivity implements View.OnClickListener {

    private  EditText username,school,email,password;
    private TextView regbtn,log;
    private  CheckBox chk;
    private  FirebaseAuth mAuth;
    private ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        username = (EditText) findViewById(R.id.username);
        school =(EditText)findViewById(R.id.userphone);
        email = (EditText)findViewById(R.id.useremail);
        password = (EditText)findViewById(R.id.pass);
        regbtn = (TextView) findViewById(R.id.btnSend);
        chk = findViewById(R.id.privacy);
        mAuth = FirebaseAuth.getInstance();
        log = findViewById(R.id.txt_new);
        regbtn.setOnClickListener(this);
        pb = findViewById(R.id.progressBar);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterScreen.this,LoginScreen.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSend:
                registeruser();
                break;
        }
    }
    private void registeruser()
    {
        String name = username.getText().toString().trim();
        String sch = school.getText().toString().trim();
        String clientemail = email.getText().toString().trim();
        String clientpass = password.getText().toString().trim();


        if(name.isEmpty())
        {
            username.setError("Please Enter Your Name.");
            username.requestFocus();
            return;
        }
        if(sch.isEmpty())
        {
            school.setError("Please Enter Your Phone Number");
            school.requestFocus();
            return;
        }
        if(clientemail.isEmpty())
        {
            email.setError("Please Enter Your Email Address");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(clientemail).matches())
        {
            email.setError("Please Enter Valid Email");
            email.requestFocus();
            return;
        }
        if(clientpass.isEmpty())
        {
            password.setError("Please Enter New Password");
            password.requestFocus();
            return;
        }


        pb.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(clientemail,clientpass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            user use = new user(name,sch,clientemail);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(use).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(RegisterScreen.this, "best wishes! Your account has been successfully created.", Toast.LENGTH_SHORT).show();
                                                pb.setVisibility(View.GONE);
                                               // ActivityCompat.requestPermissions(RegisterScreen.this,new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);
                                                startActivity(new Intent(RegisterScreen.this, LoginScreen.class));

                                            }else
                                            {
                                                Toast.makeText(RegisterScreen.this, "Sorry! Your account has not been created, please try again.", Toast.LENGTH_SHORT).show();
                                                pb.setVisibility(View.GONE);
                                            }
                                        }
                                    });

                        }else
                        {
                            Toast.makeText(RegisterScreen.this, "Sorry! Your account has not been created, please try again..", Toast.LENGTH_SHORT).show();
                            pb.setVisibility(View.GONE);

                        }
                    }
                });


    }



}
