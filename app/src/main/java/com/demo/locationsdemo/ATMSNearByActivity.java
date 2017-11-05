package com.demo.locationsdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.demo.locationsdemo.Adapters.ListATMAdapter;
import com.demo.locationsdemo.Model.ATM;
import java.util.ArrayList;

public class ATMSNearByActivity extends AppCompatActivity {

    ArrayList<ATM> listATMs = new ArrayList<>();
    ListView listView;
    ListATMAdapter listATMAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atm_near);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        initViews();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String itemValue = (String) listView.getItemAtPosition(position);
                //todo get item with that id from db and pass to method for generatin hash code
            }
        });
    }

    private void initViews() {
        ListATMAdapter adapter = new ListATMAdapter(this,0, listATMs);
        listView = (ListView) findViewById(R.id.atmsListView);
        listView.setAdapter(adapter);
    }
}
