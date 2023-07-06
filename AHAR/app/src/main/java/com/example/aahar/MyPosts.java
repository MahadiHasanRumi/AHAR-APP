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

public class MyPosts extends AppCompatActivity {
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private List<ModelMyPost> list;
    private AdapterMyPost adapter;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        recyclerView=findViewById(R.id.myPostRecyclerView);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        list=new ArrayList<>();
        adapter=new AdapterMyPost(list,this);
        recyclerView.setAdapter(adapter);
        databaseReference.child("MyPosts").child("q"+SplashScreen.phone+"gmailcom")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot){
                        list.clear();
                        if(snapshot.exists()){
                            for (DataSnapshot x:
                                    snapshot.getChildren()) {
                                ModelMyPost modelMyPost=x.getValue(ModelMyPost.class);
                                list.add(modelMyPost);
                            }
                            adapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(MyPosts.this, "No Post yet !", Toast.LENGTH_SHORT).show();
        list.add(new ModelMyPost("","","","secretKey","") );
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MyPosts.this, "Error : "+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}