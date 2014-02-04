package com.shoplocal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.shoplocal.util.PocketEntry;
import com.shoplocal.util.SqlHelper;
import com.shoplocal.util.SqlHelperTrending;
import com.shoplocal.util.TrendingEntry;

import java.util.List;

public class TrendingListActivity extends Activity {

    SqlHelperTrending db;
    private ListView l;

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        db = new SqlHelperTrending(this);

        List<TrendingEntry> list = db.getAllTrendingEntries();

        setContentView(R.layout.activity_trending_list);
        l = (ListView) findViewById(R.id.trendingListView);

        TrendingListAdapter adapter = new TrendingListAdapter(this, list);
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
                Intent intent = new Intent(TrendingListActivity.this, ItemDetailActivity.class);
                intent.putExtra("STORE_ID", storeId);
                intent.putExtra("LISTING_ID", listingId);
                startActivity(intent);
            }
        });

        db.close();
    }
}