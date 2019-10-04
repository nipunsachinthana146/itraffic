package com.example.anggarisky.splashtohomeangga;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;




public class Ac4_Follow_Friend extends AppCompatActivity implements LocationListener{

    Handler handler1 = new Handler();
    Handler handler2 = new Handler();
    private boolean refreshstatus=true;
    String pinCode;
    private Context context = this;
    private String id ="0";
    private ImageView searchbtn;
    static String latitute;
    static String lontitute;
    EditText EditTxtFriendId;
    EditText EditTxtFriendPin;
    String FriendId;
    String FriendPin;
    LocationManager locationManager;
    public static double currentLatitude  ;
    public static double currentLongitude ;




    private ArrayList<ObjLatitute> objUserDetails;
    private ArrayList<String> udetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac4__follow__friend);

        SharedPreferences sprefUserName = getSharedPreferences("session",0);
        id= sprefUserName.getString("userNameShf","name");
        searchbtn = (ImageView) findViewById(R.id.searchbtn);

        Log.e("loge","nic is - "+id);
        EditTxtFriendId = (EditText) findViewById(R.id.friendId);
        EditTxtFriendPin = (EditText) findViewById(R.id.friendPin);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Your Pin code");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        Random rand = new Random();

// Obtain a number between [0 - 49].
        int n = rand.nextInt(50000);

// Add 1 to the result to get a number from the required range
// (i.e., [1 - 50]).
        n += 1;

        input.setText(""+n);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pinCode = input.getText().toString();

                Log.e("loge","pin is - "+pinCode);

                new updatePinCode().execute();
            }
        });

        builder.show();


////////////////
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }



        getLocation();



        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                Log.e("xxtest1","loca"+Utilities.currentLatitute);
                new updateCurrentLocation().execute();
            }
        }, 2000);

        ///////////////

//
//        for(boolean i = true; i==true;i=refreshstatus){
//            handler2.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    // Do something start
//
//
//                    // Do something end
//                }
//            }, 2000);
//
//        }




        searchbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                FriendId = EditTxtFriendId.getText().toString();
                FriendPin = EditTxtFriendPin.getText().toString();

                new showMapfollowFriend().execute();
                addFragment(new MAP_act4_follow(), false, "one");



                return false;
            }
        });







    }


    private class updateCurrentLocation extends AsyncTask<Void, Void, Void> {

        public ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Ac4_Follow_Friend.this);
            pDialog.setMessage("Fetching Data..");
            pDialog.setCancelable(false);
            pDialog.show();

        }
        @Override
        protected Void doInBackground(Void... arg0) {


            try{
                ServiceHandler jsonParser = new ServiceHandler();

                String json = jsonParser.makeServiceCall(Utilities.dbUrl+"updateFollowFriendmylocation.php?id=" + id + "&currentLattitude=" + Utilities.currentLatitute + "&currentLontitude=" + Utilities.currentLontitute , ServiceHandler.POST);// local host


                Log.e("Response: ", "Update_location> " + json);

            }
            catch (Exception e){
                e.printStackTrace();
            }

            return null;

        }





        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();


            Toast.makeText(context, "Saved", Toast.LENGTH_LONG).show();


        }

    }


    private class updatePinCode extends AsyncTask<Void, Void, Void> {

        public ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Ac4_Follow_Friend.this);
            pDialog.setMessage("Fetching Data..");
            pDialog.setCancelable(false);
            pDialog.show();

        }
        @Override
        protected Void doInBackground(Void... arg0) {


            try{
                ServiceHandler jsonParser = new ServiceHandler();

                String json = jsonParser.makeServiceCall(Utilities.dbUrl+"updateFollowFriend.php?id=" + id + "&pin=" + pinCode , ServiceHandler.POST);// local host


                Log.e("Response: ", "> " + json);

            }
            catch (Exception e){
                e.printStackTrace();
            }

            return null;

        }





        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();


            Toast.makeText(context, "Saved", Toast.LENGTH_LONG).show();


        }

    }



    private class showMapfollowFriend extends AsyncTask<Void, Void, Void> {

        public ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Ac4_Follow_Friend.this);
            pDialog.setMessage("Fetching Data..");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {




            ServiceHandler jsonParser2 = new ServiceHandler();

            String json2 = jsonParser2.makeServiceCall(Utilities.dbUrl+"getLatitute.php?nic=" +FriendId + "&pin=" + FriendPin, ServiceHandler.POST); // live


            Log.e("Response: ", "> " + json2);


            if (json2 != null) {
                try {
                    JSONObject jsonObj2 = new JSONObject(json2);
                    if (jsonObj2 != null) {
                        JSONArray avails1 = jsonObj2
                                .getJSONArray("data");

                        objUserDetails = new ArrayList<>();
                        objUserDetails.clear();
                        udetails = new ArrayList<>();
                        udetails.clear();

                        for (int i = 0; i < avails1.length(); i++) {

                            JSONObject obj1 = avails1.getJSONObject(i);

                            ObjLatitute objudetails = new ObjLatitute();
                            objudetails.setLatitute(obj1.getString("currentlatitude"));
                            objudetails.setLontitute(obj1.getString("currentlontitude"));



                            objUserDetails.add(objudetails);
                            latitute=obj1.getString("currentlatitude");
                            lontitute=obj1.getString("currentlontitude");

                            Log.e("latitude is",""+latitute);

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


//
//            ObjuserDetails objudetails1 = new ObjuserDetails();
//            Name.setText(""+name);
//            Contact.setText(""+contact);

        }

    }



    public void addFragment(Fragment fragment, boolean addToBackStack, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.container_frame_back, fragment, tag);
        ft.commitAllowingStateLoss();
    }



    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }












    //////////

    ///////////////


    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, (LocationListener) this);

        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());
        //Log.e("getlo","Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());
        currentLatitude  = location.getLatitude();
        currentLongitude = location.getLongitude();
        Utilities.currentLatitute = location.getLatitude();
        Utilities.currentLontitute= location.getLongitude();


//        handler1.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Do something after 5s = 5000ms
//                Utilities.currentLatitute =currentLatitude;
//                Utilities.currentLontitute=currentLongitude;
//
//                Log.e("test1","loca"+Utilities.currentLatitute);
//            }
//        }, 8900);


        Log.e("getlo",""+currentLatitude);
        Log.e("getlo",""+currentLongitude);


//        addFragment(new MAP_act1(), false, "one");

        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            //locationText.setText(locationText.getText() + "\n"+addresses.get(0).getAddressLine(0)+", "+
            //        addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2));
        }catch(Exception e)
        {

        }

        Log.e("getlo2","lati is - "+currentLatitude+"   loti is - "+currentLongitude);
        currentLatitude  = location.getLatitude();
        currentLongitude = location.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getApplicationContext(), "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
        Log.e("getlo4","lati is - "+currentLatitude+"   loti is - "+currentLongitude);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.e("getlo5","lati is - "+currentLatitude+"   loti is - "+currentLongitude);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.e("getlo3","lati is - "+currentLatitude+"   loti is - "+currentLongitude);
    }


    //////////





    public void getSpeechInput(View view) {

        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if(intent.resolveActivity(getPackageManager())!= null){

            startActivityForResult(intent,10);
        }
        else {
            Toast.makeText(this,"Your device is not support to this feature" , Toast.LENGTH_SHORT).show();
        }

    }






////////////

    //////////

}
