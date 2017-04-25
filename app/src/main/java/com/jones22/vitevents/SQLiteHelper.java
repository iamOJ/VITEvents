package com.jones22.vitevents;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_CLUBS = "clubs";
    public static final String TABLE_EVENTS = "events";
    public static final String TABLE_TEACHERS = "teachers";
    public static final String TABLE_STUDENTS = "students";

    private static final String DATABASE_NAME = "clubsandchapters.db";

    private static final int DATABASE_VERSION = 3;

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";

    public static final String EVENTS_COLUMN_EVENTID = "eventid";
    public static final String EVENTS_COLUMN_DATE = "date";
    public static final String EVENTS_COLUMN_VENUE = "venue";
    public static final String EVENTS_COLUMN_FEE= "fee";
    public static final String EVENTS_COLUMN_TIME= "time";
    public static final String EVENTS_COLUMN_DESC= "description";
    public static final String EVENTS_COLUMN_CLUBID= "clubid";

    public static final String STUDENTS_COLUMN_REGNO= "regno";

    public static final String TEACHERS_COLUMN_EMPID= "empid";

    public static final String CLUBS_COLUMN_CLUBID= "clubid";
    public static final String CLUBS_COLUMN_EMPID= "empid";


    // Database creation sql statement
    private static final String CREATE_CLUBS_TABLE = "create table "
            + TABLE_CLUBS + "( "
            + CLUBS_COLUMN_CLUBID + " text primary key , "
            + COLUMN_NAME  + " text not null , "
            + COLUMN_EMAIL + " text not null , "
            + COLUMN_PHONE + " text not null , "
            + CLUBS_COLUMN_EMPID + " text not null , "
            + "FOREIGN KEY("+CLUBS_COLUMN_EMPID+") REFERENCES "
            + TABLE_TEACHERS + "("+TEACHERS_COLUMN_EMPID+") "
            + ");";

    private static final String CREATE_STUDENTS_TABLE = "create table "
            + TABLE_STUDENTS + "( "
            + STUDENTS_COLUMN_REGNO + " text primary key, "
            + COLUMN_NAME  + " text not null, "
            + COLUMN_EMAIL + " text not null, "
            + COLUMN_PHONE + " text not null "
            + ");";

    private static final String CREATE_TEACHERS_TABLE = "create table "
            + TABLE_TEACHERS + "( "
            + TEACHERS_COLUMN_EMPID + " text primary key, "
            + COLUMN_NAME  + " text not null, "
            + COLUMN_EMAIL + " text not null, "
            + COLUMN_PHONE + " text not null "
            + ");";

    private static final String CREATE_EVENTS_TABLE = "create table "
            + TABLE_EVENTS + "( "
            + EVENTS_COLUMN_EVENTID + " text primary key, "
            + EVENTS_COLUMN_DATE + " text not null,"
            + EVENTS_COLUMN_VENUE + " text not null,"
            + EVENTS_COLUMN_FEE + " integer not null,"
            + EVENTS_COLUMN_TIME + " text not null,"
            + EVENTS_COLUMN_DESC + " text not null,"
            + COLUMN_NAME  + " text not null, "
            + EVENTS_COLUMN_CLUBID + " text not null, "
            + "FOREIGN KEY("+EVENTS_COLUMN_CLUBID+") REFERENCES "
            + TABLE_CLUBS + "("+CLUBS_COLUMN_CLUBID+") "
            + ");";




    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        database.execSQL(CREATE_CLUBS_TABLE);
        database.execSQL(CREATE_STUDENTS_TABLE);
        database.execSQL(CREATE_TEACHERS_TABLE);
        database.execSQL(CREATE_EVENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLUBS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEACHERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

}
