package com.example.ketansharma.carhirecompany;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by ketan.sharma.
 */
public class vehicle_adapter extends ArrayAdapter<vehicle> {
    private Activity activity;
    private ArrayList<vehicle> lVehicle;
    private static LayoutInflater inflater = null;

    public vehicle_adapter(Activity activity, int textViewResourceId, ArrayList<vehicle> results) {
    super(activity, textViewResourceId, results);
        try {
            this.activity = activity;
            this.lVehicle = results;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {

        }
    }

    public int getCount() {
        return lVehicle.size();
    }

    public vehicle getItem(vehicle position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView vehicle_make;
        public TextView vehicle_model;
        public TextView no_of_wheels;
        public TextView no_of_passengers;
        public TextView status;
        public TextView hired_out_to;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
    View vi = convertView;
    final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.vehicle_list_row, null);
                holder = new ViewHolder();

                holder.vehicle_make = (TextView) vi.findViewById(R.id.vehicle_make);
                holder.vehicle_model = (TextView) vi.findViewById(R.id.vehicle_model);
                holder.no_of_wheels = (TextView) vi.findViewById(R.id.no_of_wheels);
                holder.no_of_passengers = (TextView) vi.findViewById(R.id.no_of_passengers);
                holder.status = (TextView) vi.findViewById(R.id.status);
                holder.hired_out_to = (TextView) vi.findViewById(R.id.hired_out_to);

                vi.setTag(holder);
            }
            else {
                holder = (ViewHolder) vi.getTag();
            }

            int iPassengers = lVehicle.get(position).getNumberOfPassengers();
            String sPassengers = " Passenger";

            if (iPassengers > 1) {
                sPassengers+="s";
            }

            holder.vehicle_make.setText(lVehicle.get(position).getVehicleMake().trim());
            holder.vehicle_model.setText(lVehicle.get(position).getVehicleModel().trim());
            holder.no_of_wheels.setText(String.valueOf(lVehicle.get(position).getNumberOfWheels())+" wheels");
            holder.no_of_passengers.setText(String.valueOf(iPassengers)+sPassengers);

            String sCustomerEmail = lVehicle.get(position).getCustomerEmail();
            String sStatus = "";

            if (!sCustomerEmail.equals("")) {
                sStatus = "Hired out to:";
                holder.status.setTextColor(Color.RED);
                holder.hired_out_to.setText(sCustomerEmail);
            }
            else {
                sStatus = "Available";
                holder.status.setTextColor(Color.parseColor("#109810"));
                holder.hired_out_to.setVisibility(View.GONE);
            }

            holder.status.setText(sStatus);
        } catch (Exception e) {

        }
        return vi;
    }
}
