package com.example.ticketer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticketer.Tasks.FetchLocationsTask;
import com.example.ticketer.Tasks.UpdateLocationTask;

public class MainActivity extends AppCompatActivity implements LocationListener{

    private String username;
    private FragmentRefreshListener fragmentRefreshListener;
    private boolean wantLocationUpdates;
    private static final String UPDATES_BUNDLE_KEY = "WantsLocationUpdates";
    private TextView statusOfPermission;
    public static final int PERMISSION_REQUEST_CODE = 1;
    private Location lastKnownLocation, updatedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = getIntent().getStringExtra("username");
        TextView loggedinUsers = (TextView) this.findViewById(R.id.textView);
        loggedinUsers.append(" ");
        loggedinUsers.append(username);

        Button updateButton = (Button) this.findViewById(R.id.updateLocation_button);
        Button getLocationButton = (Button) this.findViewById(R.id.getLocation_button);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptUpdate();
            }
        });
        getLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptGetLocation();
            }
        });

        statusOfPermission = (TextView) findViewById(R.id.statusOfPermission);

        if (savedInstanceState != null
                && savedInstanceState.containsKey(UPDATES_BUNDLE_KEY))
            wantLocationUpdates
                    = savedInstanceState.getBoolean(UPDATES_BUNDLE_KEY);
        else // activity is not being reinitialized from prior start
            wantLocationUpdates = false;
        if (!hasLocationPermission())
        {
            statusOfPermission.setText(R.string.permissions_denied);
            Log.w(MainActivity.class.getName(),
                    "Location permissions denied");
        }
    }

    public void attemptUpdate() {
        if (wantLocationUpdates)
        {
            wantLocationUpdates = false;
            stopGPS();
        }
        else
        {
            wantLocationUpdates = true;
            startGPS();
            UpdateLocationTask updateLocationTask = new UpdateLocationTask(this);
            updateLocationTask.execute(Double.toString(lastKnownLocation.getLongitude()), Double.toString(lastKnownLocation.getAltitude()), username);
        }

    }
    public void attemptGetLocation() {
        LocationFragment locationFragment = new LocationFragment();
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.setReorderingAllowed(false);
//                transaction.detach(locationFragment).attach(locationFragment).commit();
            if(getFragmentRefreshListener()!= null){
                getFragmentRefreshListener().onRefresh();
            }
    }

    //For refreshing lists of Location Fragments
    public FragmentRefreshListener getFragmentRefreshListener() {
        return fragmentRefreshListener;
    }

    public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener) {
        this.fragmentRefreshListener = fragmentRefreshListener;
    }

    public interface FragmentRefreshListener{
        void onRefresh();
    }

    //Location-based methods
    private boolean hasLocationPermission()
    {
        int permissionCheck = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }
        else
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.request_permission_title)
                        .setMessage(R.string.request_permission_text)
                        .setPositiveButton(
                                R.string.request_permission_positive,
                                new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick
                                            (DialogInterface dialogInterface, int i)
                                    {
                                        ActivityCompat.requestPermissions
                                                (MainActivity.this,new String[]
                                                                {Manifest.permission.
                                                                        ACCESS_FINE_LOCATION},
                                                        PERMISSION_REQUEST_CODE);
                                    }
                                })
                        .create()
                        .show();
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]
                                {Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSION_REQUEST_CODE);
            }
            return false;
        }
    }

    private void startGPS()
    {
        LocationManager locationManager = (LocationManager)
                this.getSystemService(Context.LOCATION_SERVICE);
        try
        {
            String provider = LocationManager.GPS_PROVIDER;
            locationManager.requestLocationUpdates(provider,0,0, (LocationListener) this);
            lastKnownLocation
                    = locationManager.getLastKnownLocation(provider);
            if (lastKnownLocation != null)
                statusOfPermission.setText(lastKnownLocation.toString());
//            toggleButton.setText(R.string.button_stop);
        }
        catch (SecurityException e)
        {
            statusOfPermission.setText(R.string.permissions_denied);
            Log.w(MainActivity.class.getName(),
                    "Security Exception: " + e);
        }
    }

    private void stopGPS()
    {
        LocationManager locationManager = (LocationManager)
                this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.removeUpdates((LocationListener) this);
//        toggleButton.setText(R.string.button_start);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        int permissionCheck = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (wantLocationUpdates
                && permissionCheck == PackageManager.PERMISSION_GRANTED)
            startGPS();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        // stop location updates while the activity is paused
        int permissionCheck = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED)
            stopGPS();
    }

    // called when activity is about to be killed to save app state
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putBoolean(UPDATES_BUNDLE_KEY, wantLocationUpdates);
    }

    // implementation of onLocationChanged method
    public void onLocationChanged(Location location)
    {
        statusOfPermission.setText(location.toString());
        updatedLocation = location;
        Log.i(MainActivity.class.getName(), "Location: "+location);
    }

    // implementation of onProviderDisabled method
    public void onProviderDisabled(String provider)
    {
        statusOfPermission.setText(R.string.provider_disabled);
    }

    // implementation of onProviderEnabled method
    public void onProviderEnabled(String provider)
    {
        statusOfPermission.setText(R.string.provider_enabled);
    }

    // implementation of onStatusChanged method
    public void onStatusChanged(String provider, int status,
                                Bundle extras)
    {
        statusOfPermission.setText(R.string.provider_status_changed);
    }
}