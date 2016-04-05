package com.example.ketansharma.carhirecompany;

/**
 * Created by ketan.sharma.
 */
public class customer {
    int iID = 0;
    String sName = "";
    String sEmail = "";

    int getID() {
        return	this.iID;
    }

    void setID(int value) {
        this.iID = value;
    }

    String getName() {
        return	this.sName;
    }

    void setName(String value) {
        this.sName = value;
    }

    String getEmail() {
        return	this.sEmail;
    }

    void setEmail(String value) {
        this.sEmail = value;
    }

    @Override
    public String toString() {
        return this.sName;            // What to display in the Spinner list.
    }
}
