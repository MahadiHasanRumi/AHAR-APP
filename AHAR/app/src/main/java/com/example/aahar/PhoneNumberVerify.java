package com.example.aahar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PhoneNumberVerify extends AppCompatActivity {
    private EditText editText;

    private Button button;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_verify);
        editText=findViewById(R.id.phoneNumberVerifyEditText);
        button=findViewById(R.id.phoneNumberVerifyButton);
        textView=findViewById(R.id.textViewPhoneNumberVerify);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s="+88"+editText.getText().toString();
                if(s.length()!=14){
                    editText.setError("Enter a valid number");
                    editText.requestFocus();
                    return;
                }
                Intent intent=new Intent(getApplicationContext(),OTPverify.class);
                intent.putExtra("phoneNumber",s);
                startActivity(intent);

            }
        });

    }
}