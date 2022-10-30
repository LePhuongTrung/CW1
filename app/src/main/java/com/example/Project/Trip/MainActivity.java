package com.example.Project.Trip;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.Project.MyDatabaseHelper;
import com.example.Project.R;
import com.example.Project.Weather;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView empty_imageview;
    TextView no_data;
    BottomNavigationView footer;
    TextInputLayout StringSearch;

    MyDatabaseHelper DB;
    ArrayList<String> id, name, destination, day_of_trip, risk, taxiPhone, HostelName;
    CustomAdapter customAdapter;
    Spinner TypeSearch;

    String searchEnter ="", typeSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectLayout();
        createTypeSearchSelecter();
        getIntentValue();

        footer.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Trip:
                        startActivity(new Intent(MainActivity.this,MainActivity.class));
                        break;
                    case R.id.Weather:
                        startActivity(new Intent(MainActivity.this, Weather.class));
                        break;
                }
                DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.footer);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Create.class);
                startActivity(intent);
            }
        });

        DB = new MyDatabaseHelper(MainActivity.this);
        id = new ArrayList<>();
        name = new ArrayList<>();
        destination = new ArrayList<>();
        day_of_trip = new ArrayList<>();
        risk = new ArrayList<>();
        taxiPhone = new ArrayList<>();
        HostelName = new ArrayList<>();

        if (searchEnter == null || searchEnter == ""){
            storeDataInArrays(searchEnter, typeSearch);
        } else {
            storeDataInArrays(searchEnter, typeSearch);
        }

        customAdapter = new CustomAdapter(MainActivity.this,this, id, name, destination,
                day_of_trip, risk, taxiPhone, HostelName);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }

    private void createTypeSearchSelecter() {

        TypeSearch = findViewById(R.id.Spinner);
        String[] items = new String[]{"Name", "Destination", "Date"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        TypeSearch.setAdapter(adapter);

        typeSearch = TypeSearch.getSelectedItem().toString();
    }

    public void getIntentValue(){
        Intent intent = getIntent();
        searchEnter = intent.getStringExtra("search");
        typeSearch = intent.getStringExtra("type");
    }

    public void connectLayout(){
        TypeSearch = findViewById(R.id.Spinner);
        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);
        footer = findViewById(R.id.footer);
        StringSearch = findViewById(R.id.StringSearch);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(String search, String Type){
        Cursor cursor = (Cursor) DB.readAllData(search, Type);
        if(cursor.getCount() == 0){
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                id.add(cursor.getString(0));
                name.add(cursor.getString(1));
                destination.add(cursor.getString(2));
                day_of_trip.add(cursor.getString(3));
                risk.add(cursor.getString(4));
                taxiPhone.add(cursor.getString(6));
                HostelName.add(cursor.getString(7));
            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delete, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All Trip Information?");
        builder.setMessage("Are you sure you want to delete All Trip Information?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper DB = new MyDatabaseHelper(MainActivity.this);
                DB.deleteAllData();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
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

    public void SearchData(View view) {
        Intent intent = new Intent( MainActivity.this, MainActivity.class);
        intent.putExtra("search", StringSearch.getEditText().getText().toString());
        intent.putExtra("type", TypeSearch.getSelectedItem().toString());
        startActivity(intent);
    }
}
