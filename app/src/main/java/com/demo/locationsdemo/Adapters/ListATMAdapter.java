package com.demo.locationsdemo.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.demo.locationsdemo.Model.ATM;
import com.demo.locationsdemo.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natalija on 11/5/2017.
 */

public class ListATMAdapter extends ArrayAdapter<ATM> {

    public final String TAG = this.getClass().getName();

    private Activity activity;
    private List<ATM> listATMs;
    private static LayoutInflater inflater = null;

    public ListATMAdapter(@NonNull Activity activity, int resource, List<ATM> list) {
        super(activity, resource, list);

        try {
            this.activity = activity;
            this.listATMs = list;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    @Override
    public int getCount() {
        return listATMs.size();
    }

    @Nullable
    @Override
    public ATM getItem(int position) {
        return listATMs.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView nameATM;
        public TextView distance;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.list_item_atm, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.nameATM = (TextView) vi.findViewById(R.id.displayNameTxtView);
            holder.distance = (TextView) vi.findViewById(R.id.distanceTxtView);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        holder.nameATM.setText(listATMs.get(position).getName()); //= (TextView) vi.findViewById(R.id.displayNameTxtView);
        holder.distance.setText(String.valueOf(listATMs.get(position).getDistance())+" meters");

        return vi;
    }
}
