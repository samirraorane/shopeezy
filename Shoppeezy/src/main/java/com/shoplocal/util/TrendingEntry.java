package com.shoplocal.util;

public class TrendingEntry {

    //private variables
    String _id;
    String _storeid;
    int _trendVote;
    String _title;
    String _price;
    String _description;
    String _img;

    public TrendingEntry(String id, String storeid, String title, String price, String desc, String img){
        this._id = id;
        this._storeid = storeid;
        this._title = title;
        this._price = price;
        this._description = desc;
        this._img = img;
        this._trendVote = 1;
    }

    public TrendingEntry(String id, String storeid, String title, String price, String desc, String img, int trend){
        this._id = id;
        this._storeid = storeid;
        this._title = title;
        this._price = price;
        this._description = desc;
        this._img = img;
        this._trendVote = trend;
    }

    public String getId(){
        return this._id;
    }

    public String getStoreId(){
        return this._storeid;
    }

    public String getTitle(){
        return this._title;
    }

    public String getPrice(){
        return this._price;
    }

    public String getDescription(){
        return this._description;
    }
    public String getImage(){
        return this._img;
    }
    public int getTrendVote() { return this._trendVote; }
}