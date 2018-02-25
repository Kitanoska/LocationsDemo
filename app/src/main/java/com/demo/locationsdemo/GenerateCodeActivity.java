package com.demo.locationsdemo;

import android.Manifest;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.locationsdemo.Helpers.ApplicationClass;
import com.demo.locationsdemo.Helpers.BoundLocationManager;

public class GenerateCodeActivity extends LifecycleActivity /*extends AppCompatActivity*/ {

    private TextView generatedCodeTextView, infoTExtView;
    private static final int REQUEST_LOCATION_PERMISSION_CODE = 1;
    private LocationListener mGpsListener = new GenerateCodeActivity.MyLocationListener();
    private UsersLocationViewModel usersLocationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_code);

        //////PERMISIONS LOCATION//////
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION_PERMISSION_CODE);

        } else {
            if (((LocationManager) getSystemService(LOCATION_SERVICE)).isProviderEnabled(LocationManager.GPS_PROVIDER))
                bindLocationListener();
            else
            {
                displayLocationSettingsDialog();
            }
        }

        Button generateCodeBtn = (Button) this.findViewById(R.id.generateCodeBtn);
        generatedCodeTextView = (TextView) this.findViewById(R.id.generatedCodeTextView);
        infoTExtView = (TextView) this.findViewById(R.id.infoTextView);
        usersLocationViewModel = ViewModelProviders.of(this).get(UsersLocationViewModel.class);

        generateCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (((LocationManager) getSystemService(LOCATION_SERVICE)).isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    bindLocationListener();
                    Intent atmsActivity = new Intent(getBaseContext(), ATMSNearByActivity.class);
                    startActivity(atmsActivity);
                }
                else
                {
                    displayLocationSettingsDialog();
                }


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        generatedCodeTextView.setText(ApplicationClass.getGeneratedHashCode());

        //after enabling it in settings screen
        if (((LocationManager) getSystemService(LOCATION_SERVICE)).isProviderEnabled(LocationManager.GPS_PROVIDER))
            bindLocationListener();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            bindLocationListener();
        } else {
            Toast.makeText(this, "This requires Location access", Toast.LENGTH_LONG).show();
        }
    }

    private void bindLocationListener() {
        BoundLocationManager.bindLocationListenerIn(this, mGpsListener, getApplicationContext());
    }

    private void displayLocationSettingsDialog(){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(R.string.titleLocatioDialogText)
                .setMessage(R.string.locationDialogText)
                //.setMessage(R.string.locationDialogText)
                .setPositiveButton(getString(R.string.agree), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton(getString(R.string.disagree), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    //TODO move separately
    private class MyLocationListener implements LocationListener {

        private String TAG = "Location Listener";

        @Override
        public void onLocationChanged(Location location) {
            //TextView textView = (TextView) findViewById(R.id.location);
            //textView.setText(location.getLatitude() + ", " + location.getLongitude());
            Log.v(TAG,"LocationChanged");
            //ApplicationClass.setMyLatitude(location.getLatitude());
            //ApplicationClass.setMyLongitude(location.getLongitude());

            //update live data
            //usersLocationViewModel.getCurrentUsersLocation().setValue(location);
            //atmPresenter.getAllATM();
            ApplicationClass.currentLocation.setCurrentLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(GenerateCodeActivity.this,
                    "Provider enabled: " + provider, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    }
}
