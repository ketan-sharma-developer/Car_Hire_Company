package com.example.ketansharma.carhirecompany;

/**
 * Created by ketan.sharma on 21/02/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class vehicle_database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "vehicles.mka";
    public static final String DATABASE_TABLE = "vehicles";
    public static final String DATABASE_TABLE2 = "vehicles_filter";
    public static final int DATABASE_VERSION = 1;

    public static final String KEY_UID = "_id";
    public static final String KEY_TYPE = "Type";
    public static final String KEY_MAKE = "Make";
    public static final String KEY_MODEL = "Model";
    public static final String KEY_COLOUR = "Colour";
    public static final String KEY_REG = "Reg";
    public static final String KEY_PASSENGERS = "Passengers";
    public static final String KEY_EMAIL = "Email";
    public static final String KEY_FILTER = "Filter";
    private HashMap hp;

    String GET_DATABASE_VALUES = "select " + KEY_UID + ", "
            + KEY_TYPE + ", " + KEY_MAKE + ", " + KEY_MODEL + ", " + KEY_COLOUR + ", "
            + KEY_REG + ", " + KEY_PASSENGERS + ", " + KEY_EMAIL
            + " From " + DATABASE_TABLE;

    String GET_DATABASE_VALUES2 = "select " + KEY_UID + ", " + KEY_FILTER
            + " From " + DATABASE_TABLE2;

    public vehicle_database(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + " (" + KEY_UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_TYPE + ", " + KEY_MAKE + ", " + KEY_MODEL + ", " + KEY_COLOUR + ", "
                    + KEY_REG + ", " + KEY_PASSENGERS + ", " + KEY_EMAIL + ");");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE2 + " (" + KEY_UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_FILTER + ");");

            insert2("");
        } catch (Exception e) {

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }

    public Boolean insert(String Type, String Make, String Model,
                          String Colour, String Reg, int Passengers, String Email) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_TYPE, Type);
            contentValues.put(KEY_MAKE, Make);
            contentValues.put(KEY_MODEL, Model);
            contentValues.put(KEY_COLOUR, Colour);
            contentValues.put(KEY_REG, Reg);
            contentValues.put(KEY_PASSENGERS, String.valueOf(Passengers));
            contentValues.put(KEY_EMAIL, Email);

            db.insert(DATABASE_TABLE, null, contentValues);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean insert2(String Filter) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_FILTER, Filter);

            db.delete(DATABASE_TABLE2, null, null);
            db.insert(DATABASE_TABLE2, null, contentValues);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Cursor getData(){
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String GET_DATABASE_VALUES = "select " + KEY_UID + ", "
                    + KEY_TYPE + ", " + KEY_MAKE + ", " + KEY_MODEL + ", " + KEY_COLOUR + ", "
                    + KEY_REG + ", " + KEY_PASSENGERS + ", " + KEY_EMAIL
                    + " From " + DATABASE_TABLE;

            Cursor res = db.rawQuery(GET_DATABASE_VALUES, null);
            return res;
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor getData(String key, String value){
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor res = db.rawQuery(GET_DATABASE_VALUES + " where " + key + "=" + value, null);
            return res;
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor getData2(){
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor res = db.rawQuery(GET_DATABASE_VALUES2, null);
            return res;
        } catch (Exception e) {
            return null;
        }
    }


    public int getCount(){
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            int numRows = (int) DatabaseUtils.queryNumEntries(db, DATABASE_TABLE);
            return numRows;
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean update (int UID, String email) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_EMAIL, email);
            db.update(DATABASE_TABLE, contentValues, KEY_UID + "=" + UID, null);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Integer delete (int id)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.delete(DATABASE_TABLE,
                    "id = ? ",
                    new String[]{Integer.toString(id)});
        } catch (Exception e) {
            return 0;
        }
    }

    public ArrayList<vehicle> getArray() {
        ArrayList<vehicle> vehicles = new ArrayList<vehicle>();

        try {
            ArrayList<String> filter = getArray2();
            String sWhereClause = "";

            for (String s : filter) {
                if (!s.equals("")) {
                    sWhereClause = s;
                }
            }

            SQLiteDatabase db = this.getReadableDatabase();

            String SQL = GET_DATABASE_VALUES;
            if (!sWhereClause.equals("")) {
                SQL += " WHERE " + sWhereClause;
            }

            Cursor res = db.rawQuery(SQL, null);
            res.moveToFirst();

            while (res.isAfterLast() == false) {
                vehicle Vehicle = new vehicle();
                Vehicle.setVehicleID(Integer.valueOf(res.getString(res.getColumnIndex(KEY_UID))));
                Vehicle.setVehicleType(res.getString(res.getColumnIndex(KEY_TYPE)));
                Vehicle.setVehicleMake(res.getString(res.getColumnIndex(KEY_MAKE)));
                Vehicle.setVehicleModel(res.getString(res.getColumnIndex(KEY_MODEL)));
                Vehicle.setVehicleColour(res.getString(res.getColumnIndex(KEY_COLOUR)));
                Vehicle.setVehicleReg(res.getString(res.getColumnIndex(KEY_REG)));
                Vehicle.setNumberOfPassengers(Integer.valueOf(res.getString(res.getColumnIndex(KEY_PASSENGERS))));
                Vehicle.setCustomerEmail(res.getString(res.getColumnIndex(KEY_EMAIL)));
                vehicles.add(Vehicle);

                res.moveToNext();
            }
        }
        catch (Exception e) {

        }

        return vehicles;
    }

    public ArrayList<String> getArray2()
    {
        ArrayList<String> filter = new ArrayList<String>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String SQL = GET_DATABASE_VALUES2;

            Cursor res =  db.rawQuery(SQL, null );
            res.moveToFirst();

            while(res.isAfterLast() == false){
                filter.add(res.getString(res.getColumnIndex(KEY_UID)));
                filter.add(res.getString(res.getColumnIndex(KEY_FILTER)));

                res.moveToNext();
            }
        } catch (Exception e) {

        }

        return filter;
    }
}
