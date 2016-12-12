package com.example.ulan.osm;

/**
 * Created by Админ on 09.12.2016.
 */

public class LatLong {
    double latl,longl;
    public LatLong(double latl,double longl){
        this.latl=latl;
        this.longl=longl;
    }

    public void setLatl(double latl) {
        this.latl = latl;
    }

    public void setLongl(double longl) {
        this.longl = longl;
    }

    public double getLatl() {
        return latl;
    }

    public double getLongl() {
        return longl;
    }
}
