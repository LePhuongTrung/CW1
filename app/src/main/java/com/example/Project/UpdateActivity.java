package com.example.Project;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity {

    int idCheckedRadioButton;
    String tripId, name, destination, DOT, risk, description, taxtPhone, hostelName;

    EditText nameUpdate, destinationUpdate, dateUpdate, descriptionUpdate, taxiPhoneUpdate, hostelNameUpdate;
    Button update_button, riskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Button add_Expenses_button;
        nameUpdate = findViewById(R.id.UpdateName);
        destinationUpdate = findViewById(R.id.UpdateDestination);
        dateUpdate = findViewById(R.id.Date);
        descriptionUpdate = findViewById(R.id.decription);
        taxiPhoneUpdate = findViewById(R.id.TaxiPhone);
        hostelNameUpdate = findViewById(R.id.hostelName);
        update_button = findViewById(R.id.update_button);
        add_Expenses_button = findViewById(R.id.add_Expenses);
        RadioGroup radioGroup = findViewById(R.id.radioGroupUpdate);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                idCheckedRadioButton = radioGroup.getCheckedRadioButtonId();
//                Log.i("",idCheckedRadioButton);
                riskButton = findViewById(idCheckedRadioButton);
            }
        });

        //First we call this
        getAndSetIntentData();

        //Set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(name);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //And only then we call this
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                name = nameUpdate.getText().toString().trim();
                destination = destinationUpdate.getText().toString().trim();
                DOT = dateUpdate.getText().toString().trim();
                risk = riskButton.getText().toString();
                description = descriptionUpdate.getText().toString().trim();
                taxtPhone = taxiPhoneUpdate.getText().toString().trim();
                hostelName = hostelNameUpdate.getText().toString().trim();
                myDB.updateData(tripId, name, destination, DOT, risk, description, taxtPhone, hostelName);
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        add_Expenses_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAndSetIntentData();
                Intent intent = new Intent(UpdateActivity.this, ExpensesList.class);
                intent.putExtra("TripId", String.valueOf(tripId));
                startActivity(intent);
            }
        });
    }


    public void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("destination") && getIntent().hasExtra("date") &&
                getIntent().hasExtra("taxi_phone_number") && getIntent().hasExtra("hname")){
            //Getting Data from Intent
            tripId = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            destination = getIntent().getStringExtra("destination");
            DOT = getIntent().getStringExtra("date");
            taxtPhone = getIntent().getStringExtra("taxi_phone_number");
            hostelName = getIntent().getStringExtra("hname");

            //Setting Intent Data
            nameUpdate.setText(name);
            destinationUpdate.setText(destination);
            dateUpdate.setText(DOT);
            taxiPhoneUpdate.setText(taxtPhone);
            hostelNameUpdate.setText(hostelName);
            Log.d("stev", name+" "+destination+" "+DOT);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Are you sure you want to delete " + name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(tripId);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delete_one, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete_One){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDatePickerDialog(View v){
        DialogFragment newFragment = new DateSeleter1();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void updateDOT(LocalDate DOT){
        dateUpdate.setText(DOT.toString());
    }

}

