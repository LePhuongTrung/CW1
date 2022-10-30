package com.example.Project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class Weather extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);
        TextInputLayout test = findViewById(R.id.Test);
        String a = "aabbcc";
        test.getEditText().setText(a);
    }
}
