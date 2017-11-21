package com.floow.maplocation.Constants;

/**
 * Created by milad on 11/16/2017.
 */

public class MapConst
{
    public static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static String checkPlayServicesErrorMessage = "map error message :";

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    public static final long UPDATE_INTERVAL = 1 * 1000;
    /**
     * The fastest rate for active location updates. Updates will never be more frequent
     * than this value, but they may be less frequent.
     */
    public static final long FASTEST_UPDATE_INTERVAL = UPDATE_INTERVAL / 2;

    /**
     * The max time before batched results are delivered by location services. Results may be
     * delivered sooner than this interval.
     */
    public static final long MAX_WAIT_TIME = UPDATE_INTERVAL * 2;

    /**
     * The desired Smallest Displacement for location updates.
     */
    public static final float Smallest_Displacement = 0.25F;

    /**
     * The desired zoom for camera.
     */
    public static final int defaultZoom = 19;

}
