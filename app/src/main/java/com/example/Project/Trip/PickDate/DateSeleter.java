package com.example.Project.Trip.PickDate;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.Project.Trip.Create;

import java.time.LocalDate;

public class DateSeleter extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        LocalDate localDate = LocalDate.now();
        int day = localDate.getDayOfMonth();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();

        return new DatePickerDialog(getActivity(), this, year, --month, day);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        LocalDate dot = LocalDate.of(year, ++month, day);
        ((Create)getActivity()).updateDOTAdd(dot);
    }
}
