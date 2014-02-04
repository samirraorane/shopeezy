package com.shoplocal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.shoplocal.util.Api;
import com.shoplocal.util.PocketEntry;
import com.shoplocal.util.SqlHelper;

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
import java.util.List;

public class PocketListActivity extends Activity {

    SqlHelper db;
    private ListView l;

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        db = new SqlHelper(this);

        List<PocketEntry> list = db.getAllPocketEntries();

        setContentView(R.layout.activity_pocket_list);
        l = (ListView) findViewById(R.id.pocketListView);

        PocketListingAdapter adapter = new PocketListingAdapter(this, list);
        l.setAdapter(adapter);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View v, int position, long id)
            {

                PocketEntry current = (PocketEntry)l.getItemAtPosition(position);
                String listingId = "-2045209433"; //fail over
                String storeId = "2652663";
                listingId = current.getId();
                storeId = current.getStoreId();
                Intent intent = new Intent(PocketListActivity.this, ItemDetailActivity.class);
                intent.putExtra("STORE_ID", storeId);
                intent.putExtra("LISTING_ID", listingId);
                startActivity(intent);
            }
        });

        db.close();
    }
}