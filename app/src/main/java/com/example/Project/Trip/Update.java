package com.example.Project.Trip;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.Project.Expenses.List;
import com.example.Project.Trip.PickDate.DateSeleterUpdate;
import com.example.Project.MyDatabaseHelper;
import com.example.Project.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;

public class Update extends AppCompatActivity {

    int idCheckedRadioButton;
    String tripId, name, destination, DOT, risk, description, taxtPhone, hostelName;

    TextInputLayout nameUpdate, destinationUpdate, dateUpdate, descriptionUpdate, taxiPhoneUpdate, hostelNameUpdate;
    Button update_button, riskButton;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        connectLayout();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                idCheckedRadioButton = radioGroup.getCheckedRadioButtonId();
//                Log.i("",idCheckedRadioButton);
                riskButton = findViewById(idCheckedRadioButton);
            }
        });

        getIntentData();

        setIntentDate();

        setTitle();
    }

    public void connectLayout(){
        nameUpdate = findViewById(R.id.UpdateName);
        destinationUpdate = findViewById(R.id.UpdateDestination);
        dateUpdate = findViewById(R.id.Date);
        descriptionUpdate = findViewById(R.id.decription);
        taxiPhoneUpdate = findViewById(R.id.TaxiPhone);
        hostelNameUpdate = findViewById(R.id.hostelName);
        update_button = findViewById(R.id.update_button);
        radioGroup = findViewById(R.id.radioGroupUpdate);
    }
    public void getIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("destination") && getIntent().hasExtra("date") &&
                getIntent().hasExtra("taxi_phone_number") && getIntent().hasExtra("hname")){
            tripId = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            destination = getIntent().getStringExtra("destination");
            DOT = getIntent().getStringExtra("date");
            taxtPhone = getIntent().getStringExtra("taxi_phone_number");
            hostelName = getIntent().getStringExtra("hname");

            Log.d("stev", name+" "+destination+" "+DOT);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    public void setIntentDate(){
        nameUpdate.getEditText().setText(name);
        destinationUpdate.getEditText().setText(destination);
        dateUpdate.getEditText().setText(DOT);
        taxiPhoneUpdate.getEditText().setText(taxtPhone);
        hostelNameUpdate.getEditText().setText(hostelName);
    }

    public void setTitle(){
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(name);
        }
    }

    public void UpdateTrip(View view) {

        if (nameUpdate.getEditText().getText().toString().equals("")){
            Toast.makeText(this, "Please enter trip name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destinationUpdate.getEditText().getText().toString().equals("")){
            Toast.makeText(this, "Please enter trip destination", Toast.LENGTH_SHORT).show();
            return;
        }
        if (dateUpdate.getEditText().getText().toString().equals("dd/mm/yyyy")){
            Toast.makeText(this, "Please enter Date", Toast.LENGTH_SHORT).show();
            return;
        }

        if (radioGroup.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "Please chose risk", Toast.LENGTH_SHORT).show();
            return;
        }
        MyDatabaseHelper myDB = new MyDatabaseHelper(Update.this);
        getNewValue();
        myDB.updateData(tripId, name, destination, DOT, risk, description, taxtPhone, hostelName);
        Intent intent = new Intent(Update.this, MainActivity.class);
        startActivity(intent);
    }

    public void getNewValue(){
        name = nameUpdate.getEditText().getText().toString().trim();
        destination = destinationUpdate.getEditText().getText().toString().trim();
        DOT = dateUpdate.getEditText().getText().toString().trim();
        risk = riskButton.getText().toString();
        description = descriptionUpdate.getEditText().getText().toString().trim();
        taxtPhone = taxiPhoneUpdate.getEditText().getText().toString().trim();
        hostelName = hostelNameUpdate.getEditText().getText().toString().trim();
        return;
    }


    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " Details?");
        builder.setMessage("Are you sure you want to delete " + name + " Details?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(Update.this);
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
        if (item.getItemId() == R.id.Expense){
            goExpenseList();
        }
        return super.onOptionsItemSelected(item);
    }

    public void goExpenseList(){
        getIntentData();
        Intent intent = new Intent(Update.this, List.class);
        intent.putExtra("TripId", String.valueOf(tripId));
        startActivity(intent);
    }

    public void showDatePickerDialog(View v){
        DialogFragment newFragment = new DateSeleterUpdate();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void updateDOT(LocalDate DOT){
        dateUpdate.getEditText().setText(DOT.toString());
    }

}

