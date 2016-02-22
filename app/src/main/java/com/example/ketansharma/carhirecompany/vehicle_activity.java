package com.example.ketansharma.carhirecompany;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class vehicle_activity extends AppCompatActivity {
    public ArrayList<customer> customers = new ArrayList<>();
    public Boolean newRecord = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fillData();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.save_vehicle);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(vehicle_activity.this, main_activity.class);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void fillData() {
        customerData();

        Intent intent = getIntent();

        Spinner type_value = (Spinner) findViewById(R.id.vehicle_type_value);
        final String[] vehicle_type_array = getResources().getStringArray(R.array.vehicle_type_array);
        List<String> vehicle_type_array2 = new ArrayList<String>(Arrays.asList(vehicle_type_array));

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, vehicle_type_array2);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        type_value.setAdapter(spinnerArrayAdapter);

        if (intent.hasExtra("VEHICLE_ID")) {
            newRecord = false;
            String sVehicle_ID = intent.getStringExtra("VEHICLE_ID");
            String sVehicle_Type = intent.getStringExtra("VEHICLE_TYPE");
            String sVehicle_Make = intent.getStringExtra("VEHICLE_MAKE");
            String sVehicle_Model = intent.getStringExtra("VEHICLE_MODEL");
            String sVehicle_Colour = intent.getStringExtra("VEHICLE_COLOUR");
            String sVehicle_Reg = intent.getStringExtra("VEHICLE_REG");
            int iVehicle_Number_Of_Passengers = intent.getIntExtra("VEHICLE_NUMBER_OF_PASSENGERS", 0);
            int iVehicle_Number_Of_Wheels = intent.getIntExtra("VEHICLE_NUMBER_OF_WHEELS", 0);
            int i = 0;
            int customers_size = customers.size();
            String sCustomer_Email = intent.getStringExtra("VEHICLE_CUSTOMER_EMAIL");

            TextView id = (TextView) findViewById(R.id.vehicle_id);
            id.setText(sVehicle_ID);

            type_value.setSelection(vehicle_type_array2.indexOf(sVehicle_Type));
            type_value.setEnabled(false);

            EditText value = (EditText) findViewById(R.id.vehicle_make_value);
            value.setText(sVehicle_Make);
            value.setEnabled(false);

            value = (EditText) findViewById(R.id.vehicle_model_value);
            value.setText(sVehicle_Model);
            value.setEnabled(false);

            value = (EditText) findViewById(R.id.vehicle_colour_value);
            value.setText(sVehicle_Colour);
            value.setEnabled(false);

            value = (EditText) findViewById(R.id.vehicle_reg_value);
            value.setText(sVehicle_Reg);
            value.setEnabled(false);

            value = (EditText) findViewById(R.id.vehicle_passengers_value);
            value.setText(String.valueOf(iVehicle_Number_Of_Passengers));
            value.setEnabled(false);

            value = (EditText) findViewById(R.id.vehicle_wheels_value);
            value.setText(String.valueOf(iVehicle_Number_Of_Wheels));

            for(i=0; i<customers_size; i++){
                if (customers.get(i).getEmail().equals(sCustomer_Email)) {
                    Spinner customer_value = (Spinner) findViewById(R.id.customer_value);
                    customer_value.setSelection(i);
                }
            }
        }
        else {
            newRecord = true;
            type_value.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int pos, long id) {
                    String[] vehicle_type_array = getResources().getStringArray(R.array.vehicle_type_array);
                    vehicle Vehicle = new vehicle();
                    int iWheels = Vehicle.calculateWheels(vehicle_type_array[pos]);
                    EditText value = (EditText) findViewById(R.id.vehicle_wheels_value);
                    value.setText(String.valueOf(iWheels));
                }

                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

        }
    }

    private void customerData() {
        customer Customer = new customer();
        Customer.setID(1);
        Customer.setName("");
        Customer.setEmail("");
        customers.add(Customer.getID() - 1, Customer);

        Customer = new customer();
        Customer.setID(2);
        Customer.setName("Steve Price");
        Customer.setEmail("steve.price@trustpayglobal.com");
        customers.add(Customer.getID() - 1, Customer);

        Customer = new customer();
        Customer.setID(3);
        Customer.setName("Ketan Sharma");
        Customer.setEmail("ketan.sharma.developer@gmail.com");
        customers.add(Customer.getID() - 1, Customer);

        Customer = new customer();
        Customer.setID(4);
        Customer.setName("Joe Bloggs");
        Customer.setEmail("joe.bloggs@hotmail.co.uk");
        customers.add(Customer.getID() - 1, Customer);

        Spinner customer_spinner = (Spinner) findViewById(R.id.customer_value);
        ArrayAdapter<customer> customer_adapter = new ArrayAdapter<customer>(this,
                R.layout.spinner_item, customers);
        customer_adapter.setDropDownViewResource(R.layout.spinner_item);
        customer_spinner.setAdapter(customer_adapter);
    }

    private void save() {
        vehicle_database vehicle_db = new vehicle_database(this);
        Spinner customer_spinner = (Spinner) findViewById(R.id.customer_value);
        String selected_customer_name = customer_spinner.getSelectedItem().toString();
        int selected_customer = 0;
        TextView vehicle_id = (TextView) findViewById(R.id.vehicle_id);

        for(int i=0; i<customers.size(); i++){
            if (customers.get(i).getName().equals(selected_customer_name)) {
                selected_customer = i;
            }
        }

        if (newRecord) {
            String[] vehicle_type_array = getResources().getStringArray(R.array.vehicle_type_array);

            Spinner type_spinner = (Spinner) findViewById(R.id.vehicle_type_value);
            int selected_type = type_spinner.getSelectedItemPosition();

            EditText make_value = (EditText) findViewById(R.id.vehicle_make_value);
            EditText model_value = (EditText) findViewById(R.id.vehicle_model_value);
            EditText colour_value = (EditText) findViewById(R.id.vehicle_colour_value);
            EditText reg_value = (EditText) findViewById(R.id.vehicle_reg_value);
            EditText passengers_value = (EditText) findViewById(R.id.vehicle_passengers_value);

            String sMake = make_value.getText().toString().trim();
            String sModel = model_value.getText().toString().trim();
            String sColour = colour_value.getText().toString().trim();
            String sReg = reg_value.getText().toString().trim();
            String sPassengers = passengers_value.getText().toString().trim();

            if (sMake.equals("") || sModel.equals("") || sColour.equals("") || sReg.equals("")) {
                ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
                        .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);

                Snackbar.make(findViewById(android.R.id.content), "Please enter all of the vehicle details", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED)
                        .show();
            }
            else {
                int iPassengers;
                if (sPassengers.isEmpty() || sPassengers.length() == 0 || sPassengers.equals("")) {
                    iPassengers = 4;
                } else {
                    iPassengers = Integer.parseInt(sPassengers);
                }

                vehicle_db.insert(vehicle_type_array[selected_type], sMake,
                        sModel, sColour, sReg, iPassengers, customers.get(selected_customer).getEmail());

                onBackPressed();
            }
        }
        else {
            int v_id = Integer.valueOf(vehicle_id.getText().toString());
            vehicle_db.update(v_id , customers.get(selected_customer).getEmail());

            onBackPressed();

        }
    }
}
