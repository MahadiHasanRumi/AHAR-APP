package com.example.aahar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    private Bundle bundle;
    private FirebaseAuth mAuth;
    private EditText name,password;
    private Button signUp;
    private DatabaseReference databaseReference;
    private String phoneNumber,nameString,passString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();
        bundle = getIntent().getExtras();
        phoneNumber = bundle.getString("phoneNumber");
        name=findViewById(R.id.signUpName);
        password=findViewById(R.id.signUpPassword);
        signUp=findViewById(R.id.signUpButton);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        nameString=name.getText().toString();
        passString=password.getText().toString();
              if(nameString.isEmpty() || passString.isEmpty()){
                  Toast.makeText(SignUp.this, "Please fill up !", Toast.LENGTH_SHORT).show();
                  return;
              }
                String mail = "q"+phoneNumber + "@gmail.com";
              String trimmedMail="q"+phoneNumber+"gmailcom";
                mAuth.createUserWithEmailAndPassword(mail,passString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                  databaseReference.child("users").child(trimmedMail).setValue(
                    new ModelUser(phoneNumber,passString,nameString) )    ;
                        Toast.makeText(SignUp.this, "Signed Up Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),SplashScreen.class) );
                    }


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUp.this, "Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}