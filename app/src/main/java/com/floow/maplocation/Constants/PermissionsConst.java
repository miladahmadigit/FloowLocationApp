package com.floow.maplocation.Constants;

import android.Manifest;

/**
 * Created by M.Ahmadi on 8/8/2017.
 */

public class PermissionsConst
{
    public static final int REQUEST_PERMISSION = 3;
    public static String[] defaultPermissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.ACCESS_FINE_LOCATION
            , Manifest.permission.ACCESS_COARSE_LOCATION
    };
}
