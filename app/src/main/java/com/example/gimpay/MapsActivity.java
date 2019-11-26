package com.example.gimpay;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.geo.BackendlessGeoQuery;
import com.backendless.geo.GeoPoint;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {
    private GoogleMap mMap;
    private LocationManager locationManager;
    private  String provider;
    double lat=28.4069973;
    double lng=78.1048047;
    ImageButton ibsend;
    boolean isExistingPoint=false;
    GeoPoint existingPoint;
    List<GeoPoint> list;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);



        ibsend=(ImageButton)findViewById(R.id.ibsend);
        String  type=getIntent().getStringExtra("type");

        if(type.equals("Employee"))
        {
            ibsend.setVisibility(View.GONE);
            BackendlessGeoQuery geoQuery=new BackendlessGeoQuery();
            geoQuery.addCategory("Employee");
            geoQuery.setIncludeMeta(true);
            Backendless.Geo.getPoints(geoQuery, new AsyncCallback<List<GeoPoint>>() {
                @Override
                public void handleResponse(List<GeoPoint> response) {




                    list=response;
                    if(list.size()!=0)
                    {
                        for(int i=0;i<list.size();i++)
                        {
                            LatLng positionmarker=new LatLng(list.get(i).getLatitude(),list.get(i).getLongitude());
                            mMap.addMarker(new MarkerOptions()
                                    .position(positionmarker)
                                    .snippet(list.get(i).getMetadata("updated").toString()).title(list.get(i).getMetadata("name").toString()));



                        }
                    }





                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(MapsActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            ibsend.setVisibility(View.GONE);
            BackendlessGeoQuery geoQuery=new BackendlessGeoQuery();
            geoQuery.addCategory("Employee");
            geoQuery.setIncludeMeta(true);
            Backendless.Geo.getPoints(geoQuery, new AsyncCallback<List<GeoPoint>>() {
                @Override
                public void handleResponse(List<GeoPoint> response) {

                    list=response;
                    if(list.size()!=0)
                    {
                        for(int i=0;i<list.size();i++)
                        {
                            if(list.get(i).getMetadata("name").toString().equals(getIntent().getStringExtra("type")))
                            {
                                isExistingPoint=true;
                                existingPoint=list.get(i);
                                break;
                            }
                        }
                    }
                    ibsend.setVisibility(View.VISIBLE);
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(MapsActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }






        ibsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MapsActivity.this, "Busy sending location..", Toast.LENGTH_SHORT).show();
                if(!isExistingPoint) // no existing point
                {
                    List<String> categories=new ArrayList<String>();
                    categories.add("Employee");
                    Map<String,Object> meta=new HashMap<String,Object>();
                    meta.put("name",getIntent().getStringExtra("type"));
                    meta.put("updated",new Date().toString());
                    Backendless.Geo.savePoint(lat, lng, categories, meta, new AsyncCallback<GeoPoint>() {
                        @Override
                        public void handleResponse(GeoPoint response) {
                            Toast.makeText(MapsActivity.this, "Successfully saved location", Toast.LENGTH_SHORT).show();
                            isExistingPoint=true;
                            existingPoint=response;

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(MapsActivity.this,fault.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    Backendless.Geo.removePoint(existingPoint, new AsyncCallback<Void>() {
                        @Override
                        public void handleResponse(Void response) {
                            List<String> categories=new ArrayList<String>();
                            categories.add("Employee");
                            Map<String,Object> meta=new HashMap<String,Object>();
                            meta.put("name",getIntent().getStringExtra("type"));
                            meta.put("updated",new Date().toString());
                            Backendless.Geo.savePoint(lat, lng, categories, meta, new AsyncCallback<GeoPoint>() {
                                @Override
                                public void handleResponse(GeoPoint response) {
                                    Toast.makeText(MapsActivity.this, "Successfully saved location", Toast.LENGTH_SHORT).show();
                                    isExistingPoint=true;
                                    existingPoint=response;

                                }

                                @Override
                                public void handleFault(BackendlessFault fault) {
                                    Toast.makeText(MapsActivity.this,fault.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(MapsActivity.this,fault.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });









        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
       SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria=new Criteria();
        provider=locationManager.getBestProvider(criteria,false);

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MapsActivity.this,new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},0);

        }
        else
        {
            // intent=new Intent(MainActivity.this,MapsActivity.class);
            //   intent.putExtra("type","Family");startActivity(intent);
            Location location=locationManager.getLastKnownLocation(provider);
            if(location!=null)
            {
                onLocationChanged(location);
            }

        }










    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng position = new LatLng(lat, lng);
       // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
      //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position,25));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(25),2000,null);//2 seconds

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MapsActivity.this,new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},0);

        }
        else
        {
            // intent=new Intent(MainActivity.this,MapsActivity.class);

            mMap.setMyLocationEnabled(true);
        }

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);






    }

    @Override
    public void onLocationChanged(Location location) {
        lat=location.getLatitude();
        lng=location.getLongitude();
        if(mMap!=null)
        {
            LatLng position=new LatLng(lat,lng);
            mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.person))
                    .anchor(0.0f,1.0f)
                    .title("Your last known location")
                    .position(position));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MapsActivity.this,new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},0);

        }
        else
        {//every 3 min request is sent or every 50 metre
            // intent=new Intent(MainActivity.this,MapsActivity.class);
            //    locationManager.requestLocationUpdates(provider,108000,50,this);
            //  mMap.setMyLocationEnabled(true);
            locationManager.removeUpdates(this);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
