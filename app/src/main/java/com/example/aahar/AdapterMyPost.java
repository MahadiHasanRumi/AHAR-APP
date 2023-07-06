package com.example.aahar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdapterMyPost extends RecyclerView.Adapter<AdapterMyPost.ViewHolder> {
    private List<ModelMyPost> list;
    private Context context;

    public AdapterMyPost(List<ModelMyPost> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterMyPost.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.model_my_posts,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMyPost.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        if(list.get(position).getKey().equals("secretKey") || list.size()==0){
            return;
        }
        if(list.get(position).getBooked().equals("booked")){
            holder.booked.setChecked(true);
        }
        holder.quantity.setText("Quantity : "+list.get(position).getQuantity() );
        holder.location.setText("Location : "+list.get(position).getLocation() );
        Glide.with(context).load(list.get(position).getPictureLink()).into(holder.foodImage);

        holder.booked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(list.get(position).getKey().equals("secretKey") ){
                    return;
                }
                if(isChecked){

                    databaseReference.child("MyPosts").child("q"+SplashScreen.phone+"gmailcom")
                            .child(list.get(position).getKey()).child("booked").setValue("booked");
                    databaseReference.child("Activity").child(list.get(position).getKey())
                            .child("booked").setValue("booked");
                }
                else{
                    databaseReference.child("MyPosts").child("q"+SplashScreen.phone+"gmailcom")
                            .child(list.get(position).getKey()).child("booked").setValue("not booked");
                    databaseReference.child("Activity").child(list.get(position).getKey())
                            .child("booked").setValue("not booked");
                }
            }
        });

        holder.delete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                CharSequence[] items = {"Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);

                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i == 0){
                            databaseReference.child("MyPosts").child("q"+SplashScreen.phone+"gmailcom")
                                    .child(list.get(position).getKey()).removeValue();
                            databaseReference.child("Activity").child(list.get(position).getKey()).removeValue();
                        }
                    }
                });
                dialog.show();



                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView foodImage;
        private TextView quantity,location;
        private RelativeLayout delete;
        private Switch booked;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.myPostFoodImage);
            quantity = itemView.findViewById(R.id.myPostQuantity);
            location = itemView.findViewById(R.id.myPostLocation);
            delete = itemView.findViewById(R.id.relative);
            booked = itemView.findViewById(R.id.myPostSwitch);
        }
    }
}
