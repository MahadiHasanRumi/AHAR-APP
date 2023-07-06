package com.example.aahar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {
    private TextView text;
    private FirebaseAuth mAuth;
    private Button donate,activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mAuth=FirebaseAuth.getInstance();
        text=findViewById(R.id.textView);
        text.setText("Hi "+SplashScreen.name);
        donate=findViewById(R.id.buttonDonate);
        activity=findViewById(R.id.buttonActivity);
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),Donate.class ));
            }
        });
        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Activity.class ));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logOut){
            mAuth.signOut();
            startActivity(new Intent(getApplicationContext(),SignIn.class) );
        }
        if(item.getItemId()==R.id.myPosts){
            startActivity(new Intent(getApplicationContext(),MyPosts.class));
        }
        return super.onOptionsItemSelected(item);
    }
}