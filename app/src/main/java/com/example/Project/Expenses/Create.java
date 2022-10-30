package com.example.Project.Expenses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import java.text.SimpleDateFormat;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Project.MyDatabaseHelper;
import com.example.Project.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

public class Create extends AppCompatActivity {

    String[] items =  {"Travel","Food","Transport","Medical","Other"};

    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;

    String item, dateStr, TripID, amountEnter;
    TextInputLayout amount, Additional ;
    TextView time;
    Button add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expenses);

        add = findViewById(R.id.button_add);
        setCurrentTime();

        getIntentData();

        connectLayout();

        amountEnter = amount.getEditText().getText().toString();
        setSelectList();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item == null){
                    item = "Travel";
                }
                if (amount.getEditText().getText().toString().equals("")){
                    Toast.makeText(Create.this, "Please enter amount", Toast.LENGTH_SHORT).show();
                    return;
                }

                SaveData();
                Intent intent = new Intent(Create.this, List.class);
                intent.putExtra("TripId", String.valueOf(TripID));
                startActivity(intent);
            }
        });
    }

    public void connectLayout(){
        Additional = findViewById(R.id.Additional);
        amount = findViewById(R.id.Amount);
        autoCompleteTxt = findViewById(R.id.item);
    }

    public void setCurrentTime(){
        Long date=System.currentTimeMillis();
        SimpleDateFormat dateFormat =new SimpleDateFormat("dd / MMMM / yyyy", Locale.getDefault());
        dateStr = dateFormat.format(date);
        time = findViewById(R.id.Time);
        time.setText(dateStr);
    }
    public void getIntentData(){
        Intent intent = getIntent();
        TripID = intent.getStringExtra("TripId");
    }

    public void setSelectList(){
        adapterItems = new ArrayAdapter<String>(this,R.layout.list_seletor,items);
        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = parent.getItemAtPosition(position).toString();
            }
        });

    }


    public void SaveData(){
        MyDatabaseHelper DB = new MyDatabaseHelper(Create.this);

        DB.insertExpenses(
                TripID,
                item,
                amount.getEditText().getText().toString(),
                dateStr,
                String.valueOf(Additional.getEditText().getText()));
    }
}