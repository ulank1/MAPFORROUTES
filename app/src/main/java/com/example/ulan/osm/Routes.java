package com.example.ulan.osm;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

/**
 * Created by Админ on 09.12.2016.
 */

public class Routes {
    ArrayList<GeoPoint> waypoints;
    public ArrayList<GeoPoint> getReout(String number){
        waypoints=new ArrayList<>();
        if (number.equals("100")) {
            waypoints.add(new GeoPoint(42.82467, 74.53717));
            GeoPoint endPoint = new GeoPoint(42.82462, 74.53928);

            waypoints.add(new GeoPoint(42.82467, 74.53717));
            waypoints.add(endPoint);
            waypoints.add(new GeoPoint(42.82468, 74.54049));
            waypoints.add(new GeoPoint(42.82476, 74.54145));
            waypoints.add(new GeoPoint(42.82517, 74.54311));
            waypoints.add(new GeoPoint(42.82571, 74.54464));
            waypoints.add(new GeoPoint(42.82759, 74.55005));
            waypoints.add(new GeoPoint(42.82951, 74.56047));
            waypoints.add(new GeoPoint(42.82966, 74.5693));
            waypoints.add(new GeoPoint(42.84384, 74.56838));
            waypoints.add(new GeoPoint(42.84292, 74.60881));
            waypoints.add(new GeoPoint(42.88473, 74.61175));
            waypoints.add(new GeoPoint(42.88809, 74.63546));
            waypoints.add(new GeoPoint(42.84174, 74.63617));
            waypoints.add(new GeoPoint(42.84291, 74.60881));
        }
        else {
            waypoints.add(new GeoPoint(42.82467, 74.53717));
            waypoints.add(new GeoPoint(42.88473, 74.61175));
        }
        return  waypoints;
    }
}
