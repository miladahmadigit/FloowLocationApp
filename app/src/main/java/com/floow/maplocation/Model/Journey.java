package com.floow.maplocation.Model;

/**
 * Created by milad on 11/20/2017.
 */

public class Journey
{

    private int PK_Journey;
    private String nameOfJourney;
    private String startTimeOfJourney;
    private String endTimeOfJourney;
    private int zoom;


    public int getPK_Journey()
    {
        return PK_Journey;
    }

    public void setPK_Journey(int PK_Journey)
    {
        this.PK_Journey = PK_Journey;
    }

    public String getNameOfJourney()
    {
        return nameOfJourney;
    }

    public void setNameOfJourney(String nameOfJourney)
    {
        this.nameOfJourney = nameOfJourney;
    }

    public String getStartTimeOfJourney()
    {
        return startTimeOfJourney;
    }

    public void setStartTimeOfJourney(String startTimeOfJourney)
    {
        this.startTimeOfJourney = startTimeOfJourney;
    }

    public String getEndTimeOfJourney()
    {
        return endTimeOfJourney;
    }

    public void setEndTimeOfJourney(String endTimeOfJourney)
    {
        this.endTimeOfJourney = endTimeOfJourney;
    }

    public int getZoom()
    {
        return zoom;
    }

    public void setZoom(int zoom)
    {
        this.zoom = zoom;
    }
}
