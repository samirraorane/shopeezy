package com.shoplocal.util;

import android.util.Log;

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

public class Api {

    public static JSONArray getResultsFromApi(String URL, String field){
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
            JSONObject json = null;
            try {
                json = new JSONObject(responseString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONArray stores = null;

            if (json != null) {
                try {
                    stores = json.getJSONArray(field);
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

    public static JSONObject getResultFromApi(String URL, String field){
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
            JSONObject json = null;
            try {
                json = new JSONObject(responseString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject listing = null;

            if (json != null) {
                try {
                    listing = json.getJSONObject(field);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return listing;
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
}
