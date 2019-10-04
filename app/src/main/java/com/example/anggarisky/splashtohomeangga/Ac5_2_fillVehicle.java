package com.example.anggarisky.splashtohomeangga;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Ac5_2_fillVehicle extends AppCompatActivity {

    private ArrayList<ObjmaxUserId> ObjmaxUserId;
    private ArrayList<String> uMaxId;

    Handler handler1 = new Handler();
    Handler handler2 = new Handler();
    Handler handler3 = new Handler();
    Handler handler4 = new Handler();
    Handler handler5 = new Handler();
    int typingImages[] = new int[6];
    int questiImages[] = new int[5];
    ImageView q1 ,q2 ,q3, q4, q5 ,q2Done,q3Done,q4Done;
    Button q1Yes,q1No ,q5Yes,q5No ;
    EditText q2Answer ,q3Answer,q4Answer ;
    String comptime="noTime";
    String tblId="0";
    int tblIdINT=0;
    String id="0";
    int active = 1;
    int deactive = 0;
    int startTravel = 2;
    String coreTravelRefNo="0";
    String Invite = "Yes";
    int emptySheet=1;
    String startPlace = "Malabe";
    String endPlace = "Kaduwela";
    String ShowPhoneNum = "Yes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac5_2_fill_vehicle);

        arrayLoad();
        setQuestion_1();

        SharedPreferences sprefUserName = getSharedPreferences("session",0);
        id= sprefUserName.getString("userNameShf","name");

        new getMaxTblId().execute();


        q1Yes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Invite = "Yes";
                setQuestion_2();
                return false;
            }
        });

        q1No.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                onBackPressed();

                return false;
            }
        });

        q2Done.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (!(q2Answer.getText().toString().matches(""))){
                    emptySheet = Integer.parseInt(q2Answer.getText().toString());
                    setQuestion_3();
                }

                return false;
            }
        });

        q3Done.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!( q3Answer.getText().toString().matches("")) ){
                    startPlace = q3Answer.getText().toString();
                    setQuestion_4();
                }
                return false;
            }
        });

        q4Done.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!( q4Answer.getText().toString().matches("")) ){
                    endPlace = q4Answer.getText().toString();
                    setQuestion_5();
                }
                return false;
            }
        });

        q5No.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ShowPhoneNum="No";
                Toast.makeText(getApplicationContext(),"You details succesfully added", Toast.LENGTH_SHORT).show();
                new inviteCoreTravel().execute();
                return false;
            }
        });

        q5Yes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ShowPhoneNum="Yes";
                Toast.makeText(getApplicationContext(),"You details succesfully added", Toast.LENGTH_SHORT).show();
                new inviteCoreTravel().execute();
                Log.e("emptysh","emptySheet - "+ emptySheet);





                return false;
            }
        });
    }

    private void arrayLoad(){

        q1    = (ImageView) findViewById(R.id.q1);
        q1Yes = (Button) findViewById(R.id.q1Yes);
        q1No  = (Button) findViewById(R.id.q1No);

        q2    = (ImageView) findViewById(R.id.q2);
        q2Answer = (EditText) findViewById(R.id.editText);
        q2Done  = (ImageView) findViewById(R.id.q2done);

        q3    = (ImageView) findViewById(R.id.q3);
        q3Answer = (EditText) findViewById(R.id.editText1);
        q3Done  = (ImageView) findViewById(R.id.q3done);

        q4    = (ImageView) findViewById(R.id.q4);
        q4Answer = (EditText) findViewById(R.id.editText2);
        q4Done  = (ImageView) findViewById(R.id.q4done);

        q5    = (ImageView) findViewById(R.id.q5);
        q5Yes = (Button) findViewById(R.id.q5Agree);
        q5No  = (Button) findViewById(R.id.q5ignore);


        typingImages[0] = R.drawable.assis_typing_1;
        typingImages[1] = R.drawable.assis_typing_2;
        typingImages[2] = R.drawable.assis_typing_3;
        typingImages[3] = R.drawable.assis_typing_4;
        typingImages[4] = R.drawable.assis_typing_5;
        typingImages[5] = R.drawable.assis_typing_6;

        questiImages[0] = R.drawable.assis_q1;
        questiImages[1] = R.drawable.assis_q2;
        questiImages[2] = R.drawable.assis_q3;
        questiImages[3] = R.drawable.assis_q4;
        questiImages[4] = R.drawable.assis_q5;
    }

    private void setQuestion_1(){
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q1.setImageResource(typingImages[0]);
                q1.setVisibility(View.VISIBLE);

            }
        }, 500);

        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q1.setImageResource(typingImages[1]);

            }
        }, 1300);
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q1.setImageResource(typingImages[2]);

            }
        }, 2100);
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q1.setImageResource(typingImages[3]);

            }
        }, 2900);
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q1.setImageResource(typingImages[4]);

            }
        }, 3700);
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q1.setImageResource(typingImages[5]);

            }
        }, 4500);

        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q1.setImageResource(questiImages[0]);

            }
        }, 5500);

        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q1Yes.setVisibility(View.VISIBLE);

            }
        }, 6500);

        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q1No.setVisibility(View.VISIBLE);

            }
        }, 7000);
    }

    private void setQuestion_2(){
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q2.setImageResource(typingImages[0]);
                q2.setVisibility(View.VISIBLE);

            }
        }, 500);

        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q2.setImageResource(typingImages[1]);

            }
        }, 1300);
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q2.setImageResource(typingImages[2]);

            }
        }, 2100);
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q2.setImageResource(typingImages[3]);

            }
        }, 2900);
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q2.setImageResource(typingImages[4]);

            }
        }, 3700);
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q2.setImageResource(typingImages[5]);

            }
        }, 4500);

        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q2.setImageResource(questiImages[1]);

            }
        }, 5500);

        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q2Answer.setVisibility(View.VISIBLE);

            }
        }, 6500);

        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q2Done.setVisibility(View.VISIBLE);

            }
        }, 7000);


    }

    private void setQuestion_3(){
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q3.setImageResource(typingImages[0]);
                q3.setVisibility(View.VISIBLE);

            }
        }, 500);

        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q3.setImageResource(typingImages[1]);

            }
        }, 1300);
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q3.setImageResource(typingImages[2]);

            }
        }, 2100);
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q3.setImageResource(typingImages[3]);

            }
        }, 2900);
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q3.setImageResource(typingImages[4]);

            }
        }, 3700);
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q3.setImageResource(typingImages[5]);

            }
        }, 4500);

        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q3.setImageResource(questiImages[2]);

            }
        }, 5500);

        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q3Answer.setVisibility(View.VISIBLE);

            }
        }, 6500);

        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q3Done.setVisibility(View.VISIBLE);

            }
        }, 7000);
    }

    private void setQuestion_4(){
        handler4.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q4.setImageResource(typingImages[0]);
                q4.setVisibility(View.VISIBLE);

            }
        }, 500);

        handler4.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q4.setImageResource(typingImages[1]);

            }
        }, 1300);
        handler4.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q4.setImageResource(typingImages[2]);

            }
        }, 2100);
        handler4.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q4.setImageResource(typingImages[3]);

            }
        }, 2900);
        handler4.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q4.setImageResource(typingImages[4]);

            }
        }, 3700);
        handler4.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q4.setImageResource(typingImages[5]);

            }
        }, 4500);

        handler4.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q4.setImageResource(questiImages[3]);

            }
        }, 5500);

        handler4.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q4Answer.setVisibility(View.VISIBLE);

            }
        }, 6500);

        handler4.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q4Done.setVisibility(View.VISIBLE);

            }
        }, 7000);
    }

    private void setQuestion_5(){
        handler5.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q5.setImageResource(typingImages[0]);
                q5.setVisibility(View.VISIBLE);

            }
        }, 500);

        handler5.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q5.setImageResource(typingImages[1]);

            }
        }, 1300);
        handler5.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q5.setImageResource(typingImages[2]);

            }
        }, 2100);
        handler5.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q5.setImageResource(typingImages[3]);

            }
        }, 2900);
        handler5.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q5.setImageResource(typingImages[4]);

            }
        }, 3700);
        handler5.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q5.setImageResource(typingImages[5]);

            }
        }, 4500);

        handler5.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q5.setImageResource(questiImages[4]);

            }
        }, 5500);

        handler5.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q5Yes.setVisibility(View.VISIBLE);

            }
        }, 6500);

        handler5.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                q5No.setVisibility(View.VISIBLE);

            }
        }, 7000);
    }

    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private class getMaxTblId extends AsyncTask<Void, Void, Void> {

        public ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Ac5_2_fillVehicle.this);
            pDialog.setMessage("Fetching Data..");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {




            ServiceHandler jsonParser3 = new ServiceHandler();

            String json2 = jsonParser3.makeServiceCall(Utilities.dbUrl+"getMaxblId.php?nic=" +id, ServiceHandler.POST); // live


            Log.e("Response: ", "> " + json2);


            if (json2 != null) {
                try {
                    JSONObject jsonObj2 = new JSONObject(json2);
                    if (jsonObj2 != null) {
                        JSONArray avails1 = jsonObj2
                                .getJSONArray("data");

                        ObjmaxUserId = new ArrayList<>();
                        ObjmaxUserId.clear();
                        ObjmaxUserId = new ArrayList<>();
                        ObjmaxUserId.clear();

                        for (int i = 0; i < avails1.length(); i++) {

                            JSONObject obj1 = avails1.getJSONObject(i);

                            ObjmaxUserId objmaxUserId = new ObjmaxUserId();
                            objmaxUserId.setMaxUserId(obj1.getString("tblid"));



                            ObjmaxUserId.add(objmaxUserId);
                            tblId=obj1.getString("tblid");


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




            Log.e("maxTbl","maxtableId is -"+tblId);
            tblIdINT = Integer.parseInt(tblId);
            tblIdINT = tblIdINT+1;
            Log.e("maxTbl","tblIdINT is -"+tblIdINT);

        }

    }



    private class inviteCoreTravel extends AsyncTask<Void, Void, Void> {

        public ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Ac5_2_fillVehicle.this);
            pDialog.setMessage("Fetching Data..");
            pDialog.setCancelable(false);
            pDialog.show();

        }
        @Override
        protected Void doInBackground(Void... arg0) {


            try{
                ServiceHandler jsonParser = new ServiceHandler();

                String json = jsonParser.makeServiceCall(Utilities.dbUrl+"inviteCoreTravel_encrypt.php?nic=" + id + "&tblid=" + tblIdINT + "&emptysheets=" + emptySheet + "&startlocation=" + startPlace+ "&endlocation=" + endPlace+ "&showphone=" + ShowPhoneNum+ "&comptime=" + comptime+ "&status=" + active, ServiceHandler.POST);// local host
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


            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();


        }

    }

    private class inviteCoreTravel_encrypt extends AsyncTask<Void, Void, Void> {

        public ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Ac5_2_fillVehicle.this);
            pDialog.setMessage("Fetching Data..");
            pDialog.setCancelable(false);
            pDialog.show();

        }
        @Override
        protected Void doInBackground(Void... arg0) {


            try{
                ServiceHandler jsonParser = new ServiceHandler();

                String json = jsonParser.makeServiceCall(Utilities.dbUrl+"inviteCoreTravel_encrypt.php?nic=" + id + "&tblid=" + tblIdINT + "&emptysheets=" + emptySheet + "&startlocation=" + startPlace+ "&endlocation=" + endPlace+ "&showphone=" + ShowPhoneNum+ "&comptime=" + comptime+ "&status=" + active, ServiceHandler.POST);// local host
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


            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();


        }

    }

}
