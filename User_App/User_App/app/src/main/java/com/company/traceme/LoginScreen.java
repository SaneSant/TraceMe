package com.company.traceme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.airbnb.lottie.L;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {

    private EditText txtemail,txtpass;
    private TextView reg,log,forogt,gus;
    private FirebaseAuth mAuth;
    private  CheckBox rem;
    private ProgressBar pb;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        reg = findViewById(R.id.txtreg);
        txtemail = findViewById(R.id.logphone);
        txtpass = findViewById(R.id.logpass);
        log = findViewById(R.id.btnlog);
        mAuth = FirebaseAuth.getInstance();
        log.setOnClickListener(this);
        forogt = findViewById(R.id.forg);
        gus = findViewById(R.id.gust1);
        rem = findViewById(R.id.remeberlog);
        forogt.setOnClickListener(this);
        pb = findViewById(R.id.progressBar);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        reg .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginScreen.this,RegisterScreen.class);
                startActivity(intent);
            }
        });
        gus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginScreen.this,MainActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox = preferences.getString("remember","");
        if(checkbox.equals("true"))
        {
            Intent intent = new Intent(LoginScreen.this,MainActivity.class);
            startActivity(intent);

        }else if(checkbox.equals("false"))
        {

        }

        rem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked())
                {
                    SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                    // Toast.makeText(LoginActivity.this, "Checkd", Toast.LENGTH_SHORT).show();


                }else if(compoundButton.isChecked())
                {
                    SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                    //  Toast.makeText(LoginActivity.this, "not Checkd", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtreg:
                startActivity(new Intent(this, RegisterScreen.class));
                break;
            case R.id.btnlog:
                userlogin();
                break;
            case R.id.forg:
                startActivity(new Intent(this, FrogotPassScreen.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private void userlogin() {
        String logclinetemail = txtemail.getText().toString().trim();
        String clinetlogpass = txtpass.getText().toString().trim();

        if(logclinetemail.isEmpty())
        {
            txtemail.setError("Please Enter Your Email");
            txtemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(logclinetemail).matches())
        {
            txtemail.setError("Please Enter Valid Email Address");
            txtemail.requestFocus();
            return;
        }
        if(clinetlogpass.isEmpty())
        {
            txtpass.setError("Please Enter Your Password");
            txtpass.requestFocus();
            return;
        }
        pb.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(logclinetemail,clinetlogpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {

                    Intent intent = new Intent(LoginScreen.this,MainActivity.class);
                    startActivity(intent);
                    pb.setVisibility(View.INVISIBLE);

                }else
                {
                    pb.setVisibility(View.INVISIBLE);
                    Toast.makeText(LoginScreen.this, "Sorry! Login failed.", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}