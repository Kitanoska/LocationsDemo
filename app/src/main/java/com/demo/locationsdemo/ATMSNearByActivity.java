package com.demo.locationsdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.demo.locationsdemo.Adapters.ListATMAdapter;
import com.demo.locationsdemo.Entity.ATMEntity;
import com.demo.locationsdemo.Model.ATM;
import com.demo.locationsdemo.Presenters.ATMPresenter;
import com.demo.locationsdemo.Presenters.ATMPresenterImpl;
import com.demo.locationsdemo.Presenters.UsersDataPresenterImpl;
import com.demo.locationsdemo.Views.ATMView;

import java.util.ArrayList;
import java.util.List;

public class ATMSNearByActivity extends AppCompatActivity implements ATMView {

    List<ATM> listATMs = new ArrayList<>();
    ListView listView;
    ListATMAdapter listATMAdapter;

    private ATMPresenterImpl atmPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atm_near);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        initViews();
        //load ams from db
        atmPresenter = new ATMPresenterImpl(this);
        atmPresenter.getAllATM();

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

        listView = (ListView) findViewById(R.id.atmsListView);

    }

    @Override
    public void displayListOfATMs(List<ATM> atmList) {
        listATMAdapter = new ListATMAdapter(this,0, listATMs);
        listView.setAdapter(listATMAdapter);
        listATMAdapter.notifyDataSetChanged();
    }
}
