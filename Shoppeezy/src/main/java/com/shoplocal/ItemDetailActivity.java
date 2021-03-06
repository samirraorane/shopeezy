package com.shoplocal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shoplocal.util.Api;
import com.shoplocal.util.PocketEntry;
import com.shoplocal.util.SqlHelper;
import com.shoplocal.util.SqlHelperTrending;
import com.shoplocal.util.TrendingEntry;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.app.PendingIntent.getActivity;

public class ItemDetailActivity extends Activity {

    TextView title, price, description, deal;
    Button pocketBtn, trendingBtn;
    String storeId, listingId, imageUrl;
    Boolean readyState = false;
    ProgressDialog progress;
    SqlHelper db;
    SqlHelperTrending dbTrending;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.show();

        setContentView(R.layout.activity_item_detail);

        title = (TextView) findViewById(R.id.itemTitle);
        price = (TextView) findViewById(R.id.itemPrice);
        description = (TextView) findViewById(R.id.itemDescription);
        deal = (TextView) findViewById(R.id.itemDeal);
        pocketBtn = (Button)findViewById(R.id.pocketBtn);
        trendingBtn = (Button)findViewById(R.id.trendBtn);

        db = new SqlHelper(this);
        dbTrending = new SqlHelperTrending(this);


        Intent intent = getIntent();
        storeId = intent.getStringExtra("STORE_ID");
        listingId = intent.getStringExtra("LISTING_ID");

        String url = "http://api2.shoplocal.com/retail/6883099d72e1ca52/2013.1/json/Listing?storeid="+ storeId +"&listingid=" + listingId + "&imagesize=350";
        new AsyncApi().execute(url);
    }


    public void saveToPocket(View view){

        String _title = title.getText().toString();
        String _price = price.getText().toString();
        String _description = description.getText().toString();

        if(!pocketBtn.getText().toString().equals("Added to Pocket")){
            PocketEntry p = new PocketEntry(listingId, storeId, _title, _price, _description, imageUrl);
            db.addPocketEntry(p);
            pocketBtn.setText("Added to Pocket");
            db.close();
        }
    }

    public void trendVote(View view){
        if(!trendingBtn.getText().toString().equals("Hearted this")){
            String _title = title.getText().toString();
            String _price = price.getText().toString();
            String _description = description.getText().toString();
            int votes = dbTrending.getTrendListing(listingId);
            votes = votes == -1 ? 1 : votes++;
            TrendingEntry trend = new TrendingEntry(listingId, storeId, _title, _price, _description, imageUrl, votes);
            trendingBtn.setText("Hearted this");

            dbTrending.updateTrending(trend);
        }

    }

    public void doStuff(JSONObject value) throws JSONException {
        title.setText(value.getString("Title"));
        price.setText("$" + value.getString("FinalPrice"));
        description.setText(value.getString("Description"));
        deal.setText(value.getString("Deal"));
        String listingId = value.getString("ID");

        String pocketEntry = db.getPocketListing(listingId);

        if(pocketEntry != null){
            pocketBtn.setText("Added to Pocket");
        }

        imageUrl = value.getString("ImageLocation");

        new DownloadImageTask((ImageView) findViewById(R.id.listingImage))
                .execute(imageUrl);
    }

    public class AsyncApi extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params){
            String URL = params[0];
            return Api.getResultFromApi(URL, "Results");
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPreExecute();
            try {
                ItemDetailActivity.this.doStuff(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
            readyState = true;

            progress.dismiss();
        }
    }
}