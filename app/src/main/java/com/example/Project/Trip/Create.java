package com.example.Project.Trip;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.Project.Trip.PickDate.DateSeleter;
import com.example.Project.MyDatabaseHelper;
import com.example.Project.R;
import com.example.Project.Weather;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;

public class Create extends AppCompatActivity {
    int idCheckedRadioButton;
    RadioButton radioButton = null;

    TextInputEditText Date;
    TextInputLayout TripName, hostelName, TaxiPhone, TripDestination, TripDescription;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        connectLayout();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                idCheckedRadioButton = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(idCheckedRadioButton);
            }
        });

        showDatePickerDialog();

    }
    public void connectLayout(){
        TripName = findViewById(R.id.TripName);
        hostelName = findViewById(R.id.hostelName);
        TaxiPhone = findViewById(R.id.TaxiPhone);
        TripDestination = findViewById(R.id .TripDestination);
        TripDescription = findViewById(R.id.TripDescription);
        radioGroup = findViewById(R.id.radioGroup);
    }

    public void AddTrip(View view) {
        if (TripName.getEditText().getText().toString().equals("")){
            Toast.makeText(Create.this, "Please enter trip name", Toast.LENGTH_LONG).show();
            return;
        }
        if (TripDestination.getEditText().getText().toString().equals("")){
            Toast.makeText(Create.this, "Please enter trip destination", Toast.LENGTH_LONG).show();
            return;
        }
        if (Date.getText().toString().equals("dd/mm/yyyy")){
            Toast.makeText(Create.this, "Please enter trip date", Toast.LENGTH_SHORT).show();
            return;
        }

        if (radioGroup.getCheckedRadioButtonId() == -1){
            Toast.makeText(Create.this, "Please choose risk assessment", Toast.LENGTH_LONG).show();
            return;
        }
        confirmDialog();
    }

    public void showDatePickerDialog(){
        Date = findViewById(R.id.DateEdit);
        Date.setInputType(InputType.TYPE_NULL);
        Date.setKeyListener(null);
        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DateSeleter();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
    }

    public void updateDOTAdd(LocalDate dot){
        TextInputEditText Date = findViewById(R.id.DateEdit);
        Date.setText(dot.toString());
    }
    void confirmDialog(){
        String dialog = "Trip Name: "+TripName.getEditText().getText().toString()+
                        "\nDestination: "+TripDestination.getEditText().getText().toString()+
                        "\nDate: "+Date.getText().toString()+
                        "\nRequires risk assessment:"+radioButton.getText().toString()+
                        "\nTaxi Phone: "+TaxiPhone.getEditText().getText().toString()+
                        "\nHostel Name: "+hostelName.getEditText().getText().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create Trip:");
        builder.setMessage(dialog);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper DB = new MyDatabaseHelper(Create.this);
                DB.insertTrip(
                        TripName.getEditText().getText().toString(),
                        TripDestination.getEditText().getText().toString(),
                        Date.getText().toString(),
                        radioButton.getText().toString(),
                        TripDescription.getEditText().getText().toString(),
                        TaxiPhone.getEditText().getText().toString(),
                        hostelName.getEditText().getText().toString());
                Intent intent = new Intent(Create.this, MainActivity.class);
                startActivity(intent);
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
        inflater.inflate(R.menu.weather, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.checkWeather){
            Intent intent = new Intent(Create.this, Weather.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
