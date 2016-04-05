package com.example.ketansharma.carhirecompany;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by ketan.sharma.
 */
public class vehicle_filter_dialog extends DialogFragment {
    String sSQLClause = "";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final ArrayList mSelectedItems = new ArrayList();
        final String[] vehicle_filter_array = getResources().getStringArray(R.array.vehicle_filter_array);

        int iDefaultCheck = 0;
        vehicle_database vehicle_db = new vehicle_database(getActivity());
        ArrayList<String> filter = vehicle_db.getArray2();

        if (filter.size() > 0) {
            switch (filter.get(1).trim()) {
                case "":
                    iDefaultCheck = 0;
                    break;
                case "Email = ''":
                    iDefaultCheck = 1;
                    break;
                case "Email <> ''":
                    iDefaultCheck = 2;
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.filter_list);

        builder.setSingleChoiceItems(vehicle_filter_array, iDefaultCheck, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                switch (vehicle_filter_array[arg1]) {
                    case "All":
                        sSQLClause = "";
                        break;
                    case "Available":
                        sSQLClause = " Email = ''";
                        break;
                    case "Booked Out":
                        sSQLClause = " Email <> ''";
                }
            }
        });

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                vehicle_database vehicle_db = new vehicle_database(getActivity());
                vehicle_db.insert2(sSQLClause);

                final Intent intent = new Intent();
                intent.setAction("android.intent.action.MAIN");
                intent.putExtra("update_msg","Update List");
                getActivity().sendBroadcast(intent);
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        return builder.create();
    }
}
