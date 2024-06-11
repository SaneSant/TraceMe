package com.company.traceme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView username,email,school,countuser,busc;
    private String UserID;
    private FirebaseUser users;
    private DatabaseReference reference;
    private ImageView img,profile;
    private LinearLayout messageCat,buslocation,busd,traind;
    private  String greeting ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.imageView);
        profile = findViewById(R.id.imguser);
        countuser = findViewById(R.id.usercount);
        buslocation = findViewById(R.id.busloc);
        busd = findViewById(R.id.busdetails);
        busc = findViewById(R.id.buscount);




        busd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,BusDetailsScreen.class);
                startActivity(intent);
            }
        });

        buslocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,BusLocationScreen.class);
                startActivity(intent);
            }
        });

        messageCat = findViewById(R.id.msg);

        messageCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MessageScreen.class);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,profileScreen.class);
                startActivity(intent);
            }
        });




        users = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        UserID = users.getUid();

        final  TextView fullnameText = (TextView) findViewById(R.id.displayuser);


        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user userProfile = snapshot.getValue(user.class);
                if(userProfile != null)
                {
                    String Fullname = userProfile.fullname;

                    Calendar c = Calendar.getInstance();
                    int timeOfDay = c.get(Calendar.HOUR_OF_DAY);


                    if(timeOfDay >= 0 && timeOfDay < 12){

                        greeting = "ᵍᵒᵒᵈ ᴹᵒʳⁿⁱⁿᵍ";

                    }else if(timeOfDay >= 12 && timeOfDay < 16){

                        greeting = "ᴳᵒᵒᵈ ᴬᶠᵗᵉʳⁿᵒᵒⁿ";
                    }else if(timeOfDay >= 16 && timeOfDay < 21){

                        greeting = "ᴳᵒᵒᵈ ᴱᵛᵉⁿⁱⁿᵍ";
                    }else if(timeOfDay >= 21 && timeOfDay < 24){

                        greeting = "ᴳᵒᵒᵈ ᴺⁱᵍʰᵗ";
                    }



                    fullnameText.setText("Hi! " + Fullname + "\n" + greeting);




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference ref  = FirebaseDatabase.getInstance().getReference("Drivers");

//You can use the single or the value.. depending if you want to keep track
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()) {

                    countuser.setText(snap.getChildrenCount()+"");






                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        FirebaseDatabase db = FirebaseDatabase.getInstance();


        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mDatabaseRef.child("Drivers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count= dataSnapshot.getChildrenCount();

                busc.setText(dataSnapshot.getChildrenCount()+"");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });










    }
    @Override
    public void onBackPressed() {
        Dialog customDialog = new Dialog(this);
        customDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.getWindow().getAttributes().windowAnimations
                = android.R.style.Animation_Dialog;
        customDialog.setContentView(R.layout.progress_dialog);
        TextView yesbtn = customDialog.findViewById(R.id.btn_yes);
        TextView nobtn = customDialog.findViewById(R.id.btn_no);

        yesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
        nobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.cancel();
            }
        });
        customDialog.show();
    }
}