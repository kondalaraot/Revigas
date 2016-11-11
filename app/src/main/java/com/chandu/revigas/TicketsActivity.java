package com.chandu.revigas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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


public class TicketsActivity extends BaseAppCompatActivity {


//    String ticketsURL = "http://192.168.1.36/~Chandu/Login/tickets.php";

    String ticketsURL = Constants.HOST+"tickets.php";
    RequestQueue requestQueue;

    ArrayList<Ticket> mTickets = new ArrayList<Ticket>();
    RecyclerView mRecyclerView;
    TextView mTextViewEmpty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);
        mRecyclerView = (RecyclerView) findViewById(R.id.lv_tickets);
        mTextViewEmpty = (TextView) findViewById(R.id.tv_empty);


        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

               /* Ticket ticket = mTickets.get(position);
                Intent intent = new Intent(TicketsActivity.this,InformationActivity.class);
                intent.putExtra("KEY_TICKET_OBJ",ticket);
                startActivity(intent);*/
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        showProgress("Please wait..");
        getTicketsList();

    }


    public void getTicketsList(){
        requestQueue = Volley.newRequestQueue(this);

        Intent inte  = getIntent();
        final String user_id = inte.getStringExtra("user_id");
        String params = String.format("user_id=%s",user_id);



        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, ticketsURL, params , new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                dismissProgress();
                JSONArray ticketsArray = (JSONArray)response;

                if (ticketsArray != null) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Ticket>>(){}.getType();
                    mTickets = gson.fromJson(ticketsArray.toString(), listType);

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

       /* Log.d("entries", String.valueOf(listdata));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1 , listdata);*/
        if(mTickets.size() >0){
            TicketsAdapter adapter = new TicketsAdapter(mTickets);
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
