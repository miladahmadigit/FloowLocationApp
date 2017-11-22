package com.floow.maplocation.LocationManagers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.location.Location;
import android.util.Log;

import com.floow.maplocation.Adapters.JourneyAdapter;
import com.floow.maplocation.Model.Journey;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import static com.floow.maplocation.Constants.MapConst.FASTEST_UPDATE_INTERVAL;
import static com.floow.maplocation.Constants.MapConst.Smallest_Displacement;
import static com.floow.maplocation.Constants.MapConst.UPDATE_INTERVAL;
import static com.floow.maplocation.Constants.MapConst.defaultZoom;

/**
 * Created by milad on 11/19/2017.
 */

public class ManageLocation implements IRecordLocation
{
    private IManageLocation iLocationInterface = null;
    private RecordLocations recordLocations;
    private Activity activity = null;
    private LocationCallback mLocationCallback;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private Location firstLocation = null;
    public boolean enableRecording = false;
    private boolean enableDisplayPath = false;

    public ManageLocation(IManageLocation iLocationInterface_)
    {
        this.iLocationInterface = iLocationInterface_;
        this.activity = (Activity) iLocationInterface;
        recordLocations = new RecordLocations(this, activity);
    }
    //----------------------------------------------------------------------------------------------

    /**
     * get last position or request to get location
     */
    @SuppressLint("MissingPermission")
    public void getLastLocation(final int zoom, final boolean addMarkerOrNot)
    {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(activity, new OnSuccessListener<Location>()
                {
                    @Override
                    public void onSuccess(Location location)
                    {
                        /**
                         * Got last known location. In some rare situations this can be null.
                         */
                        if (location != null)
                        {
                            iLocationInterface.cleanMarker();
                            MoveToLocation(location.getLatitude(), location.getLongitude(), zoom, addMarkerOrNot);
                        }
                        /**
                         * send request for update your location
                         */
                        startUpdateLocation(zoom, addMarkerOrNot);
                    }
                });
    }

    /**
     * get new location by callback
     */
    @SuppressLint("MissingPermission")
    public void startUpdateLocation(final int zoom, final boolean addMarkerOrNot)
    {
        createLocationRequest();
        mLocationCallback = new LocationCallback()
        {
            @Override
            public void onLocationResult(LocationResult locationResult)
            {
                for (Location location : locationResult.getLocations())
                {
                    if (location != null)
                    {
                        iLocationInterface.cleanMarker();
                        MoveToLocation(location.getLatitude(), location.getLongitude(), zoom, addMarkerOrNot);
                        if (enableRecording)
                        {
                            recordLocations.insertLocationItem(location.getLatitude(), location.getLongitude());
                        }
                        if (enableDisplayPath)
                        {
                            if (firstLocation == null)
                            {
                                firstLocation = location;
                            }
                            else
                            {
                                drawLine(firstLocation, location);
                            }
                        }
                        Log.d("loacssssion: ", "" + location.getLatitude() + "___" + location.getLongitude());
                    }
                }
            }
        };
        /**
         * send request for update location
         */
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null /* Looper */);
    }

    /**
     * create location request
     */
    public void createLocationRequest()
    {
        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)//added// 10 seconds, in milliseconds
                .setSmallestDisplacement(Smallest_Displacement)//quarter of a meter
                .setFastestInterval(FASTEST_UPDATE_INTERVAL); // 1 second, in milliseconds
    }

    /**
     * move to specific location
     *
     * @param lat
     * @param lng
     * @param zoom
     */
    public void MoveToLocation(double lat, double lng, float zoom, boolean addMarkerOrNot)
    {
        CameraUpdate updatePosition = CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), zoom);
        iLocationInterface.moveToLocation(updatePosition, addMarkerOrNot ? addMarker(lat, lng, "") : null);
    }


    /**
     * add marker on map
     *
     * @param lat
     * @param lng
     * @param title
     */
    public MarkerOptions addMarker(double lat, double lng, String title)
    {
        MarkerOptions markerOptions1 = new MarkerOptions()
                .title(title)
                .position(new LatLng(lat, lng))
                .draggable(false)
                .icon(BitmapDescriptorFactory.defaultMarker());
        return markerOptions1;
    }

    /**
     * draw line on map
     *
     * @param startPosition
     * @param endPosition
     */
    public void drawLine(Location startPosition, Location endPosition)
    {
        PolylineOptions polylineOptions = new PolylineOptions()
                .add(new LatLng(startPosition.getLatitude(), startPosition.getLongitude()))
                .add(new LatLng(endPosition.getLatitude(), endPosition.getLongitude()));
        iLocationInterface.drawLine(polylineOptions);
        firstLocation = endPosition;
    }

    /**
     * remove location requester
     */
    public void removeLocationRequester()
    {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);

        mLocationCallback=null;
        mLocationRequest=null;
        mFusedLocationClient=null;
    }

    //----------------------------------------------------------------------------------------------

    public void recordOn()
    {
        recordLocations.open();
        recordLocations.insertJourney(defaultZoom);
        enableRecording = true;
    }

    public void recordOff()
    {
        enableRecording = false;
        recordLocations.updateJourney();
    }

    public void displayPath()
    {
        enableDisplayPath = true;
    }

    public void clearPaths()
    {
        enableDisplayPath = false;
        iLocationInterface.cleanlines();
    }

    public void getAllJourneys()
    {
        recordLocations.getListOfjourneys();
    }

    public void closeDB()
    {
        recordLocations.close();
    }

    //----------------------------------------------------------------------------------------------
    @Override
    public void journeyListBackFromRecordLocation(List<Journey> journeyList)
    {
        JourneyAdapter journeyAdapter = new JourneyAdapter(activity, journeyList);
        iLocationInterface.showJourneys(journeyAdapter);
    }



}
