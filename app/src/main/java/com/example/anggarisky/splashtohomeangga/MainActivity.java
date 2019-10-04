package com.example.anggarisky.splashtohomeangga;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ImageView bgapp, clover;
    LinearLayout textsplash, texthome, menus;
    Animation frombottom;

    ImageView img_act1,img_act2,img_act3,img_act4,img_act5,img_act6;
    TextView start_welcome, start_username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img_act1 = (ImageView) findViewById(R.id.img_act1);
        img_act2 = (ImageView) findViewById(R.id.img_act2);
        img_act3 = (ImageView) findViewById(R.id.img_act3);
        img_act4 = (ImageView) findViewById(R.id.img_act4);
        img_act5 = (ImageView) findViewById(R.id.img_act5);
        img_act6 = (ImageView) findViewById(R.id.img_act6);

        start_welcome  = (TextView) findViewById(R.id.start_welcomemsg);
        start_username = (TextView) findViewById(R.id.start_username);

        setWelcomemsg();

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        bgapp = (ImageView) findViewById(R.id.bgapp);
        clover = (ImageView) findViewById(R.id.clover);
        textsplash = (LinearLayout) findViewById(R.id.textsplash);
        texthome = (LinearLayout) findViewById(R.id.texthome);
        menus = (LinearLayout) findViewById(R.id.menus);

        bgapp.animate().translationY(-1900).setDuration(800).setStartDelay(300);
        clover.animate().alpha(0).setDuration(800).setStartDelay(600);
        textsplash.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(300);

        texthome.startAnimation(frombottom);
        menus.startAnimation(frombottom);

        img_act1.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                //Intent intent = new Intent(getApplicationContext(), Ac1_Show_Map.class);
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
                finish();
                return false;
            }
        });

        img_act2.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                Intent intent = new Intent(getApplicationContext(), Ac2_Taxi.class);
                startActivity(intent);
                finish();
                return false;
            }
        });

        img_act3.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                Intent intent = new Intent(getApplicationContext(), Ac3_Weather.class);
                startActivity(intent);
                finish();
                return false;
            }
        });

        img_act4.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                Intent intent = new Intent(getApplicationContext(), Ac4_Follow_Friend.class);
                startActivity(intent);
                finish();
                return false;
            }
        });

        img_act5.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                Intent intent = new Intent(getApplicationContext(), Ac5_Core_Travel.class);
                startActivity(intent);
                finish();
                return false;
            }
        });

        img_act6.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                Intent intent = new Intent(getApplicationContext(), Ac6_Profile.class);
                startActivity(intent);
                finish();
                return false;
            }
        });

    }

    public void setWelcomemsg(){

        int currentTimeHour  =0;
        int currentTimeMini  =0;
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        SimpleDateFormat sdf1 = new SimpleDateFormat("mm");

        String currentTimeHourS = sdf.format(new Date());
        String currentTimeMiniS = sdf1.format(new Date());

        currentTimeHour = Integer.parseInt(""+currentTimeHourS);
        currentTimeMini = Integer.parseInt(""+currentTimeMiniS);

        Log.e("printTimee",""+currentTimeHour);
        Log.e("printTimee",""+currentTimeMini);

        if( (currentTimeHour>=0 && currentTimeMini>=0) && (currentTimeHour<=11 && currentTimeMini<59) ){
            Log.e("printTimee","Good Morning");
            start_welcome.setText("Good Morning");
        }

        else if( (currentTimeHour>=12 && currentTimeMini>=0) && (currentTimeHour<=16 && currentTimeMini<59) ){
            Log.e("printTimee","Good afternoon");
            start_welcome.setText("Good afternoon");
        }

        else if( (currentTimeHour>=17 && currentTimeMini>=0) && (currentTimeHour<=23 && currentTimeMini<59) ){
            Log.e("printTimee","Good evening");
            start_welcome.setText("Good evening");
        }



        start_username.setText("Nipun");
    }
}
