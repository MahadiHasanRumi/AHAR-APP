package com.example.aahar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreen extends AppCompatActivity {
    private DatabaseReference dRef;
    public static String name="",phone="";
    private String trimmedMail;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        dRef = FirebaseDatabase.getInstance().getReference();
        progressBar=findViewById(R.id.progressBarSplashScreen);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.rgb(14, 191, 97), android.graphics.PorterDuff.Mode.SRC_ATOP);

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String s= firebaseUser.getEmail();
        trimmedMail =  s.replace("@","");
        trimmedMail =  trimmedMail.replace(".","");

        dRef.child("users").child(trimmedMail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    ModelUser modelUser=snapshot.getValue(ModelUser.class);
                    name=modelUser.getName();
                    phone=modelUser.getPhone();
                    Intent i = new Intent(SplashScreen.this, HomePage.class);
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SplashScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}