package com.demo.locationsdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.demo.locationsdemo.Helpers.ApplicationClass;

public class GenerateCodeActivity extends AppCompatActivity {

    private TextView generatedCodeTextView, infoTExtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_code);

        Button generateCodeBtn = (Button) this.findViewById(R.id.generateCodeBtn);
        generatedCodeTextView = (TextView) this.findViewById(R.id.generatedCodeTextView);
        infoTExtView = (TextView) this.findViewById(R.id.infoTextView);

        generateCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent atmsActivity = new Intent(getBaseContext(), ATMSNearByActivity.class);
                startActivity(atmsActivity);
                //TODO
                //odabrati i prikazati one u krugu od 20m
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        generatedCodeTextView.setText(ApplicationClass.getGeneratedHashCode());
        //TODO update info message
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(1);
    }
}
