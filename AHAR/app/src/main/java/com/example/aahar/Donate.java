package com.example.aahar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Donate extends AppCompatActivity {
    private ProgressBar progressBar;
    //    private String trimmedTitle;
    private String pictureLink="";
    private ImageView imageView;
    private EditText edQuantity,edLocation,edLocationLink;
    private Button createButton;
    final int code = 999;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        progressBar=findViewById(R.id.addProductProgressBar);
        edQuantity=findViewById(R.id.donateQuantity);
        edLocation=findViewById(R.id.donateLocationName);
        edLocationLink=findViewById(R.id.donateLocationLink);
        imageView=findViewById(R.id.addProductChoosePicture);
        createButton=findViewById(R.id.donatePostButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             String quantity=edQuantity.getText().toString();
             String location=edLocation.getText().toString();
             String locationLink=edLocationLink.getText().toString();
             if(quantity.isEmpty()||location.isEmpty()||locationLink.isEmpty()||pictureLink.isEmpty()){
                 Toast.makeText(Donate.this, "Enter every element..", Toast.LENGTH_SHORT).show();
             return;
             }
                if(!locationLink.startsWith("https")){
                    String arr[]=locationLink.split("https",2);
                    locationLink=arr[1];
                    locationLink="https"+locationLink;
                }
             String key=databaseReference.push().getKey();
             databaseReference.child("Activity").child(key).setValue(
                     new ModelDonation(pictureLink,SplashScreen.name,SplashScreen.phone,quantity,location,locationLink,"not")
             );
             databaseReference.child("MyPosts").child("q"+SplashScreen.phone+"gmailcom")
                             .child(key).setValue(new ModelMyPost(pictureLink,quantity,location,key,"not") );
                Toast.makeText(Donate.this, "Posted!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),HomePage.class) );
            }
        });
    }
    public void signUpPictureChoosingButton(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,code);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == code && resultCode == Activity.RESULT_OK && data != null) {
            progressBar.setVisibility(View.VISIBLE);
            createButton.setEnabled(false);
            Uri result = data.getData();
            String key= databaseReference.push().getKey();
            StorageReference storageRef = FirebaseStorage.getInstance().getReference("picLink"+SplashScreen.name+key);
            storageRef.putFile(result).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            pictureLink=uri.toString();

                            imageView.setImageURI(result);
                            progressBar.setVisibility(View.INVISIBLE);
                            createButton.setEnabled(true);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Couldn't Upload !!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}