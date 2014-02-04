package com.shoplocal.util;

public class PocketEntry {

    //private variables
    String _id;
    String _storeid;
    String _campaignid;
    String _title;
    String _price;
    String _description;
    String _img;

    public PocketEntry(String id, String storeid, String title, String price, String desc, String img){
        this._id = id;
        this._storeid = storeid;
        this._title = title;
        this._price = price;
        this._description = desc;
        this._img = img;
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
}