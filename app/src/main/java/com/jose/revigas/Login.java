package com.jose.revigas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    String loginURL = Constants.HOST+"api/login.php";
    RequestQueue requestQueue;
    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        requestQueue = Volley.newRequestQueue(this);

    }


    public void showHome(View v) {

        EditText userNameField = (EditText) findViewById(R.id.username);
        String sUsername = userNameField.getText().toString();

        EditText passwordField = (EditText) findViewById(R.id.password);
        String sPassword = passwordField.getText().toString();

        String params = String.format("username=%s&password=%s", sUsername, sPassword);

        if ((sUsername.matches("")) || (sPassword.matches(""))) {

            Toast.makeText(this, "Please Enter the details", Toast.LENGTH_SHORT).show();

        } else {

            checkLoginWith(params);


        }
    }

    public void checkLoginWith (String params){

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, loginURL, params , new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                    Intent i = new Intent(Login.this , Home.class);

                String user_id  = null;
                try {
                    user_id = response.getString("IDUSER");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                i.putExtra("user_id",user_id);
                startActivity(i);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Toast.makeText(Login.this, "Error.Please try again.", Toast.LENGTH_SHORT).show();

                volleyError.printStackTrace();
            }
        }

        ) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };

        requestQueue.add(request);
    }
}


