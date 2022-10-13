package com.example.Project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;
import java.util.Locale;

public class addExpenses extends AppCompatActivity {

    String[] items =  {"Travel","Food","Transport","Medical","Other"};

    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;

    String item;
    TextInputLayout amount, Additional ;
    TextView time;
    TextInputEditText dayTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expenses);


        // set current time
        Long date=System.currentTimeMillis();
        SimpleDateFormat dateFormat =new SimpleDateFormat("dd / MMMM / yyyy", Locale.getDefault());
        String dateStr = dateFormat.format(date);
        time = findViewById(R.id.Time);
        time.setText(dateStr);
        //get tripID
        Intent intent = getIntent();
        String TripID = intent.getStringExtra("TripId");
        Button add = findViewById(R.id.button_add);

        Additional = findViewById(R.id.Additional);

        amount = findViewById(R.id.Amount);
        autoCompleteTxt = findViewById(R.id.item);

        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);
        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = parent.getItemAtPosition(position).toString();
            }
        });
        add.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (TextUtils.isEmpty(amount.getEditText().toString())){
                    Toast.makeText(addExpenses.this, "Please enter trip name", Toast.LENGTH_LONG).show();
                }
                MyDatabaseHelper DB = new MyDatabaseHelper(addExpenses.this);
                // call method insertDetails
                DB.insertExpenses(
                        TripID,
                        item,
                        String.valueOf(amount.getEditText().getText()),
                        dateStr,
                        String.valueOf(Additional.getEditText().getText()));
                Intent intent = new Intent(addExpenses.this, ExpensesList.class);
                intent.putExtra("TripId", String.valueOf(TripID));
                startActivity(intent);
            }
        });
    }
}