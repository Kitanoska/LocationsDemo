package com.demo.locationsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UsersDataActivity extends AppCompatActivity {

    Button confirmDataBtn;
    EditText firstNameEditTxt, lastNameEditTxt, cardNumEditTxt, pinEditTxt;
    String firstName, lastName, cardNumber, pinNumber;

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
                    pinNumber = String.valueOf(pinEditTxt.getText());
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
}
