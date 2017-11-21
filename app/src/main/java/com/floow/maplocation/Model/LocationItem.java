package com.floow.maplocation.Model;

/**
 * Created by milad on 11/20/2017.
 */

public class LocationItem
{

    private int PK_LocationItem;
    private int FK_Journey;
    private double lat;
    private double lng;

    public int getPK_LocationItem()
    {
        return PK_LocationItem;
    }

    public void setPK_LocationItem(int PK_LocationItem)
    {
        this.PK_LocationItem = PK_LocationItem;
    }

    public int getFK_Journey()
    {
        return FK_Journey;
    }

    public void setFK_Journey(int FK_Journey)
    {
        this.FK_Journey = FK_Journey;
    }

    public double getLat()
    {
        return lat;
    }

    public void setLat(double lat)
    {
        this.lat = lat;
    }

    public double getLng()
    {
        return lng;
    }

    public void setLng(double lng)
    {
        this.lng = lng;
    }

}
