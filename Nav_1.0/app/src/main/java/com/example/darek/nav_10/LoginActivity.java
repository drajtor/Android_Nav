package com.example.darek.nav_10;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.net.URL;
import java.net.URLConnection;

public class LoginActivity extends AppCompatActivity {

    EditText EditText_UserName;
    EditText EditText_Password;
    Button Button_Login;
    private static final int SERVER_TIMEOUT_MS = 5000;
    private static final String LOGIN_REQUEST_URL = "https://raytek.000webhostapp.com/Login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText_UserName = (EditText)findViewById(R.id.editText_UserName);
        EditText_Password = (EditText)findViewById(R.id.editText_Password);
        Button_Login = (Button)findViewById(R.id.button_Login);

        Button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String userName = EditText_UserName.getText().toString();
            String password = EditText_Password.getText().toString();

            try{
                if ( userName.equals("aaa") && password.equals("aaa") ){ //TODO remove
                    jumpToMainActivity();
                }else{
                    if (false == isOnline()) {
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(LoginActivity.this);
                        alertBuilder.setMessage("No internet connection")
                                .setNegativeButton("Ok",null)
                                .create()
                                .show();
                    }else if(false == isConnectedToServer(LOGIN_REQUEST_URL,SERVER_TIMEOUT_MS)){
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(LoginActivity.this);
                        alertBuilder.setMessage("Could not connect to server")
                                .setNegativeButton("Ok",null)
                                .create()
                                .show();
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (true == success){
                            jumpToMainActivity();
                        }else {
                            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(LoginActivity.this);
                            alertBuilder.setMessage("Login Failed")
                                        .setNegativeButton("Retry",null)
                                        .create()
                                        .show();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            };

            LoginRequest loginRequest = new LoginRequest(userName,password,LOGIN_REQUEST_URL,listener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginRequest);
            }
        });

    }

    private void jumpToMainActivity(){
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        LoginActivity.this.startActivity(intent);
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private boolean isConnectedToServer(String url, int timeout) {
        try{
            URL myUrl = new URL(url);
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(timeout);
            connection.connect();
            return true;
        } catch (Exception e) {
            // Handle your exceptions
            return false;
        }
    }

}
