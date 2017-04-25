package com.jones22.vitevents;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class StudentsDataSource {// Database fields
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String[] allColumns = {
            SQLiteHelper.COLUMN_NAME,
            SQLiteHelper.COLUMN_EMAIL,
            SQLiteHelper.STUDENTS_COLUMN_REGNO,
            SQLiteHelper.COLUMN_PHONE
    };

    public StudentsDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Students createtudents(String name,String email,String phone,String empId) {
        ContentValues values = new ContentValues();

        values.put(SQLiteHelper.STUDENTS_COLUMN_REGNO, empId);
        values.put(SQLiteHelper.COLUMN_NAME, name);
        values.put(SQLiteHelper.COLUMN_EMAIL, email);
        values.put(SQLiteHelper.COLUMN_PHONE, phone);


        long insertId = database.insert(SQLiteHelper.TABLE_STUDENTS, null,
                values);
        Cursor cursor = database.query(SQLiteHelper.TABLE_STUDENTS,
                allColumns, null, null,
                null, null, null);
        cursor.moveToFirst();
        Students newStudent = cursorToStudent(cursor);
        cursor.close();
        return newStudent;
    }

    public void deleteStudent(Students students) {
        String id = students.getRegNo();
        System.out.println("Teacher deleted with id: " + id);
        database.delete(SQLiteHelper.TABLE_STUDENTS, SQLiteHelper.STUDENTS_COLUMN_REGNO
                + " = " + id, null);
    }

    public List<Students> getAllStudents() {
        List<Students> students = new ArrayList<Students>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_TEACHERS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Students student = cursorToStudent(cursor);
            students.add(student);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return students;
    }

    private Students cursorToStudent(Cursor cursor) {
        Students students = new Students();
        students.setRegNo(cursor.getString(0));
        students.setName(cursor.getString(1));
        students.setEmail(cursor.getString(2));
        students.setPhone(cursor.getString(3));
        return students;
    }
}
