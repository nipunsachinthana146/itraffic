package com.example.anggarisky.splashtohomeangga;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Ac6_Profile extends AppCompatActivity {

    private EditText Location;
    private EditText TransportType;
    private EditText Name;
    private EditText Address1;
    private EditText Address2;
    private EditText Address3;
    private EditText PostalCode;
    private EditText Contact;

    private ImageView ProPic;

    Button EditBtn;
    Button SaveBtn;
    Button LogOutBtn;

    String usernameSh;
    String id;

    String name,nic,contact,emai,gender, address1,address2,address3,postalcode, transportType;

    private String idno = "962034099V";

    private ArrayList<ObjuserDetails> objUserDetails;
    private ArrayList<String> udetails;

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac6__profile);

        Location = (EditText) findViewById(R.id.Location);
        TransportType = (EditText) findViewById(R.id.TransportType);
        Name = (EditText) findViewById(R.id.Name);
        Address1 = (EditText) findViewById(R.id.Address1);
        Address2 = (EditText) findViewById(R.id.Address2);
        Address3 = (EditText) findViewById(R.id.Address3);
        PostalCode = (EditText) findViewById(R.id.postalCode);
        Contact = (EditText) findViewById(R.id.Contact);
        EditBtn = (Button) findViewById(R.id.EditBtn);
        SaveBtn = (Button) findViewById(R.id.SaveBtn);
        LogOutBtn = (Button) findViewById(R.id.logOutBtn);
        ProPic = (ImageView) findViewById(R.id.propic);


        SharedPreferences sprefUserName = getSharedPreferences("session",0);
        id= sprefUserName.getString("userNameShf","name");
        Log.e("usernmae","userName - "+id);





        LogOutBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                SharedPreferences spref11 = getSharedPreferences("session",0);
                SharedPreferences.Editor islogde = spref11.edit();
                islogde.putString("isloged","");
                islogde.commit();

                Intent intent = new Intent(getApplicationContext(), login_new.class);
                startActivity(intent);
                finish();
                return false;
            }
        });

        EditBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Location.setKeyListener(null);
                Location.setFocusable(true);
                Location.setFocusableInTouchMode(true);
                Location.setClickable(true);

                TransportType.setEnabled(true);
                Name.getEditableText();
                Address1.getEditableText();
                Address2.getEditableText();
                Address3.getEditableText();
                PostalCode.getEditableText();
                Contact.getEditableText();

                EditBtn.setVisibility(View.INVISIBLE);
                SaveBtn.setVisibility(View.VISIBLE);
                return false;
            }
        });
        new GetUserInfor().execute();

    }


    private class GetUserInfor extends AsyncTask<Void, Void, Void> {

        public ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Ac6_Profile.this);
            pDialog.setMessage("Fetching Data..");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {




            ServiceHandler jsonParser2 = new ServiceHandler();

            String json2 = jsonParser2.makeServiceCall(Utilities.dbUrl+"getUserInfo.php?nic=" +id, ServiceHandler.POST); // live


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

                            ObjuserDetails objudetails = new ObjuserDetails();
                            objudetails.setName(obj1.getString("name"));
                            objudetails.setNic(obj1.getString("nic"));
                            objudetails.setContact(obj1.getString("contact"));
                            objudetails.setEmail(obj1.getString("email"));
                            objudetails.setAddress(obj1.getString("address1"));
                            objudetails.setAddress2(obj1.getString("address2"));
                            objudetails.setAddress3(obj1.getString("address3"));
                            objudetails.setAddress(obj1.getString("postalcode"));
                            objudetails.setTransportType(obj1.getString("transportType"));
                            objudetails.setAddress(obj1.getString("postalcode"));
                            objudetails.setGender(obj1.getString("gender"));


                            objUserDetails.add(objudetails);
                            name=obj1.getString("name");
                            nic=obj1.getString("nic");
                            contact=obj1.getString("contact");
                            emai=obj1.getString("email");
                            address1=obj1.getString("address1");
                            address2=obj1.getString("address2");
                            address3=obj1.getString("address3");
                            postalcode=obj1.getString("postalcode");
                            transportType=obj1.getString("transportType");
                            gender=obj1.getString("gender");

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



            ObjuserDetails objudetails1 = new ObjuserDetails();
            Name.setText(""+name);
            Contact.setText(""+contact);
            TransportType.setText(""+transportType);
            Address1.setText(""+address1);
            Address2.setText(""+address2);
            Address3.setText(""+address3);
            PostalCode.setText(""+postalcode);

            if(gender=="male"){
                ProPic.setImageResource(R.drawable.male_icon);
            }
            if(gender=="female"){
                ProPic.setImageResource(R.drawable.female_icon);
            }

        }

    }

    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
