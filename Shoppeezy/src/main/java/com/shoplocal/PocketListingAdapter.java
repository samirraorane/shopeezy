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

import com.shoplocal.util.PocketEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.List;

public class PocketListingAdapter extends BaseAdapter {
    private Context mContext;
    private List<PocketEntry> mlistings;

    public PocketListingAdapter(Context c, List<PocketEntry> list) {
        mContext = c;
        mlistings = list;
    }

    @Override
    public int getCount() {
        return mlistings.size();
    }

    @Override
    public PocketEntry getItem(int position) {
        return mlistings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get the selected entry
        PocketEntry listing = mlistings.get(position);

        String name = listing.getTitle();
        String price = listing.getPrice();
        String imageUrl = listing.getImage();

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
        if(imageUrl != null){
            new DownloadImageTask((ImageView) v.findViewById(R.id.listingImage))
                    .execute(imageUrl);
        }

        // set data to display
        listingName.setText(name);
        listingPrice.setText(price);

        // return view
        return v;
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