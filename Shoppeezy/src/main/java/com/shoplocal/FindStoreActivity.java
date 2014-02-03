package com.shoplocal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

    private ListView l;
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_store_find);

        l = (ListView) findViewById(R.id.storelist);
        //String[] values = new String[] { "Pocket List", "Search Store", "Search Product",
        //       "Trending" };

        final String url = "http://api2.shoplocal.com/retail/6883099d72e1ca52/2013.1/json/Stores?citystatezip=60601&radius=5&storecount=25";

        new AsyncApi().execute(url);

    }

    public void doStuff(String [] values){
        if(values == null){
            values = new String[] {"Broken Piece of Shit"};
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FindStoreActivity.this,
                android.R.layout.simple_list_item_1, values);
        l.setAdapter(adapter);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View v, int position, long id)
            {
                AlertDialog.Builder adb;
                adb = new AlertDialog.Builder(
                        FindStoreActivity.this);
                adb.setTitle("ListView OnClick");
                adb.setMessage("Selected Item is = "
                        + l.getItemAtPosition(position));
                adb.setPositiveButton("Ok", null);
                adb.show();
            }
        });
    }

    public class AsyncApi extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... params){
            String URL = params[0];
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = null;
            try {
                response = httpclient.execute(new HttpGet(URL));
            } catch (IOException e) {
                e.printStackTrace();
            }
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                try {
                    response.getEntity().writeTo(out);
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String responseString = out.toString();
                String [] stores = new String[25];
                JSONObject json = null;
                try {
                    json = new JSONObject(responseString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray jArray = null;
                JSONObject pRetailer;

                if (json != null) {
                    try {
                        jArray = json.getJSONArray("Results");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject store = null;
                    try {
                        store = jArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        pRetailer = store.getJSONObject("PRetailer");
                        stores[i] = pRetailer.getString("Name");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return stores;
            } else{
                //Closes the connection.
                try {
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String [] result) {
            super.onPreExecute();
            FindStoreActivity.this.doStuff(result);
        }

    }
}