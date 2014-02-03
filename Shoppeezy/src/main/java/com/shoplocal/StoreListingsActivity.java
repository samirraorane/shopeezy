package com.shoplocal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
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

public class StoreListingsActivity extends Activity {

    private ListView l;
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        String storeId = intent.getStringExtra(FindStoreActivity.STORE_INFO);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_store_find);

        l = (ListView) findViewById(R.id.storelist);

        final String url = "http://api2.shoplocal.com/retail/6883099d72e1ca52/2013.1/json/AllListings?storeid=" + storeId;

        new AsyncApi().execute(url);

    }

    public void doStuff(JSONArray values){
        if(values == null){
            values = new JSONArray();
        }
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(FindStoreActivity.this,
                android.R.layout.simple_list_item_1, values);*/
        ListingAdapter adapter = new ListingAdapter(this, values);
        l.setAdapter(adapter);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View v, int position, long id)
            {
                AlertDialog.Builder adb;
                adb = new AlertDialog.Builder(
                        StoreListingsActivity.this);
                adb.setTitle("ListView OnClick");
                adb.setMessage("Selected Item is = "
                        + l.getItemAtPosition(position));
                adb.setPositiveButton("Ok", null);
                adb.show();
            }
        });
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
            StoreListingsActivity.this.doStuff(result);
        }
    }
}