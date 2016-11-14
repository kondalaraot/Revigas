package com.chandu.revigas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Home extends AppCompatActivity {


//    String[] listArray = {"Tickets","Information"};
    String[] listArray = {"Historial del cliente","Obras abiertas"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent inte  = getIntent();
        final String user_id = inte.getStringExtra("user_id");

        setContentView(R.layout.activity_home);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1 , listArray);

        ListView listView = (ListView) findViewById(R.id.optionsList);
        listView.setAdapter(adapter);


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

                }



            }
        });


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
}
