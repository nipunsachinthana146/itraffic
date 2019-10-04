package com.example.anggarisky.splashtohomeangga;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.Response.Listener;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;


import static com.example.anggarisky.splashtohomeangga.ServiceHandler.response;

public class login_new extends AppCompatActivity implements View.OnTouchListener {

    EditText usernameTxt;
    EditText passwordTxt;
    String username;
    String password;
    Button logBtn;
    private Context context = this;
    private boolean succes = false;
    String logcheck;
    String userNameSh;
    private SharedPreferences sharedPreferences;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);

        usernameTxt = (EditText) findViewById(R.id.username);
        passwordTxt = (EditText) findViewById(R.id.password);
//        logBtn = (Button) findViewById(R.id.lbBtn);

        final ImageView img = (ImageView)findViewById(R.id.imageOriginal);
        ImageView iv = (ImageView) findViewById (R.id.image);
        if (iv != null) {
            iv.setOnTouchListener (this);
        }

        /////////////

        SharedPreferences spref11 = getSharedPreferences("session",0);
        String usernameSh= spref11.getString("isloged","");
        Log.e("usernmae","isloged - "+usernameSh);

        String logcheck= spref11.getString("isloged","");

        if(logcheck.equals("yes")){
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();
        }


        /////////////


    }


    @Override
    public boolean onTouch(View v, MotionEvent ev) {
        boolean handledHere = false;

        final int action = ev.getAction();

        final int evX = (int) ev.getX();
        final int evY = (int) ev.getY();
        int nextImage = -1;			// resource id of the next image to display


        // If we cannot find the imageView, return.
        ImageView imageView = (ImageView) v.findViewById (R.id.image);
        if (imageView == null) return false;

        // When the action is Down, see if we should show the "pressed" image for the default image.
        // We do this when the default image is showing. That condition is detectable by looking at the
        // tag of the view. If it is null or contains the resource number of the default image, display the pressed image.
        Integer tagNum = (Integer) imageView.getTag ();
        int currentResource = (tagNum == null) ? R.drawable.home_back : tagNum.intValue ();

        // Now that we know the current resource being displayed we can handle the DOWN and UP events.


        switch (action) {
            case MotionEvent.ACTION_DOWN :
                if (currentResource == R.drawable.home_back) {
                    nextImage = R.drawable.home_back;
                    handledHere = true;
       /*
       } else if (currentResource != R.drawable.p2_ship_default) {
         nextImage = R.drawable.p2_ship_default;
         handledHere = true;
       */
                } else handledHere = true;
                break;

            case MotionEvent.ACTION_UP :
                // On the UP, we do the click action.
                // The hidden image (image_areas) has three different hotspots on it.
                // The colors are red, blue, and yellow.
                // Use image_areas to determine which region the user touched.
                int touchColor = Utilities.getHotspotColor ((ImageView) findViewById(R.id.imagemask), evX, evY);

                // Compare the touchColor to the expected values. Switch to a different image, depending on what color was touched.
                // Note that we use a Color Tool object to test whether the observed color is close enough to the real color to
                // count as a match. We do this because colors on the screen do not match the map exactly because of scaling and
                // varying pixel density.
                ColorTool ct = new ColorTool ();
                int tolerance = 25;
                nextImage = R.drawable.home_back;


                //1-Log
                if (ct.closeMatch (Color.parseColor("#5a2af4"), touchColor, tolerance)){
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(intent);
//                    finish();

                checkLogin();
                    if(succes){
                        SharedPreferences sprefUserName = getSharedPreferences("session",0);
                        SharedPreferences.Editor logde = sprefUserName.edit();
                        logde.putString("userNameShf",""+username);
                        logde.commit();

                        SharedPreferences sprefisLoged = getSharedPreferences("session",0);
                        SharedPreferences.Editor logde1 = sprefisLoged.edit();
                        logde1.putString("isloged","yes");
                        logde1.commit();

                        Log.e("log","success");
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }

                //2-Register
                if (ct.closeMatch (Color.parseColor("#4e4c58"), touchColor, tolerance)){
                    Intent intent = new Intent(getApplicationContext(), UserRegister.class);
                    startActivity(intent);
                    finish();
                }


                // If the next image is the same as the last image, go back to the default.
                // toast ("Current image: " + currentResource + " next: " + nextImage);
                if (currentResource == nextImage) {
                    nextImage = R.drawable.home_back;
                }
                handledHere = true;
                break;

            default:
                handledHere = false;
        } // end switch

        if (handledHere) {

            if (nextImage > 0) {
                imageView.setImageResource (nextImage);
                imageView.setTag (nextImage);
            }
        }
        return handledHere;
    }


    private void checkLogin(){
        username=usernameTxt.getText().toString();
        password=passwordTxt.getText().toString();
        Toast.makeText(getApplicationContext(), "Login button ok" , Toast.LENGTH_LONG).show();

        Response.Listener<String> responseListner = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonresponse = new JSONObject(response);
                    boolean success = jsonresponse.getBoolean("success");



                    if (success) {

                        succes = true;

                        Log.e("lg","success");
                        Toast.makeText(getApplicationContext(), "Login Success" , Toast.LENGTH_LONG).show();

                        //////////////

                        ///////////////////

                        String username1 = jsonresponse.getString("username");

                        Log.e("log","successLast");




                    } else {

//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString("username", "na");
//                        editor.putString("islogged", "0");
//                        editor.commit();


                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Login Faild , Please Correct Username Password")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };


        LoginRequest loginRequest = new LoginRequest(username, password, responseListner);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(loginRequest);

    }



}
