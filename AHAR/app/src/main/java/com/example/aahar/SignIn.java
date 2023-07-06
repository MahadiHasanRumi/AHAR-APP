package com.example.aahar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {
    private EditText userName,pass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth=FirebaseAuth.getInstance();
        userName=findViewById(R.id.signInUserName);
        pass=findViewById(R.id.signInPass);

    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null){
            startActivity(new Intent(this,SplashScreen.class));
            finish();
        }
    }

    public void signUp(View view) {

        Intent intent=new Intent(this,PhoneNumberVerify.class);
        startActivity(intent);
    }

    public void signInButton(View view) {
        String name=userName.getText().toString().trim();
        String password=pass.getText().toString().trim();
        if(name.isEmpty()){
            userName.setError("Enter number");
            userName.requestFocus();
            return;
        }
        if(name.length()!=11){
            userName.setError("Not valid");
            userName.requestFocus();
            return;
        }
        if(password.isEmpty()){
            pass.setError("Enter password");
            pass.requestFocus();
            return;
        }
        if(password.length()<6){
            pass.setError("At least 6 digit");
            pass.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword("q+88"+name+"@gmail.com",password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(SignIn.this, "Successfully signed in..", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),SplashScreen.class));
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e.getMessage().equals("There is no user record corresponding to this identifier. The user may have been deleted.")){
                    userName.setError("Wrong Number");
                    userName.requestFocus();
                }
                else if(e.getMessage().equals("The password is invalid or the user does not have a password.")){
                    pass.setError("Wrong Password");
                    pass.requestFocus();
                }
            }
        });
    }

    public void forgetPass(View view) {
        Intent intent=new Intent(this,PhoneNumberVerify.class);
        intent.putExtra("fromWhere","forgetPass");
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialogExit = new AlertDialog.Builder(SignIn.this);

        dialogExit.setTitle("EXIT!!");
        dialogExit.setMessage("Are you sure?");

        dialogExit.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();
                finish();
            }
        });

        dialogExit.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogExit.show();
    }
}