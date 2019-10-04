package com.example.anggarisky.splashtohomeangga;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class Ac5_Core_Travel extends AppCompatActivity {

    ImageView img1;
    ImageView img2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac5__core__travel);

        img1 = (ImageView)findViewById(R.id.searchVehicle);
        img2 = (ImageView)findViewById(R.id.setCoreTravel);

        img1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(getApplicationContext(), Ac5_1_SearchVehicle.class);
                startActivity(intent);
                finish();
                return false;
            }
        });

        img2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(getApplicationContext(), Ac5_2_fillVehicle.class);
                startActivity(intent);
                finish();
                return false;
            }
        });
    }

    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
