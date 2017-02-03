package com.example.darek.nav_10;

import com.android.volley.Response;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 212449139 on 2/1/2017.
 */

public class LoginRequest extends StringRequest {

    private Map<String,String> params;

    public LoginRequest (String userName, String password, String url, Response.Listener<String> listener){
        super (Method.POST,url,listener,null);
        params = new HashMap<>();
        params.put("userName",userName);
        params.put("password",password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
