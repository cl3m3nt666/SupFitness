package com.example.maxime.supfitness.Model;
/**
 * Created by clement on 04/04/16.
 */
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;
import java.util.ArrayList;

public class DatabaseHandle extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SupFit.db";
    public static final String TABLE_WEIGHT = "weightTable";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_DATE = "date";

    //We need to pass database information along to superclass
    public DatabaseHandle(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_WEIGHT + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_WEIGHT + " TEXT, " +
                COLUMN_DATE + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEIGHT);
        onCreate(db);
    }

    //Add a new row to the database
    public void addWeight(Weight weight){
        ContentValues values = new ContentValues();
        values.put(COLUMN_WEIGHT, weight.getWeight());
        values.put(COLUMN_DATE, weight.getDate());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_WEIGHT, null, values);
        db.close();
    }

    //Delete a product from the database by id
    public void deleteId(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_WEIGHT + " WHERE " + COLUMN_ID + "=\"" + id + "\";");
    }

    //Get all values and return ArrayList<weight>
    public ArrayList<Weight> getAll() {

        SQLiteDatabase db = getWritableDatabase();
        ArrayList<Weight> resultList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_WEIGHT ;

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        while (!c.isAfterLast()) {
            try {
                if (c.getString(c.getColumnIndex("id")) != null) {
                    Weight w = new Weight();
                    w.setId(c.getInt(c.getColumnIndex("id")));
                    w.setDate(c.getString(c.getColumnIndex("date")));
                    w.setWeight(c.getInt(c.getColumnIndex("weight")));
                    Log.d("GetAll()", "Date" + c.getString(c.getColumnIndex("date")) + " Id " + c.getInt(c.getColumnIndex("id")) + " W" + c.getInt(c.getColumnIndex("weight")));
                    resultList.add(w);
                }
            } catch (Exception e) {
                Log.e("SQL GET", "Error " + e.toString());
            }

            c.moveToNext();
        }
        db.close();
        return resultList;
    }
}

