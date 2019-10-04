package com.example.anggarisky.splashtohomeangga;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class activity_ac1__show_map extends AppCompatActivity {

   // public class activity_ac1__show_map extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    private static activity_ac1__show_map inst;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ListView smsListView;
    ArrayAdapter arrayAdapter;
    TextToSpeech t1;
     String message;
     String phonenumber;
     String message1 , phoneNo;
    String[] arr = new String[10];

    private TextView textOutput;
    private static final int REQUEST_CODE = 100;

    Button b1;


    private static String ms;

    public static activity_ac1__show_map instance() {

        return inst;
    }


    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac1__show_map);

        textOutput= (TextView) findViewById(R.id.textOutput);

        b1=(Button)findViewById(R.id.button);
        smsListView = (ListView) findViewById(R.id.SMSList);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsMessagesList);

        smsListView.setAdapter(arrayAdapter);

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                }
            }
        });


        if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {


            refreshSmsInbox();

        } else {

            final int REQUEST_CODE_ASK_PERMISSIONS = 123;
            ActivityCompat.requestPermissions(activity_ac1__show_map.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }


        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(5000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(getApplicationContext(),login_new.class);
                    //startActivity(intent);

                }
            }
        };
        timerThread.start();


    }



    public void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        arrayAdapter.clear();

            String str = "" + smsInboxCursor.getString(indexAddress) +
                    "\n" + smsInboxCursor.getString(indexBody) + "\n";

             phonenumber = smsInboxCursor.getString(indexAddress) ;//phone number
               message = smsInboxCursor.getString(indexBody) ;
        arr[0]=phonenumber;


        arrayAdapter.add(message);
          //  arrayAdapter.add(str);



    }

    public void updateList(final  String smsMessage) {

        ms=smsMessage;
        Handler handler = new Handler();

        handler.postDelayed(
                new Runnable() {
                    public void run() {
                        t1.speak("Hello Driver, You have some Message Can I read the message? Yes Or No", TextToSpeech.QUEUE_FLUSH, null);
                        Handler handler1 = new Handler();
                        handler1.postDelayed(
                                new Runnable() {
                                    public void run() {
                                        onClick();
                                    }
                                }, 4000);
                    }
                }, 1500);

        arrayAdapter.insert(smsMessage, 0);
        arrayAdapter.notifyDataSetChanged();

    }


    public void onClick()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException a) {

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String xxx=result.get(0);

                    if(xxx.equals("yes")){
                       // textOutput.setText("my name is kavinda");
                        Handler handler = new Handler();
                        handler.postDelayed(
                                        new Runnable() {
                            public void run() {
                                t1.speak(ms, TextToSpeech.QUEUE_FLUSH, null);
                            }
                        }, 1500);


                        handler.postDelayed(
                                new Runnable() {
                                    public void run() {
                                        t1.speak("Do You Send the message? Yes or Nor?", TextToSpeech.QUEUE_FLUSH, null);

                                        Handler handler3 = new Handler();
                                        handler3.postDelayed(
                                                new Runnable() {
                                                    public void run() {
                                                        onClick();
                                                    }
                                                }, 4000);

                                    }
                                }, 3000);

                        if(xxx.equals("yes")){

                            Handler handler5 = new Handler();
                            handler5.postDelayed(
                                    new Runnable() {
                                        public void run() {
                                          //  t1.speak("please give your message", TextToSpeech.QUEUE_FLUSH, null);
                                          //  refreshSmsInbox();
                                           // onClick();
                                            smsSend();

                                            Log.e("ddd","xxx"+ arr[0]);
                                        }
                                    }, 1500);

                           // smsSend();


                        }
                        else if(xxx.equals("no")){

                            Handler handler2 = new Handler();
                            handler2.postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            t1.speak("Thank You Driver", TextToSpeech.QUEUE_FLUSH, null);

                                        }
                                    }, 1500);
                        }


                    }
                    else if(xxx.equals("no")){

                        Handler handler = new Handler();
                        handler.postDelayed(
                                new Runnable() {
                                    public void run() {
                                        t1.speak("Thanks Driver Your Well Come", TextToSpeech.QUEUE_FLUSH, null);
                                    }
                                }, 1500);

                    }

                   // textOutput.setText(result.get(0));

                }
                break;
            }

        }
    }

    private void smsSend(){

       // phoneNo = txtphoneNo.getText().toString();
      //  message1 = txtMessage.getText().toString();

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(arr[0], null, "Dear, I'm Driving, I will Call You later", null,
                null);
        Toast.makeText(getApplicationContext(), "SMS sent.",
                Toast.LENGTH_LONG).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    smsSend(); //call method after permission grant
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.",
                            Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }



}








