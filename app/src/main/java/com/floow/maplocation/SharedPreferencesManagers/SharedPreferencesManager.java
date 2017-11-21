package com.floow.maplocation.SharedPreferencesManagers;

import android.content.Context;

import static android.content.Context.MODE_PRIVATE;
import static com.floow.maplocation.Constants.SharedPreferencesConst.editor;
import static com.floow.maplocation.Constants.SharedPreferencesConst.pref;

/**
 * Created by M.Ahmadi on 8/8/2017.
 */

public class SharedPreferencesManager
{

    /**
     * initial Shared Preferences for use
     *
     * @param context
     * @param mode
     * @return
     */
    public static boolean initSharedPreferences(Context context, int... mode)
    {
        boolean retval = true;
        if (pref == null && mode != null && mode.length > 0)
        {
            pref = context.getSharedPreferences("Pref", mode[0]);
        }
        else if (pref == null)
        {
            pref = context.getSharedPreferences("Pref", MODE_PRIVATE);
        }
        if (editor == null)
        {
            editor = pref.edit();
        }
        return retval;
    }


    //----------------------------------------------------------------------------------------------

    /**
     * put boolean value on Shared Preferences
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean setSharedPreferencesValue(String key, boolean value)
    {
        boolean retval = true;
        editor.putBoolean(key, value);// Saving boolean - true/false
        commitSharedPreferencesValue();
        return retval;
    }

    /**
     * put integer value on Shared Preferences
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean setSharedPreferencesValue(String key, int value)
    {
        boolean retval = true;
        editor.putInt(key, value);// Saving integer
        commitSharedPreferencesValue();
        return retval;
    }

    /**
     * put float value on Shared Preferences
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean setSharedPreferencesValue(String key, float value)
    {
        boolean retval = true;
        editor.putFloat(key, value);// Saving float
        commitSharedPreferencesValue();
        return retval;
    }

    /**
     * put long value on Shared Preferences
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean setSharedPreferencesValue(String key, long value)
    {
        boolean retval = true;
        editor.putLong(key, value);// Saving long
        commitSharedPreferencesValue();
        return retval;
    }

    /**
     * put String value on Shared Preferences
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean setSharedPreferencesValue(String key, String value)
    {
        boolean retval = true;
        editor.putString(key, value);// Saving String
        commitSharedPreferencesValue();
        return retval;
    }


    //----------------------------------------------------------------------------------------------

    /**
     * get boolean value from Shared Preferences
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getSharedPreferencesValue(String key, boolean defaultValue)
    {
        boolean retval = false;
        retval = pref.getBoolean(key, defaultValue);// getting boolean
        return retval;
    }

    /**
     * get int  value from Shared Preferences
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getSharedPreferencesValue(String key, int defaultValue)
    {
        int retval = 0;
        retval = pref.getInt(key, defaultValue);// getting int
        return retval;
    }

    /**
     * get float  value from Shared Preferences
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static float getSharedPreferencesValue(String key, float defaultValue)
    {
        float retval = 0;
        retval = pref.getFloat(key, defaultValue);// getting float
        return retval;
    }

    /**
     * get long  value from Shared Preferences
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static long getSharedPreferencesValue(String key, long defaultValue)
    {
        long retval = 0;
        retval = pref.getLong(key, defaultValue);// getting long
        return retval;
    }

    /**
     * get String value from Shared Preferences
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getSharedPreferencesValue(String key, String defaultValue)
    {
        String retval = "";
        retval = pref.getString(key, defaultValue);// getting String
        return retval;
    }


    //----------------------------------------------------------------------------------------------

    /**
     * remove value from Shared Preferences
     *
     * @param key
     * @return
     */
    public static boolean removeSharedPreferencesValue(String key)
    {
        boolean retval = true;
        editor.remove(key);// remove value
        commitSharedPreferencesValue();
        return retval;
    }


    //----------------------------------------------------------------------------------------------

    /**
     * clear values from Shared Preferences
     *
     * @return
     */
    public static boolean clearSharedPreferencesValues()
    {
        boolean retval = true;
        editor.clear();// clear values
        commitSharedPreferencesValue();
        return retval;
    }


    //----------------------------------------------------------------------------------------------

    /**
     * commit Shared Preferences changes
     */
    public static void commitSharedPreferencesValue()
    {
        editor.commit(); // commit changes
    }
}
