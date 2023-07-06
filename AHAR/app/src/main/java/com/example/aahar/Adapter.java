package com.example.aahar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<ModelDonation> list;
    private Context context;

    public Adapter(List<ModelDonation> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.model_activity,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(list.size()==0){
            Toast.makeText(context, "No Activity Yet!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(list.get(position).getBooked().equals("booked")){
            holder.booked.setText("Booked");
        }
        holder.name.setText(list.get(position).getName() );
        holder.phone.setText(list.get(position).getPhone() );
        holder.quantity.setText("Quantity : "+list.get(position).getQuantity() );
        holder.location.setText(list.get(position).getLocation() );
        Glide.with(context).load(list.get(position).getPictureLink()).into(holder.foodImage);

        holder.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = list.get(position).getLocationLink();

                // Create an intent to launch the web browser
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView foodImage;
        private TextView booked,name,phone,quantity,location;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            booked=itemView.findViewById(R.id.activityBook);
            foodImage=itemView.findViewById(R.id.activityFoodImage);
            name=itemView.findViewById(R.id.activityName);
            phone=itemView.findViewById(R.id.activityPhone);
            quantity=itemView.findViewById(R.id.activityQuantity);
            location=itemView.findViewById(R.id.activityLocation);

        }
    }
}
