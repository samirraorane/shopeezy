package com.shoplocal;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

public class FullscreenActivity extends Activity {

    private ListView l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        l = (ListView) findViewById(R.id.listview);
        /*String[] values = new String[] { "Pocket List", "Search Store", "Search Product",
                "Trending" };*/

        String[] values = getData();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        l.setAdapter(adapter);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View v, int position, long id)
            {

                AlertDialog.Builder adb;
                adb = new AlertDialog.Builder(
                        FullscreenActivity.this);
                adb.setTitle("ListView OnClick");
                adb.setMessage("Selected Item is = "
                        + l.getItemAtPosition(position));
                adb.setPositiveButton("Ok", null);
                adb.show();
            }
        });
    }

    private String[] getData() {
        try {
            HttpParams p = new BasicHttpParams();
            HttpClient httpclient = new DefaultHttpClient(p);
            String url = "http://vqascweb1.apimvc.crossmediaservices.com/retail/6883099d72e1ca52/2013.1/json/multiretailerpromotions?siteid=1506&MultRetPromoSort=1&pageimagewidth=189&citystatezip=60601";
            HttpGet httppost = new HttpGet(url);
            String[] stores = new String[10];

            // Instantiate a GET HTTP method
            try {
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String responseBody = httpclient.execute(httppost,
                        responseHandler);
                // Parse
                JSONObject json = new JSONObject(responseBody);
                JSONArray jArray = json.getJSONArray("Results");

                for (int i = 0; i < 10; i++) {
                    JSONObject store = jArray.getJSONObject(i);
                    stores[i] = store.getString("StoreName");
                }

                return stores;
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Throwable t) {
            Toast.makeText(this, "Request failed: " + t.toString(),
                    Toast.LENGTH_LONG).show();
        }

        return null;
    }
}