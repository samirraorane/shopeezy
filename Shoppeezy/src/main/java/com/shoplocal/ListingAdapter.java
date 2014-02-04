package com.shoplocal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class ListingAdapter extends BaseAdapter {
    private Context mContext;
    private JSONArray mlistings;

    public ListingAdapter(Context c, JSONArray list) {
        mContext = c;
        mlistings = list;
    }

    @Override
    public int getCount() {
        return mlistings.length();
    }

    @Override
    public JSONObject getItem(int position) {
        try {
            return mlistings.getJSONObject(position);
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
        JSONObject listing = null;
        try {
            listing = mlistings.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String name = "";
        String price = "";
        String imageUrl = "";

        try {
            name = listing.getString("Title");
            price = listing.getString("FinalPrice");
            imageUrl = listing.getString("ImageLocation");
            imageUrl = imageUrl.replace("200", "100");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // reference to convertView
        View v = convertView;

        // inflate new layout if null
        if(v == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            v = inflater.inflate(R.layout.listing_listview, null);
        }

        // load controls from layout resources
        TextView listingName = (TextView)v.findViewById(R.id.listingName);
        TextView listingPrice = (TextView)v.findViewById(R.id.listingPrice);
        new DownloadImageTask((ImageView) v.findViewById(R.id.listingImage))
                .execute(imageUrl);

        // set data to display
        listingName.setText(name);
        listingPrice.setText("$" + price);

        // return view
        return v;
    }

    public void updateReceiptsList(JSONArray newlist) {
        mlistings = newlist;
        this.notifyDataSetChanged();
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}