package com.jones22.vitevents;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

public class EventsDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String[] allColumns = {
            SQLiteHelper.EVENTS_COLUMN_EVENTID,
            SQLiteHelper.EVENTS_COLUMN_DATE,
            SQLiteHelper.EVENTS_COLUMN_VENUE,
            SQLiteHelper.EVENTS_COLUMN_FEE,
            SQLiteHelper.EVENTS_COLUMN_TIME,
            SQLiteHelper.EVENTS_COLUMN_DESC,
            SQLiteHelper.COLUMN_NAME,
            SQLiteHelper.EVENTS_COLUMN_CLUBID

    };

    public EventsDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Events createEvents(String name,String date,String venue,String time,String desc,int fee,String clubId,String eventId) {
        ContentValues values = new ContentValues();

        values.put(SQLiteHelper.EVENTS_COLUMN_EVENTID,eventId);
        values.put(SQLiteHelper.EVENTS_COLUMN_DATE, date);
        values.put(SQLiteHelper.EVENTS_COLUMN_VENUE, venue);
        values.put(SQLiteHelper.EVENTS_COLUMN_FEE, fee);
        values.put(SQLiteHelper.EVENTS_COLUMN_TIME, time);
        values.put(SQLiteHelper.EVENTS_COLUMN_DESC, desc);
        values.put(SQLiteHelper.COLUMN_NAME, name);
        values.put(SQLiteHelper.EVENTS_COLUMN_CLUBID, clubId);

        long insertId = database.insert(SQLiteHelper.TABLE_EVENTS, null,
                values);
        Cursor cursor = database.query(SQLiteHelper.TABLE_EVENTS,
                allColumns,null, null,
                null, null, null);
        cursor.moveToFirst();
        Events newEvent = cursorToEvent(cursor);
        cursor.close();
        return newEvent;
    }

    public void deleteEvents(Events event) {
        String id = event.getEventId();
        System.out.println("Event deleted with id: " + id);
        database.delete(SQLiteHelper.TABLE_EVENTS, SQLiteHelper.EVENTS_COLUMN_EVENTID
                + " = " + id, null);
    }

    public List<Events> getAllEvents(List<ParseObject> parseObjects){
        List<Events> events = new ArrayList<Events>();
        int len = parseObjects.size();
        ParseObject tempObj;
        Log.i("Total",String.valueOf(len));
        for(int i=0; i<len;i++){
            final Events event = new Events();
            tempObj = parseObjects.get(i);
            event.setEventId(String.valueOf(i));
            event.setClubId(String.valueOf(10*i + 1));
            event.setDate(String.valueOf(tempObj.get("date")).substring(0,10));
            event.setFee(Integer.parseInt(tempObj.get("fee").toString()));
            event.setName(tempObj.get("EventName").toString());
            Log.i("EventName",String.valueOf(i)+" + " + tempObj.get("EventName").toString());
            event.setDesc(tempObj.get("details").toString());
            event.setVenue(tempObj.get("venue").toString());
            event.setTime(tempObj.get("time").toString());
            event.setClubName(tempObj.get("Club").toString());
            ParseFile fileObject = tempObj.getParseFile("image");
            /*
            fileObject.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    if(e==null){
                        Bitmap bmp = BitmapFactory.decodeByteArray(data,0,data.length);
                        event.setImage(bmp);
                        Log.i("BMPImage",bmp.toString());
                        //Log.i("SetImage","Event.set");
                        Log.i("GetImage",event.getImage().toString());
                    }
                }
            });
            */
            events.add(event);
        }
        return events;
    }

    public List<Events> getAllEvents() {
        List<Events> events = new ArrayList<Events>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_EVENTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Events event = cursorToEvent(cursor);
            events.add(event);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return events;
    }

    private Events cursorToEvent(Cursor cursor) {
        Events events = new Events();
        events.setEventId(cursor.getString(0));
        events.setDate(cursor.getString(1));
        events.setVenue(cursor.getString(2));
        events.setFee(cursor.getInt(3));
        events.setTime(cursor.getString(4));
        events.setDesc(cursor.getString(5));
        events.setName(cursor.getString(6));
        events.setClubId(cursor.getString(7));
        return events;
    }
}
