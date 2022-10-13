package com.example.Project;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList tripId;
    private ArrayList TripName;
    private ArrayList destination;
    private ArrayList date;
    private ArrayList rick;
    private ArrayList taxiPhone;
    private ArrayList hostelName;
    private ArrayList SearchTripName;

    CustomAdapter(Activity activity, Context context, ArrayList id, ArrayList TripName, ArrayList destination,
                  ArrayList date, ArrayList rick, ArrayList taxiPhone, ArrayList hostelName){
        this.activity = activity;
        this.context = context;
        this.tripId = id;
        this.TripName = TripName;
        this.destination = destination;
        this.date = date;
        this.rick = rick;
        this.taxiPhone = taxiPhone;
        this.hostelName = hostelName;
        this.SearchTripName = TripName;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.id.setText(String.valueOf(tripId.get(position)));
        holder.TripName.setText(String.valueOf(TripName.get(position)));
        holder.destination.setText(String.valueOf(destination.get(position)));
        holder.dayOfTrip.setText(String.valueOf(date.get(position)));
        holder.taxiPhone.setText(String.valueOf(taxiPhone.get(position)));
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(tripId.get(position)));
                intent.putExtra("name", String.valueOf(TripName.get(position)));
                intent.putExtra("destination", String.valueOf(destination.get(position)));
                intent.putExtra("date", String.valueOf(date.get(position)));
                intent.putExtra("taxi_phone_number", String.valueOf(taxiPhone.get(position)));
                intent.putExtra("hname", String.valueOf(hostelName.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return tripId.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id, TripName, destination, dayOfTrip, rick, taxiPhone, hostelName;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id._id);
            TripName = itemView.findViewById(R.id.name);
            destination = itemView.findViewById(R.id.destination);
            dayOfTrip = itemView.findViewById(R.id.dayOfTrip);
            taxiPhone = itemView.findViewById(R.id.TaxiPhone);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }

    }

}
