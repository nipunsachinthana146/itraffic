package com.example.anggarisky.splashtohomeangga;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MAP_act4_follow extends Fragment {

    double currentLatitude;
    double currentLongitude;
    Handler handler1 = new Handler();
    double FriendcurrentLatitude;
    double FriendcurrentLongitude;

    LocationManager locationManager;

    public MAP_act4_follow() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap mMap) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                currentLatitude = Ac1_Show_Map.currentLatitude;
                currentLongitude = Ac1_Show_Map.currentLongitude;



                Log.e("getloact1","lati is - "+currentLatitude+"   loti is - "+currentLongitude);
                mMap.clear(); //clear old markers

                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        Log.e("xxtest1","loca"+Utilities.currentLatitute);

                        CameraPosition googlePlex = CameraPosition.builder()
                                .target(new LatLng(Utilities.currentLatitute,Utilities.currentLontitute))
                                .zoom(13)
                                .bearing(0)
                                .tilt(45)
                                .build();

                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10000, null);

                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Utilities.currentLatitute,Utilities.currentLontitute))
                                .title("Spider Man")
                                .icon(bitmapDescriptorFromVector(getActivity(),R.drawable.currentlocationcar)));

                    }
                }, 1000);


                FriendcurrentLatitude =6.937083;
                FriendcurrentLongitude =79.986419;

                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(FriendcurrentLatitude,FriendcurrentLongitude))
                        .title("Iron Man")
                        .icon(bitmapDescriptorFromVector(getActivity(),R.drawable.destinationcar)));


//                mMap.addMarker(new MarkerOptions()
//                        .position(new LatLng(6.9209594,79.9755114))
//                        .title("Captain America"));
            }
        });


        return rootView;
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }







}
