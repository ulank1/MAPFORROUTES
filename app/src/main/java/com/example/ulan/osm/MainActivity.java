package com.example.ulan.osm;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;
import org.osmdroid.views.overlay.mylocation.SimpleLocationOverlay;

import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;

import android.os.Handler;

import java.util.logging.LogRecord;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity implements MapEventsReceiver {
    LocationManager locationManager;
    MapView map;
    Routes routes;
    Polyline roadOverlay;
    int posRoute = 0;
    Marker secondMarker;
    ArrayList<Marker> markerList;
    int posMArker = 0;
    TextView textView;
    ArrayList<String> pointList;
    Handler h;
    int a = 0;
    ArrayList<String> s;
    RoadManager roadManager;
    ArrayList<GeoPoint> waypoints;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar;
        textView = (TextView) findViewById(R.id.text);
        routes = new Routes();
        markerList = new ArrayList<>();
        pointList = new ArrayList<>();
        waypoints = new ArrayList<>();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(this, this);
        s = new ArrayList<>();
        s.add("#");
        for (int i = 100; i < 400; i++) {
            s.add(i + "");
        }


        h = new Handler() {
            @Override


            public void handleMessage(android.os.Message msg) {
                // обновляем TextView

                map.invalidate();
            }

            ;
        };
//deleting the file
        //Spinner------------------------------------------------------------------------------------

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, s);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = (Spinner) findViewById(R.id.spiner);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Title");
        // выделяем элемент
        spinner.setSelection(0);

        roadManager = new OSRMRoadManager(this);
        // устанавливаем обработчик нажатия

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants.setUserAgentValue(BuildConfig.APPLICATION_ID);

        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.getOverlays().add(0, mapEventsOverlay);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        IMapController mapController = map.getController();
        mapController.setZoom(15);
        GeoPoint startPoint = new GeoPoint(42.856, 74.6018);
        mapController.setCenter(startPoint);


    }

    @Override
    protected void onResume() {
        super.onResume();


    }

//42.82467/74.53717


    @Override
    public boolean singleTapConfirmedHelper(final GeoPoint geoPoint) {


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                secondMarker = new Marker(map);
                secondMarker.setPosition(new GeoPoint(geoPoint.getLatitude(), geoPoint.getLongitude()));
                secondMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

                markerList.add(secondMarker);
                map.getOverlays().add(markerList.get(markerList.size() - 1));
                pointList.add("waypoints.add(new GeoPoint(" + geoPoint.getLatitude() + "," + geoPoint.getLongitude() + "));");
                h.sendEmptyMessage(1);


            }
        });
        thread.start();
        waypoints.add(geoPoint);
        if (a == 1) {


            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {


                    Road road = roadManager.getRoad(waypoints);


                    map.getOverlays().remove(roadOverlay);
                    roadOverlay = RoadManager.buildRoadOverlay(road);

                    map.getOverlays().add(roadOverlay);
                    h.sendEmptyMessage(1);
                }
            });
            thread1.start();
        }
        a = 1;
        return true;
    }

    @Override
    public boolean longPressHelper(GeoPoint geoPoint) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                secondMarker = markerList.get(markerList.size() - 1);
                map.getOverlays().remove(secondMarker);


                h.sendEmptyMessage(1);

            }
        });
        thread.start();
        waypoints.remove(waypoints.size() - 1);
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {


                Road road = roadManager.getRoad(waypoints);


                map.getOverlays().remove(roadOverlay);
                roadOverlay = RoadManager.buildRoadOverlay(road);

                map.getOverlays().add(roadOverlay);
                h.sendEmptyMessage(1);
            }
        });
        thread1.start();
        pointList.remove(pointList.size() - 1);
        posMArker = 1;
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        File fileName = null;
        String sdState = android.os.Environment.getExternalStorageState();
        if (sdState.equals(android.os.Environment.MEDIA_MOUNTED)) {
            File sdDir = android.os.Environment.getExternalStorageDirectory();
            fileName = new File(sdDir, "routes");
        } else {
            fileName = this.getCacheDir();
        }
        if (!fileName.exists())
            fileName.mkdirs();

        File sdDir = android.os.Environment.getExternalStorageDirectory();
        int i = 0;
        File fileNam = null;
        while (i != pointList.size()) {

             textView.append(pointList.get(i) + "\n");
            if (i % 15 == 0) {
                fileNam = new File(sdDir, "routes/" + s.get(spinner.getSelectedItemPosition()) + i / 15 + ").txt");
                try {
                    FileWriter f = new FileWriter(fileNam);
                    f.write(textView.getText().toString());
                    f.flush();
                    f.close();
                } catch (Exception e) {

                }
                textView.setText("");
            }
            if (i==pointList.size()-1&& i%15!=0){
                fileNam = new File(sdDir, "routes/" + s.get(spinner.getSelectedItemPosition()) + ((i / 15)+1) + ").txt");

                try {
                    FileWriter f = new FileWriter(fileNam);
                    f.write(textView.getText().toString());
                    f.flush();
                    f.close();
                } catch (Exception e) {

                }
                textView.setText("");
            }
            i++;
        }
        return super.onOptionsItemSelected(item);


    }






}
