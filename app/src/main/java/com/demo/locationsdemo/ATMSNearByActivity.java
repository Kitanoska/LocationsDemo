package com.demo.locationsdemo;

import android.Manifest;
import android.arch.lifecycle.LifecycleActivity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.demo.locationsdemo.Adapters.ListATMAdapter;
import com.demo.locationsdemo.Helpers.ApplicationClass;
import com.demo.locationsdemo.Helpers.BoundLocationManager;
import com.demo.locationsdemo.Model.ATM;
import com.demo.locationsdemo.Model.User;
import com.demo.locationsdemo.Presenters.ATMPresenterImpl;
import com.demo.locationsdemo.Views.ATMView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ATMSNearByActivity extends AppCompatActivity /*extends LifecycleActivity */implements ATMView {

    List<ATM> listATMs = new ArrayList<>();
    ListView listView;
    ListATMAdapter listATMAdapter;
    double code;

    //private static final int REQUEST_LOCATION_PERMISSION_CODE = 1;

    //private LocationListener mGpsListener = new ATMSNearByActivity.MyLocationListener();

    private ATMPresenterImpl atmPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atm_near);

        /*//////PERMISIONS LOCATION//////
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION_PERMISSION_CODE);

        } else {
            bindLocationListener();
        }*/

        initViews();
        //load ams from db
        atmPresenter = new ATMPresenterImpl(this);
        atmPresenter.getAllATM();

        Toast.makeText(this, String.valueOf(ApplicationClass.getMyLatitude()), Toast.LENGTH_LONG).show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                //todo get item with that id from db and pass to method for generatin hash code
                int code = generateHashCode((ATM)listView.getItemAtPosition(position));
                ApplicationClass.setGeneratedCode(String.format("%d",code));
                finish();
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

    private int generateHashCode(ATM atm) {

        float lat = Float.parseFloat(atm.getLatitude());//Long.parseLong(atm.getLatitude());
        float lon = Float.parseFloat(atm.getLongitude());

        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
        Long currentTime = Long.parseLong(sdf.format(new Date()));
        String currentTimeStr = String.valueOf(currentTime);

        if (currentTimeStr.startsWith("00")) {
            currentTimeStr = currentTimeStr.replaceFirst("00", "24");
        }

        int time = Integer.parseInt(currentTimeStr);
        //get user data too
        User user = atmPresenter.getUser();
        Long cardNumber = Long.parseLong(user.getCardNumber());
        int pin = user.getPin();

        Long constant = 27182818284l;
        float calculation = time*pin*lat*lon*cardNumber/ constant;

        Long resLongPart1 = (long) calculation;
        String part1Str = resLongPart1.toString();
        part1Str = part1Str.substring(part1Str.length() - 4, part1Str.length());

        int part1 = Integer.parseInt(part1Str);
        //we get last four digits of card number
        int part2 = Integer.parseInt(user.getCardNumber().substring(user.getCardNumber().length()-4,user.getCardNumber().length()));

        int hashCode = part1*10000+part2;
        return hashCode;
    }

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            bindLocationListener();
        } else {
            Toast.makeText(this, "This sample requires Location access", Toast.LENGTH_LONG).show();
        }
    }

    private void bindLocationListener() {
        BoundLocationManager.bindLocationListenerIn(this, mGpsListener, getApplicationContext());
    }

    //TODO move separately
    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            //TextView textView = (TextView) findViewById(R.id.location);
            //textView.setText(location.getLatitude() + ", " + location.getLongitude());
            ApplicationClass.setMyLatitude(location.getLatitude());
            ApplicationClass.setMyLongitude(location.getLongitude());
            atmPresenter.getAllATM();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(ATMSNearByActivity.this,
                    "Provider enabled: " + provider, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    } */

}
