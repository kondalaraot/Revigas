package com.jose.revigas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;


public class InformationActivity extends BaseAppCompatActivity {

//    String informationURL = "http://192.168.1.36/~Chandu/Login/information.php";
    public static final String TAG ="InformationActivity" ;

    String informationURL = Constants.HOST+"information.php";

    RequestQueue requestQueue;
    ArrayList<Information> mInformations = new ArrayList<Information>();
    RecyclerView mRecyclerView;
    TextView mTextViewEmpty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        mRecyclerView = (RecyclerView) findViewById(R.id.lv_informations);
        mTextViewEmpty = (TextView) findViewById(R.id.tv_empty);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

               /* Ticket ticket = mTickets.get(position);
                Intent intent = new Intent(InformationActivity.this,InformationActivity.class);
                intent.putExtra("KEY_TICKET_OBJ",ticket);
                startActivity(intent);*/
                Information information = mInformations.get(position);
                String jsonEmailBody = new Gson().toJson(information);
                /* Create the Intent */
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
/* Fill it with Data */
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{Constants.TO_EMAIL});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, Constants.EMAIL_SUBJECT);
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, jsonEmailBody);

/* Send it off to the Activity-Chooser */
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        showProgress("Please wait..");
        getInformationList();

    }

    public void getInformationList(){
        requestQueue = Volley.newRequestQueue(this);

        Intent inte  = getIntent();
        final String user_id = inte.getStringExtra("user_id");
        String params = String.format("user_id=%s",user_id);

        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, informationURL, params , new Response.Listener<JSONArray>() {


            @Override
            public void onResponse(JSONArray response) {

                dismissProgress();
                JSONArray ticketsArray = (JSONArray)response;
                if (ticketsArray != null) {
                    Log.d(TAG,"ticketsArrayv"+ticketsArray.toString());

                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Information>>(){}.getType();
                    mInformations = gson.fromJson(ticketsArray.toString(), listType);

                }

                showTable();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dismissProgress();
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

    public void showTable(){

        if(mInformations.size() >0){
            InformationAdapter adapter = new InformationAdapter(this,mInformations);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(llm);
            mRecyclerView.setAdapter(adapter);
        }else{

            mTextViewEmpty.setVisibility(VISIBLE);
        }

    }




}
