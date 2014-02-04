package com.shoplocal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.shoplocal.util.Api;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class FindStoreActivity extends Activity {

    public final static String STORE_ID = "com.shoplocal.store_id";
    public final static String STORE_NAME = "com.shoplocal.store_name";
    private ListView l;
    private JSONArray stores;
    ProgressDialog progress;
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);

        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.show();

        setContentView(R.layout.activity_store_find);

        l = (ListView) findViewById(R.id.storelist);

        final String url = "http://api2.shoplocal.com/retail/6883099d72e1ca52/2013.1/json/Stores?citystatezip=60601&radius=5&storecount=25";

        new AsyncApi().execute(url);

    }

    public void doStuff(JSONArray values){
        if(values == null){
            values = new JSONArray();
        }

        stores = values;
        StoreInfoAdapter adapter = new StoreInfoAdapter(this, stores);
        l.setAdapter(adapter);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View v, int position, long id)
            {
                goToStoreListings(position);
            }
        });
    }

    private JSONArray getStoresWithContent(JSONArray values) {
        JSONArray storesWithContent = new JSONArray();

        JSONObject store;
        int promotionCount;
        for (int i=0; i<values.length(); i++) {
            try {
                store = values.getJSONObject(i);
                promotionCount = store.getInt("CurrentPromotionCount");
                if (promotionCount > 0) {
                    storesWithContent.put(store);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return storesWithContent;
    }

    public void goToStoreListings(int position) {
        String storeId = "";
        String storeName = "";
        JSONObject pRetailer;
        try {
            JSONObject store = stores.getJSONObject(position);
            storeId = store.getString("ID");
            pRetailer = store.getJSONObject("PRetailer");
            storeName = pRetailer.getString("Name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, StoreListingsActivity.class);
        intent.putExtra(STORE_ID, storeId);
        intent.putExtra(STORE_NAME, storeName);
        startActivity(intent);
    }

    public class AsyncApi extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(String... params){
            String URL = params[0];
           return Api.getResultsFromApi(URL, "Results");
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            super.onPreExecute();
            FindStoreActivity.this.doStuff(result);

            progress.dismiss();
        }

    }
}