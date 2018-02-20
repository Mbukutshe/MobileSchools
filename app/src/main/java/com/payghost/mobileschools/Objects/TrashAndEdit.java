package com.payghost.mobileschools.Objects;

import android.graphics.Bitmap;

/**
 * Created by Wiseman on 2018-02-12.
 */

public class TrashAndEdit {
    public int id;
    public Bitmap image;
    public String make;
    public String model;
    public String price;
    public String featurers;
    public String transmission;
    public String mileage;
    public String year;
    public String province;
    public String url;
    public TrashAndEdit(int id,String price,String transmission, String mileage, String province,String featurers, String make,String model,String year,String url)
    {
        this.id = id;
        this.price = price;
        this.transmission = transmission;
        this.mileage = mileage;
        this.province = province;
        this.featurers = featurers;
        this.make = make;
        this.model = model;
        this.year = year;
        this.url = url;
    }
}
