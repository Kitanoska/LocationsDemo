package com.demo.locationsdemo.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

    private Activity activity;
    private List<ATM> listATMs;
    private static LayoutInflater inflater = null;

    public ListATMAdapter(@NonNull Context context, int resource, List<ATM> list) {
        super(context, resource, list);

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
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.list_item_atm, null);
                holder = new ViewHolder();

                holder.nameATM = (TextView) vi.findViewById(R.id.displayNameTxtView);
                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }

            holder.nameATM.setText(listATMs.get(position).getName());

        } catch (Exception e) {


        }
        return vi;
    }
}
