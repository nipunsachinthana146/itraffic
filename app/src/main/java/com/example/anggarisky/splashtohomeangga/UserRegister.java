package com.example.anggarisky.splashtohomeangga;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

//import com.google.android.gms.common.api.GoogleApiClient;

public class UserRegister extends AppCompatActivity {
    private Toolbar RegToolBar;
    private Context context = this;
    Spinner spinner;

    private Button registerBtn;
    private Button cancelBtn;
    private EditText nameTxt;
    private EditText nicTxt;
    private EditText contactTxt;
    private EditText emailTxt;
    private EditText addressTxt1;
    private EditText addressTxt2;
    private EditText addressTxt3;
    private EditText postalCodeTxt;
    private RadioGroup radioGroup;
    private EditText vehicletypeTxt;
    private EditText password1Txt;
    private EditText password2Txt;
    private int status;



    String id = "13";
    String name = "sachinthana";
    String nic ="897864877V";
    String contact = "0719252001";
    String email = "No mail";
    String address1 = "no21";
    String address2 = "kamburpitita";
    String address3 = "matara";
    String postalCodes = "81100";
    String gender = "male";
    String vehicleType ="wheel";
    String password11 = "12345";
    String password22;
    int genderSelectedId;

    byte active = 1;
    byte deactive = 0;


    String MobilePattern = "[0-9]{10}";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String IDPattern = "[0-9]{9}+[a-z,A-Z]";

    // private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        RegToolBar = (Toolbar) findViewById(R.id.Regtoolbar);
        RegToolBar.setTitle("User Register");
        RegToolBar.setTitleTextColor(getResources().getColor(R.color.colorTitle));

        registerBtn = (Button) findViewById(R.id.register);
        cancelBtn = (Button) findViewById(R.id.cancel);

        nameTxt = (EditText) findViewById(R.id.nameTxt);
        nicTxt = (EditText) findViewById(R.id.nicTxt);
        contactTxt = (EditText) findViewById(R.id.contactTxt);
        emailTxt = (EditText) findViewById(R.id.emailTxt);
        addressTxt1 = (EditText) findViewById(R.id.Address1);
        addressTxt2 = (EditText) findViewById(R.id.Address2);
        addressTxt3 = (EditText) findViewById(R.id.Address3);
        postalCodeTxt = (EditText) findViewById(R.id.postalCodeT);
        radioGroup = (RadioGroup) findViewById(R.id.radio);
        //vehicletypeTxt = (EditText) findViewById(R.id.vehicleTypeTxt);
        password1Txt = (EditText) findViewById(R.id.password1);
        password2Txt = (EditText) findViewById(R.id.password2);


         spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                name = nameTxt.getText().toString();
                nic = nicTxt.getText().toString();
                contact = contactTxt.getText().toString();
                email = emailTxt.getText().toString();
                address1 = addressTxt1.getText().toString();
                address2 = addressTxt2.getText().toString();
                address3 = addressTxt3.getText().toString();
                postalCodes = postalCodeTxt.getText().toString();
                genderSelectedId = radioGroup.getCheckedRadioButtonId();
                password11 = password1Txt.getText().toString();
                password22 = password2Txt.getText().toString();


                String email1 = emailTxt.getText().toString().trim();
                if(genderSelectedId==2131231008){
                    gender="male";
                }
                else if(genderSelectedId==2131231009){
                    gender="female";
                }

                vehicleType=spinner.getSelectedItem().toString();


                ////Validation Start

                //Validation End

                new userReg1().execute();
                Log.e("Response: ", "add1 - "+address1);

//                Intent intent = new Intent(context, MainActivity.class);
//                startActivity(intent);
//                finish();


            }
        });
    }


    private class userReg1 extends AsyncTask<Void, Void, Void> {

        public ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UserRegister.this);
            pDialog.setMessage("Fetching Data..");
            pDialog.setCancelable(false);
            pDialog.show();

        }
        @Override
        protected Void doInBackground(Void... arg0) {


            try{
                ServiceHandler jsonParser = new ServiceHandler();

                String json = jsonParser.makeServiceCall(Utilities.dbUrl+"userRegister.php?id=" + id + "&name=" + name + "&nic=" + nic + "&contact=" + contact + "&email=" + email + "&address1=" + contact +"&address2=" + contact+"&address3=" + contact + "&postalcode=" + postalCodes +"&gender=" + gender + "&transporttype=" + vehicleType + "&password=" + password11 + "&status=" + active, ServiceHandler.POST);// local host

                //String json = jsonParser.makeServiceCall(Utilities.dbUrl+"userRegister.php?id=" + id + "&name=" + name + "&nic=" + nic + "&contact=" + contact + "&email=" + email + "&address1=" + address1 +"&address2=" + contact+"&address3=" + contact  , ServiceHandler.POST);// local host
                //String json = jsonParser.makeServiceCall("http://192.168.8.100/itraffic/userRegister.php?id=" + id + "&name=" + name + "&nic=" + nic + "&contact=" + contact + "&email=" + email + "&address1=" + contact +"&address2=" + contact+"&address3=" + contact + "&postalcode=" + postalCodes +"&gender=" + gender + "&transporttype=" + vehicleType + "&password=" + password11 + "&status=" + active, ServiceHandler.POST);// local host
                //String json = jsonParser.makeServiceCall("http://edocdb.000webhostapp.com/saveUser11.php?title=" + title  + "&firstName=" + firstName1 + "&lName=" + lastName1 + "&uName=" + userName1 + "&password1=" + password11 + "&gender=" + gender+ "&address=" + address1   + "&contact=" + contact1  + "&nic=" + nic1 + "&mail=" + email1   + "&tpId=" + tpId  + "&diseId=" + diseId + "&docId=" +docId + "&status=" + active, ServiceHandler.POST);// local host
                //String json = jsonParser.makeServiceCall("http://10.0.2.2/eDocDb/saveUser11.php?title=" + title  + "&firstName=" + firstName1 + "&lName=" + lastName1 + "&uName=" + userName1 + "&password1=" + password11 + "&gender=" + gender+ "&address=" + address1   + "&contact=" + contact1  + "&nic=" + nic1 + "&mail=" + email1   + "&tpId=" + tpId  + "&diseId=" + diseId + "&docId=" +docId + "&status=" + active, ServiceHandler.POST);// local host
                //String json = jsonParser.makeServiceCall("http://smaratchanell.000webhostapp.com//saveUser11.php?title=" + title  + "&firstName=" + firstName1 + "&lName=" + lastName1 + "&uName=" + userName1 + "&password1=" + password11 + "&gender=" + gender+ "&address=" + address1   + "&contact=" + contact1  + "&nic=" + nic1 + "&mail=" + email1   + "&tpId=" + tpId  + "&diseId=" + diseId + "&docId=" +docId + "&status=" + active, ServiceHandler.POST);// local host

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




}
