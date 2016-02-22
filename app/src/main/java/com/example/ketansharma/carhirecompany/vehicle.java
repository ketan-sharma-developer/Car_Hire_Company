package com.example.ketansharma.carhirecompany;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by ketan.sharma on 19/02/2016.
 */
public class vehicle {
    int iVehicleID = 0;
    String sVehicleType = "";
    String sVehicleMake = "";
    String sVehicleModel = "";
    String sVehicleColour = "";
    String sVehicleReg = "";
    int iNumberOfPassengers = 0;
    String sCustomerEmail = "";

    int getVehicleID() {
        return	this.iVehicleID;
    }

    String getVehicleType() {
        return this.sVehicleType;
    }

    String getVehicleMake() {
        return this.sVehicleMake;
    }

    String getVehicleModel() {
        return this.sVehicleModel;
    }

    String getVehicleColour() {
        return this.sVehicleColour;
    }

    String getVehicleReg() {
        return this.sVehicleReg;
    }

    int getNumberOfWheels() {
        return	calculateWheels(sVehicleType);
    }

    int getNumberOfPassengers() {
        return	this.iNumberOfPassengers;
    }

    String getCustomerEmail() {
        return	this.sCustomerEmail;
    }

    void setVehicleID(int value) {
        this.iVehicleID = value;
    }

    void setVehicleType(String value) {
        this.sVehicleType = value;
    }

    void setVehicleMake(String value) {
        this.sVehicleMake = value;
    }

    void setVehicleModel(String value) {
        this.sVehicleModel = value;
    }

    void setVehicleColour(String value) {
        this.sVehicleColour = value;
    }

    void setVehicleReg(String value) {
        this.sVehicleReg = value;
    }

    void setNumberOfPassengers(int value) {
        this.iNumberOfPassengers = value;
    }

    void setCustomerEmail(String value) {
        this.sCustomerEmail = value;
    }

    int calculateWheels(String sVehicleType) {
        switch (sVehicleType) {
            case "Motorbike": return 2;
            default: return 4;
        }
    }

    ArrayList<vehicle> getVehicles(Context c) {
        String[] vehicle_type_array = c.getResources().getStringArray(R.array.vehicle_type_array);

        vehicle_database vehicle_db = new vehicle_database(c);
        if (vehicle_db.getCount() == 0) {
            vehicle_db.insert(vehicle_type_array[0], "Ford", "Mondeo", "Black", "JF02 FME", 4, "");
            vehicle_db.insert(vehicle_type_array[3], "Mercedes-Benz", "Citan", "White", "BG09 KLV", 5, "");
            vehicle_db.insert(vehicle_type_array[1], "Porche", "Boxster", "Silver", "K961 WFW", 2, "");
            vehicle_db.insert(vehicle_type_array[2], "Yamaha", "FJR1300A", "Black", "Y643 BJS", 1, "ketan.sharma.developer@gmail.com");
        }

        return vehicle_db.getArray();
    }
}
