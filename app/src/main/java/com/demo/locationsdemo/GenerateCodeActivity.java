package com.demo.locationsdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GenerateCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_code);

        Button generateCodeBtn = (Button) this.findViewById(R.id.generateCodeBtn);
        final TextView generatedCodeTextView = (TextView) this.findViewById(R.id.generatedCodeTextView);

        generateCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //generatedCodeTextView.setText("390951");
                Intent atmsActivity = new Intent(getBaseContext(), ATMSNearByActivity.class);
                startActivity(atmsActivity);
                //TODO
                //odabrati i prikazati one u krugu od 20m
            }
        });
    }
}
