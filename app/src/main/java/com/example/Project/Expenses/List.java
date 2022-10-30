package com.example.Project.Expenses;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Project.MyDatabaseHelper;
import com.example.Project.R;
import com.example.Project.Trip.MainActivity;

import java.util.ArrayList;

public class List extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView empty_imageview;
    TextView no_data;

    MyDatabaseHelper DB;
    ArrayList<String> type, id, date, amount;

    String TripID;
    CustomAdapterExpenses customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expenses_main);

        connectLayout();

        getIntentDate();


        DB = new MyDatabaseHelper(List.this);
        id = new ArrayList<>();
        type = new ArrayList<>();
        date = new ArrayList<>();
        amount = new ArrayList<>();


        storeDataInArrays();

        customAdapter = new CustomAdapterExpenses(List.this,this, id, type, date, amount);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(List.this));

    }
    public void connectLayout(){
        recyclerView = findViewById(R.id.recyclerView);
        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);
    }

    public void getIntentDate(){
        Intent intent = getIntent();
        TripID = intent.getStringExtra("TripId");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.expense, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.Home){
            goToHome();
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToHome(){
        Intent intent = new Intent(List.this, MainActivity.class);
        startActivity(intent);
    }
    void storeDataInArrays(){
        Cursor cursor = DB.readExpenses(TripID);
        if(cursor.getCount() == 0){
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                id.add(cursor.getString(0));
                type.add(cursor.getString(2));
                date.add(cursor.getString(4));
                amount.add(cursor.getString(3));
            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

    public void CreateExpenses(View view) {
        Intent intent = new Intent(List.this, Create.class);
        intent.putExtra("TripId", String.valueOf(TripID));
        startActivity(intent);
    }
}
