package com.example.Project.Expenses;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Project.R;

import java.util.ArrayList;

public class CustomAdapterExpenses extends RecyclerView.Adapter<CustomAdapterExpenses.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList id;
    private ArrayList type;
    private ArrayList date;
    private ArrayList amount;

    public CustomAdapterExpenses(Activity activity, Context context, ArrayList id, ArrayList type,
                                 ArrayList date, ArrayList amount){
        this.activity = activity;
        this.context = context;
        this.id = id;
        this.type = type;
        this.date = date;
        this.amount = amount;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.expenses_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.type.setText(String.valueOf(type.get(position)));
        holder.amount.setText(String.valueOf(amount.get(position)));
        holder.date.setText(String.valueOf(date.get(position)));
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id, type, amount, date;
        LinearLayout expensesLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id._id);
            type = itemView.findViewById(R.id.TypeRow);
            amount = itemView.findViewById(R.id.amount);
            date = itemView.findViewById(R.id.DateRow);
            expensesLayout = itemView.findViewById(R.id.expensesRow);

            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            expensesLayout.setAnimation(translate_anim);
        }

    }

}
