package com.jose.revigas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Home extends BaseAppCompatActivity {


//    String[] listArray = {"Tickets","Information"};
    String[] listArray = {"Historial del cliente","Obras abiertas","Energía Solar",""};
    String ticketsURL = Constants.HOST+"tickets.php";
    RequestQueue requestQueue;

    ArrayList<Ticket> mTickets = new ArrayList<Ticket>();
    ListView listView;

    DateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd");

    SimpleDateFormat toDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent inte  = getIntent();
        final String user_id = inte.getStringExtra("user_id");

        setContentView(R.layout.activity_home);

         listView = (ListView) findViewById(R.id.optionsList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.i(Integer.toString(position),"chandu");
                if (position == 0){

                    Intent i = new Intent(Home.this,TicketsActivity.class);
                    i.putExtra("user_id",user_id);
                    startActivity(i);

                }else if (position == 1){

                    Intent i = new Intent(Home.this,InformationActivity.class);
                    i.putExtra("user_id",user_id);
                    startActivity(i);

                }else if (position == 2){

                    Intent i = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://185.68.110.84:3030"));
                    startActivity(i);

                }



            }
        });

        getTicketsList();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_logout:
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
//                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-DD");
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    List<Date> datesList = new ArrayList<Date>();

                    for (Ticket ticket : mTickets) {
                        setTitle(ticket.getIDUSER());
                        try {
                            Date date = formatter.parse(ticket.getFecha());
                            datesList.add(date);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
//                        ticket.getFecha();
                    }
                    Collections.sort(datesList);
                   Date dateSorted =  datesList.get(datesList.size()-1);
                    String fechaDateLatest = formatter.format(dateSorted);
                    Date dateFromat = null;
                    try {
                        dateFromat = fromFormat.parse(fechaDateLatest);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    listArray[3] = "Su próxima revisión será - "+toDateFormat.format(dateFromat);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Home.this,
                            android.R.layout.simple_list_item_1 , listArray);

                    listView.setAdapter(adapter);
                }

//                showTable();

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
}
