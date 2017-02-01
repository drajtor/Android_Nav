package com.example.darek.nav_10;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {

    EditText EditText_UserName;
    EditText EditText_Password;
    Button Button_Login;

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

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (true == success){
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                LoginActivity.this.startActivity(intent);
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

                LoginRequest loginRequest = new LoginRequest(userName,password,listener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}
