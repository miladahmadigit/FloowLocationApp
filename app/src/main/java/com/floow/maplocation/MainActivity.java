package com.floow.maplocation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import com.floow.maplocation.Adapters.JourneyAdapter;
import com.floow.maplocation.LocationManagers.IManageLocation;
import com.floow.maplocation.LocationManagers.ManageLocation;
import com.floow.maplocation.PermissionsManagers.CheckPermissions;
import com.floow.maplocation.PermissionsManagers.IPermission;
import com.floow.maplocation.SharedPreferencesManagers.SharedPreferencesManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import static com.floow.maplocation.Constants.MapConst.defaultZoom;

public class MainActivity extends AppCompatActivity implements IPermission, IManageLocation
{
    private Marker marker1 = null;
    private List<Polyline> lines = new ArrayList<>();
    public GoogleMap googleMapInstance;
    private GoogleApiClient mGoogleApiClient;
    /**
     * present classes
     */
    private CheckPermissions checkPermissions = null;
    private ManageLocation manageLocation = null;

    /**
     * views
     */
    private ListView lvJourneyList = null;
    private SupportMapFragment mapFragment = null;
    private Button  btnShowJourneyList;
    private Switch swTrackOnOff, swDisplayPath;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions = new CheckPermissions(this);
    }

    //----------------------------------------------------------------------------------------------

    /**
     * check Permissions Result
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        checkPermissions.PermissionsResults(permissions);
    }

    @Override
    public void permissionGranted()
    {
        /**
         * initial view , map variable and  SharedPreferences
         */
        initViews();

        //buildGoogleApiClient();

        initMap();

        SharedPreferencesManager.initSharedPreferences(this);

        manageLocation = new ManageLocation(this);

        manageLocation.getLastLocation(defaultZoom, true);

    }

    /**
     * app going to finish if user denied permissions
     */
    @Override
    public void permissionDenied()
    {
        this.finish();
    }

    //----------------------------------------------------------------------------------------------

    /**
     * initialize views
     */
    private void initViews()
    {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.frShowMap);

        btnShowJourneyList = findViewById(R.id.btnShowJourneyList);
        btnShowJourneyList.setOnClickListener(ClickListener);

        swDisplayPath = findViewById(R.id.swDisplayPath);
        swDisplayPath.setOnCheckedChangeListener(displayPathChecking);

        swTrackOnOff = findViewById(R.id.swTrackOnOff);
        swTrackOnOff.setOnCheckedChangeListener(recordChecking);

        lvJourneyList = findViewById(R.id.lvJourneyList);
    }

//    protected synchronized void buildGoogleApiClient() {
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//        mGoogleApiClient.connect();
//    }
    /**
     * fill map variable
     */
    public void initMap()
    {
        if (googleMapInstance == null)
        {
            mapFragment.getMapAsync(new OnMapReadyCallback()
            {
                @Override
                public void onMapReady(GoogleMap googleMap)
                {
                    googleMapInstance = googleMap;
                }
            });
        }
    }


    //----------------------------------------------------------------------------------------------
    /**
     * handle click on buttons
     */
    public View.OnClickListener ClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.btnShowJourneyList:
                {
                    manageLocation.getAllJourneys();
                    break;
                }
            }
        }
    };

    /**
     * handle switch of recording location
     */
    public CompoundButton.OnCheckedChangeListener recordChecking = new CompoundButton.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            if (isChecked)
            {
                manageLocation.recordOn();
            }
            else
            {
                manageLocation.recordOff();
            }
        }
    };

    /**
     * handle switch of display path
     */
    public CompoundButton.OnCheckedChangeListener displayPathChecking = new CompoundButton.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            if (isChecked)
            {
                manageLocation.displayPath();
            }
            else
            {
                manageLocation.clearPaths();
            }
        }
    };


    //----------------------------------------------------------------------------------------------

    /**
     * move to specific location on map
     *
     * @param updatePosition
     */
    @Override
    public void moveToLocation(CameraUpdate updatePosition, MarkerOptions markerOptions)
    {
        if(googleMapInstance!=null)
        {
            googleMapInstance.moveCamera(updatePosition);
            if (markerOptions != null)
            {
                marker1 = googleMapInstance.addMarker(markerOptions);
            }
        }
    }

    /**
     * remove marker
     */
    @Override
    public void cleanMarker()
    {
        if (marker1 != null)
        {
            marker1.remove();
            marker1 = null;
        }
    }

    /**
     * draw lines on map
     * @param polylineOptions
     */
    @Override
    public void drawLine(PolylineOptions polylineOptions)
    {
        if(googleMapInstance!=null)
        {
            Log.d("drawLine",""+polylineOptions.getPoints().get(0).latitude+"--"+polylineOptions.getPoints().get(0).longitude);

            lines.add(googleMapInstance.addPolyline(polylineOptions));
        }
    }

    /**
     * remove all line on map
     */
    @Override
    public void cleanlines()
    {
        for (Polyline ln : lines)
        {
            ln.remove();
        }
    }

    /**
     * show list of journeys
     * @param journeyAdapter
     */
    @Override
    public void showJourneys(JourneyAdapter journeyAdapter)
    {
        lvJourneyList.setAdapter(journeyAdapter);
    }
    //----------------------------------------------------------------------------------------------


    @Override
    protected void onStart()
    {
        super.onStart();
        if(manageLocation!=null && manageLocation.mFusedLocationClient==null)
            manageLocation.getLastLocation(defaultZoom, true);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if(manageLocation!=null && !manageLocation.enableRecording )
        {
            manageLocation.removeLocationRequester();
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onPause()
    {
        super.onPause();

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        manageLocation.closeDB();
    }

}
