package com.chandu.revigas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class LoginActivity extends BaseAppCompatActivity {

    private static final String TAG ="LoginActivity" ;
    //    String loginURL = "http://192.168.1.36/~Chandu/Login/login.php";
    String loginURL = Constants.HOST+"api/login.php";
    RequestQueue requestQueue;
    Button login;
    String mUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        requestQueue = Volley.newRequestQueue(this);

    }


    public void showHome(View v) {

        EditText userNameField = (EditText) findViewById(R.id.username);
        mUserEmail = userNameField.getText().toString();

        EditText passwordField = (EditText) findViewById(R.id.password);
        String sPassword = passwordField.getText().toString();

        String params = String.format("username=%s&password=%s", mUserEmail, sPassword);

        if ((mUserEmail.matches("")) || (sPassword.matches(""))) {

            Toast.makeText(this, "Please Enter the details", Toast.LENGTH_SHORT).show();

        } else {
            /*showProgress("Logging in,please wait...");
            checkLoginWith(params);*/

            Intent i = new Intent(LoginActivity.this , Home.class);
            i.putExtra("user_id",mUserEmail);
            startActivity(i);

        }
    }

    public void checkLoginWith (String params){
        Log.d(TAG,"login  "+loginURL);
        Log.d(TAG,"params "+params);
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, loginURL, params , new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                    dismissProgress();
                Log.d(TAG,"resp "+response.toString());

                Intent i = new Intent(LoginActivity.this , Home.class);

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
                dismissProgress();
                Toast.makeText(LoginActivity.this, "Error.Please try again.", Toast.LENGTH_SHORT).show();
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


