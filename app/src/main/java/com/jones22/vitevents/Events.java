package com.jones22.vitevents;

import android.graphics.Bitmap;
import android.util.Log;

public class Events {

    private String date;
    private String venue;
    private int fee;
    private String time;
    private String desc;
    private String clubId;
    private String name;
    private String eventId;
    private Bitmap bMap;
    private String clubName;

    public String getEventId() {
        return eventId;
    }

    public Bitmap getImage(){
        return bMap;
    }

    public void setImage(Bitmap image){
        //bMap.createBitmap(image);
        bMap = image;
        Log.i("setImage",image.toString());
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getClubName(){
        return clubName;
    }

    public void setClubName(String temp){
        clubName = temp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }
}
