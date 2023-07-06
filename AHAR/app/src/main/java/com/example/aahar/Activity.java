package com.example.aahar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Activity extends AppCompatActivity {
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private List<ModelDonation> list;
    private Adapter adapter;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        recyclerView=findViewById(R.id.recyclerView);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        list=new ArrayList<>();
        adapter=new Adapter(list,this);
        recyclerView.setAdapter(adapter);

        databaseReference.child("Activity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if(snapshot.exists()){
                    for (DataSnapshot x:snapshot.getChildren()
                         ) {
                        ModelDonation modelDonation=x.getValue(ModelDonation.class);
                        if(modelDonation.getBooked().equals("booked")){
                          continue;
                        }
                        list.add(modelDonation);
                    }
                    recyclerView.scrollToPosition(adapter.getItemCount() - 1);
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(Activity.this, "No Activities!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Activity.this, "Error : "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}