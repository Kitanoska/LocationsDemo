package com.demo.locationsdemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.demo.locationsdemo.Adapters.ListATMAdapter;
import com.demo.locationsdemo.Helpers.ApplicationClass;
import com.demo.locationsdemo.LocationChangeVariable;
import com.demo.locationsdemo.Model.ATM;
import com.demo.locationsdemo.Model.User;
import com.demo.locationsdemo.Presenters.ATMPresenterImpl;
import com.demo.locationsdemo.Presenters.UsersDataPresenterImpl;
import com.demo.locationsdemo.R;
import com.demo.locationsdemo.Views.ATMView;
import com.demo.locationsdemo.Views.UsersDataView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ATMSNearByActivity extends AppCompatActivity implements ATMView, UsersDataView {

    ListView listView;
    ListATMAdapter listATMAdapter;
    double code;
    //private UsersLocationViewModel usersLocationViewModel;
    private ATMPresenterImpl atmPresenter;
    private UsersDataPresenterImpl usersDataPresenterImpl;

    //JUST FOR DEMO REMOVE AFTER
    Button addNewAtm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atm_near);

        initViews();
        //setUpObservers();

        //load ams from db
        atmPresenter = new ATMPresenterImpl(this);
        usersDataPresenterImpl = new UsersDataPresenterImpl(this);
        atmPresenter.getAllATMNear(ApplicationClass.currentLocation.getCurrentLocation());

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

        //Just for demo remove after
        addNewAtm.setOnClickListener(view ->{
            Intent i = new Intent(this, AddATMActivity.class);
            startActivity(i);
        });

    }

    private void initViews() {

        //just for demo remove after this button
        addNewAtm = (Button) findViewById(R.id.addATMBtn);
        listView = (ListView) findViewById(R.id.atmsListView);
        //usersLocationViewModel = ViewModelProviders.of(this).get(UsersLocationViewModel.class);
        ApplicationClass.currentLocation.setListener(new LocationChangeVariable.ChangeListener() {
            @Override
            public void onChange() {
               atmPresenter.getAllATMNear(ApplicationClass.currentLocation.getCurrentLocation());
            }
        });
    }

   /* private void setUpObservers(){

        // Create the observer which updates the UI.
        final Observer<Location> nameObserver = new Observer<Location>() {
            @Override
            public void onChanged(@Nullable final Location currentLoc) {
                // Update the UI, in this case check again in db for list of atms and display them.
                atmPresenter.getAllATMNear(currentLoc);
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        usersLocationViewModel.getCurrentUsersLocation().observe(this, nameObserver);

    }*/

    @Override
    public void displayListOfATMs(List<ATM> atmList) {
        listATMAdapter = new ListATMAdapter(this,0, atmList);
        listView.setAdapter(listATMAdapter);
        listATMAdapter.notifyDataSetChanged();
    }

    @Override
    public void openNextScreen() {
        //Nothing should be here
    }

    @Override
    public void displayError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        atmPresenter.getAllATMNear(ApplicationClass.currentLocation.getCurrentLocation());
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
        User user = usersDataPresenterImpl.getUser();
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

}
