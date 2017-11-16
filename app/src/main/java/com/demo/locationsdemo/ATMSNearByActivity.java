package com.demo.locationsdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.demo.locationsdemo.Adapters.ListATMAdapter;
import com.demo.locationsdemo.Model.ATM;
import com.demo.locationsdemo.Model.User;
import com.demo.locationsdemo.Presenters.ATMPresenterImpl;
import com.demo.locationsdemo.Views.ATMView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

                //String itemValue = (String) listView.getItemAtPosition(position);
                //todo get item with that id from db and pass to method for generatin hash code
                generateHashCode((ATM)listView.getItemAtPosition(position));
            }
        });
    }

    private void initViews() {

        listView = (ListView) findViewById(R.id.atmsListView);

    }

    @Override
    public void displayListOfATMs(List<ATM> atmList) {
        listATMAdapter = new ListATMAdapter(this,0, atmList);
        listView.setAdapter(listATMAdapter);
        listATMAdapter.notifyDataSetChanged();
    }


    private double generateHashCode(ATM atm) {

        Double lat = Double.parseDouble(atm.getLatitude());//Long.parseLong(atm.getLatitude());
        Double lon = Double.parseDouble(atm.getLongitude());

        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        SimpleDateFormat sdf1 = new SimpleDateFormat("mm");
        Long currentHour = Long.parseLong(sdf.format(new Date()));
        Long currentMinute = Long.parseLong(sdf1.format(new Date()));

        //get user data too
        User user = atmPresenter.getUser();
        Long cardNumber = Long.parseLong(user.getCardNumber());
        Long lastFour = Long.parseLong(user.getCardNumber().substring(user.getCardNumber().length()-4,user.getCardNumber().length()));
        Long pin = Long.valueOf(user.getPin());

        double codeMul = currentHour*currentMinute*pin*lat*lon*cardNumber;
        Long constant = 27182818284l;
        double code = codeMul / constant;
        double result = lastFour*10000+code;
        Toast.makeText(this,"Generated code "+String.valueOf(result), Toast.LENGTH_LONG).show();
        return lastFour*10000+code;
    }
}
