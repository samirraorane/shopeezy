package com.shoplocal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SavedStoresActivity extends Activity {

    public final static String STORE_ID = "com.shoplocal.store_id";
    public final static String STORE_NAME = "com.shoplocal.store_name";
    private ListView l;
    private String[]  stores;
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_find);
        l = (ListView) findViewById(R.id.storelist);

        stores = new String[] { "2478536", "2593837" };
        SavedStoresAdapter adapter = new SavedStoresAdapter (this, stores);
        l.setAdapter(adapter);
    }
}