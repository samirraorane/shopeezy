package com.shoplocal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StoreInfoAdapter extends BaseAdapter {
    private Context mContext;
    private JSONArray mStores;

    public StoreInfoAdapter(Context c, JSONArray list) {
        mContext = c;
        mStores = list;
    }

    @Override
    public int getCount() {
        return mStores.length();
    }

    @Override
    public JSONObject getItem(int position) {
        try {
            return mStores.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get the selected entry
        JSONObject store = null;
        try {
            store = mStores.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject pRetailer;
        String name = "";
        String distance = "";

        try {
            pRetailer = store.getJSONObject("PRetailer");
            name = pRetailer.getString("Name");
            distance = store.getString("Distance");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // reference to convertView
        View v = convertView;

        // inflate new layout if null
        if(v == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            v = inflater.inflate(R.layout.store_listview, null);
        }

        // load controls from layout resources
        TextView storeName = (TextView)v.findViewById(R.id.storeName);
        TextView storeDistance = (TextView)v.findViewById(R.id.storeDistance);

        // set data to display
        storeName.setText(name);
        storeDistance.setText(distance + "m");

        // return view
        return v;
    }
}