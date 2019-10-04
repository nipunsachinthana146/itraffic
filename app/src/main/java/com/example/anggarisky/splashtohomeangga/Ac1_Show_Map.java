package com.example.anggarisky.splashtohomeangga;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Ac1_Show_Map extends AppCompatActivity implements LocationListener {

    private TextView txtView;
    private Button btn1, btn2;
    LocationManager locationManager;
    public static double currentLatitude  ;
    public static double currentLongitude ;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac1__show_map_my);

        txtView = (TextView) findViewById(R.id.txtView);




        ////////////////
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }



            getLocation();
            Log.e("test1","test01");
        ///////////////
        Log.e(".","lati is - "+currentLatitude+"   loti is - "+currentLongitude);
        addFragment(new MAP_act1(), false, "one");

        Log.e("UtiLoca","locais "+Utilities.currentLatitute);

    }

    ///////////////


    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);

        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLatitude  = location.getLatitude();
        currentLongitude = location.getLongitude();
        Utilities.currentLatitute = location.getLatitude();
        Utilities.currentLontitute= location.getLongitude();

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


    public void addFragment(Fragment fragment, boolean addToBackStack, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.showMap, fragment, tag);
        ft.commitAllowingStateLoss();
    }


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




    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){

            case 10:

                if (resultCode== RESULT_OK && data!= null){
                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtView.setText(result.get(0));
                }

                break;
        }

    }

////////////
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
