package com.floow.maplocation.PermissionsManagers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import com.floow.maplocation.Constants.PermissionsConst;

import static android.support.v4.app.ActivityCompat.requestPermissions;
import static android.support.v4.content.ContextCompat.checkSelfPermission;
import static com.floow.maplocation.Constants.PermissionsConst.defaultPermissions;

/**
 * Created by M.Ahmadi on 8/7/2017.
 */

public class CheckPermissions
{
    private IPermission permissionStatus=null;
    private Activity activity=null;

    public CheckPermissions(IPermission permissionStatus_)
    {
        this.permissionStatus=permissionStatus_;
        this.activity = (Activity) permissionStatus;
        if (checkMultiPermissions(defaultPermissions, activity))
        {
            permissionStatus.permissionGranted();
        }
        else
        {
            permissionsRequest(defaultPermissions, activity);
        }
    }

    /**
     * check if all permissions are granted
     * @param permissions
     */
    public void PermissionsResults(String[] permissions)
    {
        if(checkMultiPermissions(permissions,activity))
        {
            permissionStatus.permissionGranted();
        }
        else
        {
            permissionStatus.permissionDenied();
        }
    }

    /**
     * check permission
     *
     * @param permissions
     * @param activity
     * @return
     */
    @TargetApi(Build.VERSION_CODES.M)
    public boolean checkMultiPermissions(String[] permissions, Activity activity)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            for (String permission : permissions)
            {
                if (checkSelfPermission(activity, permission) == PackageManager.PERMISSION_DENIED)
                {
                    return false;
                }
            }
            return true;
        }
        else
        {
            return true;
        }
    }


    /**
     * request permission
     * @param permissions
     * @param activity
     * @return
     */
    @TargetApi(Build.VERSION_CODES.M)
    public void permissionsRequest(String[] permissions, Activity activity)
    {
        requestPermissions(activity, permissions, PermissionsConst.REQUEST_PERMISSION);
    }


}
