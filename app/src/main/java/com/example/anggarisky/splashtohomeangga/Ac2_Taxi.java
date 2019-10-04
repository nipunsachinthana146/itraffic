package com.example.anggarisky.splashtohomeangga;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Ac2_Taxi extends AppCompatActivity {

    private Context context = this;
    private ListView listView;
    ArrayList<ObjAvailability> availabilities;

    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac2__taxi);

        SharedPreferences sprefUserName = getSharedPreferences("session",0);
        id= sprefUserName.getString("userNameShf","name");

        listView = (ListView) findViewById(R.id.availistView);
        new getnearTaxi().execute();

        //addFragment(new MAP_act5_show_nearVehicle(), false, "one");
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


    private class getnearTaxi extends AsyncTask<Void, Void, Void> {

        public ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Ac2_Taxi.this);
            pDialog.setMessage("Fetching Data..");
            pDialog.setCancelable(false);
            pDialog.show();

        }
        @Override
        protected Void doInBackground(Void... arg0) {

            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(Utilities.dbUrl+"searchnearTaxi.php?nic=" +id, ServiceHandler.POST); // live


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


            AdpTaxiAvailability availability2 = new AdpTaxiAvailability(getApplicationContext(), availabilities);
            listView.setAdapter(availability2);


        }

    }


    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
