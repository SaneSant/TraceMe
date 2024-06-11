package com.company.traceme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import org.w3c.dom.Text;

public class MessageScreen extends AppCompatActivity {

    private String UserID;
    private FirebaseUser users;
    private DatabaseReference reference;
    private EditText busno,message;
    private TextView email,send,adminMai;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_screen);

        users = FirebaseAuth.getInstance().getCurrentUser();
        busno = findViewById(R.id.busno1);
        message = findViewById(R.id.usermsg);
        email = findViewById(R.id.useremail);
        send = findViewById(R.id.btnSend);
        adminMai =findViewById(R.id.admail);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" +adminMai.getText().toString()));
                intent.putExtra(Intent.EXTRA_SUBJECT,busno.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT,message.getText().toString());
                startActivity(intent);

            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Users");
        UserID = users.getUid();


        final  TextView fullemailText = (TextView) findViewById(R.id.useremail);


        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user userProfile = snapshot.getValue(user.class);
                if(userProfile != null)
                {

                    String email = userProfile.emails;



                    fullemailText.setText(email);




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}