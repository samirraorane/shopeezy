package com.shoplocal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SavedStoresAdapter extends BaseAdapter {
    private Context mContext;
    private String[] mOptions;

    public SavedStoresAdapter(Context c, String[] list) {
        mContext = c;
        mOptions = list;
    }

    @Override
    public int getCount() {
        return mOptions.length;
    }

    @Override
    public String getItem(int position) {
        return mOptions[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get the selected entry
        String option = mOptions[position];

        // reference to convertView
        View v = convertView;

        // inflate new layout if null
        if(v == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            v = inflater.inflate(R.layout.store_listview, null);
        }

        TextView storeName = (TextView)v.findViewById(R.id.storeName);
        TextView storeDistance = (TextView)v.findViewById(R.id.storeDistance);
        TextView storeAddress = (TextView)v.findViewById(R.id.storeAddress);

        if(position == 0){
            storeName.setText("Staples");
            storeDistance.setText("0.11m");
            storeAddress.setText("111 North Wabash Ave.");
        } else if(position == 1) {
            storeName.setText("Macy's");
            storeDistance.setText("0.16m");
            storeAddress.setText("111 North State Street");
        }

        return v;
    }


}