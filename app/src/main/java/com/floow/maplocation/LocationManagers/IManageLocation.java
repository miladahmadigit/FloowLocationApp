package com.floow.maplocation.LocationManagers;

import com.floow.maplocation.Adapters.JourneyAdapter;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by milad on 11/19/2017.
 */

public interface IManageLocation
{
    void moveToLocation(CameraUpdate updatePosition, MarkerOptions markerOptions);
    void cleanMarker();
    void drawLine(PolylineOptions polylineOptions);
    void cleanlines();
    void showJourneys(JourneyAdapter journeyAdapter);
}
