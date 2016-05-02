package com.example.maxime.supfitness.fragment;


import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.example.maxime.supfitness.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends SupportMapFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener,
        LocationListener{

    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    private LatLng latLng = new LatLng(49.1175, 2.42494);//default position Luzarches
    private final int[] MAP_TYPES = {
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NONE };
    private int curMapTypeIndex = 1;
    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        mGoogleApiClient = new GoogleApiClient.Builder( getActivity() )
                .addConnectionCallbacks( this )
                .addOnConnectionFailedListener( this )
                .addApi( LocationServices.API )
                .build();

        initListeners();
    }
    private void initListeners() {
        getMap().setOnMarkerClickListener(this);
        getMap().setOnMapLongClickListener(this);
        getMap().setOnInfoWindowClickListener(this);
        getMap().setOnMapClickListener(this);
    }
    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mCurrentLocation = LocationServices
                .FusedLocationApi
                .getLastLocation( mGoogleApiClient );

        initCamera(mCurrentLocation);
    }
    private void initCamera( Location location ) {
        if (location != null) {
            latLng = new LatLng(location.getLatitude(),
                    location.getLongitude());
        }
        CameraPosition position = CameraPosition.builder()
                .target( latLng )
                .zoom( 16f )
                .bearing( 0.0f )
                .tilt(0.0f)
                .build();

        getMap().animateCamera( CameraUpdateFactory
                .newCameraPosition(position), null );

        getMap().setMapType( MAP_TYPES[curMapTypeIndex] );
        getMap().setTrafficEnabled( true );
        getMap().setMyLocationEnabled( true );
        getMap().getUiSettings().setZoomControlsEnabled(true);
    }
    @Override
    public void onResume() {
        try{
            super.onResume();
        }catch(NullPointerException e){
            Log.d("onResume", "NullPointerException: " + e);
        }
    }

    @Override
    public void onDestroy() {
        try{
            super.onDestroy();
        }catch(NullPointerException e){
            Log.d("onDestroy", "NullPointerException: " + e);
        }
    }

    @Override
    public void onLowMemory() {
        try{
            super.onLowMemory();
        }catch(NullPointerException e){
            Log.d("onLowMemory", "NullPointerException: " + e);
        }
    }
    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        MarkerOptions options = new MarkerOptions().position( latLng );
        options.title( getAddressFromLatLng( latLng ) );

        options.icon(BitmapDescriptorFactory.defaultMarker());
        getMap().addMarker(options);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        MarkerOptions options = new MarkerOptions().position( latLng );
        options.title( getAddressFromLatLng( latLng ) );

        options.icon( BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(getResources(),
                        R.mipmap.ic_launcher)) );

        getMap().addMarker( options );
    }
    private String getAddressFromLatLng( LatLng latLng ) {
        Geocoder geocoder = new Geocoder( getActivity() );

        String address = "";

        try {
            address = geocoder
                    .getFromLocation( latLng.latitude, latLng.longitude, 1 )
                    .get( 0 ).getAddressLine( 0 );

        } catch (IOException e ) {
        }

        return address;
    }
    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return true;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
    // Let's draw Circleon map
    private void drawCircle( LatLng location ) {
        CircleOptions options = new CircleOptions();
        options.center( location );
        //Radius in meters
        options.radius( 10 );
        options.fillColor( getResources()
                .getColor( R.color.colorPrimary ) );
        options.strokeColor( getResources()
                .getColor( R.color.colorPrimaryDark ) );
        options.strokeWidth( 10 );
        getMap().addCircle(options);
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLngChange = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLngChange, 10);
        getMap().animateCamera(cameraUpdate);
    }
}
