package com.demo.locationsdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.locationsdemo.Database.DatabaseAccess;
import com.demo.locationsdemo.Helpers.ApplicationClass;

import java.io.IOException;
import java.util.Random;

public class AddATMActivity extends AppCompatActivity {

    private EditText lonEditTxt, latEditTxt, nameEditTxt;
    private String longitude, latitude, name; /*="ATM ERSTE " + new Random().nextInt(99);*/
    private Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_add_atm);

        lonEditTxt = (EditText) findViewById(R.id.lonEditTxt);
        latEditTxt = (EditText) findViewById(R.id.latEditTxt);
        nameEditTxt = (EditText) findViewById(R.id.nameEditTxt);

        addBtn = (Button) findViewById(R.id.addBtn);

        addBtn.setOnClickListener(view->{

            longitude = String.valueOf(lonEditTxt.getText());
            latitude = String.valueOf(latEditTxt.getText());
            name = String.valueOf(nameEditTxt.getText());

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    //AppDatabase.getAppDatabase(ApplicationClass.getContext()).userDao().insertUsersData(user); //ROOM
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(ApplicationClass.getContext());

                    try {
                        databaseAccess.open();
                    } catch (IOException e) {
                        e.printStackTrace();
                        //TODO

                    }
                    databaseAccess.insertATM(longitude, latitude, name);
                    databaseAccess.close();

                    Toast.makeText(AddATMActivity.this,"New ATM added", Toast.LENGTH_SHORT).show();

                    AddATMActivity.this.finish();
                }
            });
        });


    }
}
