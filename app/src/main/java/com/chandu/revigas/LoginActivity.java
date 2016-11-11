package com.chandu.revigas;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
    String mPassword;

    EditText mUserNameField;
    EditText mPasswordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }


    public void showHome(View v) {

        mUserNameField = (EditText) findViewById(R.id.username);
         mPasswordField = (EditText) findViewById(R.id.password);
       attemptLogin();
    }

    private void attemptLogin() {
        /*if (mAuthTask != null) {
            return;
        }*/

        // Reset errors.
        mUserNameField.setError(null);
        mPasswordField.setError(null);

        // Store values at the time of the login attempt.
        mUserEmail = mUserNameField.getText().toString();
        mPassword = mPasswordField.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        /*if (!TextUtils.isEmpty(password) ) {
            mPasswordField.setError("Invalid password");
            focusView = mPasswordField;
            cancel = true;
        }
*/
        // Check for a valid mobile number.
        if (TextUtils.isEmpty(mUserEmail)) {
            mUserNameField.setError(getString(R.string.error_field_required));
            focusView = mUserNameField;
            cancel = true;
        } /*else if (!isValidMobile(poneNo)) {
            mPhoneNoView.setError(getString(R.string.error_invalid_mobile));
            focusView = mPhoneNoView;
            cancel = true;
        }*/
        if (TextUtils.isEmpty(mPassword)) {
            mPasswordField.setError(getString(R.string.error_field_required));
            focusView = mPasswordField;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            NetworkManager.getInstance().postRequest(Constants.API_LOGIN,getParams(),this);

            showProgress("Logging in,please wait...");
            checkLoginWith();
        }
    }

    public void checkLoginWith (){
        requestQueue = Volley.newRequestQueue(this);
        String params = String.format("username=%s&password=%s", mUserEmail, mPassword);
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
                    user_id = response.getString("email");
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


