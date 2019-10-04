package com.example.anggarisky.splashtohomeangga;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nipun on 4/27/2018.
 */

public class LoginRequest extends StringRequest {
   //private static final String Login_Request_URL = "http://192.168.8.101/eDocDb/LoginU.php";

   // private static final String Login_Request_URL = "http://10.0.2.2/eDocDb/LoginU.php";
    private static final String Login_Request_URL = Utilities.dbUrl+"LoginU.php";

    private Map<String,String> params;

    public LoginRequest(String username, String password, Response.Listener<String>Listner){
        super(Method.POST,Login_Request_URL, Listner, null);

        params = new HashMap<>();
        params.put("username",username);
        params.put("password",password);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
