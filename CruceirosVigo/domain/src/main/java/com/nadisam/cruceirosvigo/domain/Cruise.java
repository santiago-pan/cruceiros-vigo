package com.nadisam.cruceirosvigo.domain;

/**
 * Created by santiagopan on 21/02/16.
 */
public class Cruise
{
    private String arrive;
    private String cruise;
    private int    length;
    private String arriveTime;
    private String departureTime;
    private String from;
    private String to;
    private String consignee;
    private String picture;
    private String other;

    public String getArrive()
    {
        return arrive;
    }

    public void setArrive(String arrive)
    {
        this.arrive = arrive;
    }

    public String getCruise()
    {
        return cruise;
    }

    public void setCruise(String cruise)
    {
        this.cruise = cruise;
    }

    public int getLength()
    {
        return length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }

    public String getArriveTime()
    {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime)
    {
        this.arriveTime = arriveTime;
    }

    public String getDepartureTime()
    {
        return departureTime;
    }

    public void setDepartureTime(String departureTime)
    {
        this.departureTime = departureTime;
    }

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    public String getTo()
    {
        return to;
    }

    public void setTo(String to)
    {
        this.to = to;
    }

    public String getConsignee()
    {
        return consignee;
    }

    public void setConsignee(String consignee)
    {
        this.consignee = consignee;
    }

    public String getPicture()
    {
        return picture;
    }

    public void setPicture(String picture)
    {
        this.picture = picture;
    }

    public String getOther()
    {
        return other;
    }

    public void setOther(String other)
    {
        this.other = other;
    }
}
