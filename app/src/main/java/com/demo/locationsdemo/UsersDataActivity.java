package com.demo.locationsdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.locationsdemo.Presenters.UsersDataPresenter;
import com.demo.locationsdemo.Views.UsersDataView;

public class UsersDataActivity extends AppCompatActivity implements UsersDataView {

    private final String TAG = this.getClass().getName();

    Button confirmDataBtn;
    EditText firstNameEditTxt, lastNameEditTxt, cardNumEditTxt, pinEditTxt;
    String firstName, lastName, cardNumber;
    Integer pinNumber;

    private UsersDataPresenter usersDataPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_data);

        initViews();

        confirmDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firstNameEditTxt.getText().toString().trim().equals("")
                        || lastNameEditTxt.getText().toString().trim().equals("")
                        || cardNumEditTxt.getText().toString().trim().equals(""))

                    Toast.makeText(UsersDataActivity.this,"Please provide all fields with data", Toast.LENGTH_SHORT).show();

                else{

                    firstName = String.valueOf(firstNameEditTxt.getText());
                    lastName = String.valueOf(lastNameEditTxt.getText());
                    cardNumber = String.valueOf(cardNumEditTxt.getText());

                    pinNumber = Integer.parseInt(String.valueOf(pinEditTxt.getText()));

                    usersDataPresenter.saveUsersData(firstName, lastName, cardNumber, pinNumber);
                }
            }
        });
    }

    private void initViews() {

        firstNameEditTxt = (EditText) findViewById(R.id.firstNameEditTxt);
        lastNameEditTxt = (EditText) findViewById(R.id.lastNameEditTxt);
        cardNumEditTxt = (EditText) findViewById(R.id.cardNumEditTxt);
        pinEditTxt = (EditText) findViewById(R.id.pinEditTxt);
        confirmDataBtn = (Button) findViewById(R.id.nextBtn);
    }

    /**
     * If users data is successfully saved then next screen is displayed
     */
    @Override
    public void openNextScreen() {
        Intent generateCodeActivity = new Intent(this,GenerateCodeActivity.class);
        startActivity(generateCodeActivity);
        Log.i(TAG,"Fingerprint Authentication succeeded.");
    }
}
