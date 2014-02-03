package com.shoplocal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.shoplocal.util.Api;

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
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ItemDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_item_detail);


        Intent intent = getIntent();
        String campaignId = intent.getStringExtra("CAMPAIGN_ID");
        String storeId = intent.getStringExtra("STORE_ID");
        String listingId = intent.getStringExtra("LISTING_ID");

        String url = "http://api2.shoplocal.com/retail/"+campaignId+"/2013.1/json/Listing?storeid="+storeId+"&listingid=" + listingId;
        new AsyncApi().execute(url);
    }
    public void doStuff(JSONObject value){

    }

    public class AsyncApi extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params){
            String URL = params[0];
            return Api.getResultFromApi(URL, "data");
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPreExecute();
            ItemDetailActivity.this.doStuff(result);
        }
    }
}