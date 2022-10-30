package com.example.Project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "TripDetails.db";

    private static final String TABLE_NAME = "trips";
    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String DESTINATION_COLUMN = "destination";
    public static final String DOT_COLUMN = "date";
    public static final String RICK_COLUMN = "risk_assessment ";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String TAXI_PHONE_COLUM = "taxi_phone_number";
    public static final String HOSTEL_NAME_COLUMN = "hname";

    private static final String TABLE_EXPENSES = "expenses";
    public static final String EXPENSES_ID = "id_Expenses";
    public static final String TRIP_ID = "id_Trip";
    public static final String TYPE = "TypeFee";
    public static final String AMOUNT = "Amount";
    public static final String TIME_COLUMN = "Time_of_the_expense";
    public static final String Additional_COLUMN = "Additional_comments";


    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTripTable = "CREATE TABLE " + TABLE_NAME +
                " (" + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COLUMN + " TEXT, "
                + DESTINATION_COLUMN +" TEXT, "
                + DOT_COLUMN + " TEXT, "
                + RICK_COLUMN + " TEXT, "
                + DESCRIPTION_COLUMN + " TEXT,"
                + TAXI_PHONE_COLUM + " TEXT, "
                + HOSTEL_NAME_COLUMN + " TEXT) ";
        db.execSQL(createTripTable);

        String createExpenseTable = "CREATE TABLE " + TABLE_EXPENSES +
                " (" + EXPENSES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TRIP_ID + " INTEGER NOT NULL, "
                + TYPE +" TEXT, "
                + AMOUNT + " TEXT, "
                + TIME_COLUMN + " TEXT, "
                + Additional_COLUMN + " TEXT) ";
        db.execSQL(createExpenseTable);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertTrip(String name, String destination, String dot, String risk, String description, String taxiPhone, String hostelName){
        ContentValues rowValues = new ContentValues();


        rowValues.put(NAME_COLUMN, name);
        rowValues.put(DESTINATION_COLUMN, destination);
        rowValues.put(DOT_COLUMN, dot);
        rowValues.put(RICK_COLUMN, risk);
        rowValues.put(DESCRIPTION_COLUMN, description);
        rowValues.put(TAXI_PHONE_COLUM, taxiPhone);
        rowValues.put(HOSTEL_NAME_COLUMN, hostelName);

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_NAME,null, rowValues);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public void insertExpenses(String tripId, String type, String amount, String time, String additional){
        ContentValues rowValues = new ContentValues();

        rowValues.put(TRIP_ID, tripId);
        rowValues.put(TYPE, type);
        rowValues.put(AMOUNT, amount);
        rowValues.put(TIME_COLUMN, time);
        rowValues.put(Additional_COLUMN, additional);

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_EXPENSES,null, rowValues);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
        return;
    }
    public Cursor readAllData(String search, String TYPE){
        SQLiteDatabase db;
        String query;
        if (search == null || search == ""){
            query = "SELECT * FROM " + TABLE_NAME;
            db = this.getReadableDatabase();
        }
        else if (TYPE.equals("Name")) {
            query = "SELECT * FROM " + TABLE_NAME + " WHERE " + NAME_COLUMN + " LIKE " + " '%" + search + "%' ";
            db = this.getReadableDatabase();
        }
        else if (TYPE == "Destination") {
            query = "SELECT * FROM " + TABLE_NAME + " WHERE " + DESTINATION_COLUMN + " LIKE " + " '%" + search + "%' ";
            db = this.getReadableDatabase();
        }
        else {
            query = "SELECT * FROM " + TABLE_NAME + " WHERE " + DOT_COLUMN + " LIKE " + " '%" + search + "%' ";
            db = this.getReadableDatabase();
        }

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


    public Cursor readExpenses(String id){
        String query = "SELECT * FROM " + TABLE_EXPENSES +
                        " WHERE " + TRIP_ID + "=" + id;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


    public void updateData(String id, String name, String destination, String dot, String risk, String description, String taxiPhone, String hostelName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues rowValues = new ContentValues();

        rowValues.put(NAME_COLUMN, name);
        rowValues.put(DESTINATION_COLUMN, destination);
        rowValues.put(DOT_COLUMN, dot);
        rowValues.put(RICK_COLUMN, risk);
        rowValues.put(DESCRIPTION_COLUMN, description);
        rowValues.put(TAXI_PHONE_COLUM, taxiPhone);
        rowValues.put(HOSTEL_NAME_COLUMN, hostelName);

        long result = db.update(TABLE_NAME, rowValues, "id=?", new String[]{id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
        return;
    }

    public void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
        return;
    }

    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        return;
    }

}

