package com.example.anggarisky.splashtohomeangga;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Ac5_1_SearchVehicle extends AppCompatActivity {

    private Context context = this;
    private ListView listView;
    ArrayList<ObjAvailability> availabilities;
    private String startlocation;
    private String endlocation;
    private EditText startlocationEdt;
    private EditText endlocationEdt;
    private ImageView searchbtn;
    private ImageView refreshBtn;

    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac5_1__search_vehicle);

        SharedPreferences sprefUserName = getSharedPreferences("session",0);
        id= sprefUserName.getString("userNameShf","name");

        listView = (ListView) findViewById(R.id.availistView);
        new getCoreTravelFriend().execute();
        startlocationEdt = (EditText)findViewById(R.id.EditTextStartLocation);
        endlocationEdt = (EditText)findViewById(R.id.EditTextEndLocation);
        searchbtn = (ImageView) findViewById(R.id.searchbtn);
        refreshBtn= (ImageView) findViewById(R.id.refreshbtn);
         //addFragment(new MAP_act5_show_nearVehicle(), false, "one");

        searchbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startlocation = startlocationEdt.getText().toString();
                endlocation = endlocationEdt.getText().toString();

                if( (! startlocation.isEmpty()) && (! endlocation.isEmpty()) ){
                    new searchCo_travel().execute();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Enter Start or End Trip")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                }
                return false;
            }
        });


        refreshBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startlocation = startlocationEdt.getText().toString();
                endlocation = endlocationEdt.getText().toString();


                    new getCoreTravelFriend().execute();

                return false;
            }
        });

    }


    public void addFragment(Fragment fragment, boolean addToBackStack, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.showMap, fragment, tag);
        ft.commitAllowingStateLoss();
    }


    private class getCoreTravelFriend extends AsyncTask<Void, Void, Void> {

        public ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Ac5_1_SearchVehicle.this);
            pDialog.setMessage("Fetching Data..");
            pDialog.setCancelable(false);
            pDialog.show();

        }
        @Override
        protected Void doInBackground(Void... arg0) {

            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(Utilities.dbUrl+"searchCoreTravel.php?nic=" +id, ServiceHandler.POST); // live


            Log.e("Response: ", "> " + json);


            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray avails = jsonObj
                                .getJSONArray("data");

                        availabilities = new ArrayList<>();
                        availabilities.clear();
                        for (int i = 0; i < avails.length(); i++) {

                            JSONObject obj = avails.getJSONObject(i);

                            ObjAvailability availability = new ObjAvailability();
                            availability.setStartocation(obj.getString("startlocation"));
                            availability.setEndLocation(obj.getString("endlocation"));
                            availability.setEmptySheets(obj.getString("emptysheets"));

                            availabilities.add(availability);


                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();


            //   Toast.makeText(context, "Saved", Toast.LENGTH_LONG).show();


            AdpAvailability availability1 = new AdpAvailability(getApplicationContext(), availabilities);
            listView.setAdapter(availability1);


        }

    }

    private class searchCo_travel extends AsyncTask<Void, Void, Void> {

        public ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Ac5_1_SearchVehicle.this);
            pDialog.setMessage("Fetching Data..");
            pDialog.setCancelable(false);
            pDialog.show();

        }
        @Override
        protected Void doInBackground(Void... arg0) {

            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(Utilities.dbUrl+"searchCoreTravelTrip.php?startlocation=" +startlocation  +"&endlocation=" + endlocation  , ServiceHandler.POST); // live


            Log.e("Response: ", "> " + json);


            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray avails = jsonObj
                                .getJSONArray("data");

                        availabilities = new ArrayList<>();
                        availabilities.clear();
                        for (int i = 0; i < avails.length(); i++) {

                            JSONObject obj = avails.getJSONObject(i);

                            ObjAvailability availability = new ObjAvailability();
                            availability.setStartocation(obj.getString("startlocation"));
                            availability.setEndLocation(obj.getString("endlocation"));
                            availability.setEmptySheets(obj.getString("emptysheets"));

                            availabilities.add(availability);


                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();


            //   Toast.makeText(context, "Saved", Toast.LENGTH_LONG).show();


            AdpAvailability availability1 = new AdpAvailability(getApplicationContext(), availabilities);
            listView.setAdapter(availability1);


        }

    }


    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
