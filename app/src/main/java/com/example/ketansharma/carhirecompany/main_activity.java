package com.example.ketansharma.carhirecompany;

import android.app.DialogFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import java.util.ArrayList;

public class main_activity extends AppCompatActivity {
    public static ArrayList<vehicle> vehicles = new ArrayList<vehicle>();
    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fillList();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_vehicle);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main_activity.this, vehicle_activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_filter_list :
                DialogFragment newFragment = new vehicle_filter_dialog();
                newFragment.show(getFragmentManager(),"vehicle_filter");
                break;
            case R.id.action_about :
                Toast.makeText(this,"Developed by Ketan Sharma ketan.sharma.developer@gmail.com", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter(
                "android.intent.action.MAIN");

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String msg_for_me = intent.getStringExtra("update_msg");

                if (msg_for_me.equals("Update List")) {
                    fillList();
                }
            }
        };

        this.registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(this.mReceiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void fillList() {
        vehicle Vehicle = new vehicle();
        vehicles = Vehicle.getVehicles(this);

        ListView listView = (ListView) findViewById(R.id.vehicle_list_view);
        vehicle_adapter aVehicle;
        aVehicle = new vehicle_adapter(main_activity.this,0,vehicles);
        listView.setAdapter(aVehicle);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(main_activity.this, vehicle_activity.class);

                vehicle Vehicle = new vehicle();
                intent.putExtra("VEHICLE_ID", String.valueOf(vehicles.get(position).getVehicleID()));
                intent.putExtra("VEHICLE_TYPE", vehicles.get(position).getVehicleType());
                intent.putExtra("VEHICLE_MAKE", vehicles.get(position).getVehicleMake());
                intent.putExtra("VEHICLE_MODEL", vehicles.get(position).getVehicleModel());
                intent.putExtra("VEHICLE_COLOUR", vehicles.get(position).getVehicleColour());
                intent.putExtra("VEHICLE_REG", vehicles.get(position).getVehicleReg());
                intent.putExtra("VEHICLE_NUMBER_OF_PASSENGERS", vehicles.get(position).getNumberOfPassengers());
                intent.putExtra("VEHICLE_NUMBER_OF_WHEELS", vehicles.get(position).getNumberOfWheels());
                intent.putExtra("VEHICLE_CUSTOMER_EMAIL", vehicles.get(position).getCustomerEmail());

                startActivity(intent);

                onStop();
            }
        });
    }
}
