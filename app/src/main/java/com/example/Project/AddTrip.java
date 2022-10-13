package com.example.Project;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AddTrip extends AppCompatActivity {
    int idCheckedRadioButton;
    RadioButton radioButton = null;

    private Context mContext;

    private ListView ListAccommodation;
    TextInputEditText Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        TextInputLayout TripName = findViewById(R.id.TripName); // khai báo tên khối : TextInputLayout với tên biến TripName
        TextInputLayout hostelName = findViewById(R.id.hostelName);
        TextInputLayout TaxiPhone = findViewById(R.id.TaxiPhone);
        TextInputLayout TripDestination = findViewById(R.id .TripDestination);
        TextInputLayout TripDescription = findViewById(R.id.TripDescription);

        RadioButton Yes = findViewById(R.id.Yes);
        RadioButton No = findViewById(R.id.No);
        Button ButtonSaveTrip = findViewById(R.id.ButtonSaveTrip);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                idCheckedRadioButton = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(idCheckedRadioButton);
            }
        });
        showDatePickerDialog();
        ButtonSaveTrip.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (TripName.getEditText().getText().toString().equals("")){
                    Toast.makeText(AddTrip.this, "Please enter trip name", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TripDestination.getEditText().getText().toString().equals("")){
                    Toast.makeText(AddTrip.this, "Please enter trip destination", Toast.LENGTH_LONG).show();
                    return;
                }
                if (Date.getText().toString().equals("dd/mm/yyyy")){
                    Toast.makeText(AddTrip.this, "Please enter trip date", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (radioGroup.getCheckedRadioButtonId() == -1){
                    Toast.makeText(AddTrip.this, "Please choose risk assessment", Toast.LENGTH_LONG).show();
                    return;
                }
                // tạo biến db đại diện cho hàm xử lí database mới
                MyDatabaseHelper DB = new MyDatabaseHelper(AddTrip.this);
                // gọi hành động insertDetails
                DB.insertDetails(
                        String.valueOf(TripName.getEditText().getText()),
                        String.valueOf(TripDestination.getEditText().getText()),
                        Date.getText().toString(),
                        radioButton.getText().toString(),
                        String.valueOf(TripDescription.getEditText().getText()),
                        String.valueOf(TaxiPhone.getEditText().getText()),
                        String.valueOf(hostelName.getEditText().getText()));
                // chuyển đến hành động kế tiếp
                Intent intent = new Intent(AddTrip.this, MainActivity.class);
                startActivity(intent);
            }
        });
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
}
