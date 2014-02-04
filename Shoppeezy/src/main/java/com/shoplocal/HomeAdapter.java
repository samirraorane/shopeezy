package com.shoplocal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeAdapter extends BaseAdapter {
    private Context mContext;
    private String[] mOptions;

    public HomeAdapter(Context c, String[] list) {
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
            v = inflater.inflate(R.layout.home_listview, null);
        }

        // load controls from layout resources
        TextView optionName = (TextView)v.findViewById(R.id.optionName);
        ImageView optionImage = (ImageView) v.findViewById(R.id.optionImage);

        // set data to display
        optionName.setText(option);
        if(position == 0){
            optionImage.setImageResource(R.drawable.ic_pocket);
        } else if(position == 1) {
            optionImage.setImageResource(R.drawable.ic_savedstores);
        } else if(position == 2){
            optionImage.setImageResource(R.drawable.ic_stores);
        } else if(position == 3){
            optionImage.setImageResource(R.drawable.ic_products);
        } else if(position == 4){
            optionImage.setImageResource(R.drawable.ic_trending);
        }

        // return view
        return v;
    }


}