package com.floow.maplocation.LocationManagers;

import com.floow.maplocation.Model.Journey;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by milad on 11/19/2017.
 */

public interface IRecordLocation
{
    void journeyListBackFromRecordLocation(List<Journey> journeysList);
}
