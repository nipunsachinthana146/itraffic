package com.example.anggarisky.splashtohomeangga;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nipun on 3/18/2018.
 */


public class AdpAvailability extends ArrayAdapter<ObjAvailability> {

    private Context mContext;
    private ArrayList<ObjAvailability> avlist;
    private String price;
    private String price1="R.s ";
    private String price2;

    public AdpAvailability(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<ObjAvailability> list) {
        super(context, 0, list);
        mContext = context;
        avlist = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.availableraw, parent, false);

        TextView startLocation = (TextView) listItem.findViewById(R.id.startLocation);
        TextView endLocation = (TextView) listItem.findViewById(R.id.endLocation);
        TextView emptySheets   = (TextView) listItem.findViewById(R.id.emptySheets);
        TextView description = (TextView) listItem.findViewById(R.id.Description);

        ObjAvailability availability = avlist.get(position);

        startLocation.setText(availability.getStartocation());
        endLocation.setText(availability.getEndLocation());
        emptySheets.setText(availability.getEmptySheets());
        description.setText("To confirm call - 0778252001");



        return listItem;
    }
}
