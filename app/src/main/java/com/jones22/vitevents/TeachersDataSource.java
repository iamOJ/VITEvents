package com.jones22.vitevents;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rohan Chopra on 18/10/16.
 */

public class TeachersDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String[] allColumns = {
            SQLiteHelper.COLUMN_NAME,
            SQLiteHelper.COLUMN_EMAIL,
            SQLiteHelper.TEACHERS_COLUMN_EMPID,
            SQLiteHelper.COLUMN_PHONE
    };

    public TeachersDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Teachers createTeachers(String name,String email,String phone,String empId) {
        ContentValues values = new ContentValues();

        values.put(SQLiteHelper.TEACHERS_COLUMN_EMPID, empId);
        values.put(SQLiteHelper.COLUMN_NAME, name);
        values.put(SQLiteHelper.COLUMN_EMAIL, email);
        values.put(SQLiteHelper.COLUMN_PHONE, phone);


        long insertId = database.insert(SQLiteHelper.TABLE_TEACHERS, null,
                values);
        Cursor cursor = database.query(SQLiteHelper.TABLE_TEACHERS,
                allColumns, null, null,
                null, null, null);
        cursor.moveToFirst();
        Teachers newTeacher = cursorToTeacher(cursor);
        cursor.close();
        return newTeacher;
    }

    public void deleteTeacher(Teachers teachers) {
        String id = teachers.getEmpId();
        System.out.println("Teacher deleted with id: " + id);
        database.delete(SQLiteHelper.TABLE_TEACHERS, SQLiteHelper.TEACHERS_COLUMN_EMPID
                + " = " + id, null);
    }

    public List<Teachers> getAllTeachers() {
        List<Teachers> teachers = new ArrayList<Teachers>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_TEACHERS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Teachers teacher = cursorToTeacher(cursor);
            teachers.add(teacher);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return teachers;
    }



    private Teachers cursorToTeacher(Cursor cursor) {
        Teachers teachers = new Teachers();
        teachers.setEmpId(cursor.getString(0));
        teachers.setName(cursor.getString(1));
        teachers.setEmail(cursor.getString(2));
        teachers.setPhone(cursor.getString(3));
        return teachers;
    }
}
